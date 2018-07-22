package ru.otus.spring.sokolovsky.hw05.jdbcdao;

import java.util.*;
import java.util.stream.Collectors;

public class SqlSelectBuilder {
    private String tableName;
    private Set<String> wheres = new HashSet<>();
    private List<String> joins = new ArrayList<>();
    private String alias;
    private int limit = 0;
    private String select;

    SqlSelectBuilder(String tableName) {
        this.tableName = tableName;
    }

    SqlSelectBuilder useAlias(String alias) {
        this.alias = alias;
        return this;
    }

    SqlSelectBuilder join(String join) {
        joins.add(join);
        return this;
    }

    SqlSelectBuilder useFilterFields(Collection<String> fields) {
        if (fields.size() > 0) {
            wheres.add(getFilterString(fields));
        }
        return this;
    }

    SqlSelectBuilder useFilterFields(String... fields) {
        useFilterFields(Arrays.asList(fields));
        return this;
    }

    SqlSelectBuilder limit(int value) {
        limit = value;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("select ");

        sb.append(Objects.requireNonNullElse(select, " * "));

        sb.append(" from `").append(tableName).append("`");

        if (alias != null) {
            sb.append(" ").append(alias).append(" ");
        }
        if (joins.size() > 0) {
            joins.forEach(s -> sb.append(" ").append(s).append(" "));
        }
        if (wheres.size() > 0) {
            sb.append(" where ");
            sb.append(String.join(" and ", wheres));
        }
        if (limit != 0) {
            sb.append(" limit " + limit);
        }
        return sb.toString();
    }

    private String getFilterString(Collection<String> fields) {
        if (fields.size() == 0) {
            return "";
        }
        return fields.stream()
                .map((field) -> field + "=:" + field)
                .collect(Collectors.joining(" and "));
    }

    SqlSelectBuilder select(String s) {
        select = s;
        return this;
    }

    SqlSelectBuilder addWhere(String s) {
        wheres.add(s);
        return this;
    }
}
