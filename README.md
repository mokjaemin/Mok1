# PROJECT : Make a restaurant reservation


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
- SERVER : TOMCAT
- LANGUAGE : JAVA 17
- CLIENT : HTTP Requests/Response
- CLIENT API : Restful API
- SEQURITY : JASYPT Library, Spring Security, JWT
- DATA API : Query DSL, Hibernate
- DATABASE : MYSQL with AWS(Main DB), Redis(Cache), H2(Testing)
- TESTING : JUNIT5, Mockito
- TEST COVERAGE : JACOCO
- DEPLOYMENT : Docker, Amazon EC2

## TRAITS
- MVC STRUCTURE, SINGLETON PATTERN를 활용해 구조 체계화 및 인스턴스 최소화
- JWT, Spring Security를 통한 인증/인가 및 내부 용량 효율화 및 JASYPT을 통한 내부보안
- QueryDSL의 컴파일 처리로 오류 감소 및 인스턴스 최소화
- Swagger를 활용한 API 관리문서 간편 제작
- RestFul API를 활용하여 일련의 규칙을 통해 주소 간소화
- Mysql의 인덱싱, 조인 등으로 성능 향상
- Redis를 활용하여 캐시를 통해 속도 향상
- JUNIT, Mokito를 통한 테스팅 99%
- AWS, Docker, Amazon EC2로 클라우드 환경 구축

## Test Coverage
[Test Coverage File](https://docs.google.com/spreadsheets/d/1OKFicrW9nmyS5sHzxdf49Tp4WqHmk1qrBU6Q3lpNXAg/edit?usp=sharing)

## PROTOTYPE
[ProtoType File](https://www.figma.com/proto/p667iVrA2n38Qvr8l64xaQ/Graduation-Project?node-id=6-49&scaling=scale-down&page-id=0%3A1&starting-point-node-id=6%3A15)

## API SPECIFICATION
[API Specification File](https://docs.google.com/document/d/13W18PYfz040IY35pNc1uI9T5nt0C9nzI4S5q_MH0USw/edit)

## DATABASE SPECIFICATION
[DataBase File](https://docs.google.com/document/d/1AKWyWDcy_u3G-zKH0nWjQUifhvrn_jncopUeqKt-gEQ/edit)

## Trouble Shooting
[Trouble Shooting With Blog](https://mokjaemin88.tistory.com/category/Trouble%20Shooting)

## Function
### Member
1. 계정 생성
2. 로그인
3. 회원 정보 찾기
4. 회원 정보 수정
5. 회원 정보 삭제
### Store Basic
1. 가게 등록
2. 가게 지역별 리스트
3. 가게 관리자 페이지 로그인
### Store Info
1. 가게 쉬는날 등록
2. 가게 쉬는날 수정
3. 가게 쉬는날 삭제
4. 가게 쉬는날 출력
5. 가게 영업시간 정보 등록
6. 가게 영업시간 정보 수정
7. 가게 영업시간 정보 삭제
8. 가게 영업시간 정보 출력
9. 가게 예약 테이블 정보 출력
10. 가게 예약 테이블 정보 수정
11. 가게 예약 테이블 정보 삭제
12. 가게 메뉴 등록
13. 가게 메뉴 수정
14. 가게 메뉴 삭제
### Reservation & Order & Pay & Coupon
1. 가게 자리/시간 예약
2. 가게 자리/시간 수정
3. 가게 자리/시간 삭제
4. 음식 주문 등록
5. 음식 주문 수정
6. 음식 주문 삭제
7. 결제 등록
8. 결제 취소
9. 쿠폰 조회
### Recipt & Board
1. 영수증 출력
2. 영수증 댓글 등록
3. 영수증 댓글 수정
4. 영수증 댓글 삭제
5. 영수증 대댓글 등록
6. 영수증 대댓글 수정
7. 영수증 대댓글 삭제
8. 공개 게시판 글 등록
9. 공개 게시판 글 수정
10. 공개 게시판 글 삭제
11. 공개 게시판 댓글 등록
12. 공개 게시판 댓글 수정
13. 공개 게시판 댓글 삭제
