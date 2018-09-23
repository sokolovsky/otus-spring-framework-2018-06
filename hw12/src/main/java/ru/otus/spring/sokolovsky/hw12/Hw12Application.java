package ru.otus.spring.sokolovsky.hw12;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.spring.sokolovsky.hw12.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw12.changelogs.SeedCreatorImpl;

@SpringBootApplication
@EnableMongoRepositories
public class Hw12Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw12Application.class, args);
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
