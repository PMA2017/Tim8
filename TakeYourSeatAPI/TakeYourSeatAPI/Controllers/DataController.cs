using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using log4net;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using TakeYourSeatAPI.Business;

namespace TakeYourSeatAPI.Controllers
{
    public class DataController : ApiController
    {
        private readonly DataService _dataService = new DataService();
        private readonly ILog _logger = LogManager.GetLogger("APILogger");

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
    }
}
