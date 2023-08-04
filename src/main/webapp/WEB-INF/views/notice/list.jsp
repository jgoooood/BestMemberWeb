<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 목록 조회</title>
		<style>
			table{
				width : 800px;
				border : 1px solid black;
				border-collapse : collapse;'
			}
			th, td {
				border : 1px solid black;
			}
		</style>
	</head>
	<body>
		<h1>공지사항 목록</h1>
		<table>
			<colgroup>
				<col width="10%">
				<col width="35%">
				<col width="10%">
				<col width="25%">
				<col width="10%">
			</colgroup>
			<thead>
				<tr>
					<th>글번호</th>
					<th>글제목</th>
					<th>글쓴이</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
<!-- 			for(Notice notice : nList) -->
<!-- jsp는 get말고 requestScope메소드를 사용하고 requestScope 생략가능함 -->
				<c:forEach var="notice" items="${requestScope.nList }">
					<tr>
<!-- 					자바 : notice.getNoticeNo() -->
						<td>${notice.noticeNo }</td>
<!-- 					자바 : notice.getNoticeSubject() -->
<!-- a태그는 url설계를 직접해줘야 함 ?키값=value값/ form은 action의 name으로 쿼리스트링 만들 수 있음 -->
<!-- 쿼리문 생각 : SELECT *FROM NOTICE_TBL WHERE NOTICE=NO = ? -->
<!-- url설계후 컨트롤러생성 -->
<!-- form태그없으면 doGet으로 동작함->form은 post사용가능 -->
						<td><a href="/notice/detail.do?noticeNo=${notice.noticeNo }">${notice.noticeSubject }</a></td>
						<td>${notice.noticeWriter }</td>
						<td>${notice.noticeDate }</td>
						<td>${notice.viewCount }</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="5" align="center">
					${pageNavi }
<!-- 							dao에서 만들어줌 -->
<!-- 						<a href="#">1</a> -->
<!-- 						<a href="#">2</a> -->
<!-- 						<a href="#">3</a> -->
<!-- 						<a href="#">4</a> -->
<!-- 						<a href="#">5</a> -->
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>