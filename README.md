# P2PJSP
jsp, servlet을 이용한 파일 저장, 공유 프로그램

## 데이터베이스 : Oracle DB

<details>
<summary>BOARD 테이블 (게시판 글) </summary>
<div markdown="1">
<pre>
<code>

CREATE TABLE "JOEBROOKS"."BOARD" 
(	
	"TITLE" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"CONTENT" VARCHAR2(1000 BYTE) NOT NULL ENABLE, 
	"REG_DATE" DATE NOT NULL ENABLE, 
	"VIEW_COUNT" NUMBER NOT NULL ENABLE, 
	"ID" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"IDX" NUMBER NOT NULL ENABLE, 
	"SHAREFOLDER" VARCHAR2(50 BYTE), 
	 CONSTRAINT "BOARD_PK" PRIMARY KEY ("IDX")
)

</pre>
</code>
</div>
</details>


<details>
<summary>FILELIST 테이블 (개인 파일) </summary>
<div markdown="1">
<pre>
<code>

 CREATE TABLE "JOEBROOKS"."FILELIST" 
   (	
	"ID" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"FILENAME" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"FILESIZE" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"FILETYPE" VARCHAR2(20 BYTE) NOT NULL ENABLE, 
	"FILEPATH" VARCHAR2(100 BYTE), 
	"AUTH" VARCHAR2(10 BYTE), 
	 CONSTRAINT "FILELIST_FK1" FOREIGN KEY ("ID")
	  REFERENCES "JOEBROOKS"."MEMBER" ("ID") ON DELETE CASCADE ENABLE
   )

</pre>
</code>
</div>
</details>


<details>
<summary>MEMBER 테이블 (회원 정보) </summary>
<div markdown="1">
<pre>
<code>

CREATE TABLE "JOEBROOKS"."MEMBER" 
(	
	"ID" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"PW" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	 CONSTRAINT "MEMBER_PK" PRIMARY KEY ("ID")
  )

</pre>
</code>
</div>
</details>


<details>
<summary>REPLY 테이블 (게시판 댓글) </summary>
<div markdown="1">
<pre>
<code>

CREATE TABLE "JOEBROOKS"."REPLY" 
   (	
	"IDX" NUMBER NOT NULL ENABLE, 
	"ID" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"CONTENT" VARCHAR2(300 BYTE) NOT NULL ENABLE, 
	"REG_DATE" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	 CONSTRAINT "REPLY_FK1" FOREIGN KEY ("IDX")
	  REFERENCES "JOEBROOKS"."BOARD" ("IDX") ENABLE
   ) 
</pre>
</code>
</div>
</details>

## REST API
|Task|Method|Path|
|-----------|-----|--------|
|로그인|POST|/login.do/{id,pw}|
|로그아웃|POST|/logout.do|
|회원가입|POST|/join.do|
|내 파일 보여주기|GET|/showall.do/{type,page,first}|
|파일 삭제|GET|/filedelete.do/{fileName}|
|파일 다운로드|POST|/filedownload.do/{fileName}|
|파일 업로드|POST|/fileupload.do/{fileName}|
|폴더 삭제|GET|/folderdelete.do/{folderName}|
|하위, 상위 폴더로 이동|GET|/foldermove.do/{query, folderName}|
|폴더 만들기|GET|/folderupdate.do/{folderName}|
|게시판 글 목록 보여주기|GET|/showboard.do/{page, search}|
|작성한 글 보여주기|GET|/showcontent.do/{idx, title, redirect, commentPage}
|게시글 작성|POST|/boardwrite.do/{state}
|게시글 삭제|GET|/boardremove.do/{idx}|
|댓글 작성|POST|/bardcomment.do/{idx, title}|
|공유 폴더 보여주기|GET|/showguest.do/{id, folderName}|

### 쿼리스트링 부연설명
#### showall.do
*  type=(all, video, image, audio), first=(파일 view에 첫 접속인지 판단, 접속했던 폴더 경로 초기화) 

#### foldermove.do
*  , query=(back:뒤로가기, search:폴더 접근)

#### showboard.do
*  page=(null이면 1페이지로 설정), search=(게시글 검색시 사용)

#### showcontent.do
* idx=(인덱스), title=(제목), redirect=(리다이렉트 여부 표시, 조회수에 영향), commentPage=(댓글 페이지, null일시 1로 설정)

#### boardwrite.do
*  state=(write:작성 준비, complete:작성 완료)

## 개발 환경
* Java, TomCat
* HTML, CSS, JavaScript
* Ajax, bootstrap
* Oracle DB

## 구현 기능
### 클라우드
* 클라우드 스토리지 서비스
* 파일 업로드, 삭제, 다운로드
* 폴더 만들기, 삭제
* 파일을 종류별로 확인 가능 => 사진, 동영상, 사운드 파일
* 게스트 폴더 생성 기능 => 회원들과 공유 가능한 폴더

### 게시판
* 파일 공유 게시판
* 게시글 CRUD 
* 댓글 작성기능
* 게시글 검색 기능
* 공유 링크 기능으로 자신의 클라우드 게스트 폴더 접속 링크를 올릴 수 있음.

## 작동 모습
### [홈, 비회원 접근 제한, 로그인]
<div>
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757382-1038b000-03d5-11eb-9d47-a5afe1708dc5.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757384-1169dd00-03d5-11eb-8792-3daf1906f64e.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757386-12027380-03d5-11eb-8938-152c6601ec1e.png">
</div>

### [게시판, 페이지 이동, 검색]
<div>
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757387-12027380-03d5-11eb-90f1-7b25ae83e2c4.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757388-129b0a00-03d5-11eb-9a3c-69af66c6e2a8.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757390-129b0a00-03d5-11eb-9845-022960c9c52d.png">
</div>

### [게시글 보기, 공유 링크 게시글, 공유 스토리지 접속]
<div>
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757393-1333a080-03d5-11eb-9d63-273793513063.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757395-13cc3700-03d5-11eb-9004-073b992c14d8.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757397-13cc3700-03d5-11eb-9e2b-4b44a1cd4c1c.png">
</div>

### [스토리지 보기, '공부' 폴더 확인, 파일 업로드 과정]
<div>
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757399-1464cd80-03d5-11eb-9392-f146cb22394d.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757401-14fd6400-03d5-11eb-90bb-3ff89ba63260.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757404-14fd6400-03d5-11eb-995d-4580ac98b4b4.png">
</div>

### [파일 다운로드 및 삭제, 폴더 삭제]
<div>
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757406-1595fa80-03d5-11eb-804e-e43f6db7af7e.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757408-162e9100-03d5-11eb-934d-0b8cc6bb23c4.png">
</div>

### [파일 분류, 동영상 파일만 보기, 이미지 파일만 보기]
<div>
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757410-162e9100-03d5-11eb-935f-9ec6fc9e0fce.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757411-16c72780-03d5-11eb-8626-be61b805a2ab.png">
  <img width="700" src="https://user-images.githubusercontent.com/59993347/94757412-16c72780-03d5-11eb-896d-1b7c59750d98.png">
</div>