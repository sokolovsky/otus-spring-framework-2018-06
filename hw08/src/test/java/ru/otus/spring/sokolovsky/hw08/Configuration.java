package ru.otus.spring.sokolovsky.hw08;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.sokolovsky.hw08.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw08.changelogs.SeedCreatorImpl;

@TestConfiguration
public class Configuration {

    @Bean
    public SeedCreator seedCreator(MongoClient mongoClient, @Value("${spring.data.mongodb.database}") String dbName) {
        Mongobee driver = new Mongobee(mongoClient);
        driver.setDbName(dbName);
        driver.setChangeLogsScanPackage(
                this.getClass().getPackageName() + ".changelogs");
        return new SeedCreatorImpl(driver, mongoClient.getDatabase(dbName));
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
