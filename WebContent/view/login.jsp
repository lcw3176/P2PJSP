<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
		String error = (String)request.getAttribute("errorcode");
		if(error != null)	
		{
			if(error.equals("notFound"))
			{
				out.println("<script>alert('아이디 혹은 비밀번호를 확인해 주세요.');history.back();</script>");
			}
		}
		
		
	%>
	
<div class="container">
	<form class="form-signin" method="post" action="login.do">
        <h2 class="form-signin-heading">로그인</h2>
        <label>id</label>
        <input class="form-control" type="text" name="id" placeholder="id">
        <label>Password</label>
        <input class="form-control" type="password" name="pw" placeholder="Password">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
</div>
