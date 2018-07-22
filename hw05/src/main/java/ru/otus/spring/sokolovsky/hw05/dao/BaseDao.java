package ru.otus.spring.sokolovsky.hw05.dao;

abstract public class BaseDao {

    abstract String getTableName();

    SqlSelectBuilder createSelectBuilder() {
        return new SqlSelectBuilder(getTableName());
    }

    interface ColumnNameTranslator {
        String translate(String column);
    }

    static class IdleColumnNameTranslator implements ColumnNameTranslator {

        @Override
        public String translate(String column) {
            return column;
        }
    }

    static class PrefixCutTranslator implements ColumnNameTranslator {

        private String prefix;

        PrefixCutTranslator(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public String translate(String column) {
            return column.replace(prefix, "");
        }
    }

    static abstract class RowMapper implements org.springframework.jdbc.core.RowMapper {
        private ColumnNameTranslator columnNameTranslator;
        RowMapper(ColumnNameTranslator columnNameTranslator) {
            this.columnNameTranslator = columnNameTranslator;
        }

        RowMapper() {
            this(new IdleColumnNameTranslator());
        }

        String getColumnId(String column) {
            return columnNameTranslator.translate(column);
        }
    }
}
