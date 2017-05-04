using System;
using System.Collections;
using System.Collections.Generic;
using System.Drawing;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using log4net;
using Newtonsoft.Json;
using TakeYourSeatAPI.Business;
using TakeYourSeatAPI.Models;

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

        [HttpPost]
        [Route("api/Data/Insert")]
        public IHttpActionResult Post(InsertDto data)
        {
            try
            {
                var tableName = data.TableName;
                var columnsValues = JsonConvert.DeserializeObject<Dictionary<string, string>>(data.Data);
                var retVal = _dataService.Insert(tableName, columnsValues);
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
