# 모르는 개 산책

## 목차
- [프로젝트 소개](#프로젝트-소개)
- [팀원](#팀원)
- [배포된 인스턴스 주소](#배포된-인스턴스-주소)
- [ERD](#ERD)
- [사용 기술](#사용-기술)
- [서비스 주요 기능](#서비스-주요-기능)

### 프로젝트 소개
* * *
<br>

### 배포된 인스턴스 주소
* * *
<br>

### ERD
* * *
<br>
<img width="982" alt="ERD이미지" src="https://github.com/Step3-kakao-tech-campus/Team17_BE/assets/105683527/ed135e62-49e1-4c1b-9f3e-69157417380b">

### 팀원
* * *
| [박영규](https://github.com/pyg410)  | [이승건](https://github.com/DEVdongbaek)                      | [곽민주](https://github.com/MinjuKwak01)                         |
|------|-----------------------------------------------------------|-----------------------------------------------------------|
|![](https://avatars.githubusercontent.com/u/35277854?v=4)| ![](https://avatars.githubusercontent.com/u/55571682?v=4) | ![](https://avatars.githubusercontent.com/u/59433441?v=4) |![](https://avatars.githubusercontent.com/u/57715601?v=4)|




### 사용 기술
* * *
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<br>
<br>

### 서비스 주요 기능
* * *

- ###### 회원가입 및 로그인

    - **JWT**를 이용한 로그인 방식을 선택했습니다. 
    - **refresh token**을 사용하여 access token의 유효 기간을 짧게 하여 보안을 강화하면서도
      사용자가 자주 로그아웃되지 않도록하였습니다.


- ###### 메인페이지 필터링, 검색, 정렬, 페이징

    - **커서 기반 페이징**을 이용하여 **무한스크롤**을 구현하였습니다. 두 번째 요청부터 key값을 이용하여 다음 페이지를 요청합니다.
        - 해당 key값은 현재 위치와 리스트 마지막 공고글의 위치의 거리 차이 값 입니다. 
        - 불러올 공고글이 더 이상 없다면 key값을 -1로 설정하여 더 이상 요청하지 않습니다.
	- 메인페이지는 사용자의 현재 위치를 기반으로 각 공고글에 저장된 위치와 거리가 가까운 순으로 정렬됩니다.
    - **필터링 기능** (강아지 사이즈, 견종)을 이용하여 원하는 조건에 맞는 공고글만 볼 수 있습니다.
        - 강아지 사이즈와 견종 항목을 여러 개 선택했을 때, OR 조건으로 필터링 됩니다.
    - **제목 검색 기능**을 이용하여 원하는 키워드를 포함한 공고글만 볼 수 있습니다.


- ###### 공고글 작성, 강아지 불러오기 및 조회

    - 공고글에 본인이 이미 등록해놓은 강아지를 불러올 수 있습니다.
    - 제목, 현재 위치, 시작시간, 종료시간, 멍코인, 특이사항을 입력하여 공고글을 작성할 수 있습니다. 
- ###### 프로필 등록, 수정 및 조회

    - 
- ###### 강아지 등록, 수정 및 조회

    -
- ###### 지원서 작성 및 조회

    - 
- ###### 채팅방 목록 조회, 채팅

    - 
- ###### 산책 시작, 산책 허락, 산책 종료

	-

- ###### 리뷰 작성 및 조회

    - 

- ###### 결제

    - 
