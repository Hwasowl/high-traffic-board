package kuke.board.view.service;

import kuke.board.view.repository.ArticleViewDistributedLockRepository;
import kuke.board.view.repository.ArticleViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;
    private final ArticleViewRepository articleViewRepository;
    private final ArticleViewDistributedLockRepository articleViewDistributedLockRepository;
    private static final int BACK_UP_BACH_SIZE = 100;
    public static final Duration TTL = Duration.ofMinutes(10);

    public Long increase(Long articleId, Long userId) {
        if (!articleViewDistributedLockRepository.lock(articleId, userId, TTL)) {
            return articleViewRepository.read(articleId);
        }
        Long count = articleViewRepository.increase(articleId);
        if (count % BACK_UP_BACH_SIZE == 0) {
            articleViewCountBackUpProcessor.backUp(articleId, count);
        }
        return count;
    }

    public Long count(Long articleId) {
        return articleViewRepository.read(articleId);
    }
}
