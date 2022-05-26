<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item active"><a href="/">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">Change Password</li>
    </ol>
</nav>
<div class="change-password login">
    <form:form action="" onsubmit='return formValidate(this)' method="post" modelAttribute="user_pass">
        <h1 style="text-align: center;">Đổi Mật Khẩu</h1>
        <div>
            <label for="password">Mật Khẩu Hiện Tại:</label>
            <form:password path="currentPassword" id="password" placeholder="current password"
                           onkeyup='handleInputChange(this)'/>
            <div class="hasError">
                <span><form:errors path="currentPassword" /></span>
            </div>
        </div>
        <div>
            <label for="newPassword">Mật Khẩu Mới:</label>
            <form:password path="newPassword" id="newPassword" placeholder="new password"
                           onkeyup='handleInputChange(this)'/>
            <div class="hasError">
                <span><form:errors path="newPassword" /></span>
            </div>
        </div>
        <div>
            <label for="newPasswordConfirm">Xác Nhận Mật Khẩu Mới:</label>
            <form:password path="confirmNewPassword" id="newPasswordConfirm" placeholder="confirm new password"
                           onkeyup='handleInputChange(this)'/>
            <div class="hasError">
                <span><form:errors path="confirmNewPassword" /></span>
            </div>
        </div>

        <c:if test="${result != null }">
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
            let children = e.children;
            let password = document.getElementById('password');
            let newPassword = document.getElementById('newPassword');
            let newPasswordConfirm = document
                .getElementById('newPasswordConfirm');

            let check = 0;
            if (password.value.length <= 0)
                password.nextElementSibling.getElementsByTagName('span')[0].innerHTML = 'Od password cannot empty!';
            else if (newPassword.value.length <= 0)
                newPassword.nextElementSibling.getElementsByTagName('span')[0].innerHTML = 'New password cannot empty!';
            else if (newPassword.value.length < 6)
                newPassword.nextElementSibling.getElementsByTagName('span')[0].innerHTML = 'New password must have above 6 characters!';
            else if (newPasswordConfirm.value.length <= 0)
                newPasswordConfirm.nextElementSibling
                    .getElementsByTagName('span')[0].innerHTML = 'Confirm password cannot empty!';
            else {
                if (newPassword.value !== newPasswordConfirm.value)
                    newPasswordConfirm.nextElementSibling
                        .getElementsByTagName('span')[0].innerHTML = 'Confirm password not fit with new password';
                else {
                    newPasswordConfirm.nextElementSibling
                        .getElementsByTagName('span')[0].innerHTML = '';
                    e.action = ''
                    return true;
                }
            }

            return false;
        }
    </script>
</div>