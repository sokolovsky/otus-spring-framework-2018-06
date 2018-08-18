package ru.otus.spring.sokolovsky.hw09.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.DBRef;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

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
            n -> collection.insertOne(new Document().append("title", n))
        );

        collection.createIndex(new Document("title", 1));
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
        collection.createIndex(new Document("name", 1));
    }

    @ChangeSet(author = "user", id = "registerBooks", order = "0030")
    public void registerBooks(MongoDatabase mongoDatabase) {
        MongoCollection<Document> bookCollection = mongoDatabase.getCollection("books");
        MongoCollection<Document> genreCollection = mongoDatabase.getCollection("genres");
        MongoCollection<Document> authorCollection = mongoDatabase.getCollection("authors");

        String[][] books = {
                {"Повести Белкина (сборник)", "978-5-91045-983-4"},
                {"Евгений Онегин", "978-5-389-09531-1"},
                {"Сказка о царе Салтане", "978-5-9930-2218-5"},

                {"Федор Тютчев: Стихи", "978-5-9905833-8-2"},
                {"Федор Тютчев: Стихи детям", "978-5-99058313-8-2"},

                {"Вечера на хуторе близ Диканьки", "978-5-17-099707-7"},
                {"Петербургские повести", "978-5-91045-970-4"},
        };

        String[][] booksToAuthors = {
                {"978-5-91045-983-4", "Александр Пушкин"},
                {"978-5-389-09531-1", "Александр Пушкин"},
                {"978-5-9930-2218-5", "Александр Пушкин"},

                {"978-5-9905833-8-2", "Фёдор Тютчев"},
                {"978-5-99058313-8-2", "Фёдор Тютчев"},
                {"978-5-91045-983-4", "Фёдор Тютчев"},

                {"978-5-17-099707-7", "Николай Гоголь"},
                {"978-5-91045-970-4", "Николай Гоголь"}
        };

        String[][] booksToGenres = {
                {"978-5-91045-983-4", "Классическая проза"},
                {"978-5-389-09531-1", "Русская классика"},
                {"978-5-9930-2218-5", "Литература 19 века"},
                {"978-5-9930-2218-5", "Русская классика"},

                {"978-5-9905833-8-2", "Литература 19 века"},
                {"978-5-99058313-8-2", "Древнерусская литература"},
                {"978-5-91045-983-4", "Русская классика"},

                {"978-5-17-099707-7", "Русская классика"},
                {"978-5-91045-970-4", "Литература 19 века"},
                {"978-5-91045-970-4", "Русская классика"}
        };

        Arrays.stream(books).forEach((book) -> {
            Document bookDocument = new Document("title", book[0]);
            String isbn = book[1];
            bookDocument.append("isbn", isbn);
            List<DBRef> genreIds = new ArrayList<>();
            List<DBRef> authorIds = new ArrayList<>();
            Arrays.stream(booksToGenres).filter(i -> i[0].equals(isbn)).forEach((i) -> {
                String title = i[1];
                Document first = genreCollection.find(eq("title", title)).first();
                Objects.requireNonNull(first);

                genreIds.add(new DBRef("genres", first.getObjectId("_id")));
            });

            Arrays.stream(booksToAuthors).filter(i -> i[0].equals(isbn)).forEach(i -> {
                String name = i[1];
                Document first = authorCollection.find(eq("name", name)).first();
                Objects.requireNonNull(first);
                authorIds.add(new DBRef("authors", first.getObjectId("_id")));
            });

            bookDocument.append("genres", genreIds);
            bookDocument.append("authors", authorIds);
            bookCollection.insertOne(bookDocument);
        });

        bookCollection.createIndex(new Document("isbn", 1));
    }
}
