# 🐾 모르는 개 산책



## 목차
- [프로젝트 소개](#프로젝트-소개)
- [팀원](#팀원)
- [배포된 인스턴스 주소](#배포된-인스턴스-주소)
- [ERD](#ERD)
- [API 명세서](#API-명세서)
- [시작 가이드](#시작-가이드)
- [사용 기술](#사용-기술)
- [서비스 주요 기능](#서비스-주요-기능)

## 프로젝트 소개



내 가족을 챙기는 하나의 방법, “모르는개 산책”(반려견 산책 매칭) 플랫폼입니다. <br>
기획의도 ~~



## 👨‍👨‍👧‍👧 팀원


| [박영규](https://github.com/pyg410)                          | [이승건](https://github.com/DEVdongbaek)                      | [곽민주](https://github.com/MinjuKwak01)                         |
|-----------------------------------------------------------|-----------------------------------------------------------|-----------------------------------------------------------|
| ![](https://avatars.githubusercontent.com/u/74770498?v=4) | ![](https://avatars.githubusercontent.com/u/102592414?v=4) | ![](https://avatars.githubusercontent.com/u/105683527?v=4) |
| Backend (테크리더)                                            |Backend |Backend 



## 🔨 배포된 인스턴스 주소



<br>

📆 프로젝트 기간 : 2023.09 ~ 2023.11



## ☁️ ERD



<br>
<img width="982" alt="ERD이미지" src="https://github.com/Step3-kakao-tech-campus/Team17_BE/assets/105683527/ed135e62-49e1-4c1b-9f3e-69157417380b">

## 📒 API 명세서



[📒 API 명세서 노션](https://www.notion.so/ERD-API-d8322a13a7ff471391947d075e2f4d5f)

## 시작 가이드



```
Java 17, Spring 3.1.4
```
1. 프로젝트 클론
```
git clone https://github.com/Step3-kakao-tech-campus/Team17_BE.git
cd Team17_BE
cd dog-walking
```

2. 실행
```
./gradlew build
cd build
cd libs
java -jar dog-walking-0.0.1-SNAPSHOT.jar
```

## 🛠 사용 기술


- Java 17
- Spring Boot
- Spring Framework
- Spring Boot Starter
- Spring Data JPA
- Spring Security
- Socket.io
- Lombok
- H2 Database
- MySQL
- JUnit 5
- gradle


## 화면구성


| 메인 화면 (로그인 전)                                                                                                            | 메인 화면 (로그인 후)                                                                                                            |
| -------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
|  |  |
| <div align="center"><b>회원가입</b></div>                                                                                        | <div align="center"><b>로그인</b></div>                                                                                          |
|  | |
| <div align="center"><b>카카오 로그인 로딩 화면</b></div>                                                                         | <div align="center"><b>비밀번호 재설정</b></div>                                                                                 |
|  | |
| <div align="center"><b>그룹 생성</b></div>                                                                                       | <div align="center"><b>그룹 페이지</b></div>                                                                                     |
|  |  |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/287c99de-2e6b-48b2-bc95-5a9638ea6a49" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/eb2d3a1a-ccfe-4d42-b842-bf829d91124e" width="400"> |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/045e3617-2523-4a4a-b032-0c942abc3ef9" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/2be639a6-ce8a-4a31-9b4d-6360e97f317f" width="400"> |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/1cacb976-b387-4daf-bb38-62b63bdeef86" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/cb3f34b9-1111-4ca8-bbf6-c8086ff6c8be" width="400"> |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/bdd558d1-04c1-46a5-a051-5e22bd3e40bf" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/4d73c338-4965-4859-9070-cd9a9c4e7f68" width="400"> |
| <div align="center"><b>그룹 가입 페이지</b></div>                                                                                | <div align="center"><b>내 문서 기여 목록 페이지</b></div>                                                                        |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/e185380b-ca4f-499f-a8b0-3daf2b62b33a" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/def24d52-ce88-4924-b961-93bc00b322a7" width="400"> |
| <div align="center"><b>그룹원 목록 조회</b></div>                                                                                | <div align="center"><b>그룹원 초대</b></div>                                                                                     |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/cbd21b85-096c-40cf-a6bd-a875f22d7848" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/afa67564-6032-4a14-a5f2-be06ff3f93f1" width="400"> |
| <div align="center"><b>그룹 검색 결과</b></div>                                                                                  | <div align="center"><b>없는 페이지 검색 결과</b></div>                                                                           |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/4803ccef-a906-487a-add6-4991d22b66c1" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/c888a306-df4a-4783-a234-6607e4eb3986" width="400"> |
| <div align="center"><b>마이페이지</b></div>                                                                                      | <div align="center"><b>그룹 마이페이지</b></div>                                                                                 |
| <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/efa55677-5963-40f0-b043-711990bf1ad0" width="400"> | <img src="https://github.com/Step3-kakao-tech-campus/Team8_FE/assets/78250089/87dbe659-457a-40fb-91c7-eb87ee7f8c64" width="400"> |

<br/>


## 🎯 서비스 주요 기능


- ### 📌 회원가입 및 로그인

    - **JWT**를 이용한 로그인 방식을 선택했습니다. 
    - **refresh token**을 사용하여 access token의 유효 기간을 짧게 하여 보안을 강화하면서도
      사용자가 자주 로그아웃되지 않도록하였습니다.


- ### 📌 메인페이지 필터링, 검색, 정렬, 페이징

    - **커서 기반 페이징**을 이용하여 **무한스크롤**을 구현하였습니다. 두 번째 요청부터 key값을 이용하여 다음 페이지를 요청합니다.
        - 해당 key값은 현재 위치와 리스트 마지막 공고글의 위치의 거리 차이 값 입니다.
	- 메인페이지는 사용자의 현재 위치를 기반으로 각 공고글에 저장된 위치와 거리가 가까운 순으로 정렬됩니다.
    - **필터링 기능** (강아지 사이즈, 견종)을 이용하여 원하는 조건에 맞는 공고글만 볼 수 있습니다.
        - 강아지 사이즈와 견종 항목을 여러 개 선택했을 때, OR 조건으로 필터링 됩니다.
    - **제목 검색 기능**을 이용하여 원하는 키워드를 포함한 공고글만 볼 수 있습니다.


- ### 📌 공고글 작성, 강아지 불러오기 및 조회

    - 공고글에 본인이 이미 등록해놓은 강아지를 불러올 수 있습니다.
    - 제목, 현재 위치, 시작시간, 종료시간, 멍코인, 특이사항을 입력하여 공고글을 작성할 수 있습니다. 
  
- ### 📌 프로필 등록, 수정 및 조회

    - 
- ### 강아지 등록, 수정 및 조회

    -
- ### 지원서 작성 및 조회

    - 
- ### 채팅방 목록 조회, 채팅

    - 
- ### 산책 시작, 산책 허락, 산책 종료

	-

- ### 리뷰 작성 및 조회

    - 

- ### 결제

    - 
