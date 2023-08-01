<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 상세조회</title>
	</head>
	<body>
		<h1>공지사항 상세</h1>
		<ul>
			<li>
				<label>글번호</label>
<!-- 				noticeNo는 Notice클래스(vo)멤버변수명을 가져온 것 -->
				<span>${requestScope.notice.noticeNo }</span>
			</li>
			<li>
				<label>작성일</label>
				<span>${requestScope.notice.noticeDate }</span>
			</li>
			<li>
				<label>글쓴이</label>
				<span>${requestScope.notice.noticeWriter }</span>
			</li>
			<li>
				<label>제목</label>
<!-- 				requestScope는 생략가능 -->
				<span>${notice.noticeSubject }</span>
			</li>
			<li>
				<label>내용</label>
				<p>${notice.noticeContent }</p>
			</li>
		</ul>
	</body>
</html>