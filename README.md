## 프로젝트 개요

• **프로젝트 명**: 풍선 딜리버리

• **프로젝트 설명**: ‘풍선 딜리버리’는 주문 관리 플랫폼입니다. 사용자는 이 플랫폼을 통해 배달 및 포장 주문을 손쉽게 처리할 수 있으며, 가게 주인은 가게와 메뉴를 관리할 수 있습니다. 또한, AI가 자동으로 메뉴 설명을 추천해주는 기능을 제공하여 사용 편의성을 높였습니다. 

• **프로젝트 기간**: 2024년 8월 22일 - 2024년 9월 2일

<br>
  
## 팀원

|박주창|김진선|이지선|
|:---:|:---:|:---:|
|[joo-chang](https://github.com/joo-chang)|[kimzinsun](https://github.com/kimzinsun)|[easyxun](https://github.com/easyxun)|
|<img src="https://avatars.githubusercontent.com/u/63954779?v=4" alt="Image Description" width="30%" /> | <img src="https://avatars.githubusercontent.com/u/122031650?v=4" alt="Image Description" width="30%" /> | <img src="https://avatars.githubusercontent.com/u/107982536?v=4" alt="Image Description" width="30%" /> |
|Leader| | |
|주문, 결제|유저(인증/인가), 고객센터|가게, 메뉴, AI|

<br>

## 프로젝트 목적 

이번 프로젝트의 주요 목적은 MSA(Microservices Architecture)를 실습하며, Redis를 이용한 빠른 권한 체크, 서비스 간의 통신을 관리하는 Eureka 서버 및 Gateway 서버를 직접 구현해보는 것입니다. 이를 통해 분산 시스템 환경에서의 개발 역량을 높이고, 성능 최적화 및 서비스 간의 효율적인 통신 방법을 익히고자 하였습니다.


<br>

## 프로젝트 구성

### 시스템 아키텍처

![image](https://github.com/user-attachments/assets/aa5d4856-d971-4465-a4ce-ad695ed7a49b)

<br>

### ERD

![Pasted image 20240902164001](https://github.com/user-attachments/assets/3d792a80-cf1c-46b7-93ff-13a271a67ff2)

<br>

### 기술 스택

- Framework: `SpringBoot`, `SpringSecurity`, `SpringDataJPA`, `Spring Cloud`
- Language: `JAVA 17`
- Database: `PostgreSQL`, `Redis`
- Login: `JWT`
- Deploy: `Docker`,  `AWS EC2`
- AI: `Gemini AI`
- API doc: `Postman`
- Communication Tools : `Github`, `Notion`, `Slack`

<br>

### API docs

**Postman Documentation**

[API Document](https://documenter.getpostman.com/view/37961544/2sAXjM4X9z)

<br>

## 주요 기능


1. **주문 및 결제 기능**: 주문 및 결제를 진행하고, 주문 상태를 관리할 수 있습니다.

2. **가게 등록 및 메뉴 관리**: 가게 등록, 메뉴 추가, 수정, 삭제, 검색 기능을 제공하여 가게 운영의 효율성을 높였습니다.

3. **사용자 권한 관리**: 사용자 역할에 따라 권한을 부여하고 관리할 수 있는 기능을 제공합니다.

4. **AI 기반 메뉴 설명 추천**:  AI 기반 메뉴 설명 추천 기능으로 사용자의 편의성을 극대화했습니다.

5. **공지사항** : 관리자 권한으로 공지사항을 등록하고 관리할 수 있는 기능을 제공합니다.

6. **신고 및 답변** : 사용자 및 가게 주인이 신고 글을 작성할 수 있으며, 관리자는 이에 대한 답변을 관리할 수 있습니다.

<br>

## 프로젝트 실행 방법
- Java 17, PostgreSQL, Redis 설치
- 프로젝트 클론 및 docker 설치 후 로컬 환경에서 순차적으로 실행
1. 프로젝트 클론
   ```
    git clone https://github.com/yourusername/balloon-delivery.git
   ```

2. application-private.yml 파일 생성
   <details>
   <summary>(private.yml 작성 예시)</summary>
     
   ```yaml
   jwt:
     secret: <your-jwt-secret>
     expiration: <your-expiration-time>
   ADMIN_TOKEN: <your-admin-token>
   ai:
     url: <your-ai-url>
   ```
   
3. application-datasource.yml 파일 생성
   <details>
   <summary>(datasource.yml 작성 예시)</summary>

   ```yaml
   spring:
     datasource:
        driver-class-name: org.postgresql.Driver
        url: jdbc:postgresql://<your-database-url>:<your-port>/<your-database-name>
        name: <your-username>
        password: <your-password>
   data:
     redis:
        host: <your-redis-host>
        port: <your-redis-port>
        username: <your-redis-username>
        password: <your-redis-password>
     jpa:
        hibernate:
          ddl-auto: update
        properties:
          hibernate:
            dialect: org.hibernate.dialect.PostgreSQLDialect
            format_sql: false
   ```

4. 프로젝트 빌드 및 실행
   ```
    ./gradlew clean build
   ```

5. 도커 컴포즈로 서비스 실행
   ```
    docker-compose up --build
   ```

6. 서비스 확인
- Gateway:8080
- Eureka:19090
- BalloonDelivery:19093

<br>

## 트러블 슈팅

### 배포

#### 문제 상황

- Eureka 서버, 게이트웨이, API 어플리케이션을 도커 컴포즈 파일로 만들어 배포
- 동작시켜보니 서비스간 통신 실패로 토큰 검증이 제대로 이루어 지지 않아 401 에러 발생

#### 해결 방안

- Feign Client는 Eureka에 등록된 서비스 이름을 기반으로 서비스를 호출
- 하지만 개발 환경에서 localhost를 사용하여 서비스가 등록되어, Feign Client가 이를 올바르게 인식하지 못하고, 서비스 디스커버리가 제대로 작동하지 않음
- localhost로 설정된 부분을 Eureka 서버에 등록된 서비스 이름으로 호출 되도록 설정 변경

<br>

### Custom Exception Handler 적용 문제 해결

#### 문제 상황

- Custom Exception Handler를 만들었지만, 파일 이름 때문에 핸들러의 적용 순서가 예상과 다르게 동작
- 그 결과, 예외가 발생했을 때 예외 처리 로직이 원하는 순서로 적용되지 않음

### 해결 방안

-	스프링에서 예외 처리 핸들러의 적용 순서는 기본적으로 클래스의 이름 순서에 따라 결정
-	이를 해결하기 위해 @Order 애노테이션을 사용하여 Exception Handler의 적용 순서를 명시적으로 설정
-	@Order 애노테이션을 통해 예외 핸들러의 우선 순위를 지정하여, 예외 처리 로직이 의도한 대로 순차적으로 적용되도록 설정
