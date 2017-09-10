using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;
using log4net;
using log4net.Layout;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using TakeYourSeatAPI.Business;
using TakeYourSeatAPI.Business.Model;
using TakeYourSeatAPI.Model;

namespace TakeYourSeatAPI.Controllers
{
    public class DataController : ApiController
    {
        private readonly DataService _dataService = new DataService();
        private readonly ILog _logger = LogManager.GetLogger("APILogger");
        private readonly FCMService _fcmService = new FCMService();

        [HttpGet]
        [Route("api/Data/GetAll/{tableName}")]
        public IHttpActionResult GetAll(string tableName)
        {
            try
            {
                var retVal = _dataService.GetAll(tableName);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpGet]
        [Route("api/Data/GetBy/{tableName}/{columnName}/{value}")]
        public IHttpActionResult GetBy(string tableName, string columnName, string value)
        {
            try
            {
                var retVal = _dataService.GetBy(tableName, columnName, value);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPut]
        [Route("api/Data/GetByMany/{tableName}/{columnName}")]
        public IHttpActionResult GetByMany(string tableName, string columnName, JObject values)
        {
            var jsonArray = values.First.First.ToString();
            var valuesList = JsonConvert.DeserializeObject<List<string>>(jsonArray);

            try
            {
                var retVal = _dataService.GetByMany(tableName, columnName, valuesList);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPut]
        [Route("api/Data/Insert/{tableName}")]
        public IHttpActionResult Insert(string tableName, JObject jsonData)
        {
            try
            {
                var columnsValues = JsonConvert.DeserializeObject<Dictionary<string, string>>(jsonData.ToString());
                var retVal = _dataService.Insert(tableName, columnsValues);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPut]
        [Route("api/Data/Update/{tableName}/{columnName}/{value}")]
        public IHttpActionResult Update(string tableName, string columnName, string value, JObject jsonData)
        {
            try
            {
                var columnsValues = JsonConvert.DeserializeObject<Dictionary<string, string>>(jsonData.ToString());
                var retVal = _dataService.Update(tableName, columnsValues, columnName, value);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPut]
        [Route("api/Data/Delete/{tableName}/{columnName}/{value}")]
        public IHttpActionResult Delete(string tableName, string columnName, string value)
        {
            try
            {
                var retVal = _dataService.Delete(tableName, columnName, value);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpGet]
        [Route("api/Data/GetReservations/{restaurantId}")]
        public IHttpActionResult GetReservations(int restaurantId)
        {
            try
            {
                var retVal = _dataService.GetReservations(restaurantId);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpGet]
        [Route("api/Data/GetFriends/{value}")]
        public IHttpActionResult GetFrinds(string value)
        {
            try
            {
                var retVal = _dataService.GetFriends(value);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpGet]
        [Route("api/Data/GetNonFriends/{value}")]
        public IHttpActionResult GetNonFrinds(string value)
        {
            try
            {
                var retVal = _dataService.GetNonFriends(value);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPut]
        [Route("api/Data/DeleteFriendship/{userId}/{friendId}")]
        public IHttpActionResult DeleteFriendship(int userId, int friendId)
        {
            try
            {
                var retVal = _dataService.DeleteFriendship(userId, friendId);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPut]
        [Route("api/Data/AddFriendship/{userId}/{friendId}")]
        public IHttpActionResult AddFriendship(string userId, string friendId)
        {
            try
            {
                var retVal = _dataService.AddFriendship(userId, friendId);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPost]
        [Route("api/Data/FinishReservation")]
        public IHttpActionResult FinishReservation(CompleteReservation data)
        {
            try
            {
                _logger.Info("Reservation data : " + data.Reservation);
                _logger.Info("Number of tables: " + data.TableIds.Count.ToString());
                _logger.Info("Number of friends: " + data.FriendIds.Count.ToString());

                _logger.Info("All data: " + JsonConvert.SerializeObject(data));

                var reservation = JsonConvert.DeserializeObject<Dictionary<string, string>>(data.Reservation);
                reservation.Remove("Id");

                var retVal = _dataService.CompleteReservation(reservation, data.FriendIds, data.TableIds);

                SendInvitations(data, retVal.ToString());

                return Ok(retVal);

            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        private void SendInvitations(CompleteReservation data, string reservationId)
        {
            var reservation = JsonConvert.DeserializeObject<Dictionary<string, string>>(data.Reservation);
            string sender;
            string restaurantId;
            reservation.TryGetValue("User", out sender);
            reservation.TryGetValue("RestaurantId", out restaurantId);

            var senderInfo = _dataService.GetBy("User", "Id", sender) as List<Dictionary<string, object>>;
            object senderFirstName;
            object senderLastName;
            senderInfo.First().TryGetValue("Name", out senderFirstName);
            senderInfo.First().TryGetValue("LastName", out senderLastName);

            var restaurantInfo = _dataService.GetBy("Restaurant", "Id", restaurantId) as List<Dictionary<string, object>>;
            object restaurantName;
            restaurantInfo.First().TryGetValue("Name", out restaurantName);

            var reservationInfo = _dataService.GetBy("Reservation", "Id", reservationId) as List<Dictionary<string, object>>;
            object startDate;
            object endDate;
            reservationInfo.First().TryGetValue("StartDate", out startDate);
            reservationInfo.First().TryGetValue("EndDate", out endDate);

            foreach(var invitedFriendId in data.FriendIds) {
                var userResults = _dataService.GetBy("User", "Id", invitedFriendId) as List<Dictionary<string, object>>;
                object friendToken;
                userResults.First().TryGetValue("Token", out friendToken);

                var objNotification = new
                {
                    to = friendToken,
                    notification = new
                    {
                        title = "New Invitation",
                        click_action = "takeyourseat.activities.InviteDialogActivity",
                        sound = "default",
                    },
                    data = new
                    {
                        senderFullName = senderFirstName.ToString() + " " + senderLastName.ToString(),
                        restaurantName = restaurantName,
                        startDate = startDate,
                        endDate = endDate,
                        reservationId = reservationId, 
                        friendId = invitedFriendId
                    }
                };
                SendNotification(objNotification);
            }

        }

        [HttpGet]
        [Route("api/Data/GetRestaurantsWithLocation")]
        public IHttpActionResult GetRestaurantsWithLocation()
        {
            try
            {
                var retVal = _dataService.GetAllRestaurantsWithLocation();
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpPut]
        [Route("api/Data/UpdateInvitationStatus/{reservationId}/{userId}/")]
        public IHttpActionResult UpdateInvitationStatus(string reservationId, string userId)
        {
            try
            {
                var retVal = _dataService.UpdateInvitationStatus(reservationId, userId);
                return Ok(retVal);
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                return ResponseMessage(Request.CreateErrorResponse(HttpStatusCode.InternalServerError, ex.Message));
            }
        }

        [HttpGet]
        [Route("api/Data/SendFriendRequest/{userId}/{friendId}")]
        public bool SendFriendRequest(string userId, string friendId)
        {
            var userResults = _dataService.GetBy("User", "Id", userId) as List<Dictionary<string, object>>;
            var friendResult = _dataService.GetBy("User", "Id", friendId) as List<Dictionary<string, object>>;

            object userName;
            object userLastName;
            object friendToken;
            userResults.First().TryGetValue("Name", out userName);
            userResults.First().TryGetValue("LastName", out userLastName);
            friendResult.First().TryGetValue("Token", out friendToken);


            var objNotification = new
            {
                to = friendToken,
                notification = new
                {
                    title = "New Friend Request",
                    //body = "Accept as a friend?",
                    click_action = "takeyourseat.activities.DialogActivity",
                    sound = "default",
                },
                data = new
                {
                    userId = userId,
                    friendId = friendId,
                    userName = userName.ToString(),
                    userLastName = userLastName.ToString()
                }
            };
            return SendNotification(objNotification);
        }

        public bool SendNotification(object objNotification)
        {
            bool retVal = false;

            WebRequest tRequest = WebRequest.Create("https://fcm.googleapis.com/fcm/send");
            string serverKey = "AAAAIv__qpQ:APA91bG758gNXlh_C-nnqU4tozSUbNXCmEnvUZyRrbBJed-ll2A98zzA3kJuomeLhHrCM2pFvpaf5IxgITzX4pzdEGgF0_adH4qWjBXqlGKUxw7e6NLYVCpDJY717PMuTmD4m6mutuUa";
            var senderId = "150323833492";
            tRequest.Method = "post";
            tRequest.ContentType = "application/json";

            string jsonNotificationFormat = Newtonsoft.Json.JsonConvert.SerializeObject(objNotification);

            Byte[] byteArray = Encoding.UTF8.GetBytes(jsonNotificationFormat);
            tRequest.Headers.Add(string.Format("Authorization: key={0}", serverKey));
            tRequest.Headers.Add(string.Format("Sender: id={0}", senderId));
            tRequest.ContentLength = byteArray.Length;
            tRequest.ContentType = "application/json";
            using (Stream dataStream = tRequest.GetRequestStream())
            {
                dataStream.Write(byteArray, 0, byteArray.Length);

                using (WebResponse tResponse = tRequest.GetResponse())
                {
                    using (Stream dataStreamResponse = tResponse.GetResponseStream())
                    {
                        using (StreamReader tReader = new StreamReader(dataStreamResponse))
                        {
                            String responseFromFirebaseServer = tReader.ReadToEnd();

                            FCMResponse response = Newtonsoft.Json.JsonConvert.DeserializeObject<FCMResponse>(responseFromFirebaseServer);
                            if (response.success == 1)
                            {
                                _logger.Info("Notification sent successfully");
                                retVal = true;
                            }
                            else if (response.failure == 1)
                            {
                                _logger.Error(string.Format("Error sent from FCM server, after sending request : {0} , for following device info: {1}", responseFromFirebaseServer, jsonNotificationFormat));
                                retVal = false;
                            }

                        }
                    }

                }
            }
            return retVal;
        }

    }
}
