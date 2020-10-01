<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


	<%
		String isJoin = (String)request.getAttribute("newMember");
		if(isJoin != null)	
		{
			out.println("<script>alert('가입을 환옇합니다. 로그인을 진행해 주세요.');history.back();</script>");
		}
		
		
	%>
	
	
<div class="container-marketing">

	<!-- START THE FEATURETTES -->

	<hr class="featurette-divider">

	<div class="row featurette">
		<div class="col-md-7">
			<h2 class="featurette-heading">
				곧 출시될 예정입니다. <span class="text-muted">StrawberryM</span>
			</h2>
			<p class="lead">
			윈도우 음악 프로그램이었던 Strawberry가
			모바일로 새로 만들어집니다.
			xamarin, andriod, no ios</p>
		</div>
		<div class="col-md-5">
			<img class="featurette-image img-responsive center-block"
				data-src="holder.js/500x500/auto" alt="300x300"
				src="${ pageContext.request.contextPath}/static/img/music.png"
				data-holder-rendered="true">
		</div>
	</div>

</div> 