package ru.otus.spring.sokolovsky.hw05.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SqlFilter {

    private final List<Object> values;
    private String sql = "";

    SqlFilter(Map<String, Object> filter) {
        values = new ArrayList<>();
        if (filter.size() > 0) {
            sql = filter.entrySet().stream().map((entry) -> {
                values.add(entry.getValue());
                return entry.getKey() + "=?";
            }).collect(Collectors.joining(" & "));
        }
    }

    public String getSql() {
        return sql;
    }

    public Object[] getValues() {
        return values.toArray();
    }
}
