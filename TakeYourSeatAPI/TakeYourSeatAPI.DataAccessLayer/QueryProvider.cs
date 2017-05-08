using System.Collections.Generic;
using System.Linq;

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
            var columnsPart = string.Join(", ", columnNames.Select(c => $"[{c}]"));
            return string.Format(Resources.Queries.SelectAll, columnsPart, tableName);
        }

        public string GetSelectWhereQuery(string tableName, List<string> columnNames, string columnName, string value)
        {
            var columnsPart = string.Join(", ", columnNames.Select(c => $"[{c}]"));
            return string.Format(Resources.Queries.SelectWhere, columnsPart, tableName, columnName, value);
        }

        public string GetInsertQuery(string tableName, List<string> columnNames, List<string> values)
        {
            var columnsPart = string.Join(", ", columnNames.Select(c => $"[{c}]"));
            var valuesPart = string.Join(", ", values.Select(v => $"'{v}'"));
            return string.Format(Resources.Queries.Insert, tableName, columnsPart, valuesPart);
        }

        public string GetUpdateQuery(string tableName, Dictionary<string, string> columnsValues, string columnName, string value)
        {
            var columnValuesPart = string.Join(", ", columnsValues.Select(c => $"[{c.Key}] = '{c.Value}'"));
            return string.Format(Resources.Queries.Update, tableName, columnValuesPart, columnName, value);
        }

        public string GetDeleteQuery(string tableName, string columnName, string value)
        {
            return string.Format(Resources.Queries.Delete, tableName, columnName, value);
        }
    }
}
