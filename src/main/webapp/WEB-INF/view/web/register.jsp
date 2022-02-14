<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
</head>
<body>
	<div class="body-container">
		<div class="login">
			<form action="register" method="post"
				onsubmit='return formValidate(this)'>
				<h1 style="text-align: center;">Account Registration</h1>
				<div>
					<label for="username">Username:</label> 
						<input type="text" value="${result !='success'? '': user.username}"
						name="username" id="username" placeholder="Username"
						onkeyup='handleInputChange(this)' pattern="^[\w]+"
						title="Tên đăng nhập không được chứa ký tự đặc biệt!">
					<div class="hasError">
						<span></span>
					</div>
				</div>
				<div>
					<label for="name">Fulname:</label> 
					<input type="text"
						name="fullname" id="name" placeholder="................"
						onkeyup='handleInputChange(this)' value="${result !='success'? '':user.fullname }">
					<div class="hasError">
						<span></span>
					</div>
				</div>
				<div>
					<label for="password">Password:</label> 
					<input type="password"
						name="password" id="password" placeholder="................."
						onkeyup='handleInputChange(this)' value="${result !='success'? '':user.password }">
					<div class="hasError">
						<span></span>
					</div>
				</div>

				<div>
					<label for="email">Email:</label> 
					<input type="email" name="email"
						id="email" placeholder="................"
						onkeyup='handleInputChange(this)' value="${result !='success'? '':user.email }">
					<div class="hasError">
						<span></span>
					</div>
				</div>
				<c:if test="${result != null }">
					<p
						style="text-align: center; background: antiquewhite; padding: 5px; display: block; font-size: 12px">
						${message }
						<c:if test="${result == 'success'}">
							<a href="${pageContext.request.contextPath}/login" style="color: tomato">Đăng Nhập</a>
						</c:if>
					</p>
				</c:if>
				<input type="submit" value="Register">
			</form>

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
								.getElementsByTagName('span')[0].innerHTML = 'Tài khoản không được bỏ trống!';
					else if (fullname.value.length <= 0)
						fullname.nextElementSibling
								.getElementsByTagName('span')[0].innerHTML = 'Họ Tên không được bỏ trống!';
					else if (password.value.length <= 0)
						password.nextElementSibling
								.getElementsByTagName('span')[0].innerHTML = 'Mật khẩu không được bỏ trống!';
					else if (password.value.length < 6)
						password.nextElementSibling
								.getElementsByTagName('span')[0].innerHTML = 'Mật khẩu phải từ 6 ký tự trở lên!';
					else if (email.value.length <= 0)
						email.nextElementSibling.getElementsByTagName('span')[0].innerHTML = 'Email không được bỏ trống!';
					else {
						children[4].setAttribute('disabled', false);
						return true;
					}

					children[4].setAttribute('disabled', true);
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

.login form>div {
	margin: 15px 0;
}

.login form>div>label {
	width: 30%;
	display: inline-block;
	font-size: 15px;
}

.login form input[type='checkbox'] {
	margin-left: 30%;
	cursor: pointer;
}

.login form>div>span {
	margin-left: 30%;
	width: 50%;
}

.login form input[type='text'], input[type='password'], input[type='email']
	{
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