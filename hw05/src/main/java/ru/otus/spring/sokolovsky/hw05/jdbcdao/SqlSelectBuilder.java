package ru.otus.spring.sokolovsky.hw05.jdbcdao;

import java.util.*;
import java.util.stream.Collectors;

public class SqlSelectBuilder {
    private String tableName;
    private Set<String> filterFields = new HashSet<>();
    private List<String> joins = new ArrayList<>();
    private String alias;
    private int limit;

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
        filterFields.addAll(fields);
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
        StringBuilder sb = new StringBuilder("select * from `" + tableName + "`");
        if (alias != null) {
            sb.append(" as ").append(alias).append(" ");
        }
        if (joins.size() > 0) {
            joins.forEach(s -> sb.append(" ").append(s).append(" "));
        }
        String filter = getFilterString();
        if (!filter.equals("")) {
            sb.append(" where ");
            sb.append(filter);
        }
        return sb.toString();
    }

    private String getFilterString() {
        if (filterFields.size() == 0) {
            return "";
        }
        return filterFields.stream()
                .map((field) -> field + "=:" + field)
                .collect(Collectors.joining(" & "));
    }
}
