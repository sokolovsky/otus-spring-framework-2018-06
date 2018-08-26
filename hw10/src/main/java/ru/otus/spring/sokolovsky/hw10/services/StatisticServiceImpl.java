package ru.otus.spring.sokolovsky.hw10.services;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import ru.otus.spring.sokolovsky.hw10.domain.Author;
import ru.otus.spring.sokolovsky.hw10.domain.Genre;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class StatisticServiceImpl implements StatisticService {

    interface ResultDbItem {
        String generateId();
        Number getCount();
    }

    static abstract class ResultDbItemImpl implements ResultDbItem {
        int count;

        @Override
        public Number getCount() {
            return count;
        }
    }

    static class ResultGenre extends ResultDbItemImpl {
        Genre model;
        @Override
        public String generateId() {
            return model.getId();
        }
    }

    static class ResultAuthor extends ResultDbItemImpl {
        Author model;

        @Override
        public String generateId() {
            return model.getId();
        }
    }



    private MongoOperations mongoOperations;

    public StatisticServiceImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    private EntityStatistic getContextAggregationResult(String field, Class<? extends ResultDbItem> classId) {
        Aggregation aggregation = newAggregation(
                unwind(field),
                group(field).count().as("count"),
                project(field, "count").and("_id").as("model")
        );
        List<? extends ResultDbItem> list = mongoOperations.aggregate(aggregation, "books", classId).getMappedResults();
        Map<String, Number> map = list.stream().collect(Collectors.toMap(ResultDbItem::generateId, ResultDbItem::getCount));
        return new EntityStatistic(map);
    }

    @Override
    public EntityStatistic getGenreToBookStatistic() {
        return getContextAggregationResult("genres", ResultGenre.class);
    }

    @Override
    public EntityStatistic getAuthorsToBookStatistic() {
        return getContextAggregationResult("authors", ResultAuthor.class);
    }
}
