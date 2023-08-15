# 1
# Maven을 활용한 도커 이미지
FROM maven:3-openjdk-17

# 작업 디렉토리 설정
WORKDIR /app

# 소스 코드 복사
# 현재 경로의 src 폴더를 ./src에 복사
COPY src ./src
COPY pom.xml .

# 애플리케이션 빌드
RUN mvn package

# 애플리케이션 실행
CMD ["java", "-jar", "target/ReservationServer1-1.0.0.jar"]

