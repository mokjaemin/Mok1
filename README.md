# PROJECT : Make a restaurant reservation


# DESCRIPTION
- 해당 프로젝트는 음식점 예약 서버입니다.
- 음식점이 닫았거나 만석인 상황을 더 이상 겪지 마세요!


# FOR
- 음식점을 갔다가 음식점이 닫거나 만석이라 불편을 겪으신 분
- 중요한 약속이 있어 음식점 예약을 간편하게 하고 싶은 분
- 음식점 단골 기억을 하기 어려운 사장님들


# USER INTERFACE ( CLICK BELOW )
[![Video Label](http://img.youtube.com/vi/4kFZOOxNqD0/0.jpg)](https://youtu.be/4kFZOOxNqD0)


# ENV
- IDE : STS4
- BUILD TOOLS : MAVEN
- FRAMEWORK : SPRING BOOT
- SERVER : TOMCAT
- LANGUAGE : JAVA
- CLIENT : HTTP Requests/Response
- CLIENT API : Restful API
- CLIENT TESTING : Insomnia, SWAGGER
- SEQURITY : JASYPT Library, Spring Security, JWT
- DATA API : Query DSL
- DATABASE : MYSQL with AWS(Main DB), Redis(Cache)
- TESTING : JUNIT, Mockito
- TEST COVERAGE : JACOCO

# TRAITS
- MVC STRUCTURE, SINGLETON PATTERN를 활용해 구조 체계화 및 인스턴스 최소화
- AOP를 통한 유의미한 로그 관리
- JASYPT을 통한 내부보안
- JWT, Spring Security를 통한 인증/인가 및 내부 용량 효율화
- MailSender로 보안 강화
- QueryDSL의 컴파일을 통한 오류 감소 및 인스턴스 최소화
- Swagger를 활용한 API 관리문서 간편 제
- RestFul API를 활용하여 일련의 규칙을 통해 주소 간소화
- Mysql을 활용하여 인덱싱, 조인 등으로 성능 향상
- AWS를 활용하여 환경 개선
- Redis를 활용하여 캐시를 통해 속도 향상
- 사진/동영상 저장시 다른 저장공간을 사용해 용량 사용 개선
- JUNIT, Mokito를 통한 테스팅

# Details
- 개선 내용 정리본
- https://mokjaemin88.tistory.com/category/Trouble%20Shooting


# PROTOTYPE
https://www.figma.com/proto/p667iVrA2n38Qvr8l64xaQ/Graduation-Project?node-id=6-49&scaling=scale-down&page-id=0%3A1&starting-point-node-id=6%3A15


# API SPECIFICATION
https://docs.google.com/document/d/13W18PYfz040IY35pNc1uI9T5nt0C9nzI4S5q_MH0USw/edit


# DATABASE SPECIFICATION
https://docs.google.com/document/d/1AKWyWDcy_u3G-zKH0nWjQUifhvrn_jncopUeqKt-gEQ/edit


# Function
- Member
1. 계정 생성
2. 로그인
3. 회원 정보 찾기
4. 회원 정보 수정
5. 회원 정보 삭제
- Store Basic
1. 가게 등록
2. 가게 지역별 리스트
3. 가게 관리자 페이지 로그인
- Store Info
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
- Reservation & Order & Pay & Coupon
1. 가게 자리/시간 예약
2. 가게 자리/시간 수정
3. 가게 자리/시간 삭제
4. 음식 주문 등록
5. 음식 주문 수정
6. 음식 주문 삭제
7. 결제 등록
8. 결제 취소
9. 쿠폰 조회
- Recipt & Board
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
