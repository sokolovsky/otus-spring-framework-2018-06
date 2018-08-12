package ru.otus.spring.sokolovsky.hw08.changelogs;

import com.github.mongobee.Mongobee;
import com.github.mongobee.exception.MongobeeException;
import com.mongodb.client.MongoDatabase;

public class SeedCreatorImpl implements SeedCreator {

    private Mongobee driver;
    private MongoDatabase database;

    public SeedCreatorImpl(Mongobee mongobee, MongoDatabase mongoDatabase) {
        driver = mongobee;
        database = mongoDatabase;
    }

    @Override
    public void create() {
        database.drop();
        try {
            driver.execute();
        } catch (MongobeeException e) {
            e.printStackTrace();
        }
    }
}
