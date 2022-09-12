# rest-api server : based on my standard(나의 표준 rest-api 구현)

## 1. 설명
* 기존의 프로젝트에 기본으로 들어가던 백엔드 틀(표준)을 rest 형식으로 재구성
* 유저-게시판-댓글 이 존재
* 유저는 스프링시큐리티 라이브러리 사용.
* 게시판과 댓글은 서로 부모 자식관계이며, 게시글 삭제시 엮여있는 댓글도 같이 삭제됨.
* 회원가입은 기존의 방식과 같으나 로그인 기존의 form-login방식이 아닌 자체 로그인 방식으로 설계함

## 2. 로그인 설계
* 로그인을 하게되면 dto와 세션을 넘긴다.
* 이메일로 유저엔티티를 가져오고 UsernamePasswordAuthenticationtoken을 만들어 토큰을 발급한다.
* 해당토큰을 시큐리티 컨텍스트홀더에 집어넣는다.
* 그리고 세션또한 set으로 컨텍스트홀더를 설정해준다.
* 가져온 엔티티의 auth와 이메일을 바탕으로 ADMIN 또는 MEMBER 를 확인하고 GrantedAuthority를 설정해준다.
* UserDetailsService와 겹치는 부분도 존재하지만 일단은 모두다 작성하였다.
* principal 과 같이 컨텍스트 홀더나 Authentication을 사용하는것들은 모두 테스트 하였고 모두 성공적으로 사용가능했다.

## 3. 게시글 수정/삭제 설계
* 게시글 상세조회 시 현재 로그인한 유저 이름을 클라이언트로 전송한다.
* 게시글 상세조회 시 게시글 수정/삭제가 가능한데,
* 게시글 작성자와 현재 로그인 유저가 같을 경우 수정/삭제버튼을 띄운다.
* 다를경우 수정/삭제버튼을 보이지 않게 처리한다.

## 4. 댓글 삭제 설계
* 댓글 삭제같은 경우 현재 로그인한 객체와 클라이언트로부터 받은 댓글 작성자를 비교한다.
* 서로 같다면 댓글 삭제처리한다.
* 다를 경우 bad resquest를 보낸다.

## 5. api 설계
#### users
* /user/signup - get
* /user/signup - post
* /user/login - get
* /user/login - post
* /user/login/result - get
* /user/logout/result - get

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
* /api/{boardId}/comment/delete/{id} - post : 댓글 삭제

## 6. json body
#### users
<pre>
{
    "email" : "yc4852@gmail.com",
    "password" : "1234"
}

{
    "email" : "admin@restStandard.com",
    "password" : "1234"
}
</pre>
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

## 7. http status
* 글 작성완료시 201(created)
* 수정, 삭제시 200(ok)

## 8. 배운점

### 1. 수정(거의 복습)
* jpa를 쓰기때문에 id값과 createdDate는 자동입력됨
* 다만, 당연하게도 수정시에는 json에 id값 넣어주어야함.
* createdDate처럼 업데이트 하지 않을 필드는 @Column(updatable = false)을 걸어주면됨.

### 2. json Long 타입 넘기기
* Long 타입을 json으로 넘길때에는 정수던 실수던 모두 number형식으로 넘긴다.
* (자바스크립트에도 당연하게도 정수 실수 구분없이 모두 number 형이다.)
* 그냥 값을 써주면되고 자바처럼 뒤에 L을 붙이지 않아도된다.

### 3. 다양한 값 넘기기
* 넘겨야 하는 값이 한개가아니라 여러개(리스트말고)일때는 map을 사용하면 효과적이다.
* 예를 들어 게시글을 수정/삭제시에는 principal.getname으로 현재 권한을 가지고 있는 주체를 넘겨서
  게시글 작성자와 비교해주어야하는데, 이때 map을 사용해서 권한주체와 게시글을 map에 넣어 보내주면된다.