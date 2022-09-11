# rest-api server : based on my standard(나의 표준 rest-api 구현)

## 1. 설명
* 기존의 프로젝트에 기본으로 들어가던 백엔드 틀(표준)을 rest 형식으로 재구성
* 유저-게시판-댓글 이 존재
* 유저는 스프링시큐리티 라이브러리 사용.
* 게시판과 댓글은 서로 부모 자식관계이며, 게시글 삭제시 엮여있는 댓글도 같이 삭제됨.

## 2. api 설계
#### board
* /api/home - get(페이징파라미터 걸어주어야함) : 모든 게시글
* /api/post - get : 게시글 등록
* /api/post - post : 게시글 등록
* /api/{id} - get : 게시글 상세조회
* /api/{id} - post : 게시글 수정(body에 id값 넣어야함)
* /api/delete/{id} - post : 게시글 삭제

#### comment
* /api/{boardId}/comment - get(페이징 없음) : 모든 댓글
* /api/{boardId}/comment - post : 댓글 등록
* /api/{boardId}/comment/{id} - post : 댓글 삭제

## 3. json body
#### board
<pre>
{
    "content" : "first text",
    "good" : 1,
    "title" : "test1",
    "writer" : "chan"
}

{
    "content" : "second text",
    "good" : 2,
    "title" : "test2",
    "writer" : "kim"
}

{
    "content" : "third text",
    "good" : 3,
    "title" : "test3",
    "writer" : "park"
}
</pre>

#### comment
<pre>
{
    "content" : "댓글",
    "user" : "chan",
    "boardId" : 1
}
</pre>

## 3. http status
* 글 작성완료시 201(created)
* 수정, 삭제시 200(ok)

## 배운점

### 1. 수정(거의 복습)
jpa를 쓰기때문에 id값과 createdDate는 자동입력됨
다만, 당연하게도 수정시에는 json에 id값 넣어주어야함.
createdDate처럼 업데이트 하지 않을 필드는 @Column(updatable = false)을 걸어주면
다시 값을 집어넣지않아도 수정시에 원래 값이 업데이트되지않고 남아있다.

### 2. method에 따른 좋은 restful 설계 불가능
restFul api의 좋은 설계는 url이 다 같고 method만 달라지는것인데,
요즘은 메소드를 get과 post밖에 쓰지않는다.
따라서 생성, 수정, 삭제 모두 같은 method, 같은 url을 사용하면 에러가 발생하게되면
따라서 어떤 동작인지 메소드를 url에 기재해주면 해당 에러는 사라진다.
결론적으로 get과 post만 쓰게되면 restful한 api를 설계하기 힘들다.

### 3. json Long 타입 넘기기
Long 타입을 json으로 넘길때에는 정수던 실수던 모두 number형식으로 넘기기 때문에
(자바스크립트에도 당연하게도 정수 실수 구분없이 모두 number 형이다.)
그냥 1 써주면되고 뒤에 L을 붙이지 않아도된다.

로그인은 폼로그인이 아닌 자체로그인 시스템.

{
"email" : "yc4852@gmail.com",
"password" : "1234"
}