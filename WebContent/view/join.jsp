<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<%
		String error = (String)request.getAttribute("errorcode");
		if(error != null)	
		{
			if(error.equals("alreadyId"))
			{
				out.println("<script>alert('이미 존재하는 아이디입니다.');history.back();</script>");
			}
		}
		
		
	%>

<div class="container">
	<form class="form-signin" method="post">
        <h2 class="form-signin-heading">회원가입</h2>
        <label>id</label>
        <input class="form-control" placeholder="id" name="id">
        <label>Password</label>
        <input type="password" class="form-control" placeholder="Password" name="pw">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
</div>

