package kuke.board.article;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan(basePackages = "kuke.board")
@EnableJpaRepositories(basePackages = "kuke.board")
@SpringBootApplication
@EnableScheduling
public class ArticleApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ArticleApplication.class, args);
    }
}
