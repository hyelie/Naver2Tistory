## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## 실행 전
N2T/config.json에 있는 정보를 채워 넣어야 합니다.
APP_ID : 티스토리 Open API에서 설정하는 App ID
SECRET_KEY : 티스토리 Open API에서 설정하는 Secret Key
BLOG_NAME : 포스팅 할 블로그 이름

## 버전 변경
네이버 블로그 스마트 에디터 버전, 또는 티스토리 에디터 버전 변경으로 인해 HTML이 수정되었을 경우 Converter.java의 dfsDOM을 수정하면 됨.

## 미완성 TODO
1) readme 작성, 프로그램 소개, 오픈소스화 배포.

## 다음 업데이트
1) 글 폰트 따서 대/중/소제목으로 선택. paragraph에 size가 있으니까 골라서 24 대제목 19 중제목 16 소제목으로. 이건 json으로 빼서 사용자가 선택 가능하게. - 1.0.1
2) category 선택 기능도 - 1.0.2