<%@page import="java.util.ArrayList"%>
<%@page import="com.joebrooks.shareApp.model.dto.CommentDTO"%>
<%@page import="com.joebrooks.shareApp.model.dto.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/boardTransfer.js">

	
</script>


<script>
function checkForm(){
	var content = document.getElementById("comment").value;
	
	if(content == null || content.trim() == ""){
		alert("댓글을 입력해 주세요.");
		return false;
	}
}

</script>

<%
	BoardDTO dto = (BoardDTO) request.getAttribute("contentInfo");
ArrayList<CommentDTO> arr = (ArrayList<CommentDTO>) request.getAttribute("commentInfo");
HttpSession sess = request.getSession();
String id = (String) sess.getAttribute("sid");
%>

<button class="btn btn-success" onclick="doList()">목록</button>

<%
	if (id.equals(dto.getId())) {
%>
<button class="btn btn-danger" onclick="doDelete(<%=dto.getIdx()%>)">글
	삭제</button>

<%
	}
%>

<div class="table-responsive">
	<table class="table">
		<thead>
			<tr>
				<th class="table-header">제목</th>
				<td><%=dto.getTitle()%></td>
			</tr>
		</thead>


		<tbody>
			<tr>
				<th>작성자</th>
				<td><%=dto.getId()%></td>
				<th>작성일</th>
				<td><%=dto.getRegDate()%></td>
				<th>조회수</th>
				<td><%=dto.getViewCount()%></td>
			</tr>
		</tbody>

		<tbody>
			<tr>
				<th colspan="2">공유 링크</th>
				<td colspan="4"><a href="showguest.do?id=<%=dto.getId()%>&foldername=<%=dto.getShare()%>"><%=dto.getShare()%></a></td>
			</tr>
		</tbody>


		<tbody>
			<tr>
				<td colspan="2"><%=dto.getContent()%></td>
			</tr>
		</tbody>
	</table>
</div>

<label>댓글</label>


<div class="table-responsive">
	<table class="table">

		<%
			if (arr != null) {
			for (CommentDTO comment : arr) {
		%>
		<tr>
			<td><%=comment.getId()%></td>
			<td><%=comment.getContent()%></td>
			<td class="reply-date"><%=comment.getReg_date()%></td>
		</tr>
		<%
			}
		}
		%>

	</table>
</div>




<form
	action="boardcomment.do?idx=<%=dto.getIdx()%>&title=<%=dto.getTitle()%>"
	method="post" onsubmit="return checkForm();">
	<div class="form-group">
		<input type="text" class="form-control" name="comment" id="comment"
			placeholder="댓글을 입력하세요">
		</textarea>
	</div>
	<input type="submit" class="btn btn-primary" value="완료">
</form>