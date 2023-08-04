<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>서비스 결과 성공</title>
	</head>
	<body>
		<h1>서비스 결과 성공</h1>
		<h2>${requestScope.msg }</h2>
		<script>
			const result = '${msg}';
			const url = '${url}';
			alert(result);
			//location 페이지 이동(without data)
			//location.href = "/index.jsp";
			location.href = url;
		</script>
	</body>
</html>