# Naver2Tistory - Blog Moving program

Read this with Korean: [KR](./readme/README_KR.md)

## Overview
This program reformats post contents to fit the Tistory Editor and upload to the Tistory Blog when you give Naver Blog posting link.

![Exceution gif](./readme/%EC%8B%A4%ED%96%89%20%ED%99%94%EB%A9%B4.gif)

I made this program to automate moving from Naver Blog to Tistory Blog.

## Feature
- Naver Blog HTML crawling and image saving
- Upload to the Tistory Blog in a public state
- Currently supported formats are:
    - text with alignment
    - table
    - picture with caption
    - quotation
    - horizontal divider
    - source code
    - link

## Getting Started
### prerequisite
JDK 17 is need to run this program.

Please install after checking the JDK installation method for each operating system from the [link](https://github.com/hyelie/Naver2Tistory#environment-setup).

### How to run

This program can be run as follows:

Download and unzip the zip file from [link](https://github.com/hyelie/Naver2Tistory/releases/tag/v0.0.1). There are 3 files in the zip file: N2T.jar, config.json, and list.txt

After that, you need to put the value in the configuration file.

### Put Tistory Key in config.json
config.json is environment setting value used by this program.
- APP_ID: App ID set in Tistory Open API
- SECRET_KEY : Secret Key set in Tistory Open API
- BLOG_NAME : The name of the blog to post (xxx part of xxx.tistory.com)

App ID and Secret Key can get as follows: [Link](https://hyelie.tistory.com/entry/Tistory-Open-API-%EC%95%B1-%EB%93%B1%EB%A1%9D) 

Example) config.json
```
{
    "APP_ID" : "de3...",
    "SECRET_KEY" : "de3...",
    "USER_ID" : "hyelie"
}
```

### Put links in list.txt
list.txt contains link of Naver Blog post to be moved to Tistory Blog.

Separate the Naver Blog post links you want to move to Tistory Blog with the newline.

***Note*** - Private posts cannot be read. Please make your post public.

Example) list.tst
```
https://blog.naver.com/jhi990823/222848946415
https://blog.naver.com/jhi990823/222848946416
https://blog.naver.com/jhi990823/222848946417
```

### Execution
Move to unzip directory and run following command.
```
java -jar N2T.jar
```

## Installation
### Requirements

JDK 17

### Environment Setup

Linux
First, install JDK 17 using following command.
```
sudo apt install openjdk-17-jdk
```

Set up the Java development environment in Visual Studio Code as shown in the following link. [Install JDK on Linux, Setup the Java development environment](https://hyelie.tistory.com/entry/GCP%EC%97%90-Java-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85?category=947331)

Windows

Install Java SE 17 on following [link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

### Execution

After cloning the repository, build in the N2T folder.
```
git clone https://github.com/hyelie/Naver2Tistory.git
cd Naver2Tistory/N2T
build
```

## Update history

* 1.0.0 (Current)
    - Naver Blog HTML crawling and image saving
    - Upload to the Tistory Blog in a public state
    - Currently supported formats are:
        - text with alignment
        - table
        - picture with caption
        - quotation
        - horizontal divider
        - source code
        - link
* 1.0.1
    - According to the font size on the Naver Blog, classifictaion function which classify headline, middle title, small title on Tistory Blog whill be added.
* 1.0.2
    - Category selection function will be added.

## Authors
[hyelie](https://github.com/hyelie) - **Jeong Hyeil** - <hyelie@postech.ac.kr>

See the list of [contributors](./CONTRIBUTORS) who participated in this project.

## How to contribute

1. Fork this repository on link [https://github.com/hyelie/Naver2Tistory/fork](https://github.com/hyelie/Naver2Tistory/fork)
2. Create a new branch using command `git checkout -b feature/featureName`.
3. Commit using command `git commit -am 'Add some feature'`.
4. Push to branch using command `git push origin feature/featureName`
5. Send pull request.

## License

Naver2Tistory is available under the MIT license. See the [License file](./LICENSE) for more info.