package kuke.board.like.api;

import kuke.board.like.service.response.ArticleLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class LikeApiTest {
    RestClient restClient = RestClient.create("http://localhost:9002");

    @Test
    void likeAndUnLikeTest() {
        Long articleId = 9999L;

        like(articleId, 1L);
        like(articleId, 2L);
        like(articleId, 3L);

        ArticleLikeResponse read1 = read(articleId, 1L);
        ArticleLikeResponse read2 = read(articleId, 1L);
        ArticleLikeResponse read3 = read(articleId, 1L);
        System.out.println("read1 = " + read1);
        System.out.println("read2 = " + read2);
        System.out.println("read3 = " + read3);

        unlike(articleId, 1L);
        unlike(articleId, 2L);
        unlike(articleId, 3L);
    }

    void like(Long articleId, Long userId) {
        restClient.post()
            .uri("/v1/article-likes/articles/{articleId}/users/{userId}/like", articleId, userId)
            .retrieve();
    }

    void unlike(Long articleId, Long userId) {
        restClient.delete()
            .uri("/v1/article-likes/articles/{articleId}/users/{userId}/unlike", articleId, userId)
            .retrieve();
    }

    ArticleLikeResponse read(Long articleId, Long userId) {
        return restClient.get()
            .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
            .retrieve()
            .body(ArticleLikeResponse.class);
    }
}
