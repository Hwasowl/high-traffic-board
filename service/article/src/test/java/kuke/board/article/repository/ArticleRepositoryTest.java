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
        List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);
        log.info("all = {}", articles.size());
        for (Article article : articles) {
            log.info("article = {}", article);
        }
    }

    @Test
    void countTest() {
        Long count = articleRepository.count(1L, 1000000L);
        log.info("count = {}", count);
    }

    @Test
    void findInfinityScrollTest() {
        List<Article> articles = articleRepository.findAllInfiniteScroll(1L, 30L);
        for (Article article : articles) {
            log.info("article1 = {}", article.getArticleId());
        }

        Long lastArticleId = articles.get(articles.size() - 1).getArticleId();
        List<Article> articles1 = articleRepository.findAllInfiniteScroll(1L, 30L, lastArticleId);
        for (Article article : articles1) {
            log.info("article2 = {}", article.getArticleId());
        }
    }
}
