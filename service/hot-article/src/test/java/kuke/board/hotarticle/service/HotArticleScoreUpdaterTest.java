package kuke.board.hotarticle.service;

import kuke.board.common.event.Event;
import kuke.board.hotarticle.repository.ArticleCreatedTimeRepository;
import kuke.board.hotarticle.repository.HotArticleListRepository;
import kuke.board.hotarticle.service.eventHandler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotArticleScoreUpdaterTest {
    @InjectMocks
    HotArticleScoreUpdater hotArticleScoreUpdater;
    @Mock
    HotArticleScoreCalculator hotArticleScoreCalculator;
    @Mock
    HotArticleListRepository hotArticleListRepository;
    @Mock
    ArticleCreatedTimeRepository articleCreatedTimeRepository;

    @Test
    void updateIfArticleNotCreatedToday() {
        //given
        Long articleId = 1L;
        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);

        given(eventHandler.findArticleId(event)).willReturn(articleId);

        LocalDateTime createdTime = LocalDateTime.now().minusDays(1);
        given(articleCreatedTimeRepository.read(articleId)).willReturn(createdTime);

        //when
        hotArticleScoreUpdater.update(event, eventHandler);

        //then
        verify(eventHandler, never()).handle(event);
        verify(hotArticleListRepository, never()).add(anyLong(), any(), anyLong(), anyLong(), any());
    }

    @Test
    void updateTest() {
        //given
        Long articleId = 1L;
        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);

        given(eventHandler.findArticleId(event)).willReturn(articleId);

        LocalDateTime createdTime = LocalDateTime.now().now();
        given(articleCreatedTimeRepository.read(articleId)).willReturn(createdTime);

        //when
        hotArticleScoreUpdater.update(event, eventHandler);

        //then
        verify(eventHandler).handle(event);
        verify(hotArticleListRepository).add(anyLong(), any(), anyLong(), anyLong(), any());
    }
}
