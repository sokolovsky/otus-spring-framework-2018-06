package ru.otus.spring.sokolovsky.hw08.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

@ChangeLog
public class SeedTestDataChangeLog {

    @ChangeSet(author = "user", id = "eraseAllData", order = "0001")
    public void eraseAll(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(author = "user", id = "createInitGenres", order = "0010")
    public void createGenres(MongoDatabase mongoDatabase) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("genres");
        String[] genres = {
                "Классическая проза",
                "Литература 19 века",
                "Русская классика",
                "Древнерусская литература"
        };

        Arrays.stream(genres).forEach(
                n -> collection.insertOne(new Document().append("name", n))
        );
    }

    @ChangeSet(author = "user", id = "registerAuthors", order = "0020")
    public void registerAuthors(MongoDatabase mongoDatabase) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("authors");
        String[] authors = {
                "Фёдор Тютчев",
                "Николай Гоголь",
                "Александр Пушкин"
        };

        Arrays.stream(authors).forEach(
                n -> collection.insertOne(new Document().append("name", n))
        );
    }
}
