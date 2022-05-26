<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section>
	<div class="video-list">
		<h3>Video TITLE?</h3>
		<select id="video-title">
			<c:forEach var="video" items="${videoList }">
				<option value="${video.id }">${video.title }</option>
			</c:forEach>
		</select>
	</div>
	
	<script type="text/javascript">
	document.getElementById('video-title').addEventListener('change', (e)=>{
		window.location.href ='?keyID='+e.target.value;
	});
	</script>
	<style>
.video-list {
	display: flex;
}

.video-list>h3 {
	width: 20%;
}

.video-list>select {
	width: 80%;
	border: 1px solid gainsboro;
	background: reseashell;
}

option {
	text-shadow: 0 1px 0 rgba(0, 0, 0, 0.4);
	transition: 0.3s ease-in-out;
}

.video-list>select select::selection {
	background: salmon;
}
</style>
</section>