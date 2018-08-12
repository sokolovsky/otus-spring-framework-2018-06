package ru.otus.spring.sokolovsky.hw08.changelogs;

import com.github.mongobee.Mongobee;
import com.github.mongobee.exception.MongobeeException;
import com.mongodb.client.MongoDatabase;

public class SeedCreatorImpl implements SeedCreator {

    private Mongobee driver;
    private MongoDatabase database;

    public final String COLLECTION_LOG_NAME = "changelog_log";
    public final String COLLECTION_LOCK_NAME = "changelog_lock";


    public SeedCreatorImpl(Mongobee mongobee, MongoDatabase mongoDatabase) {
        driver = mongobee;
        database = mongoDatabase;
        driver.setChangelogCollectionName(COLLECTION_LOG_NAME);
        driver.setLockCollectionName(COLLECTION_LOCK_NAME);
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
