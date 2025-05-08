package kuke.board.like.repository;

import kuke.board.like.domain.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    Optional<ArticleLike> findByArticleIdAndUserId(Long articleId, Long userId); //unique index
}
