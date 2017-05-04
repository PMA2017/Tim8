using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using System.Linq;
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
            var query = _queryProvider.GetSelectColumnNamesQuery(tableName);
            var sqlCommand = new SqlCommand(query, _connection);
            try
            {
                var columnNames = new List<string>();
                var reader = sqlCommand.ExecuteReader();
                while (reader.Read())
                {
                    columnNames.Add(reader.GetString(0));
                }
                reader.Close();
                return columnNames;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                throw;
            }
        }

        public List<Dictionary<string, object>> GetAll(string tableName)
        {
            var columnNames = GetColumnNames(tableName);
            var query = _queryProvider.GetSelectAllQuery(tableName, columnNames);
            var sqlCommand = new SqlCommand(query, _connection);

            try
            {
                var retVal = new List<Dictionary<string, object>>();
                var reader = sqlCommand.ExecuteReader();
                while (reader.Read())
                {
                    var row = columnNames.ToDictionary(column => column, column => reader[column]);
                    retVal.Add(row);
                }
                reader.Close();
                return retVal;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                throw;
            }
        }

        public List<Dictionary<string, object>> GetBy(string tableName, string columnName, string value)
        {
            var columnNames = GetColumnNames(tableName);
            var query = _queryProvider.GetSelectWhereQuery(tableName, columnNames, columnName, value);
            var sqlCommand = new SqlCommand(query, _connection);

            try
            {
                var retVal = new List<Dictionary<string, object>>();
                var reader = sqlCommand.ExecuteReader();
                while (reader.Read())
                {
                    var row = columnNames.ToDictionary(column => column, column => reader[column]);
                    retVal.Add(row);
                }
                reader.Close();
                return retVal;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                throw;
            }
        }

        public object Insert(string tableName, List<string> columnNames, List<string> values)
        {
            var query = _queryProvider.GetInsertQuery(tableName, columnNames, values);
            var sqlCommand = new SqlCommand(query, _connection);

            try
            {
                var retVal = sqlCommand.ExecuteScalar();
                return retVal;
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
                throw;
            }
        }

        public void Dispose()
        {
            _connection.Dispose();
        }
    }
}
