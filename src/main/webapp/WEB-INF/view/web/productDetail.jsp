<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item active"><a href="/">Home</a></li>
        <li class="breadcrumb-item active">Product</li>
        <li class="breadcrumb-item active" aria-current="page">${product.title}</li>
    </ol>
</nav>

<div class="container">
    <div class="main-content">
        <div class="main-product d-flex bg-white">
            <div class="product-Image" style="width:
            40%">
                <img src=${product.image} width="100%" height="100%" alt=""/>
            </div>
            <div class="product_detail text-center p-3">
                <h1>${product.title}</h1>
                <p>Price: ${product.price}$</p>
                <p class="text-left">
                    <b>Description:</b>
                    <span class="d-block">${product.description}</span>
                </p>
                <div class="btn btn-outline-primary mt-5" onclick="addItemIntoCart(${product.id})">Add Cart</div>
            </div>
        </div>
    </div>
    <div class="other-product mt-2">
        <div class="titleRow">
            <h4 style="position: relative; z-index: 10;">Other products</h4>
        </div>
        <div class="productContainer" style="margin: 10px 2px;">
            <c:if test="${nearProducts.size() != 0}">
                <c:forEach var="p" items="${nearProducts}">
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
<%--                                <div class="btn btn-outline-primary">Buy now</div>--%>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
