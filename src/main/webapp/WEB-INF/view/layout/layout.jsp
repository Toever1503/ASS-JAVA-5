<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page session="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="/static/css/productList.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/css/cart.css">
    <link rel=stylesheet type="text/css" href="/static/css/login.css">
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="cart"/>
<div class="bodyContainer container" style="min-height: 700px; padding: 5px;">
    <div class="mainContent d-flex">
        <div class="bodyContent" style="width: 80%;">
            <tiles:insertAttribute name="body"/>
        </div>
        <aside style="width: 20%; padding: 10px;">
            <tiles:insertAttribute name="aside"/>
        </aside>
    </div>
</div>
<tiles:insertAttribute name="footer"/>

</body>
</html>