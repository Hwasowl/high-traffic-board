package kuke.board.article.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ArticlePageResponse {
    private List<ArticleResponse> articles;
    private Long articleCount;

    public static ArticlePageResponse of (List<ArticleResponse> articles, Long articleCount) {
        return new ArticlePageResponse(articles, articleCount);
    }
}
