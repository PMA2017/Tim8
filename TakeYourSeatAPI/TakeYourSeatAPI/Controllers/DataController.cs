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
                    userLastName = userName.ToString()
                }
            };
            return SendNotification(objNotification);
        }

        public bool SendNotification(object objNotification)
        {
            bool retVal = false;

            WebRequest tRequest = WebRequest.Create("https://fcm.googleapis.com/fcm/send");
            string serverKey = "AAAAIv__qpQ:APA91bHxAi6lP6K6sQwzXJQAm8E-VG5XTiLQIGLVYioxoYQuW-KAkEAqBytOybbDuF2lhSwv1CnAMZTHfSE92zD7IdHoz3zuhXUaS9QGmQSFsATVq62rpNX5CCnQsrD3vbu-uostQnkc";
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
