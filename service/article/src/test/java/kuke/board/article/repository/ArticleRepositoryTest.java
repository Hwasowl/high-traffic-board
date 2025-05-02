package kuke.board.article.repository;

import kuke.board.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void findAllTest() {
        List<Article> all = articleRepository.findAll(1L, 1499970L, 30L);
        log.info("all = {}", all.size());
        for (Article article : all) {
            log.info("article = {}", article);
        }
    }

    @Test
    void countTest() {
        Long count = articleRepository.count(1L, 1000000L);
        log.info("count = {}", count);
    }
}
