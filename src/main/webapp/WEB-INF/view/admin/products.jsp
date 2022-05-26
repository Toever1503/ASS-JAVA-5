<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<span class="add-new-film"><a
        href="/admin/product/add">Thêm Mới</a></span>
<script>
    function deleteProduct(e, id) {
        modalConfirm("Are you want to delete this product?", () => {
            fetch('/api/product/delete/' + id)
                .then(res => res.json())
                .then(data => {
                    if (data.result == 'success') {
                        e.target.parentNode.parentNode.parentNode.remove();
                        alert("Delete successfully!");
                    }
                else
                    alert("Delete failed, code: 500");
                }).catch(err => console.log(err));
        })
    }
</script>
<div class="section">
    <table>
        <thead>
        <tr>
            <th style="width: 0"></th>
<%--            <th><input type="checkbox"></th>--%>
            <th>Title</th>
            <th>Category</th>
            <th>Date Update</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${productList.getContent()}">
            <tr>
                <td></td>
<%--                <td style="padding: 5px;"><input type="checkbox"></td>--%>
                <td style="max-width: 300px">
                    <div class="column-title" style="word-break: break-all;">
                            ${product.title }</div>

                    <div class="column-action">
										<span><a
                                                href="/admin/product/edit/${product.id}">Edit</a>
										</span>
                        <span onclick="deleteProduct(this, ${product.id})">Delete</span>
                        <span><a href="/${product.title}/${product.id}">View</a></span>
                    </div>
                </td>
                <td>
                        ${product.getProduct_Cat().getCatName()}
                </td>
                <td><span>
                        ${product.dateUpdate != null ?product.dateUpdate.toGMTString(): '-' }
                </span></td>
            </tr>
        </c:forEach>

        <tr class="page-action">
            <td></td>
            <td></td>
            <td></td>
            <td>
                <c:if test="${productList.hasPrevious()}">
                    <a class="page-link" href="?page=${productList.getNumber()}">Previous | </a>
                </c:if>
                <li class="page-item"><a class="page-link" href="#">${productList.getNumber()}</a>
                <c:if test="${productList.hasNext()}">
                    <a class="page-link" href="?page=${productList.getNumber()}"> | Next</a>
                </c:if>
            </td>
        </tr>

        </tbody>
    </table>
</div>


