<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item active"><a href="/">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">Edit Profile</li>
    </ol>
</nav>
<div class="login">
    <form:form action="" onsubmit='return formValidate(this)' method="post" modelAttribute="user_edit">
        <h1 style="text-align: center;">Cập Nhật Tài Khoản</h1>
        <div>
            <label for="username">Tài khoản:</label> <form:input
                path="username" id="username" disabled="true" placeholder="Tên đăng nhâp"/>
            <div class="hasError">
                <span><form:errors path="username"/></span>
            </div>
        </div>
        <div>
            <label for="name">Họ Và Tên:</label> <form:input type="text"
                                                             path="fullname" id="name" placeholder="................"
                                                             onkeyup='handleInputChange(this)'/>
            <div class="hasError">
                <span><form:errors path="fullname"/></span>
            </div>
        </div>

        <div>
            <label for="email">Email:</label>
            <form:input type="email" path="email" id="email" placeholder="................"
                        onkeyup='handleInputChange(this)'/>
            <div class="hasError">
                <span><form:errors path="email"/></span>
            </div>
        </div>

        <c:if test="${result !=null }">
            <p style="text-align: center; background: antiquewhite; padding: 5px; display: block;">
                    ${message }</p>
        </c:if>

        <input type="submit" value="Lưu">
    </form:form>

    <script>
        function handleInputChange(e) {
            e.nextElementSibling.getElementsByTagName('span')[0].innerHTML = null;
        }

        function formValidate(e) {
            let fullname = document.getElementById('username');
            let email = document.getElementById('email');

            let check = 0;
            if (fullname.value.length == 0)
                fullname.nextElementSibling.getElementsByTagName('span')[0].innerHTML = 'Name cannot empty!';
            else
                check++;
            if (email.value.length == 0)
                email.nextElementSibling.getElementsByTagName('span')[0].innerHTML = 'Email cannot empty!';
            else if (email.value.indexOf(' ') != -1)
                email.nextElementSibling.getElementsByTagName('span')[0].innerHTML = 'Email cannot container space!';
            else
                check++;
            if (check == 2)
            {
                fullname.disabled = false;
                return true;
            }
            return false;
        }
    </script>
</div>