# Naver2Tistory - 블로그 이사 프로그램
## 개요
네이버 블로그 링크를 올리면, 본문에 있는 내용을 티스토리 에디터에 맞게 형식을 고쳐 포스팅 해 주는 프로그램

이미지(실행 사진)

블로그를 이사할 때, 작성했던 포스팅을 옮기고 싶어 복사 붙여넣기를 하자니 형식이 깨져 보기 좋지 않습니다.

이 프로그램은 해당 문제를 해결하기 위해 만들게 되었습니다.   

## 기능
- 네이버 블로그 HTML 크롤링 및 이미지 저장
- 티스토리 블로그에 공개 상태로 업로드
- 현재 지원하는 형식 : 글(+정렬), 표, 사진(+캡션), 인용구, 구분선, 소스코드, 링크

## 시작하기
이 프로그램은 다음과 같이 실행할 수 있습니다.

다음 [링크]()에서 zip파일을 다운로드하고 압축 해제한다.

이후 환경설정 파일에 값을 넣어야 한다. config.json

### 2. 환경설정 값 채워넣기
config.json에 값 넣기
Tistory key는 이렇게 받아온다. [링크](https://hyelie.tistory.com/entry/Tistory-Open-API-%EC%95%B1-%EB%93%B1%EB%A1%9D)

config.json의 값은 이런이런 값이고, 위에서 받은 것으로 값을 채워넣는다.

### 3. list.txt에 값 넣기
list.txt에 값을 채워넣는다.

### 4. 실행한다.

## 설치
### 요구 환경
JDK 17

### 환경 구성 및 실행

환경 설정 방법

Linux

먼저 JDK 17을 설치한다.
```
sudo apt install openjdk-17-jdk
```

[JDK 설치, Java 개발환경 설정](https://hyelie.tistory.com/entry/GCP%EC%97%90-Java-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85?category=947331)와 같이 Visual Studio Code에서 자바 개발환경을 설정한다.

해당 저장소를 클론한 후, N2T폴더에서 빌드하면 된다.
```
git clone https://github.com/hyelie/Naver2Tistory.git
cd Naver2Tistory/N2T
```

윈도우

먼저 JDK 17을 설치한다. [설치 링크](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

이클립스 또는 인텔리제이를 사용한다면 빈 프로젝트 생성 후 N2T/lib에 있는 파일들은 라이브러리에 넣고, N2T/src에 있는 자바 파일들은 소스코드에 넣고 실행하면 된다.

## 업데이트 내역

* 1.0.0 (현재)
    * 첫 배포
* 1.0.1
    * 네이버 블로그의 글 폰트 사이즈에 따라 티스토리의 대제목, 중제목, 소제목으로 분류 기능 추가 예정.
* 1.0.2
    * list.txt에 카테고리를 입력해 카테고리 자동 선택 기능 추가 예정.

## 저자
  - [hyelie](https://github.com/hyelie) - **정혜일** - <hyelie@postech.ac.kr>
