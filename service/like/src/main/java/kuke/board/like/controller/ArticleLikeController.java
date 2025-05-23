package kuke.board.like.controller;

import kuke.board.like.service.ArticleLikeService;
import kuke.board.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public ArticleLikeResponse read(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        return articleLikeService.read(articleId, userId);
    }

    @GetMapping("/v1/article-likes/articles/{articleId}/count")
    public Long read(
        @PathVariable("articleId") Long articleId
    ) {
        return articleLikeService.count(articleId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-like-1")
    public void likePessimisticLock1(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        articleLikeService.likePessimisticLock1(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-unlike-1")
    public void unlikePessimisticLock1(
            @PathVariable("articleId") Long articleId,
            @PathVariable("userId") Long userId
    ) {
        articleLikeService.unlikePessimisticLock1(articleId, userId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-like-2")
    public void likePessimisticLock2(
        @PathVariable("articleId") Long articleId,
        @PathVariable("userId") Long userId
    ) {
        articleLikeService.likePessimisticLock2(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-unlike-2")
    public void unlikePessimisticLock2(
        @PathVariable("articleId") Long articleId,
        @PathVariable("userId") Long userId
    ) {
        articleLikeService.unlikePessimisticLock2(articleId, userId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/optimistic-like-1")
    public void likeOptimisticLock(
        @PathVariable("articleId") Long articleId,
        @PathVariable("userId") Long userId
    ) {
        articleLikeService.likeOptimisticLock(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/optimistic-unlike-1")
    public void unlikeOptimisticLock(
        @PathVariable("articleId") Long articleId,
        @PathVariable("userId") Long userId
    ) {
        articleLikeService.unlikeOptimisticLock(articleId, userId);
    }
}
