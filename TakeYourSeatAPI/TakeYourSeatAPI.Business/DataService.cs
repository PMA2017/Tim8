using System.Collections.Generic;
using System.Linq;
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

        public bool Update(string tableName, Dictionary<string, string> columnsValues, string columnName, string value)
        {
            return _dataRepository.Update(tableName, columnsValues, columnName, value);
        }

        public bool Delete(string tableName, string columnName, string value)
        {
            return _dataRepository.Delete(tableName, columnName, value);
        }

        public object GetByMany(string tableName, string columnName, List<string> valuesList)
        {
            return _dataRepository.GetByMany(tableName, columnName, valuesList);
        }

        public object GetReservations(int restaurantId)
        {
            return _dataRepository.GetReservations(restaurantId);
        }
    }
}
