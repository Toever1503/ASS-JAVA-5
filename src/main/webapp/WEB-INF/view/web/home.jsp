<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item active" aria-current="page">Home</li>
    </ol>
</nav>
<div class="slideProduct" style="height: 400px;">
    <div id="carouselControls" class="carousel slide h-100" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselControls" data-bs-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselControls" data-bs-slide-to="1"
                    aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselControls" data-bs-slide-to="2"
                    aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner h-100">
            <div class="carousel-item h-100 active">
                <img src="https://graphicgoogle.com/wp-content/uploads/2017/10/Facebook-New-Fashion-Sale-Banner.jpg"
                     class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item h-100">
                <img src="https://www.w3schools.com/bootstrap5/chicago.jpg" class="d-block w-100"
                     alt="...">
            </div>
            <div class="carousel-item h-100">
                <img src="https://static.vecteezy.com/system/resources/previews/000/662/988/original/fashion-modern-sale-banner-vector.jpg"
                     class="d-block w-100" alt="...">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselControls"
                data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselControls"
                data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
</div>
<div class="titleRow">
    <h4 style="position: relative; z-index: 10;">New products</h4>
</div>
<div class="productContainer" style="margin: 10px 2px;">

    <c:forEach var="p" items="${listProducts.getContent()}">
        <div class="productItem">
            <span class="badge badge-info" style="position: absolute;">New</span>
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
                        <%--                        <div class="btn btn-outline-primary">Buy now</div>--%>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div style="margin: 0 auto; max-width: 300px">
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <c:if test="${listProducts.hasPrevious()}">
                <li class="page-item"><a class="page-link" href="?page=${listProducts.getNumber()-1}">Previous</a></li>
            </c:if>
            <li class="page-item"><a class="page-link" href="#">${listProducts.getNumber()}</a></li>
            <c:if test="${listProducts.hasNext()}">
                <li class="page-item"><a class="page-link" href="?page=${listProducts.getNumber()+1}">Next</a></li>
            </c:if>
        </ul>
    </nav>
</div>