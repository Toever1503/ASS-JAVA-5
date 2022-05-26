<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item active"><a href="/">Home</a></li>
        <li class="breadcrumb-item active">Category</li>
        <li class="breadcrumb-item active" aria-current="page">${category.catName}</li>
    </ol>
</nav>

<div class="productContainer" style="margin: 10px 2px;">
    <c:if test="${listProductCart.getTotalElements()==0}">
        <h4>This category don't have any products</h4>
    </c:if>
    <c:forEach var="p" items="${listProductCart.getContent()}">
        <div class="productItem">
            <a href="/product/${p.title}/${p.id}">
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
<%--                    <div class="btn btn-outline-primary">Buy now</div>--%>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div style="margin: 0 auto; max-width: 300px">
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <c:if test="${listProductCart.hasPrevious()}">
                <li class="page-item"><a class="page-link" href="?page=${listProductCart.getNumber()-1}">Previous</a></li>
            </c:if>
            <li class="page-item"><a class="page-link" href="#">${listProductCart.getNumber()}</a></li>
            <c:if test="${listProductCart.hasNext()}">
                <li class="page-item"><a class="page-link" href="?page=${listProductCart.getNumber()+1}">Next</a></li>
            </c:if>
        </ul>
    </nav>
</div>