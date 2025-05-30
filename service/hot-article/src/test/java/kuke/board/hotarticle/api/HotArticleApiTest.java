package kuke.board.hotarticle.api;

import kuke.board.hotarticle.service.response.HotArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;

public class HotArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9004");

    @Test
    void readAllTest() {
        List<HotArticleResponse> responses = restClient.get()
            .uri("/v1/hot-articles/article/date/{dateStr}", "20250529")
            .retrieve()
            .body(new org.springframework.core.ParameterizedTypeReference<List<HotArticleResponse>>() {
            });

        for (HotArticleResponse response : responses) {
            System.out.println("response = " + response.getArticleId() +
                ", title = " + response.getTitle() +
                ", createdAt = " + response.getCreatedAt());
        }
    }
}
