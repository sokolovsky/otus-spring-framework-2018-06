package ru.otus.spring.sokolovsky.hw13.services;

import lombok.Data;
import lombok.Getter;

import java.util.Map;

public interface StatisticService {

    EntityStatistic getGenreToBookStatistic();

    EntityStatistic getAuthorsToBookStatistic();

    @Data
    class EntityStatistic {
        @Getter
        private Map<?, Number> map;

        public EntityStatistic(Map<?, Number> map) {
            this.map = map;
        }

        public Number getValue(Object key) {
            return map.getOrDefault(key, 0);
        }
    }
}
