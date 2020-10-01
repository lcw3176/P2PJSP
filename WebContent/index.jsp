<%@page
	import="com.sun.org.apache.xerces.internal.impl.xpath.regex.REUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage = "./view/fixed/error.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Strawberry Drive</title>

<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous">
	
</script>

<script src="http://malsup.github.com/jquery.form.js"></script>


<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>



<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">


<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<link rel="stylesheet"
	href="${ pageContext.request.contextPath}/static/css/style.css">

</head>

<%
	String view = (String) request.getAttribute("view");
	String sess = (String) session.getAttribute("sid");
%>


<body>
	<header>
		<jsp:include page="./view/fixed/header.jsp"></jsp:include>
	</header>
	
	<%
	
		if(view == null)
		{
			%>
	<aside>
		<jsp:include page="./view/fixed/aside.jsp"></jsp:include>
	</aside>
			<% 
		}
	%>


	<nav>
		<jsp:include page="./view/fixed/nav.jsp"></jsp:include>
	</nav>

	<section>

		<%
			if (view == null) {
		%>
		<jsp:include page="./view/home.jsp"></jsp:include>
		<%
			}

		else if (view != null && sess != null) {
		%>
		<jsp:include page="<%=view%>"></jsp:include>
		<%
			}

		else if (view != null && sess == null) {

		if (view.equals("/view/login.jsp") || view.equals("/view/join.jsp")) {
		%>
		<jsp:include page="<%=view%>"></jsp:include>
		<%
			}

		else {
		%>
		<jsp:include page="./view/noMember.jsp"></jsp:include>
		<%
			}

		}
		%>


	</section>


	<footer> </footer>

</body>
</html>