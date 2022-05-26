<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
    <h3>Orders</h3>
</div>

<div class="myOrders">
    <script>
        function showOrder(id, address) {
            jQuery('.orderDetail').show();
            fetch('/api/order/getOrderDetails/' + id)
                .then(res => res.json())
                .then(data => {
                    if (data.result == 'success') {
                        console.log(data)
                        const td = document.createElement('td');
                        data.data.forEach(item => {
                            let tr = document.createElement('tr')
                            let td1 = td.cloneNode(true);
                            td1.innerHTML = item.id.productId.title;

                            let td2 = td.cloneNode(true);
                            td2.innerHTML = item.price + '$';

                            let td3 = td.cloneNode(true);
                            td3.innerHTML = 'x' + item.quantity;

                            tr.appendChild(td1);
                            tr.appendChild(td2);
                            tr.appendChild(td3);

                            jQuery('.listLineItems table tbody')[0].appendChild(tr);
                        });
                    }
                }).catch(error => console.log(error));

            // fetch('/api/order/address/'+address).then(res => res.json())
            //     .then(data => {
            //         console.log('address');
            //         console.log(data)
            //         if(data != null){
            //             document.getElementById('province').innerText = data;
            //             document.getElementById('ward').innerText = data;
            //             document.getElementById('district').innerText = data;
            //             document.getElementById('detail').innerText = data;
            //         }
            //
            //     }).catch(err => console.log(err));
        }

        function closeOrderDetail() {
            jQuery('.orderDetail').hide();
            // document.getElementById('province').innerHTML = ' ';
            // document.getElementById('ward').innerHTML = ' ';
            // document.getElementById('district').innerHTML = ' ';
            // document.getElementById('detail').innerHTML = ' ';
            jQuery('.listLineItems .table tbody tr').empty();
        }

        function cancelOrder(e, orderId) {
            console.log(e)
            if (e.className.indexOf('disabled') != -1) {
                return;
            }
            fetch('/api/order/cancel/' + orderId)
                .then(res => res.json())
                .then(data => {
                    if (data.result == 'success') {
                        e.parentNode.parentNode.querySelector('.status').innerHTML = 'Canceled';

                        e.className = e.className + ' disabled';
                        e.parentNode.previousSibling.innerHTML = 'Confirmed';
                        console.log(e.previousSibling)
                    } else {
                        alert(data.message);
                    }
                }).catch(err => console.log(err));
        }

        function confirmOrder(e, orderId) {
            console.log(e)
            if (e.className.indexOf('disabled') != -1) {
                return;
            }
            fetch('/api/order/confirm/' + orderId)
                .then(res => res.json())
                .then(data => {
                    if (data.result == 'success') {
                        e.parentNode.parentNode.querySelector('.status').innerHTML = 'Confirmed';
                        console.log(e.nextElementSibling)
                        e.className = e.className + ' disabled';
                    } else
                        alert(data.message);
                }).catch(err => console.log(err));
        }
    </script>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Order Id</th>
            <th>Fullname</th>
            <th>Phone</th>
            <th>Delivery Fee</th>
            <th>Total Price</th>
            <th>status</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${!myOrders.isEmpty()}">
            <c:forEach var="order" items="${myOrders}">
                <tr data-id="${order.id}" onclick="showOrder(${order.id}, ${order.address})">
                    <td>${order.id}</td>
                    <td>${order.fullname}</td>
                    <td>${order.phone}</td>
                    <td>${order.deliveryFee}$</td>
                    <td>${order.totalPrice}$</td>
                    <td class="status">${order.status == 'waiting'? 'waiting for confirm' : order.status=='confirmed' ? 'Confirmed': 'Canceled' }</td>
                    <td>
                        <c:if test="${order.status != 'canceled'}">
                            <div class="btn btn-outline-primary mr-2"
                                 onclick="confirmOrder(this, ${order.id}); event.stopPropagation();">Confirm
                            </div>
                            <div class="btn btn-outline-primary"
                                 onclick="cancelOrder(this, ${order.id}); event.stopPropagation();">Cancel
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${myOrders.isEmpty()}">
            <h1>Orders empty!</h1>
        </c:if>

        </tbody>
    </table>
</div>
<div class="orderDetail p-3">
    <div class="address">
        <%--        <div class="row">--%>
        <%--            <p class="col-2"><b>Province:</b> <span id="province"></span></p>--%>
        <%--            <p class="col-2"><b>District:</b> <span id="district"></span></p>--%>
        <%--            <p class="col-2"><b>Ward:</b> <span id="ward"></span></p>--%>
        <%--        </div>--%>
        <%--        <div class="detail">--%>
        <%--            <span style="color: black; font-weight: bold;">Address: </span>--%>
        <%--            <span id="detail"></span>--%>
        <%--        </div>--%>
        <div>
            Order detail
        </div>
        <div class="listLineItems">
            <h4>Products:</h4>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Price</th>
                    <th>Quantity</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
    <button class="btn btn-primary" id="closeOrderDetail" onclick="closeOrderDetail()">Close</button>
</div>
<style>
    .orderDetail {
        position: absolute;
        top: 10%;
        width: 100%;
        height: 500px;
        display: none;
        background-color: #0dcaf0;
    }

    .main-content {
        position: relative;
    }

    #closeOrderDetail {
        position: absolute;
        top: 10px;
        right: 10px;
    }
</style>
