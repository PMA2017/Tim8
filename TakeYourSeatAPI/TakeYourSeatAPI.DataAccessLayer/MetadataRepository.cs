using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using log4net;

namespace TakeYourSeatAPI.DataAccessLayer
{
    public class MetadataRepository : IDisposable
    {
        private readonly SqlConnection _connection;
        private readonly QueryProvider _queryProvider = new QueryProvider();
        private readonly ILog _logger = LogManager.GetLogger("APILogger");

        public MetadataRepository()
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
            }
            catch (Exception ex)
            {
                _logger.Error(ex.Message);
            }

            return columnNames;
        }

        public void Dispose()
        {
            _connection.Close();
        }
    }
}
