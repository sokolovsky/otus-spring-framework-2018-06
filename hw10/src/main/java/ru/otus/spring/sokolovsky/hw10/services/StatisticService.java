package ru.otus.spring.sokolovsky.hw10.services;

import java.util.Map;

public interface StatisticService {

    EntityStatistic getGenreToBookStatistic();

    EntityStatistic getAuthorsToBookStatistic();

    class EntityStatistic {
        private Map<?, Number> map;

        EntityStatistic(Map<?, Number> map) {
            this.map = map;
        }

        public Number getValue(Object key) {
            return map.getOrDefault(key, 0);
        }
    }
}
