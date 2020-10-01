<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/P2PJSP">Home</a>
		</div>
	
		<%
			Object isMember = session.getAttribute("sid");
		if (isMember != null) {
		%>
		<div id="navbar" class="navbar-collapse collapse">
			<form class="navbar-form navbar-right" method="post"
				action="logout.do">
				<button type="submit" class="btn btn-primary">로그아웃</button>
			</form>
		</div>
		<%
			}
		%>
	
		<%
			if (isMember == null) {
		%>
	
		<div id="navbar" class="navbar-collapse collapse">
	
			<form class="navbar-form navbar-right" method="post" action="join.do">
				<button type="submit" class="btn btn-primary">회원가입</button>
			</form>
	
	
			<form class="navbar-form navbar-right" method="post" action="login.do">
				<button type="submit" class="btn btn-success">로그인</button>
			</form>
	
		</div>
		<%
			}
		%>
	</div>
</div>
