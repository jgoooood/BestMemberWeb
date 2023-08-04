<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원가입</title>
		<style>
			ul{
				list-style-type : none;
				padding-left : 0;
			}
			li {
				margin : 10px;
			}
			ul label {
				width : 80px;
				text-align : left;
				float : left;
			}
		</style>
	</head>
	<body>
		<h1>회원가입</h1>
		<h3>회원정보를 입력하세요</h3>
		<form action="/member/register.do" method="post">
			<fieldset>
				<legend>회원가입</legend>
				<ul>
					<li>
						<!--기능을 위한 id, 쿼리스트링 생성을 위한 name -->
						<label for="member-id">아이디</label>
						<input type="text" id="member-id" name="member-id">
					</li>
					<li>
						<label for="member-pw">비밀번호</label>
						<input type="password" id="member-pw" name="member-pw">
					</li>
					<li>
						<label for="member-name">이름</label>
						<input type="text" id="member-name" name="member-name">
					</li>
					<li>
						<label for="member-age">나이</label>
						<input type="text" id="member-age" name="member-age">
					</li>
					<li>
						<label for="member-gender">성별</label>
						남 <input type="radio" id="member-gender" name="member-gender" value="M">
						여 <input type="radio" id="member-gender" name="member-gender" value="F">
					</li>
					<li>
						<label for="member-email">이메일</label>
						<input type="text" id="member-email" name="member-email">
					</li>
					<li>
						<label for="member-phone">전화번호</label>
						<input type="text" id="member-phone" name="member-phone">
					</li>
					<li>
						<label for="member-address">주소</label>
						<input type="text" id="member-address" name="member-address">
					</li>
					<li>
						<label for="member-hobby">취미</label>
						<input type="text" id="member-hobby" name="member-hobby">
					</li>
				</ul>
			</fieldset>
			<input type="submit" value="가입하기">
			<input type="reset" value="초기화">
		</form>
	</body>
</html>