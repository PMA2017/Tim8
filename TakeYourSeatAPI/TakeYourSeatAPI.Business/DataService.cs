using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TakeYourSeatAPI.DataAccessLayer;

namespace TakeYourSeatAPI.Business
{
    public class DataService
    {
        private readonly DataRepository _dataRepository = new DataRepository();

        public object GetAll(string tableName)
        {
            return _dataRepository.GetAll(tableName);
        }

        public object GetByColumnValue(string tableName, string columnName, string value)
        {
            return _dataRepository.GetByColumnValue(tableName, columnName, value);
        } 
    }
}
