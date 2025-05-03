package kuke.board.comment.api;

import kuke.board.comment.service.request.CommentCreateRequest;
import kuke.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

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
}
