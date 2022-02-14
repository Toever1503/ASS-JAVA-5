<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="@/css/productList.css">
    <link rel="stylesheet" href="@/css/style.css">
</head>
<body>
<tiles:insertAttribute name="header"/>

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