<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="login">
	<form action="" onsubmit='return formValidate(this)' method="post">
		<h1 style="text-align: center;">Cập Nhật Tài Khoản</h1>
		<div>
			<label for="username">Tài khoản:</label> <input disabled type="text"
				name="username" id="username" placeholder="Tên đăng nhâp" value="${sessionScope.user_Loged.username }">
			<div class="hasError">
				<span></span>
			</div>
		</div>
		<div>
			<label for="name">Họ Và Tên:</label> <input type="text"
				name="fullname" id="name" placeholder="................"
				onkeyup='handleInputChange(this)' value="${user.fullname }">
			<div class="hasError">
				<span></span>
			</div>
		</div>

		<div>
			<label for="email">Email:</label> <input type="email" name="email"
				id="email" placeholder="................"
				onkeyup='handleInputChange(this)' value="${user.email }">
			<div class="hasError">
				<span></span>
			</div>
		</div>

		<c:if test="${result !=null }">
			<p style="text-align: center; background: antiquewhite; padding: 5px; display: block;">
				${message }</p>
		</c:if>

		<input type="submit" value="Lưu">
	</form>

	<script>
		function handleInputChange(e) {
			e.nextElementSibling.getElementsByTagName('span')[0].innerHTML = null;
		}

		function formValidate(e) {
			let fullname = document.getElementById('name');
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
				return true;
			return false;
		}
	</script>
</div>