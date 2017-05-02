using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TakeYourSeatAPI.DataAccessLayer
{
    public class QueryProvider
    {
        public string GetSelectColumnNamesQuery(string tableName)
        {
            return string.Format(Resources.Queries.SelectColumnNames, tableName);
        }

        public string GetSelectAllQuery(string tableName, List<string> columnNames)
        {
            var columnsPart = string.Join(", ", columnNames.ToArray());
            return string.Format(Resources.Queries.SelectAll, columnsPart, tableName);
        }

        public string GetSelectByColumnValueQuery(string tableName, List<string> columnNames, string columnName, string value)
        {
            var columnsPart = string.Join(", ", columnNames.ToArray());
            return string.Format(Resources.Queries.SelectByColumnValue, columnsPart, tableName, columnName, "'" + value + "'");
        }
    }
}
