<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="cart text-center">
    <i class="fa fa-shopping-cart" aria-hidden="true"></i>
    <a href="#" id="openCart">Shopping Cart</a>
    <script>
        function lineItemChange(e, product) {
            console.log('change lineitem')
            console.log(e);
            console.log(product);
            let totalPrice = document.getElementById('cartTotalPrice');
            if (e.value == 0) {
                confirmModal('Are you want remove this product?', () => {
                    fetch('/deleteItemInCart/' + product.id)
                        .then(res => res.json())
                        .then(data => {
                            if (data.result == 'error') {
                                alert('Cannot remove~. Try again!');
                                e.value = e.dataset.prev;
                            } else
                                e.parentNode.parentNode.remove();
                        }).catch(err => console.log(err));
                })
            } else
                fetch('/api/lineItemChangeQuantity/' + product.id + '/' + e.value)
                    .then(res => res.json())
                    .then(data => {
                        if (data.result == 'success') {
                            totalPrice.innerHTML = Number(totalPrice.innerHTML) + ((e.value - e.dataset.prev) * product.price);
                            e.dataset.prev = e.value;
                        } else
                            e.value = e.dataset.prev;
                    }).catch(err => {
                    console.log(err)
                    e.value = e.dataset.prev
                })
        }

        document.getElementById('openCart').addEventListener('click', function () {
            fetch('/api/userCart')
                .then(res => res.json())
                .then(data => {
                    if (data.result == 'success') {
                        if (data != 0) {
                            if (data.length != 0) {
                                jQuery('#cartScroll table tbody tr').empty();
                                document.querySelector('#cartScroll h3').style.display = 'block !important';
                                let totalPrice = 0;
                                const tbl = document.querySelector('#cartScroll table tbody');
                                let td = document.createElement('td');
                                data.data.map(item => {
                                    let tr = document.createElement('tr');
                                    let input = document.createElement('input');
                                    input.class = 'quantity';
                                    input.type = 'number';
                                    input.min = 1;
                                    input.max = 1000;
                                    input.step = 1;
                                    input.value = item.quantity;
                                    input.dataset.prev = item.quantity;
                                    input.onchange = () => lineItemChange(input, item.product);

                                    let td1 = td.cloneNode(true);
                                    let img = document.createElement('img');
                                    img.width = 80;
                                    img.height = 100;
                                    img.class = "d-block m-auto";
                                    img.src = item.product.image;
                                    td1.appendChild(img);

                                    let td2 = td.cloneNode(true);
                                    td2.innerHTML = item.product.title;

                                    let td3 = td.cloneNode(true);
                                    let span = document.createElement("span");
                                    td3.innerHTML = '$';
                                    span.innerHTML = item.product.price;
                                    span.class = 'lineItemPrice';
                                    td3.appendChild(span);

                                    let td4 = td.cloneNode(true);
                                    td4.appendChild(input);

                                    let td5 = td.cloneNode(true);
                                    td5.innerHTML = 'X';
                                    td5.style.cursor = 'pointer';
                                    td5.class = 'btn btn-outline-primary p-1 m-1';
                                    td5.addEventListener('click', (e) => {
                                        deleteItemInCart(e, item.product);
                                    })

                                    tr.appendChild(td1);
                                    tr.appendChild(td2);
                                    tr.appendChild(td3);
                                    tr.appendChild(td4);
                                    tr.appendChild(td5);

                                    tbl.appendChild(tr);
                                    totalPrice += item.product.price * item.quantity;
                                });
                                document.getElementById('cartTotalPrice').innerHTML = totalPrice;
                            } else
                                document.querySelector('#cartScroll h3').style.display = 'none !important';
                        } else
                            document.querySelector('#cartScroll h3').style.display = 'none !important';
                    } else {
                        document.querySelector('#cartScroll h3').style.display = 'block !important';
                    }
                })
                .catch(err => console.log(err))
            document.getElementById('cartScroll').style.display = 'block';
        });
    </script>
</div>
<div class="categories text-center">
    <div class="titleAside ">
        <h4 style="position: relative; z-index: 10;">Category</h4>
    </div>
    <c:forEach var="cat" items="${categories}">
        <div class="cat"><a href="/category/${cat.catName}">${cat.catName}</a></div>
    </c:forEach>
</div>
<div class="topSales">
    <div class="titleAside text-center">
        <h4 style="position: relative; z-index: 10; ">Top Sales</h4>
    </div>
    <c:forEach var="p" items="${topSales}">
        <div class="productItem" style="margin-left: 0">
            <a href="/product/${p.title}/${p.id}">
                <div class="productImage"
                     style="background-image: url('${p.image}');width: 50px; height: 70px; margin-bottom: 5px">
                </div>
            </a>
            <div class="productInfo">
                <h3 class="title">${p.title.length() >= 15 ? p.title.substring(0, 15): p.title}</h3>
                <div class="productPrice">
                    <div class="oldPrice">
                        <del></del>
                    </div>
                    <div class="newPrice">${p.price}$</div>
                </div>
                <div class="productAction">
                    <div class="btn btn-outline-primary" onclick="addItemIntoCart(${p.id})">Add</div>
<%--                    <div class="btn btn-outline-primary">Buy now</div>--%>
                </div>
            </div>
        </div>
    </c:forEach>
</div>