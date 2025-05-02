package kuke.board.article.repository;

import kuke.board.article.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // secondary index(covering index)로 조회 후 -> cluster index 에 join.
    @Query(
        value = "select article.article_id, article.title, article.content, article.board_id, article.writer_id, " +
                "article.created_at, article.modified_at " +
                "from (" +
                "select article_id from article " +
                "where board_id = :boardId " +
                "order by article_id desc" +
                " limit :limit offset :offset" +
                ") t left join article on t.article_id = article.article_id ",
        nativeQuery = true
    )
    List<Article> findAll(
        @Param("boardId") Long boardId,
        @Param("offset") Long offset,
        @Param("limit") Long limit
    );

    // secondary index(covering index)로 수량 조회 후 -> 그 수량 만큼 count 연산
    @Query(
        value = "select count(*) from (" +
                "select article_id from article " +
                "where board_id = :boardId " +
                " limit :limit) t",
        nativeQuery = true
    )
    Long count(@Param("boardId") Long boardId, @Param("limit") Long limit);
}
