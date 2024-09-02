## 프로젝트 개요

• **프로젝트 명**: 풍선 딜리버리

• **프로젝트 설명**: ‘풍선 딜리버리’는 주문 관리 플랫폼입니다. 사용자는 이 플랫폼을 통해 배달 및 포장 주문을 손쉽게 처리할 수 있으며, 가게 주인은 가게와 메뉴를 관리할 수 있습니다. 또한, 메뉴 등록 시 AI가 자동으로 메뉴 설명을 추천해주는 기능을 제공하여 사용 편의성을 높였습니다. 

• **프로젝트 기간**: 2024년 8월 22일 - 2024년 9월 2일

  <br>
  
## 팀원

|박주창|김진선|이지선|
|:---:|:---:|:---:|
|[joo-chang](https://github.com/joo-chang)|[kimzinsun](https://github.com/kimzinsum)|[easyxun](https://github.com/easyxun)|
|![](https://avatars.githubusercontent.com/u/63954779?v=4")|![](https://avatars.githubusercontent.com/u/122031650?v=4")|![](https://avatars.githubusercontent.com/u/107982536?v=4)|
|Leader|||

<br>

## 프로젝트 목적 

이번 프로젝트의 주요 목적은 MSA(Microservices Architecture)를 실습하며, Redis를 이용한 빠른 권한 체크, 서비스 간의 통신을 관리하는 Eureka 서버 및 Gateway 서버를 직접 구현해보는 것입니다. 이를 통해 분산 시스템 환경에서의 개발 역량을 높이고, 성능 최적화 및 서비스 간의 효율적인 통신 방법을 익히고자 하였습니다.

• **핵심 기능**:

1. **배달 및 포장 주문 기능**: 사용자들은 배달과 포장 주문을 손쉽게 선택하고 진행할 수 있습니다.

2. **주문 정보 및 결제 정보 확인**: 실시간으로 주문 정보와 결제 상태를 확인할 수 있습니다.

3. **가게 등록 및 메뉴 관리**: 가게를 플랫폼에 등록하고, 메뉴를 추가, 수정, 삭제할 수 있습니다.

4. **사용자 권한 관리**: 사용자 역할에 따라 권한을 부여하고 관리할 수 있는 기능을 제공합니다.

5. **AI 기반 메뉴 설명 추천**: 메뉴 등록 시 AI를 이용해 자동으로 메뉴 설명을 추천하는 기능을 통해 편리성을 극대화했습니다.  

<br>

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



  

  

## 주요 기능

  

## 트러블 슈팅
