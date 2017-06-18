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
            var properColumnValues = columnsValues.Where(c => c.Key != "Id").ToDictionary(t => t.Key, t => t.Value);
            return _dataRepository.Update(tableName, properColumnValues, columnName, value);
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

        public object GetFriends(string userId)
        {
            var connections = _dataRepository.GetBy("Friends", "User", userId);
            var values = new List<string>();
            foreach(var c in connections)
            {
                object value;
                c.TryGetValue("Friend", out value);
                if (value != null) values.Add(value.ToString());
            }
            return GetByMany("User", "Id", values);
        }

        public object GetNonFriends(string userId)
        {
            var connections = _dataRepository.GetBy("Friends", "User", userId);
            if (connections.Count == 0)
            {
                return _dataRepository.GetAll("User");
            }
            var values = new List<string>();
            foreach (var c in connections)
            {
                object value;
                c.TryGetValue("Friend", out value);
                if (value != null) values.Add(value.ToString());
            }
            return _dataRepository.GetByNotMany("User", "Id", values);
        }

        public bool DeleteFriendship(int userId, int friendId)
        {
            return _dataRepository.DeleteFriendship(userId, friendId);
        }

        public bool AddFriendship(string userId, string friendId)
        {
            var firstPair = new Dictionary<string, string>
            {
                {"User", userId},
                {"Friend", friendId }
            };
            var secondPair = new Dictionary<string, string>
            {
                {"User", friendId},
                {"Friend", userId }
            };

            var firstResult = _dataRepository.Insert("Friends", firstPair.Keys.ToList(), firstPair.Values.ToList());
            var secondResult = _dataRepository.Insert("Friends", secondPair.Keys.ToList(), secondPair.Values.ToList());

            return firstResult.ToString() != "-1" && secondResult.ToString() != "-1";
        }
    }
}
