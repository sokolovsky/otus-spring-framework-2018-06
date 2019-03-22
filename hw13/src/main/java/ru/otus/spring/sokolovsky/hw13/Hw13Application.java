package ru.otus.spring.sokolovsky.hw13;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.sokolovsky.hw13.authenticate.User;
import ru.otus.spring.sokolovsky.hw13.authenticate.UserRepository;
import ru.otus.spring.sokolovsky.hw13.changelogs.SeedCreator;
import ru.otus.spring.sokolovsky.hw13.changelogs.SeedCreatorImpl;

import javax.annotation.PostConstruct;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@SpringBootApplication
@EnableMongoRepositories
public class Hw13Application {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${data.user.password}")
    private String userPassword;

	public static void main(String[] args) {
		SpringApplication.run(Hw13Application.class, args);
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

    @PostConstruct
    public void userPasswordInit() {
        List.of("user", "editor", "main_editor").stream().forEach(u -> {
            User user = userRepository.findByLogin(u);
            if (user == null) {
                return;
            }
            user.setPassword(passwordEncoder.encode(userPassword));
            userRepository.save(user);
        });
    }
}
