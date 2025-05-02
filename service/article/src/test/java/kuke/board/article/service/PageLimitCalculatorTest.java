package kuke.board.article.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageLimitCalculatorTest {

    @Test
    void calculatePageLimit() {
        calculatePageLimitTest(1L, 30L, 10L, 301L);
        calculatePageLimitTest(7L, 30L, 10L, 301L);
        calculatePageLimitTest(10L, 30L, 10L, 301L);
        calculatePageLimitTest(11L, 30L, 10L, 601L);
    }

    void calculatePageLimitTest(Long page, Long pageSize, Long movablePageCount, Long expected) {
        Long pageLimit = PageLimitCalculator.calculatePageLimit(page, pageSize, movablePageCount);
        assertEquals(expected, pageLimit);
    }
}
