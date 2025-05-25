package kuke.board.view.service;

import kuke.board.view.entity.ArticleViewCount;
import kuke.board.view.repository.ArticleViewCountBackUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {
    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @Transactional
    public void backUp(Long articleId, Long viewCount) {
        int updatedCount = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);
        if (updatedCount == 0) {
            articleViewCountBackUpRepository.findById(articleId)
                .ifPresentOrElse(ignored -> { },
                    () -> articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, viewCount))
                );
        }
    }
}
