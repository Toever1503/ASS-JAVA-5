<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel=stylesheet type="text/css" href="${pageContext.request.contextPath}/static/css/login.css">
</head>
<body>
<div class="body-container">
    <div class="login">
        <form:form action="" modelAttribute="user"
                   onsubmit='return formValidate(this)' method="post">
            <h1 style="text-align: center;">Login</h1>
            <div>
                <label for="username">Username:</label>
                <form:input path="username" value="${user.username}"
                            onkeyup="handleInputChange(this)" id="username"/>
                <div class="hasError">
                    <span></span>
                </div>
            </div>
            <div>
                <label for="password">Password:</label>
                <form:password path="password" value="${user.password}"
                               onkeyup="handleInputChange(this)" id="password"/>
                <div class="hasError">
                    <span></span>
                </div>
            </div>
            <div>
                <form:checkbox value="true" path="isRemember"
                               id="remember"/>
                <label for="remember">Remember me?</label>
            </div>

            <c:if test="${result!= null }">
                <p
                        style="text-align: center; background: antiquewhite; padding: 5px; display: block;">
                        ${message }</p>

                <c:if test="${result =='success' }">
                    <script type="text/javascript">
                        setTimeout(() => {
                            window.location.replace("http://localhost:8080/");
                        }, 2000);
                    </script>
                </c:if>
            </c:if>
            <input type="submit" value="Login">
        </form:form>

        <script>
            function handleInputChange(e) {
                e.nextElementSibling.getElementsByTagName('span')[0].innerHTML = null;
            }

            function formValidate(e) {
                let children = e.children;
                let username = document.getElementById('username');
                let password = document.getElementById('password');

                let check = 0;
                if (username.value.length <= 0)
                    username.nextElementSibling
                        .getElementsByTagName('span')[0].innerHTML = 'Tài khoản không được bỏ trống!';
                else if (password.value.length <= 0)
                    password.nextElementSibling
                        .getElementsByTagName('span')[0].innerHTML = 'Mật khẩu không được bỏ trống!';
                else {
                    children[4].setAttribute('disabled', false);
                    document.getElementsByTagName('form')[0].action = '/login';
                    return true;
                }

                return false;
            }
        </script>
    </div>


</div>

</body>
</html>
<style>
    body {
        background-color: #f1ededba;
    }

    .body-container {
        width: 1000px;
        margin: 0 auto;
        display: block !important;
    }

    .login {
        margin-top: 100px;
    }

    .login form {
        padding: 15px;
        background-color: white;
        width: 500px;
        margin: 0 auto;
        border-radius: 5px;
    }

    .login form > div {
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

    .login form input[type='text'], input[type='password'], input[type='email'] {
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