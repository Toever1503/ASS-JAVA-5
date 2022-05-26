<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header>
	<div class="header-content">
		<a href="/admin">
			<div class="home-icon"></div>
		</a> <a href="/">Go to web</a>
	</div>
	<style>
* {
	margin: 0;
	padding: 0;
}

a {
	text-decoration: none;
	color: black;
}

header {
	background-color: antiquewhite;
	padding: 5px;
}

.header-content .home-icon {
	display: inline-block;
	background-image:
		url('https://th.bing.com/th/id/R.daab0bbec29a936da1a238d6c872874b?rik=ZNnrA%2f%2feOdRMlg&riu=http%3a%2f%2fcdn.onlinewebfonts.com%2fsvg%2fimg_517699.png&ehk=1E2%2fTRcpdysCjK8lVWkPBuGeYpo4UxYLGQ1WPjog430%3d&risl=&pid=ImgRaw&r=0');
	height: 20px;
	width: 20px;
	background-size: cover;
}

.header-content a {
	margin: 10px;
	font-weight: bold;
	transition: 0.3s ease-in-out;
}

.header-content a:hover {
	color: coral;
}
</style>
</header>

<style>
.body-content {
	width: 100%;
	min-height: 800px;
	display: flex;
}

.body-content aside {
	width: 15%;
	background-color: white;
	border-right: 1px solid black;
}

.body-content .main-content {
	width: 85%;
	background-color: skyblue;
	padding: 10px;
}

aside .action-container {
	
}

aside .action-item {
	padding: 10px;
	border-top: 1px solid black;
	font-weight: bold;
}

aside .action-item:hover {
	background-color: powderblue;
}
</style>