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

        public object GetBy(string tableName, string columnName, string value)
        {
            return _dataRepository.GetBy(tableName, columnName, value);
        }

        public object Insert(string tableName, Dictionary<string, string> columnsValues)
        {
            var columnNames = columnsValues.Keys.ToList();
            var values = columnsValues.Values.ToList();
            return _dataRepository.Insert(tableName, columnNames, values);
        } 
    }
}
