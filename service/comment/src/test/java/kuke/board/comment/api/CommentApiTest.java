package kuke.board.comment.api;

import kuke.board.comment.service.request.CommentCreateRequest;
import kuke.board.comment.service.response.CommentPageResponse;
import kuke.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentApiTest {
    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));
        //commentId=176930976977432576
        //	commentId=176930977610772480
        //	commentId=176930977673687040
    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
            .uri("/v1/comments/{commentId}", 176930976977432576L)
            .retrieve()
            .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
//commentId=176931649332752384
//	commentId=176931649576022016
//	commentId=176931649647325184
        restClient.delete()
            .uri("/v1/comments/{commentId}", 176931649647325184L)
            .retrieve();
    }


    CommentResponse createComment(CommentCreateRequest request) {
        return restClient.post()
            .uri("/v1/comments")
            .body(request)
            .retrieve()
            .body(CommentResponse.class);
    }

    @Test
    void readAll() {
        CommentPageResponse response = restClient.get()
            .uri("/v1/comments?articleId=1&page=1&pageSize=10")
            .retrieve()
            .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
        //comment.getCommentId() = 176932430774841344
        //	comment.getCommentId() = 176932430812590083
        //comment.getCommentId() = 176932430774841345
        //	comment.getCommentId() = 176932430812590084
        //comment.getCommentId() = 176932430774841346
        //	comment.getCommentId() = 176932430812590082
        //comment.getCommentId() = 176932430774841347
        //	comment.getCommentId() = 176932430812590081
        //comment.getCommentId() = 176932430774841348
        //	comment.getCommentId() = 176932430812590086
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponse> responses1 = restClient.get()
            .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
            .retrieve()
            .body(new ParameterizedTypeReference<List<CommentResponse>>() {
            });

        System.out.println("firstPage");
        for (CommentResponse comment : responses1) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        Long lastParentCommentId = responses1.getLast().getParentCommentId();
        Long lastCommentId = responses1.getLast().getCommentId();

        List<CommentResponse> responses2 = restClient.get()
            .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5&lastParentCommentId=%s&lastCommentId=%s"
                .formatted(lastParentCommentId, lastCommentId))
            .retrieve()
            .body(new ParameterizedTypeReference<List<CommentResponse>>() {
            });

        System.out.println("secondPage");
        for (CommentResponse comment : responses2) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
    }
}
