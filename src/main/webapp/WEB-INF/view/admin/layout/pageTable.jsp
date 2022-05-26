<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<td colspan="2">
	
	<c:if test="${page != 0 }">
		<span><a href="?page=0${keyID != null ? keyID :''}">|<</a> </span> 
		<span><a href="?page=${page-1 }${keyID != null ? keyID :''}"><</a> </span> 
	</c:if>
								
	<span class="pageNow" style="background: none;">${page+1 } <c:if test="${total != 0}"> / ${total }</c:if> </span> 
								
	<c:if test="${page != total-1 && total != 0 }">
		<span><a href="?page=${page+1 }${keyID != null ? keyID :''}">></a></span> 
		<span><a href="?page=${total-1 }${keyID != null ? keyID :''}">>|</a></span>
	</c:if>
</td>