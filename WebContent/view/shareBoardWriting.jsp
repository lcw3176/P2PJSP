<%@page import="com.joebrooks.shareApp.model.dto.FileDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArrayList<FileDTO> arr = (ArrayList<FileDTO>)request.getAttribute("shareFolderList");
%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/boardTransfer.js">
	
</script>

<button class="btn btn-primary" onclick="doList()">목록</button>

<form action="boardwrite.do?state=complete" method="post" id="boardform">
	<div class="form-group">
		<label>제목</label> <input type="text" class="form-control" name="title"
			id="title" placeholder="제목을 입력하세요">
	</div>

	<div class="form-group">
		<label>내용</label>
		<textarea class="form-control" name="content" id="content"
			placeholder="내용을 입력하세요" rows="15"></textarea>
	</div>

	<div class="form-group">
		<label>공유 폴더 선택</label>
		<select class="form-control" name="sharefolder">
			<option value="" disabled selected>폴더를 선택하세요</option>
			
			<%
				for(FileDTO dto: arr){
					%>
					<option><%=dto.getName() %></option>
					
					<%
				}
			%>
		</select>
	</div>
	<input type="button" class="btn btn-success" onclick="doCommit()"
		value="완료">
</form>