package kuke.board.hotarticle.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Repository
@RequiredArgsConstructor
public class ArticleCreatedTimeRepository {

    private final StringRedisTemplate redisTemplate;

    // hot-article::article::{articleId}::created-time
    private static final String KEY_FORMAT = "hot-article::article::%s::created-time";

    public void createOrUpdate(Long articleId, LocalDateTime createdAt, Duration ttl) {
        redisTemplate.opsForValue().set(
            generateKey(articleId),
            String.valueOf(createdAt.toInstant(ZoneOffset.UTC).toEpochMilli()),
            ttl
        );
        // 점수 반영할 때 날짜가 필요한데, 만약 따로 만들어 두지 않으면 매번 찔러서 비교해야 함.
    }

    public void delete(Long articleId) {
        redisTemplate.delete(generateKey(articleId));
    }

    public LocalDateTime read(Long articleId) {
        String result = redisTemplate.opsForValue().get(generateKey(articleId));
        if (result == null) {
            return null;
        }
        return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(Long.valueOf(result)), ZoneOffset.UTC
        );
    }

    private String generateKey(Long articleId) {
        return String.format(KEY_FORMAT, articleId);
    }
}
