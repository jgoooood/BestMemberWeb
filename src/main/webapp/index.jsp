<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jstl라이브러리 코드삽입 -->
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>베스트 멤버 웹</title>
	</head>
	<body>
<!-- 	헤더를 상속받아서 사용가능 -->
		<jsp:include page="./header.html"></jsp:include>
		<h1>베스트 멤버 웹</h1>
		<h2>로그인 페이지</h2>
<%-- 		<c:if test="${sessionScope.memberId != null }"></c:if> --%>
		<c:if test="${sessionScope.memberId ne null }">
			${sessionScope.memberName}님 환영합니다. <a href="/member/logout.do">로그아웃</a>
<%-- 			<a href="/member/myInfo.do?memberid=${memberId }"> session의 로그인한 아이디를 넣어줌--%>
			<a href="/member/myInfo.do?member-id=${memberId }">마이페이지</a>
			<!-- 주의 : myInfo는 마이페이지 클릭에 대한 결과페이지임 -->
			<!-- 폼태그의 쿼리스트링은 name속성값을 입력하면 되지만,
			a태그는 쿼리스트링(?이하 구문)을 직접 넣어줌 -->
			<!-- post는 form에서 전송할때 감출때만사용하고 나머지는 모두 get방식임 -->
		</c:if>
		<!-- sessionScope.memberId에서 sessionScope 생략가능(유일한 값) -->
		<c:if test="${memberId eq null}"> 
			<fieldset>
				<legend>로그인</legend>
				<form action="/member/login.do" method="post">
					<input type="text" name="member-id"><br>
					<input type="password" name="member-pw"><br>
					<div>
						<input type="submit" value="로그인"> 
						<input type="reset" value="취소">
						<a href="/member/enroll.jsp">회원가입</a> 
					</div>
				</form>
			</fieldset>
		</c:if>
	</body>
</html>