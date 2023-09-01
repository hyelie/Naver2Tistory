# Naver2Tistory - Blog Migration Program

[한국어](./readme/README_KR.md)로 읽기

<p align="center">
    <img src="/readme/logo.png">
</p>

## Overview
This program reformats post contents to fit the Tistory Editor and uploads it to the Tistory Blog when you provide Naver Blog posting link.

![Exceution gif](./readme/%EC%8B%A4%ED%96%89%20%ED%99%94%EB%A9%B4.gif)

I created this program to automate the process of migrating from Naver Blog to Tistory Blog.

## Feature
- Naver Blog HTML crawling and image saving
- Upload to the Tistory Blog in a public state
- Currently supported formats include:
    - Text (+ alignment, + bold/tilt/underline/strikethrough)
    - Table
    - Picture with caption
    - Quotation
    - Horizontal dividers
    - Source code
    - Links

## Usage
### Requirements
JDK 17 is required to run this program.

Please install it after checking the JDK installation method for your operating system from the [link](https://github.com/hyelie/Naver2Tistory#environment-setup).

### How to run

This program can be run as follows:

Download and unzip the zip file from [this link](https://github.com/hyelie/Naver2Tistory/releases/tag/v1.0.2). There are 3 files in the zip file: N2T.jar(or N2T.exe), /config/tistory.json, and list.txt

Next, put the value in the configuration file.

### Adding Tistory Key in /config/tistory.json
/config/tistory.json contains environment setting value used by this program.
- APP_ID: App ID set in Tistory Open API
- SECRET_KEY : Secret Key set in Tistory Open API
- BLOG_NAME : The name of the blog to post (xxx part of xxx.tistory.com)

You can obtain App ID and Secret Key as follows: [Link](https://hyelie.tistory.com/entry/Tistory-Open-API-%EC%95%B1-%EB%93%B1%EB%A1%9D) 

Example) /config/tistory.json
```
{
    "APP_ID" : "de3...",
    "SECRET_KEY" : "de3...",
    "USER_ID" : "hyelie"
}
```

### Adding links in list.txt
list.txt contains link of Naver Blog post to be moved to Tistory Blog.

Separate the Naver Blog post links you want to move to Tistory Blog with the new line.

***Note*** - Private posts cannot be read. Please make your post public.

Example) list.txt
```
https://blog.naver.com/jhi990823/222848946415
https://blog.naver.com/jhi990823/222848946416
https://blog.naver.com/jhi990823/222848946417
```

### Execution
If you downloaded the .jar file, navigate to the unzipped directory and run the following command.
```
java -jar N2T.jar
```

If you downloaded the .exe file, execute the N2T.exe file in the unzipped folder.

### Enter Tistory Code
For the `CODE` appears during execution, enter xxx part from the 'code=xxx' URL that appears in the window where you clicked the 'allow' button.

## Getting Started
### Prerequisites

JDK 17

### Environment Setup

- Linux
First, install JDK 17 using the following command.
```
sudo apt install openjdk-17-jdk
```

Set up the Java development environment in Visual Studio Code as shown in the following [link](https://hyelie.tistory.com/entry/GCP%EC%97%90-Java-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85?category=947331).

- Windows

Install Java SE 17 on the following [link](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

### Installation

After cloning the repository, build the project in the Naver2Tistory folder.
```
git clone https://github.com/hyelie/Naver2Tistory.git
cd Naver2Tistory
// build
```

## Update history

* 1.0.0
    - Naver Blog HTML crawling and image saving
    - Upload to the Tistory Blog in a public state
    - Currently supported formats are:
        - Text with alignment
        - Table
        - Picture with caption
        - Quotation
        - Horizontal dividers
        - Source code
        - Links
* 1.0.1
    - (Fixed) URL recognition error resolved
    - (Fixed) Fixed an issue where posts would not be uploaded if there were <, >, ", & symbols in the post.
    - (Changed) uploaded to the Tistory blog in a **private** state
* 1.0.2
    - (Fixed) Fixed an issue where posts would not be uploaded if there were << EOF symbols in the code block.
* 1.0.3 (Current)
    - (Feat) Added bold/tilt/underline/strikeout feature to text format.
    - (Removed) Removed image saving feature.
* 1.1.0
    - According to the font size on the Naver Blog, classifictaion function which classify headline, middle title, small title on Tistory Blog will be added.
* 1.2.0
    - Category selection function will be added.

## Authors
[hyelie](https://github.com/hyelie) - **Jeong Hyeil** - <hyelie@postech.ac.kr>

See the list of [contributors](./CONTRIBUTORS) who participated in this project.

## How to contribute

1. Fork this repository on link [https://github.com/hyelie/Naver2Tistory/fork](https://github.com/hyelie/Naver2Tistory/fork)
2. Create a new branch using command `git checkout -b feature/featureName`.
3. Commit using command `git commit -am 'Add some feature'`.
4. Push to branch using command `git push origin feature/featureName`
5. Send a pull request.

## License

Naver2Tistory is available under the MIT license. See the [License file](./LICENSE) for more info.
