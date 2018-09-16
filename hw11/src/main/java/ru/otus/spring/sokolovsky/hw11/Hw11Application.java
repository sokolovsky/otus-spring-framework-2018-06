package ru.otus.spring.sokolovsky.hw11;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.sokolovsky.hw11.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw11.changelogs.SeedCreatorImpl;

@SpringBootApplication
public class Hw11Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw11Application.class, args);
    }

    @Bean
    public SeedCreator seedCreator(MongoClient mongoClient, @Value("${spring.data.mongodb.database}") String dbName) {
        Mongobee driver = new Mongobee(mongoClient);
        driver.setDbName(dbName);
        driver.setChangeLogsScanPackage(
                this.getClass().getPackageName() + ".changelogs");
        return new SeedCreatorImpl(driver, mongoClient.getDatabase(dbName));
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
