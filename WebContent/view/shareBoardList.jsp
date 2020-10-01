<%@page import="com.joebrooks.shareApp.model.dto.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/boardTransfer.js">
	
</script>

<%
	ArrayList<BoardDTO> arr = (ArrayList<BoardDTO>) request.getAttribute("pagesInfo");
%>

<h3>파일 공유 게시판</h3>
<button class="btn btn-success" onclick="doWrite()">글쓰기</button>

<div class="table-responsive">
	<table class="table table-hover">
		<thead>
			<tr class="info">
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>

		<%
			for (BoardDTO dto : arr) {
		%>

		<tbody>
			<tr>
				<td><a
					href="showcontent.do?title=<%=dto.getTitle()%>&idx=<%=dto.getIdx()%>"><%=dto.getTitle()%></a></td>
				<td><%=dto.getId()%></td>
				<td><%=dto.getRegDate()%></td>
				<td><%=dto.getViewCount()%></td>
			</tr>
		</tbody>

		<%
			}
		%>

	</table>
</div>

<div class="pagination-container">
	<ul class="pagination">

		<%
			int totalPage = (int) request.getAttribute("totalPage");
		String pageCheck = (String) request.getParameter("page");
		String searchPage = (String) request.getAttribute("searchPage");

		int beforePage = 0;
		int nextPage = 0;
		int nowPage = 0;

		if (pageCheck == null) {
			beforePage = 1;
			nextPage = 2;
		} else {
			nowPage = Integer.parseInt(pageCheck);

			if (nowPage == 1) {
				beforePage = 1;
				nextPage = 2;
			} else if (nowPage == (totalPage / 10) + 1) {
				beforePage = (totalPage / 10);
				nextPage = (totalPage / 10) + 1;
			} else {
				beforePage = nowPage - 1;
				nextPage = nowPage + 1;
			}
		}

		if (searchPage == null) {
		%>
		<li class="page-item"><a class="page-link"
			href="showboard.do?page=<%=beforePage%>">Previous</a></li>
		<%
			if (pageCheck == null) {
			for (int i = 0; i < (totalPage / 10) + 1; i++) {

				if (i == 9) {
			break;
				}
		%>
		<li class="page-item"><a href="showboard.do?page=<%=i + 1%>"><%=i + 1%></a></li>
		<%
			}
		}

		else {

		if (nowPage < 5) {
		nowPage = 5;
		}
		for (int i = nowPage - 4; i <= nowPage + 4; i++) {
		%>
		<li class="page-item"><a href="showboard.do?page=<%=i%>"><%=i%></a></li>
		<%
			if (i == (totalPage / 10) + 1) {
			break;
		}
		}
		}
		%>

		<li class="page-item"><a class="page-link"
			href="showboard.do?page=<%=nextPage%>">Next</a></li>

		<%
			} else {
		%>
		<li class="page-item"><a class="page-link"
			href="showboard.do?page=<%=beforePage%>&search=<%=searchPage%>">Previous</a></li>
		<%
			if (pageCheck == null) {
			for (int i = 0; i < (totalPage / 10) + 1; i++) {

				if (i == 9) {
			break;
				}
		%>
		<li class="page-item"><a
			href="showboard.do?page=<%=i + 1%>&search=<%=searchPage%>"><%=i + 1%></a></li>
		<%
			}
		}

		else {

		if (nowPage < 5) {
		nowPage = 5;
		}
		for (int i = nowPage - 4; i <= nowPage + 4; i++) {
		%>
		<li class="page-item"><a
			href="showboard.do?page=<%=i%>&search=<%=searchPage%>"><%=i%></a></li>
		<%
			if (i == (totalPage / 10) + 1) {
			break;
		}
		}
		}
		%>

		<li class="page-item"><a class="page-link"
			href="showboard.do?page=<%=nextPage%>&search=<%=searchPage%>">Next</a></li>

		<%
			}
		%>


	</ul>
</div>


<form action="showboard.do" class="input-group" id="search-form">
	<input type="text" class="form-control" placeholder="검색 키워드를 입력하세요!"
		name="search"> <span class="input-group-btn"> <input
		type="submit" class="btn btn-secondary" value="찾기">
		</button>
	</span>
</form>