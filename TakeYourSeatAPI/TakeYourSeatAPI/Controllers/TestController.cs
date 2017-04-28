using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using log4net;
using Newtonsoft.Json;
using TakeYourSeatAPI.DataAccessLayer;

namespace TakeYourSeatAPI.Controllers
{
    public class TestController : ApiController
    {
        [HttpGet]
        public string RunTest()
        {
            List<Dictionary<string, object>> retVal;
            using (var dataRepository = new DataRepository())
            {
                retVal = dataRepository.GetAll("User");
            }
            return JsonConvert.SerializeObject(retVal);
        }
    }
}
