<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="login.js">
</script>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>

	<c:set var="id" value="${sessionScope.id }"></c:set>

	<c:if test="${id == null || id ==''}">
		<div id="status">
			<ul>
				<li><label for="id">아이디</label> <input id="id" name="id"
					type="email" size="20" maxlength="50" placeholder="email@kings.com" />
				</li>

				<li><label for="passwd">비밀번호</label> <input id="passwd"
					name="passwd" type="password" size="20" placeholder="6~16자 숫자/문자"
					maxlength="16"></li>

				<li class="label2">
					<button id="login">로그인</button>
					<button id="register">회원가입</button>
				</li>

			</ul>
		</div>
	</c:if>
	<!-- session 값이 있다면 아래 화면 -->
	<c:if test="${id != null }">
		<div id="status">
			<ul>
				<li><b>${id }</b>님이 로그인 하셨습니다.</li>
				<li class="label2"><button id="logout">로그아웃</button><button id="update">회원 정보 변경</button></li>
				
			</ul>
		</div>
	</c:if>
	
</body>
</html>