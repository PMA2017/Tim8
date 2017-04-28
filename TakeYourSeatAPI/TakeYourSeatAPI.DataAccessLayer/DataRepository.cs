using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Text;
using System.Threading.Tasks;
using log4net;

namespace TakeYourSeatAPI.DataAccessLayer
{
    public class DataRepository : IDisposable
    {
        private readonly SqlConnection _connection;
        private readonly QueryProvider _queryProvider = new QueryProvider();
        private readonly ILog _logger = LogManager.GetLogger("APILogger");

        public DataRepository()
        {
            try
            {
                _connection = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
                _connection.Open();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
            }
        }

        public List<string> GetColumnNames(string tableName)
        {
            var columnNames = new List<string>();

            var query = _queryProvider.GetSelectColumnNamesQuery(tableName);
            var sqlCommand = new SqlCommand(query, _connection);
            try
            {
                var reader = sqlCommand.ExecuteReader();
                while (reader.Read())
                {
                    columnNames.Add(reader.GetString(0));
                }
                reader.Close();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
            }

            return columnNames;
        }

        public List<Dictionary<string, object>> GetAll(string tableName)
        {
            var columnNames = GetColumnNames(tableName);
            var query = _queryProvider.GetSelectAllQuery(tableName, columnNames);
            var sqlCommand = new SqlCommand(query, _connection);
            var retVal = new List<Dictionary<string, object>>();

            try
            {
                var reader = sqlCommand.ExecuteReader();
                while (reader.Read())
                {
                    var row = columnNames.ToDictionary(column => column, column => reader[column]);
                    retVal.Add(row);
                }
                reader.Close();
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);                
            }

            return retVal;
        }

        public void Dispose()
        {
            _connection.Dispose();
        }
    }
}
