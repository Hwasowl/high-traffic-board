package kuke.board.article.api;

import kuke.board.article.entity.Article;
import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleApiTest {

    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleResponse articleResponse = create(new ArticleCreateRequest(
            "hi", "my content", 1L, 1L
        ));
        System.out.println("articleResponse = " + articleResponse);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Test
    void readTest() {
        ArticleResponse read = read(176248532240965632L);
        System.out.println("read = " + read);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest() {
        update(176248532240965632L);
        ArticleResponse read = read(176248532240965632L);
        System.out.println("read = " + read);
    }

    void update(Long articleId) {
        restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi 2", "my content 2"))
                .retrieve();
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }

    @Test
    void deleteTest() {
        delete(176248532240965632L);
        ArticleResponse read = read(176248532240965632L);
        System.out.println("read = " + read);
    }

    void delete(Long articleId) {
        restClient.delete()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve();
    }

    @Test
    void readAllTest() {
        ArticlePageResponse body = restClient.get()
            .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
            .retrieve()
            .body(ArticlePageResponse.class);

        System.out.println("readAll = " + body.getArticleCount());

        for (ArticleResponse article : body.getArticles()) {
            System.out.println("article = " + article);
        }
    }

    @Test
    void readAllInfiniteScrollTest() {
        List<ArticleResponse> articles1 = restClient.get()
            .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=30")
            .retrieve()
            .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
            });

        System.out.println("firstPage");

        for(ArticleResponse article : articles1) {
            System.out.println("articleId = " + article.getArticleId());
        }

        Long lastArticleId = articles1.getLast().getArticleId();
        List<ArticleResponse> articles2 = restClient.get()
            .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=30&lastArticleId={lastArticleId}", lastArticleId)
            .retrieve()
            .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
            });
        System.out.println("secondPage");
        for(ArticleResponse article : articles2) {
            System.out.println("articleId = " + article.getArticleId());
        }
    }
}
