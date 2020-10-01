<%@page import="com.joebrooks.shareApp.model.dto.FileDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	ArrayList<FileDTO> arr = (ArrayList<FileDTO>) request.getAttribute("filesInfo");
%>
<script type="text/javascript"
	src="${ pageContext.request.contextPath}/static/js/guestTransfer.js"></script>


<div class="progress-wrap">
	<div class="progress">
		<div class="progress-bar" id="ajax-progress" role="progressbar"
			aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
			style="width: 0%"></div>
	</div>
	<h3 id="progress-fileName"></h3>
	<h5 id="progress-speed"></h5>
</div>


<label id="adminId"><%=arr.get(0).getId() %></label>
<label>님의</label>
<label id="adminPath"><%=arr.get(0).getPath() %></label>

<div class="table-responsive">
	<table class="table table-hover">

		<thead>
			<tr class="info">
				<th>파일 이름</th>
				<th>파일 종류</th>
				<th>용량</th>
			</tr>
		</thead>

		<%
			for (FileDTO dto : arr) {
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
		%>


	</table>
</div>
