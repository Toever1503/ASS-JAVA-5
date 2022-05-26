<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="bodyContentLeft col-7">
    <script>
        document.getElementsByClassName('main-content')[0].className = 'main-content row';
        const onClickEdit = (id, catName) => {
            document.getElementById('id').value = id;
            document.getElementById('catName').value = catName;
            document.getElementById('errorMessage').innerText = '';
            document.getElementById('catAction').innerText = 'Edit';
        }
        const onclickDelete = (e, id) => {
            modalConfirm("Are you want to delete this category?", () => {
                fetch('/api/category/delete/' + id)
                    .then(res => res.json())
                    .then(data => {
                        if (data.result == 'success') {
                            e.target.parentNode.parentNode.parentNode.remove();
                            alert("Delete successfully!");
                        } else
                            alert("Delete failed, code: 500");
                    }).catch(err => console.log(err));
            })
        }
    </script>
    <div id="cartScroll" class="cart-container position-absolute">
        <h3 class="text-center">Category</h3>

        <div class="table-wrapper-scroll-y my-custom-scrollbar">
            <table class="table table-bordered table-striped mb-0">
                <tbody>
                <c:forEach var="obj" items="${listCats}">
                    <tr>
                        <td>${obj.catName}</td>
                        <td>
                            <div class="btn btn-outline-primary" onclick="onClickEdit(${obj.id}, ${obj.catName})">Edit
                            </div>
                            <div class="btn btn-outline-primary" onclick="onclickDelete(this, ${obj.id})">Delete
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="bodyContentRight col-5 p-3" style="border-left:1px solid gainsboro; ">
    <script>
        const submitCat = () => {
            const id = document.getElementById('id').value;
            const catName = document.getElementById('catName').value;
            const errorMessage = document.getElementById('errorMessage');
            const catAction = document.getElementById('catAction');
            if (catName === '')
                errorMessage.innerText = 'Please enter category name';
            else
                fetch('/api/category', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: id,
                        catName: catName
                    })
                }).then(res => res.json())
                    .then(data => {
                        if (data.result == 'success') {
                            errorMessage.innerText = catAction.innerText + " Successfully!";
                            catAction.innerText = 'Add';
                            let tr = document.createElement('tr');
                            let td1 = document.createElement('td');
                            let td2 = document.createElement('td');
                            td1.innerText = data.data.catName;
                            td2.innerHTML = `<div class="btn btn-outline-primary" onclick="onClickEdit(this)">Edit</div>
                                            <div class="btn btn-outline-primary" onclick="onclickDelete(this, ${data.data.id})">Delete</div>`;
                            tr.appendChild(t1);
                            tr.appendChild(t2);
                            document.querySelector('#cartScroll table tbody').appendChild(tr);
                            jQuery('#formCat')[0].reset();
                        } else
                            errorMessage.innerText = data.message;
                    }).catch(err => console.log(err));
        }

        const clearForm = () => {
            jQuery('#formCat')[0].reset();
            document.getElementById('catAction').innerText = 'Add';
        }
    </script>
    <form action="javascript:void(0)" id="formCat">
        <h3 class="text-center">Form</h3>
        <input class="d-none" type="text" name="id" id="id">
        <div class="form-group mb-2">
            <label for="catName">Category name:</label>
            <input type="text" maxlength="255" required class="form-control" name="catName"
                   placeholder="Enter category name" id="catName">
        </div>
        <p id="errorMessage" style="font-size: 13px; color: red;">
        </p>
    </form>
    <button id="catAction" class="btn btn-outline-primary" onclick="submitCat()">Add</button>
    <button class="btn btn-outline-primary" onclick="clearForm()">Clear</button>
</div>