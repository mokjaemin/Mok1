# PROJECT : 음식점 예약 웹


## DESCRIPTION
- 해당 프로젝트는 음식점 예약 서버 프로젝트입니다.
- 음식점이 닫았거나 만석인 상황을 마주친 경우가 많을 것입니다.
- 이를 해결하기 위해 미리 좌석이 있는지 확인하고 시간, 장소, 테이블 등 예약이 가능합니다.


## FOR
- 음식점을 갔다가 음식점이 닫거나 만석이라 불편을 겪으신 분
- 중요한 약속이 있어 음식점 예약을 간편하게 하고 싶은 분
- 음식점 고객들을 편리하게 관리하고 싶은 모든 가게 사장님들


## USER INTERFACE ( CLICK BELOW )
[![Video Label](http://img.youtube.com/vi/4kFZOOxNqD0/0.jpg)](https://youtu.be/4kFZOOxNqD0)


## ENV
- IDE : STS4
- BUILD TOOLS : MAVEN
- FRAMEWORK : SPRING BOOT 3.0.5
- SERVER : APACHE TOMCAT
- LANGUAGE : JAVA 17
- CLIENT : HTTP Requests/Response
- CLIENT API : Restful API
- SEQURITY : JASYPT, Spring Security, JWT
- DATA API : Query DSL, Hibernate
- DATABASE : MYSQL 8.0(Main DB), Redis 7.0.10(Cache), H2(Testing)
- TESTING : JUNIT5, Mockito
- TEST COVERAGE : JACOCO
- DEVOPS : Docker, Amazon EC2, AMAZON RDS

## TRAITS
- 다양한 명세서 (프로토타입, 다이어그램, API 문서, Swagger 등)를 작성하여 사용자에게 종합적인 정보 제공
- Spring Security를 기반으로 Access 및 Refresh Token 구조를 확립하여 공격자 보안 문제 대응
- Java 8 기반의 Stream으로 클린 코드 작성하여 가독성을 향상
- Querydsl 활용으로 코드 가독성 향상 및 JPA 구조 간소화
- DB 인덱스, 쿼리 최적화로 디스크 I/O 사용량 감소시켜 조회 API 평균 응답시간 53% 단축
- 데이터베이스 트랜잭션의 전파 및 격리 설정을 통해 데이터 일관성 및 안정성을 보장
- Redis를 활용한 캐싱으로 자주 요청되는 레코드를 처리하여 응답시간 약 2초 단축하여 사용자 경험 최적화
- Junit5 및 Mockito를 사용하여 BDD 기반의 단위 및 통합 테스트 작성 및 Jacoco 테스트 커버리지 향상
- 규모에 따라 변화하는 애플리케이션 규모에 맞게 GitHub Flow 브랜치 전략을 도입하여 협업 구조를 구축
- Amazon RDS, EC2, Docker를 활용한 클라우드 배포 및 DB 환경 구축

## STRUCTURE
<img width="806" alt="스크린샷 2023-10-15 19 52 47" src="https://github.com/mokjaemin/Mok1/assets/95067670/428bbb6c-4141-46eb-91e8-69846d075ecd">

## DIAGRAM
<img width="704" alt="스크린샷 2023-10-25 22 58 36" src="https://github.com/mokjaemin/Mok1/assets/95067670/76af113f-d2ad-4219-a36c-5ba96732b034">


## PROTOTYPE
[ProtoType File](https://www.figma.com/proto/p667iVrA2n38Qvr8l64xaQ/Graduation-Project?node-id=6-49&scaling=scale-down&page-id=0%3A1&starting-point-node-id=6%3A15)

## Test Coverage
[Test Coverage File](https://docs.google.com/spreadsheets/d/1OKFicrW9nmyS5sHzxdf49Tp4WqHmk1qrBU6Q3lpNXAg/edit?usp=sharing)

## API SPECIFICATION
[API Specification File](https://docs.google.com/document/d/13W18PYfz040IY35pNc1uI9T5nt0C9nzI4S5q_MH0USw/edit)

## DATABASE SPECIFICATION
[DataBase File](https://docs.google.com/document/d/1AKWyWDcy_u3G-zKH0nWjQUifhvrn_jncopUeqKt-gEQ/edit)

