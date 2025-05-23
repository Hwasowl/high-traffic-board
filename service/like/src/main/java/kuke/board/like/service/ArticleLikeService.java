package kuke.board.like.service;

import kuke.board.common.snowflake.Snowflake;
import kuke.board.like.domain.ArticleLike;
import kuke.board.like.domain.ArticleLikeCount;
import kuke.board.like.repository.ArticleLikeCountRepository;
import kuke.board.like.repository.ArticleLikeRepository;
import kuke.board.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleLikeCountRepository articleLikeCountRepository;

    public ArticleLikeResponse read(Long articleId, Long userId) {
        return articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
                .map(ArticleLikeResponse::from)
                .orElseThrow();
    }

    // update 구문
    @Transactional
    public void likePessimisticLock1(Long articleId, Long userId) {
        articleLikeRepository.save(ArticleLike.create(snowflake.nextId(), articleId, userId)
        );
        int result = articleLikeCountRepository.increase(articleId);
        if (result == 0) {
            // 최초 요청 시에는 update 되는 레코드가 없으므로 1로 초기화.
            // 트래픽이 순식간에 몰릴 수 있는 상황에는 유실될 수 있으므로, 게시글 생성 시점에 미리 0으로 초기화 해둘 수 있다.
            articleLikeCountRepository.save(ArticleLikeCount.init(articleId, 1L));
        }
    }

    @Transactional
    public void unlikePessimisticLock1(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
            .ifPresent(articleLike -> {
                articleLikeRepository.delete(articleLike);
                articleLikeCountRepository.decrease(articleId);
            });
    }

    // 비관적 - select ... for update + update
    @Transactional
    public void likePessimisticLock2(Long articleId, Long userId) {
        articleLikeRepository.save(ArticleLike.create(snowflake.nextId(), articleId, userId));
        ArticleLikeCount articleLikeCount = articleLikeCountRepository.findLockedByArticleId(articleId)
            .orElseGet(() -> articleLikeCountRepository.save(ArticleLikeCount.init(articleId, 0L)));
        articleLikeCount.increase();
    }

    @Transactional
    public void unlikePessimisticLock2(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
            .ifPresent(articleLike -> {
                articleLikeRepository.delete(articleLike);
                articleLikeCountRepository.findLockedByArticleId(articleId)
                    .orElseThrow()
                    .decrease();
            });
    }

    // 낙관적
    @Transactional
    public void likeOptimisticLock(Long articleId, Long userId) {
        articleLikeRepository.save(ArticleLike.create(snowflake.nextId(), articleId, userId));
        articleLikeCountRepository.findById(articleId)
            .orElseGet(() -> articleLikeCountRepository.save(ArticleLikeCount.init(articleId, 0L)));
        articleLikeCountRepository.increase(articleId);
    }

    @Transactional
    public void unlikeOptimisticLock(Long articleId, Long userId) {
        articleLikeRepository.findByArticleIdAndUserId(articleId, userId)
            .ifPresent(articleLike -> {
                articleLikeRepository.delete(articleLike);
                articleLikeCountRepository.findById(articleId)
                    .orElseThrow()
                    .decrease();
            });
    }

    public Long count(Long articleId) {
        return articleLikeCountRepository.findById(articleId)
            .map(ArticleLikeCount::getLikeCount)
            .orElse(0L);
    }
}
