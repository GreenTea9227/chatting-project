# chatting-project

## Tech :computer:

### Tech Stack

**Languages & Frameworks**
- Java, Spring (JPA, Cloud Gateway)

**Messaging & Databases**
- STOMP, Kafka, RabbitMQ, MySQL, MongoDB, Redis

**Testing & Monitoring**
- JUnit, Testcontainers, Fixture Money, Resilience4J, CloudWatch

**DevOps**
- Docker, GitHub Actions


## System Architecture 🏗️

![chat-process drawio](https://github.com/GreenTea9227/chatting-project/assets/95036191/f6d0d909-19a6-409c-87b2-de7f516e08c9)

- MSA
- 헥사고날 아키텍처
- 멀티 모듈

## Chatting Flow 💬
![chat-send_message drawio](https://github.com/GreenTea9227/chatting-project/assets/95036191/5140c22d-fc4d-408d-9350-8b86e75f5e80)

**기능**

1. **인증 및 로그인 서비스**
    - JWT 방식으로 인증 구현
    - 로그인 시 Redis를 사용하여 유저 정보 저장 및 MySQL에도 데이터 저장
    - Spring Cloud Gateway를 통해 서비스 간 인증 통일성 보장
2. **채팅 서비스**
    - Kafka를 이용하여 채팅 기능 구현
    - 회원 데이터는 MySQL에 저장하고, 채팅방 및 메시지는 MongoDB에 저장
    - Redis hash를 사용하여 채팅방 현황 실시간 관리
    - Redis pub/sub 기능을 이용한 알림 기능 구현
    - Spring Cloud Gateway를 사용하여 채팅 서비스에 대한 인증 처리
3. **로그 서비스**
    - Kafka를 통해 실시간 로그 데이터 처리
    - AWS CloudWatch Logs를 사용하여 로그 데이터 저장 및 관리
    - Resilience4j를 활용하여 로그 저장 시 장애 대응 메커니즘 구현
4. **테스트 코드**
    - Testcontainer를 활용하여 통합 테스트 환경 구축, 테스트 환경의 통일로 인해 테스트 수행 시간 약 50% 단축
    - TestFixtures로 공용 테스트 어노테이션 생성 및 적용
    - Naver의 Fixture Monkey를 사용하여 테스트용 더미 데이터 생성
