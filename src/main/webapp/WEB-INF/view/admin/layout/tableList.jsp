<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="section">
	<table>
		<thead>
			<tr>
				<c:forEach var="headerTable" items="${columnHeader }">
					<th>${headerTable }</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			<c:when test="${actionType=='favoriteVideos' }">
					<c:forEach var="obj" items="${favoriteVideoList }">
						<tr>
							<td>${obj.videoTitle }</td>
							<td>${obj.favourCount }</td>
							<td>${obj.latestDate }</td>
							<td>${obj.oldestDate }</td>
						</tr>
					</c:forEach>
				</c:when>
				
				<c:when test="${actionType=='favoriteUsers' }">
					<c:forEach var="obj" items="${favoUserList }">
						<tr>
							<td>
								<div class="column-title">${obj.username }</div>
							</td>
							<td>${obj.username }</td>
							<td><span>${obj.email }</span></td>
							<td><span>${obj.favoDate }</span></td>
						</tr>
					</c:forEach>
				</c:when>
				
				<c:when test="${actionType=='shareFriends' }">
					<c:forEach var="obj" items="${shareUserList }">
						<tr>
							<td>
								<div class="column-title">${obj.senderName }</div>
							</td>
							<td>${obj.senderEmail }</td>
							<td><span>${obj.receiveEmail }</span></td>
							<td><span>${obj.sentDate }</span></td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>

			<tr class="page-action">
				<td></td>
				<td></td>
				<td></td>
				<!-- divide page -->
				<jsp:include page="pageTable.jsp"></jsp:include>
			</tr>
		</tbody>
	</table>
</div>

<style>
.section {
	border: 1px solid gray;
	background-color: white;
	margin: 10px 0;
}

.add-new-film {
	border: 1px solid gray;
	padding: 5px;
	border-radius: 5px;
	background-color: white;
}

.section table {
	width: 100%;
	border-collapse: collapse;
	border: 1px solid black;
	text-align: center;
}

.section table thead {
	width: 100%;
	margin: 10px 0;
	border: 1px solid gray;
}

.section table tbody {
	width: 100%;
	margin: 10px 0;
	border: 1px solid gray;
}

.section table tr {
	line-height: 35px;
}

.section table tbody tr:nth-child(odd) {
	background: #fff0f0;
}

.section table td:nth-child(1) input {
	display: block;
	margin: 0 auto;
}

.section table td:nth-child(2), .section table td:nth-child(3), .section table td:nth-child(4),
	.section table td:nth-child(5) {
	width: 300px;
}

.column-action {
	font-size: 12px;
	height: 30px;
}

.column-action span {
	display: none;
	transition: 0.4s ease-in-out;
}

.section table td:nth-child(2):hover .column-action>span {
	display: inline;
}

.column-action span:nth-child(2) {
	border-left: 1px solid gray;
	border-right: 1px solid gray;
	padding: 2px;
}

.column-action span:hover a {
	color: teal;
}

.section table tbody tr.page-action {
	line-height: unset;
}

.section table tbody tr.page-action span {
	display: inline-block;
	padding: 5px;
	border: 1px solid gainsboro;
	font-weight: bold;
	transition: 0.3s ease-in-out;
	cursor: pointer;
}

.section table tbody tr.page-action span:hover {
	background-color: azure;
}
</style>