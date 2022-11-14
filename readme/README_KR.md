# Naver2Tistory - 블로그 이사 프로그램

다른 언어로 읽기: [영어](../README.md)

## 개요
네이버 블로그 링크를 올리면, 해당 포스팅의 내용을 티스토리 에디터에 맞게 형식을 고쳐 포스팅 해 주는 프로그램입니다.

![실행 영상](./%EC%8B%A4%ED%96%89%20%ED%99%94%EB%A9%B4.gif)

블로그를 이사할 때 작성했던 포스팅을 옮기고 싶어 복사 붙여넣기를 하자니 형식이 깨져 하나하나 다 고쳐야 하는 문제를 해결하기 위해 이 프로그램을 만들었습니다.

## 기능
- 네이버 블로그 HTML 크롤링 및 이미지 저장
- 티스토리 블로그에 공개 상태로 업로드
- 현재 지원하는 형식
    - 글(+정렬)
    - 표
    - 사진(+캡션)
    - 인용구
    - 구분선
    - 소스코드
    - 링크

## 사용법
### 요구 사항
이 프로그램을 실행하기 전 JDK 17이 설치되어 있어야 합니다.

[여기](https://github.com/hyelie/Naver2Tistory/blob/main/readme/README_KR.md#%ED%99%98%EA%B2%BD-%EA%B5%AC%EC%84%B1)에서 운영체제별 JDK 설치 방법을 확인 후, 설치해 주세요.

### 실행 방법
이 프로그램은 다음과 같이 실행할 수 있습니다.

[링크](https://github.com/hyelie/Naver2Tistory/releases/tag/v1.0.2)에서 zip파일을 다운로드하고 압축 해제합니다. zip파일 내에는 N2T.jar(또는 N2T.exe), config.json, list.txt 3개의 파일이 있습니다.

이후 환경설정 파일에 값을 넣어야 합니다.

### config.json에 Tistory Key 넣기
config.json은 해당 프로그램이 사용하는 환경설정 값입니다.
- APP_ID : 티스토리 Open API에서 설정하는 App ID
- SECRET_KEY : 티스토리 Open API에서 설정하는 Secret Key
- BLOG_NAME : 포스팅 할 블로그 이름 (xxx.tistory.com에서 xxx 부분)

App ID와 Secret Key는 [다음](https://hyelie.tistory.com/entry/Tistory-Open-API-%EC%95%B1-%EB%93%B1%EB%A1%9D)과 같이 받아옵니다.

예시) config.json
```
{
    "APP_ID" : "de3...",
    "SECRET_KEY" : "de3...",
    "USER_ID" : "hyelie"
}
```

### list.txt에 링크 넣기
list.txt는 티스토리로 옮길 네이버 블로그 포스팅 링크입니다.

티스토리로 옮기고 싶은 네이버 블로그 포스팅 링크들을 줄바꿈으로 구분해서 list.txt에 작성합니다.

유의점 - 비공개 게시글은 읽을 수 없습니다. 게시글을 공개 상태로 설정해 주세요.

예시) list.tst
```
https://blog.naver.com/jhi990823/222848946415
https://blog.naver.com/jhi990823/222848946416
https://blog.naver.com/jhi990823/222848946417
```

### 실행
jar파일이 있는 경우, 압축 해제한 폴더에서 다음 명령어를 실행하세요.
```
java -jar N2T.jar
```

exe 파일을 다운로드 한 경우, 압축 해제한 폴더에서 N2T.exe를 실행하세요.

### Tistory Code 입력
실행 중 나오는 CODE는 '허가하기' 버튼을 누른 창에서 나타나는 url의 'code=xxx'부분에서 xxx를 입력하면 됩니다.

## 시작하기
### 전제 조건
JDK 17

### 환경 구성
Linux

먼저 JDK 17을 설치합니다.
```
sudo apt install openjdk-17-jdk
```

다음 링크와 같이 Visual Studio Code에서 자바 개발환경을 설정합니다. [Linux에 JDK 설치, Java 개발환경 설정](https://hyelie.tistory.com/entry/GCP%EC%97%90-Java-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85?category=947331)

Windows

[링크](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)에서 Java SE 17을 설치합니다.

### 설치 및 실행

해당 저장소를 클론한 후, N2T폴더에서 빌드합니다.
```
git clone https://github.com/hyelie/Naver2Tistory.git
cd Naver2Tistory/N2T
// build
```

## 업데이트 내역

* 1.0.0
    - 네이버 블로그 HTML 크롤링 및 이미지 저장
    - 티스토리 블로그에 공개 상태로 업로드
    - 지원하는 형식
        - 글(+정렬)
        - 표
        - 사진(+캡션)
        - 인용구
        - 구분선
        - 소스코드
        - 링크

* 1.0.1
    - (Fixed) URL 인식 오류 해결
    - (Fixed) 포스트에 <, >, ", & 기호가 있는 경우 올라가지 않던 문제 해결
    - (Changed) 티스토리 블로그에 **비공개** 상태로 업로드
* 1.0.2 (현재)
    - (Fixed) 코드에 << EOF와 같이 HTML에서 끝을 나타내는 구문이 있는 경우 올라가지 않던 문제 해결
* 1.1.0
    * 네이버 블로그의 글 폰트 사이즈에 따라 티스토리의 대제목, 중제목, 소제목으로 분류 기능 추가 예정
* 1.2.0
    * list.txt에 카테고리를 입력해 카테고리 자동 선택 기능 추가 예정

## 저자
[hyelie](https://github.com/hyelie) - **정혜일** - <hyelie@postech.ac.kr>

해당 프로젝트에 참여한 [기여자 목록](./CONTRIBUTORS)

## 기여 방법

1. [https://github.com/hyelie/Naver2Tistory/fork](https://github.com/hyelie/Naver2Tistory/fork)에서 해당 레포지토리를 포크합니다.
2. `git checkout -b feature/featureName` 명령어로 새 브랜치를 만듭니다.
3. `git commit -am 'Add some feature'` 명령어로 커밋합니다.
4. `git push origin feature/featureName` 명령어로 브랜치에 푸시합니다.
5. 풀 리퀘스트를 보내주세요.

## License

Naver2Tistory는 MIT 라이센스를 제공합니다. [라이센스 파일](./LICENSE)에서 자세한 정보를 확인하세요.
