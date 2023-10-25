# PROJECT : 음식점 예약 웹


## DESCRIPTION
- 해당 프로젝트는 음식점 예약 서버 프로젝트입니다.
- 음식점이 닫았거나 만석인 상황을 마주친 경우가 많을 것입니다.
- 이를 해결하기 위해 미리 좌석이 있는지 확인하고 시간, 장소, 테이블 등 예약이 가능합니다.

## FILES DESCRIPTION
- src/main/java/com.ReservationServer1
- - config : config 파일 패키지
  - - cache : Cache 관련 Config
    - JaspytConfig : Jasypt 라이브러리 관련 Config
    - JPAAuditingConfig : JPA Auditing 관련 Config
    - JWTFilter : JWT 인증/인가 필터
    - PasswordEncoderConfig : 회원 비밀번호 암호화 관련 Config
    - QueryDSLConfig : Querydsl 사용 환경 설정 Config
    - SwaggerConfig : Swagger Page 관련 Config
    - WebSecurityConfig : 인증/인가 처리 Config
  - controller : controller 파일 패키지
  - - MemberController : 회원 관련 Controller
    - StoreController : 가게 기본 정보 관련 Controller
    - StoreInfoController : 가게 상세 정보 관련 Controller
    - StorePORController : 예약, 주문, 결제, 쿠폰 관련 Controller
    - StoreBoardControlelr : 가게 게시판 관련 Controller
  - DAO : DB연결 관련 패키지
  - - Cache : Cache 관련 설정
    - Impl : DAO 인터페이스 구현체
    - - MemberDAOImpl : 회원 정보 처리 DAO
      - StoreDAOImpl : 가게 기본 정보 처리 DAO
      - StoreInfoDAOImpl : 가게 상세 정보 처리 DAO
      - StorePORDAOImpl : 예약, 주문, 결제, 쿠폰 관련 DAO
      - StoreBoardDAOImpl : 가게 게시판 관련 DAO
    - MemberDAO : 회원 정보 처리 인터페이스
    - StoreDAO : 가게 기본 정보 처리 인터페이스
    - StoreInfoDAO : 가게 상세 정보 처리 인터페이스
    - StoreInfoDAO : 가게 상세 정보 처리 인터페이스
    - StorePORDAO : 예약, 주문, 결제, 쿠폰 관련 인터페이스
    - StoreBoardDAO : 가게 게시판 관련 인터페이스
  - data
  - - DTO : DTO 관련 패키지
    - - board : 게시판 관련 Request/Response
      - - BoardCountResultDTO : 게시판 양 Response
        - BoardDTO : 게시판 등록 Request
        - BoardListResultDTO : 게시판 리스트 Response
        - BoardResutlDTO : 게시판 결과 Response
      - member : 회원 관련 Request/Response
      - - FindPwdDTO : 비밀번호 찾기 Request
        - LoginDTO : 로그인 Request
        - MemberDTO : 회원가입 Request
        - ModifyMemberDTO : 회원수정 Request
        - SearchMemberDTO : 회원검색 Request/Response
      - POR : 예약, 주문, 결제 관련 Request/Response
      - - OrderDTO : 주문 관련 Request
        - PayDTO : 결제 Request
        - ReservationDTO : 예약 Request
      - Store : 가게 기본 정보 관련 Request/Response
      - - Cache : 가게 리스트 출력 Caching Response
        - StoreDTO : 가게 정보 등록 Request
        - StoreFoodsInfoDTO : 가게 음식 정보 등록 Request
        - StoreLitResultDTO : 가게 검색 결과 Response
        - StoreRestDayDTO : 가게 쉬는날 정보 Request
        - StoreTableInfoDTO : 가게 테이블 정보 Request
        - StoreTImeInfoDTO : 가게 시간 정보 Request
  - - Entity : Entity 관련 패키지
    - - board : 게시판 관련 Entity
      - - StoreBoardEntity : 게시판 테이블
        - 


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

## MAIN TRAITS
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

 

## SERVER STRUCTURE
<img width="706" alt="스크린샷 2023-10-15 19 52 47" src="https://github.com/mokjaemin/Mok1/assets/95067670/428bbb6c-4141-46eb-91e8-69846d075ecd">

## CLASS DIAGRAM
<img width="704" alt="스크린샷 2023-10-25 22 58 36" src="https://github.com/mokjaemin/Mok1/assets/95067670/76af113f-d2ad-4219-a36c-5ba96732b034">

## USER INTERFACE 
#### -> CLICK BELOW
[![Video Label](http://img.youtube.com/vi/4kFZOOxNqD0/0.jpg)](https://youtu.be/4kFZOOxNqD0)


