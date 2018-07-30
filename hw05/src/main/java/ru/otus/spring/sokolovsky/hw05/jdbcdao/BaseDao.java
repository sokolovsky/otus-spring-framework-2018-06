package ru.otus.spring.sokolovsky.hw05.jdbcdao;

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

    static class PrefixAppendTranslator implements ColumnNameTranslator {

        private String prefix;

        PrefixAppendTranslator(String prefix) {
            this.prefix = prefix;
        }

        @Override
        public String translate(String column) {
            return prefix+column;
        }
    }

    static abstract class RowMapper<E> implements org.springframework.jdbc.core.RowMapper<E> {
        private ColumnNameTranslator columnNameTranslator;
        RowMapper(ColumnNameTranslator columnNameTranslator) {
            this.columnNameTranslator = columnNameTranslator;
        }

        RowMapper() {
            this(new IdleColumnNameTranslator());
        }

        ColumnNameTranslator getColumnNameTranslator() {
            return columnNameTranslator;
        }

        String getColumnId(String column) {
            return getColumnNameTranslator().translate(column);
        }
    }
}
