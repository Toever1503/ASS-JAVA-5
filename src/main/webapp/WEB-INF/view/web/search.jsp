<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav aria-label="breadcrumb">
	<ol class="breadcrumb">
		<li class="breadcrumb-item active"><a href="/">Home</a></li>
		<li class="breadcrumb-item active">Search</li>
		<li class="breadcrumb-item active" aria-current="page">${keyWord}</li>
	</ol>
</nav>

<div>
	<h3>Results with keyword: <i>${keyWord}</i></h3>
</div>
<div class="productContainer" style="margin: 10px 2px;">

	<c:if test="${listProducts.getContent().size() != 0}">
		<c:forEach var="p" items="${listProducts.getContent()}">
			<div class="productItem">
				<a href="/${p.title}/${p.id}">
					<div class="productImage"
						 style="background-image: url('${p.image}');">
					</div>
				</a>
				<div class="productInfo">
					<h3 class="title">${p.title}</h3>
					<div class="productPrice">
						<div class="oldPrice">
							<del></del>
						</div>
						<div class="newPrice">${p.price}$</div>
					</div>
					<div class="productAction">
						<div class="btn btn-outline-primary" onclick="addItemIntoCart(${p.id})">Add</div>
<%--						<div class="btn btn-outline-primary">Buy now</div>--%>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>

</div>

<div style="margin: 0 auto; max-width: 300px">
	<nav aria-label="Page navigation example">
		<ul class="pagination">
			<li class="page-item"><a class="page-link" href="#">Previous</a></li>
			<li class="page-item"><a class="page-link" href="#">1</a></li>
			<li class="page-item"><a class="page-link" href="#">2</a></li>
			<li class="page-item"><a class="page-link" href="#">3</a></li>
			<li class="page-item"><a class="page-link" href="#">Next</a></li>
		</ul>
	</nav>
</div>