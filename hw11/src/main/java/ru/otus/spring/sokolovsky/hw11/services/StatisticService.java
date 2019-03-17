package ru.otus.spring.sokolovsky.hw11.services;

import lombok.Data;
import lombok.Getter;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface StatisticService {

    Mono<EntityStatistic> getGenreToBookStatistic();

    Mono<EntityStatistic> getAuthorsToBookStatistic();

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
