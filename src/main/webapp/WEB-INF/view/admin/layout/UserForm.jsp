<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<span class="add-new-film"><a href="/admin/users">Quay Lại</a></span>
<style>
    .add-new-film {
        border: 1px solid gray;
        padding: 5px;
        border-radius: 5px;
        background-color: white;
    }
</style>

<div class="login">
    <form:form action="" onsubmit='return formValidate(this)' method="post" modelAttribute="user">
        <h1 style="text-align: center;">${title}</h1>
        <form:input path="id" style="display: none"/>
        <div>
            <label>Username:</label>
            <form:input type="text" readonly="${type=='edit'?'true':'false'}" pattern="[\w]+" path="username"
                        id="username" placeholder="enter username"
                        title="Username only contains characters from a-z,0-9 and not have space"/>
            <div class="hasError">
                <span><form:errors path="username"/></span>
            </div>
        </div>
        <div>
            <label for="name">Fullname:</label>
            <form:input type="text"
                        path="name" id="name" placeholder="................"
                        onkeyup='handleInputChange(this)'/>
            <div class="hasError">
                <span><form:errors path="name"/></span>
            </div>
        </div>
        <div>
            <label for="password">Password:</label>
            <form:password
                    path="password" id="password" placeholder="................."
                    onkeyup='handleInputChange(this)'/>
            <div class="hasError">
                <span><form:errors path="password"/></span>
            </div>
        </div>

        <div>
            <label for="email">Email:</label>
            <form:input type="email" path="email" id="email" placeholder="................"
                        onkeyup="handleInputChange(this)"/>
            <div class="hasError">
                <span><form:errors path="email"/></span>
            </div>
        </div>

        <div>
            <label>Role:</label>
            <input type="checkbox" }
                   name="role" id="role" style="margin-left: unset;">
            Admin?
            <div class="hasError">
                <span></span>
            </div>
        </div>

        <c:if test="${result !=null }">
            <p
                    style="text-align: center; background: antiquewhite; padding: 5px; display: block;">
                    ${message }</p>
        </c:if>

        <input type="submit" value="Submit">
    </form:form>

    <script>
        function handleInputChange(e) {
            e.nextElementSibling.getElementsByTagName('span')[0].innerHTML = null;
        }

        function formValidate(e) {
            let children = e.children;
            let username = document.getElementById('username');
            let password = document.getElementById('password');
            let fullname = document.getElementById('name');
            let email = document.getElementById('email');

            let check = 0;
            if (username.value.length <= 0)
                username.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Username cannot empty!';
            else if (fullname.value.length <= 0)
                fullname.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Name cannot empty!';
            else if (password.value.length <= 0)
                password.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Password cannot empty!';
            else if (password.value.length < 6)
                password.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Password must be greater than 6 characters!';
            else if (password.value.indexOf(' ') != -1)
                password.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Password cannot contains space!';
            else if (email.value.length <= 0)
                email.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Email cannot empty!';
            else if (email.value.indexOf(' ') != -1)
                email.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Email cannot contains space!';
            else {
                document.getElementById('username').readOnly = false;
                e.action = '/admin/${type=='edit'?'edit/${user.id}' :'add'}' + (document.getElementById('role').checked == true ? 2 : 1);
                return true;
            }
            return false;
        }
    </script>
</div>

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