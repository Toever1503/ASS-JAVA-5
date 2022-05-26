<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="cartScroll" class="cart-container position-absolute">
    <div class="btn btn-outline-primary" id="closeCart">X</div>
    <h3 class="text-center">Giỏ hàng</h3>
    <script>
        document.getElementById('closeCart').addEventListener('click', function () {
            document.getElementById('cartScroll').style.display = 'none';
        });

        function deleteItemInCart(e, product) { //id is the id of the product in cart
            // document.getElementById(id).remove();
            console.log('delete', e)
            //remove the item from cart html
            const check = confirm('Are you want remove this product?');
            if (check) {
                fetch('/api/deleteItemInCart/' + product.id)
                    .then(res => res.json())
                    .then(data => {
                        if (data.result == 'success') {
                            alert('Delete successfully!')
                            // e.parentNode.parentNode.removeChild(e.parentNode);
                            let totalPrice = document.getElementById('cartTotalPrice');
                            totalPrice.innerHTML = Number(totalPrice.innerHTML) - (e.target.previousElementSibling.firstElementChild.value * product.price);
                            e.target.parentNode.remove();
                        } else {
                            alert('Delete failed! Server got error, code: 500')
                        }
                    })
                    .catch(err => console.log(err));
            }
        }

        function addItemIntoCart(id) {//id is the id of the product in cart
            console.log('addItem')
            fetch('/api/addItemToCart/' + id + '/1')
                .then(res => res.json())
                .then(data => {
                    if (data.result == 'success') {
                        alert('add successfully!')
                    } else {
                        alert('You need login to use this feature!')
                    }
                })
                .catch(err => console.log(err))
        }
    </script>
    <div class="table-wrapper-scroll-y my-custom-scrollbar">
        <h4 class="text-center" style="display: none">Giỏ hàng trống</h4>
        <table class="table table-bordered table-striped mb-0">
            <thead>
            <tr>
                <th></th>
                <th>Sản Phẩm</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th style="width: 10px;"></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

    </div>
    <div>
        <h3 class="text-center">Total: $<span id="cartTotalPrice">0</span></h3>
    </div>
    <div class="actionCart m-2">
        <a href="/order/orderProcess" class="btn btn-outline-primary">Order Now</a>
    </div>

</div>