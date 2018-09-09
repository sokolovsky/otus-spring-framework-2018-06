package ru.otus.spring.sokolovsky.hw09;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.sokolovsky.hw09.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw09.changelogs.SeedCreatorImpl;

@SpringBootApplication
@EnableMongoRepositories

public class Hw09Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw09Application.class, args);
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
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
