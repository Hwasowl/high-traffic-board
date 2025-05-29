package kuke.board.hotarticle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "kuke.board")
@SpringBootApplication
public class HotArticleApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(HotArticleApplication.class, args);
    }
}
