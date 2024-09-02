## 프로젝트 개요

• **프로젝트 명**: 풍선 딜리버리

• **프로젝트 설명**: ‘풍선 딜리버리’는 주문 관리 플랫폼입니다. 사용자는 이 플랫폼을 통해 배달 및 포장 주문을 손쉽게 처리할 수 있으며, 가게 주인은 가게와 메뉴를 관리할 수 있습니다. 또한, 메뉴 등록 시 AI가 자동으로 메뉴 설명을 추천해주는 기능을 제공하여 사용 편의성을 높였습니다. 

• **프로젝트 기간**: 2024년 8월 22일 - 2024년 9월 2일

  <br>
  
## 팀원

|박주창|김진선|이지선|
|:---:|:---:|:---:|
|[joo-chang](https://github.com/joo-chang)|[kimzinsun](https://github.com/kimzinsun)|[easyxun](https://github.com/easyxun)|
|![](https://avatars.githubusercontent.com/u/63954779?v=4")|![](https://avatars.githubusercontent.com/u/122031650?v=4")|![](https://avatars.githubusercontent.com/u/107982536?v=4)|
|Leader|||

<br>

## 프로젝트 목적 

이번 프로젝트의 주요 목적은 MSA(Microservices Architecture)를 실습하며, Redis를 이용한 빠른 권한 체크, 서비스 간의 통신을 관리하는 Eureka 서버 및 Gateway 서버를 직접 구현해보는 것입니다. 이를 통해 분산 시스템 환경에서의 개발 역량을 높이고, 성능 최적화 및 서비스 간의 효율적인 통신 방법을 익히고자 하였습니다.


<br>

## 프로젝트 구성

### 시스템 아키텍처

![Pasted image 20240902163944](https://github.com/user-attachments/assets/33d0cb1b-c8cb-4bb8-b942-8016c6962baf)



### ERD

![Pasted image 20240902164001](https://github.com/user-attachments/assets/3d792a80-cf1c-46b7-93ff-13a271a67ff2)


### 기술 스택

- Framework: `SpringBoot`, `SpringSecurity`, `SpringDataJPA`, `Spring Cloud`
- Language: `JAVA 17`
- Database: `PostgreSQL`, `Redis`
- Login: `JWT`
- Deploy: `Docker`,  `AWS EC2`
- AI: `Gemini AI`
- API doc: `Swagger`
- Communication Tools : `Notion`, `Slack`

### API docs

**Postman Documentation**

[API Document](https://documenter.getpostman.com/view/37961544/2sAXjM4X9z)


## 주요 기능


1. **주문 및 결제 기능**: 주문과 결제, 주문 상태를 변경하고 관리할 수 있습니다.

2. **가게 등록 및 메뉴 관리**: 가게를 등록하고, 메뉴를 추가, 수정, 삭제할 수 있습니다.

3. **사용자 권한 관리**: 사용자 역할에 따라 권한을 부여하고 관리할 수 있는 기능을 제공합니다.

4. **AI 기반 메뉴 설명 추천**: 메뉴 등록 시 AI를 이용해 자동으로 메뉴 설명을 추천하는 기능을 통해 편리성을 극대화했습니다.

5. **공지사항** : 관리자 권한으로 공지사항을 등록하고 관리할 수 있는 기능을 제공합니다.

6. **신고 및 답변** : 사용자와 가게 주인은 신고 글을 작성할 수 있고, 관리자는 답변을 작성하고 관리할 수 있는 기능을 제공합니다.


## 트러블 슈팅

> 배포
> 
- Eureka 서버, 게이트웨이, API 어플리케이션을 도커 컴포즈 파일로 만들어 배포
- 동작시켜보니 서비스간 통신이 제대로 이루어지지 않아 401 에러 발생
- 로그를 확인해보니 gateway에서 호출한 Feign Client 설정이 잘못된것을 확인하여 어플리케이션 이름을 맞춰주고 해결

> Exception Handler
> 
- Custom Exception Handler를 만들면서 파일 이름 때문에 Exception Handler의 순서가 적용되지 않는 문제 발생 → 예외 처리 로직이 예상과 다르게 처리
- `@Order` 어노테이션을 사용하여 Exception Handler의 순서를 지정해 적용
