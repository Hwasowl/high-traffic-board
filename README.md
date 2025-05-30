# 대규모 트래픽 처리 게시판 서비스

## 학습 내용
* 대규모 시스템 설계
* MSA & EDD
* 분산 시스템, 동시성 문제를 다루는 방법
* 데이터베이스 인덱스를 활용한 대규모 데이터 쿼리 최적화
* 높은 쓰기 트래픽에서도 데이터 일관성을 보장하는 방법
* 이벤트 스트림 처리 및 비동기 애플리케이션 구축, 이벤트 유실 방지를 위한 시스템 구축
* 다양한 요구사항에 적용할 수 있는 조회 최적화 전략

# 기술 정리 과정
* [#1] 대용량 데이터 조회 최적화 (Covering Index)
https://hwasowl-log.tistory.com/33
* [#2] 동시성 문제 해결 방안 (비동기 순차처리, 비관적 & 낙관적 락)
https://hwasowl-log.tistory.com/34
* [#3] Kafka Producer 설계 방법 (Transactional Messaging, Transactional Outbox)
https://hwasowl-log.tistory.com/37
* [#4] 조회 서비스 최적화 (CQRS)
https://hwasowl-log.tistory.com/38
* [#5] 캐시 최적화 (Request Collapsing)
https://hwasowl-log.tistory.com/39
