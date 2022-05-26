<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="route-action">
    <span class="route-item"><a href="/admin/products">Quay lại</a></span>
</div>
<section class="video login">
    <form:form action="" method="post" modelAttribute="product" onsubmit='return formValidate(this)'>
        <script>

            function formValidate(e) {
                e.action = window.location.href + '/' + document.getElementById('category').value;

                if (document.getElementById('image').value === '') {
                    document.getElementById('imageError').innerHTML = 'Image cannot null';
                    return false;
                } else
                    document.getElementById('imageError').innerHTML = null;
                return true;
            }
        </script>
        <form:input path="id" cssStyle="display: none"/>

        <div class="form-group">
            <label for="title">Title(*):</label>
            <form:input type="text" required="true" path="title" class="form-control" placeholder="Enter title"
                        id="title"/>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <form:textarea path="description" class="form-control" placeholder="Enter description"
                           id="description"/>
        </div>

        <div class="form-group">
            <label for="price">Price(*):</label>
            <form:input type="text" pattern="[0-9]{1,8}"
                        required="true" title="price cannot exceed 8 numbers"
                        path="price"
                        class="form-control"
                        placeholder="Enter price" id="price"/>
        </div>

        <div class="form-group">
            <label for="year">Year(*):</label>
            <form:input type="text" pattern="[0-9]{4}" required="true" title="Year must be 4 numbers" path="year"
                        class="form-control"
                        placeholder="Enter year" id="year"/>
        </div>

        <div class="form-group">
            <label for="season">Season(*):</label>
            <form:select path="season" id="season" class="form-control">
                <form:option value="spring">Spring</form:option>
                <form:option value="summer">Summer</form:option>
                <form:option value="fall">Fall</form:option>
                <form:option value="winter">Winter</form:option>
            </form:select>
        </div>

        <div class="form-group">
            <label for="category">Category(*):</label>
            <select class="form-control" id="category">
                <c:forEach var="cat" items="${listCategories}">
                    <option value="${cat.id}">${cat.catName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="imageUpload">Image Banner</label>
            <input
                    style="display: none;" type="file" accept='image/*'
                    name="imageUpload" id="imageUpload">
            <form:input style="display: none; border: none" path='image' value="${product.image }"
                        id='image'/>

            <a id="uploadFile"
               style="text-decoration: underline; cursor: pointer; font-size: 14px;">Upload
                ảnh</a>
            <p id="imageError" style="color: red"></p>
            <span class="image_uploaded"
                  style="color: cornflowerblue; margin-left: 15px">${product.image }</span>

            <script type="text/javascript">
                document.addEventListener("DOMContentLoaded", function () {
                    document.getElementById("uploadFile").addEventListener('click', function () {
                        document.getElementById('imageUpload').click();
                    });
                    // end choose upfile

                    document.getElementById("imageUpload").addEventListener('change', function (e) {
                        console.log("uploading file")

                        if (e.target.files.length != 0) {
                            let formData = new FormData();
                            formData.enctype = 'multipart/form-data';
                            formData.append('data', e.target.files[0]);

                            fetch("/api/uploadFile", {
                                method: 'post',
                                cache: 'no-cache',
                                body: formData
                            })
                                .then(res => res.json())
                                .then(result => {
                                    console.log(result)
                                    if (result.result === 'success') {
                                        console.log(result.data[0])
                                        imgBanner = document.getElementById("image").value = result.data[0];
                                        document.getElementsByClassName('image_uploaded')[0].innerHTML = result.data[0].replace(/(\d+\/\d+\/)/, '');
                                    }

                                }).catch(error => console.log(error))
                        }

                    });
                    // end up file
                });
            </script>
        </div>
        <div>
            <c:if test="${result!=null}">
                <span style="color: red">${message}</span>
            </c:if>
        </div>
        <input type="submit" value="Submit">
    </form:form>
</section>


<style>
    .item_checked {
        background: bisque !important;
    }

    .section {
        border: 1px solid gray;
        background-color: white;
        margin: 10px 0;
    }

    .route-action {

    }

    .route-action .route-item {
        border: 1px solid gray;
        padding: 5px;
        border-radius: 5px;
        background-color: white;
    }

    .login form {
        width: unset;
    }

    .login form > div > label {
        display: block;
        font-size: 20px;
    }

    .login form input[type='text'], input[type='password'], input[type='email'],
    textarea {
        width: 95%;
    }

    .login .hasError {
        margin-left: 0;
    }

    .container-box {
        position: relative;
        margin-top: 10px;
    }

    .container-box .container-item {
        display: inline-block;
        background: gainsboro;
        padding: 5px;
        border-radius: 4px;
        margin: 2px 0;
        cursor: pointer;
    }
</style>

<style>
    body {
        background-color: #f1ededba;
    }

    .body-container {
        width: 1000px;
        margin: 0 auto;
    }

    .login {
        margin-top: 10px;
    }

    .login form {
        padding: 15px;
        background-color: white;
        width: 500px;
        margin: 0 auto;
        border-radius: 5px;
    }

    .login form > div {
        position: relative;
        margin: 15px 0;
    }

    .login form > div > label {
        width: 30%;
        display: inline-block;
        font-size: 15px;
    }

    .login form input[type='checkbox'] {
        margin-left: 30%;
        cursor: pointer;
    }

    .login form > div > span {
        margin-left: 30%;
        width: 50%;
    }

    .login form input[type='text'], input[type='password'], input[type='email'],
    textarea {
        padding: 8px;
        width: 63%;
        border: 1px solid gainsboro;
    }

    .login form input[type='submit'] {
        display: block;
        margin: 5px auto;
        padding: 6px;
        transition: 0.3s ease-in-out;
        border: 1px solid gainsboro;
        cursor: pointer;
        border-radius: 3px;
    }

    .login form input[type='submit']:hover {
        background-color: seashell;
    }

    .login .hasError {
        margin-left: 30%;
        padding: 5px;
        color: cornflowerblue;
        font-size: 14px;
    }
</style>
