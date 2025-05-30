package kuke.board.articleread.repository;

import kuke.board.articleread.client.ArticleClient;
import kuke.board.common.event.payload.*;
import lombok.Getter;

@Getter
public class ArticleQueryModel {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private String createdAt;
    private String modifiedAt;
    private Long articleCommentCount;
    private Long articleLikeCount;

    public static ArticleQueryModel create(ArticleCreatedEventPayload payload) {
        ArticleQueryModel model = new ArticleQueryModel();
        model.articleId = payload.getArticleId();
        model.title = payload.getTitle();
        model.content = payload.getContent();
        model.boardId = payload.getBoardId();
        model.writerId = payload.getWriterId();
        model.createdAt = payload.getCreatedAt().toString();
        model.modifiedAt = payload.getModifiedAt().toString();
        model.articleCommentCount = 0L;
        model.articleLikeCount = 0L;
        return model;
    }

    public static ArticleQueryModel create(ArticleClient.ArticleResponse response, Long articleCommentCount, Long articleLikeCount) {
        ArticleQueryModel model = new ArticleQueryModel();
        model.articleId = response.getArticleId();
        model.title = response.getTitle();
        model.content = response.getContent();
        model.boardId = response.getBoardId();
        model.writerId = response.getWriterId();
        model.createdAt = response.getCreatedAt();
        model.modifiedAt = response.getModifiedAt();
        model.articleCommentCount = articleCommentCount;
        model.articleLikeCount = articleLikeCount;
        return model;
    }

    public void updateBy(CommentCreatedEventPayload payload) {
        this.articleCommentCount = payload.getArticleCommentCount();
    }

    public void updateBy(CommentDeletedEventPayload payload) {
        this.articleCommentCount = payload.getArticleCommentCount();
    }

    public void updateBy(ArticleLikedEventPayload payload) {
        this.articleCommentCount = payload.getArticleLikeCount();
    }

    public void updateBy(ArticleUnlikedEventPayload payload) {
        this.articleCommentCount = payload.getArticleLikeCount();
    }

    public void updateBy(ArticleUpdatedEventPayload payload) {
        this.title = payload.getTitle();
        this.content = payload.getContent();
        this.boardId = payload.getBoardId();
        this.writerId = payload.getWriterId();
        this.createdAt = payload.getCreatedAt().toString();
        this.modifiedAt = payload.getModifiedAt().toString();
    }
}
