<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Stats</h3>
<section class="row">
    <style>
        section {
            border: 1px solid gainsboro;
            width: 90%;
            padding: 5px;
        }
    </style>
    <script>
        function onChangeDatePicker() {
            const date = document.getElementById('datePicker').value;
            fetch('/api/order/calculateRevenueByDate/' + date)
                .then(res => res.json())
                .then(data => {
                    console.log(data)
                    if (data.result == 'success') {
                        document.getElementById('totalRevenue').innerHTML = data.data + '$';
                    } else {
                        document.getElementById('totalRevenue').innerHTML = 'Revenue have not yet!';
                    }
                }).catch(err => document.getElementById('totalRevenue').innerHTML = 'server got error, code: 500');
        }

        function onChangeCategoryPicker() {
            const cat = document.getElementById('categoryPicker').value;
            fetch('/api/order/calculateRevenueByCategory/' + cat)
                .then(res => res.json())
                .then(data => {
                    console.log(data)
                    if (data.result == 'success') {
                        document.getElementById('totalRevenue').innerHTML = data.data + '$';
                    } else {
                        document.getElementById('totalRevenue').innerHTML = 'Revenue have not yet!';
                    }
                }).catch(err => document.getElementById('totalRevenue').innerHTML = 'server got error, code: 500');
        }
    </script>
    <div class="form-group col-3">
        <label for="datePicker">Pick Time</label>
        <input type="date" class="form-control" name="datePicker" id="datePicker">
        <div class="btn btn-outline-primary m-1 p-1" onclick="onChangeDatePicker()">Apply</div>
    </div>
    <div class="form-group col-3">
        <label for="categoryPicker">Pick Category</label>
        <select type="date" class="form-control" name="categoryPicker" id="categoryPicker">
            <option value="">123</option>
            <c:forEach var="cat" items="${listCat}">
                <option value="${cat.id}">${cat.catName}</option>
            </c:forEach>
        </select>
        <div class="btn btn-outline-primary m-1 p-1" onclick="onChangeCategoryPicker()">Apply</div>
    </div>
</section>
<div class="statsContent">
    <h3 class="d-inline">Total revenue: </h3>
    <span id="totalRevenue">1141$</span>
</div>