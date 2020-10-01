<%@page import="java.util.ArrayList"%>
<%@page import="com.joebrooks.shareApp.model.dto.FileDTO"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	ArrayList<FileDTO> arr = (ArrayList<FileDTO>) request.getAttribute("filesInfo");
	String type = (String) request.getAttribute("type");
%>

<!--  마우스 우클릭 컨텍스트 메뉴 -->
<script type="text/javascript"
	src="${ pageContext.request.contextPath}/static/js/contextMenu.js"></script>

<!-- 파일 전송 경로 지정 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/fileTransfer.js">
	
</script>


<div class="progress-wrap">
	<div class="progress">
		<div class="progress-bar" id="ajax-progress" role="progressbar"
			aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
			style="width: 0%"></div>
	</div>
	<h3 id="progress-fileName"></h3>
	<h5 id="progress-speed"></h5>
</div>



<form method="post" enctype="multipart/form-data" id="file">
	<label class="btn btn-primary btn-file"> 
	<input type="file"
		name="upload" style="display: none;" onchange="doUpload()">
		업로드
	</label>
</form>

<button class="btn btn-danger" onclick="cancelUpload()">업로드 취소</button>

<%
	if(request.getAttribute("isGuest") == null){
		%>	
		<button class="btn btn-success" onclick="domkdir()">폴더 생성</button>
		<button class="btn btn-secondary" onclick="domkGuestDir()">게스트 폴더 생성</button>
		<% 
	}
%>



<button class="btn btn-info" onclick="doGoBackdir()">뒤로 가기</button>


<div class="table-responsive">
	<table class="table table-hover">
		<%
			if (type.equals("all")) {
		%>
		<thead>
			<tr class="info">
				<th>파일 이름</th>
				<th>파일 종류</th>
				<th>용량</th>
			</tr>
		</thead>

		<%
			for (FileDTO dto : arr) {

			if (dto.getType().equals("folder")) {
		%>
		<tbody>
			<tr>
				<td><a
					href="foldermove.do?query=search&folderName=<%=dto.getName()%>"
					class="folderName"><%=dto.getName()%></a></td>
				<td><%=dto.getType()%></td>
				<td><%=dto.getSize()%></td>
			</tr>
		</tbody>

		<%
			} else {
		%>
		<tbody>
			<tr>
				<td class="fileName"><%=dto.getName()%></td>
				<td><%=dto.getType()%></td>
				<td><%=dto.getSize()%></td>
			</tr>
		</tbody>

		<%
			}
		}
		}

		else {
		%>

		<thead>
			<tr class="info">
				<th>파일 이름</th>
				<th>파일 종류</th>
				<th>용량</th>
				<th>경로</th>
			</tr>
		</thead>

		<%
			for (FileDTO dto : arr) {
				String fixedPath = dto.getPath();
				if(fixedPath == null){
					fixedPath = "\\";
				}
		%>
		<tbody>
			<tr>
				<td class="fileName"><%=dto.getName()%></td>
				<td><%=dto.getType()%></td>
				<td><%=dto.getSize()%></td>
				<td><%=fixedPath%></td>
			</tr>
		</tbody>

		<%
			}
		}
		%>


	</table>
</div>

<div class="pagination-container">
	<ul class="pagination">
		<%
			int totalPage = (int) request.getAttribute("totalPage");
		
		for (int i = 0; i < (totalPage / 10) + 1; i++) {
		%>
		<li class="page-item"><a class="page-link"
			href="showall.do?page=<%=i + 1%>"><%=i + 1%></a></li>
		<%
			}
		%>
	</ul>
</div>




