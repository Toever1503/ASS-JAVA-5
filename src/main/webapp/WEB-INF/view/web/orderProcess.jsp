<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<style>
    form > div {
        margin: 10px 0;
    }
</style>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item active"><a href="/">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">Order</li>
    </ol>
</nav>
<div id="orderForm" style="width: 700px; margin: auto;">
    <form:form action="" method="post" modelAttribute="orderInfo">
        <h4 class="text-center col-12">Order Information</h4>
        <div class="form-group">
            <label for="fullname">Fullname</label>
            <form:input path="fullname" required="true" maxlength="255" placeholder="fullname" class="form-control"
                        id="fullname"/>
            <span class="p-2" style="color:red"><form:errors path="fullname"/></span>
        </div>
        <div class="form-group">
            <label for="phone">Phone</label>
            <form:input title="Phone cannot exceed 10 numbers" pattern="0[0-9]{9}" path="phone" required="true"
                        maxlength="10" placeholder="phone" type="text" class="form-control"
                        id="phone"/>
            <span class="p-2" style="color:red"><form:errors path="phone"/></span>
        </div>
        <div class="addDress row mt-2">
            <script>
                const optionOrder = document.createElement("option");

                function onchangeProvince(id) {
                    const selectTag = document.getElementById('district');
                    jQuery(selectTag).empty();
                    jQuery('#ward').empty();
                    fetch('/api/getDistricts/' + id)
                        .then(res => res.json())
                        .then(data => {
                            data.map(district => {
                                let option = optionOrder.cloneNode(true);
                                option.value = district.id;
                                option.innerHTML = district.name;
                                selectTag.appendChild(option);
                            })
                            selectTag.value = data[0].id;
                            onchangeDistrict(data[0].id);
                        })
                        .catch(err => console.log(err));

                }

                function onchangeDistrict(id) {
                    const selectTag = document.getElementById('ward');
                    jQuery(selectTag).empty();
                    fetch('/api/getWards/' + id)
                        .then(res => res.json())
                        .then(data => {
                            data.map(ward => {
                                let option = optionOrder.cloneNode(true);
                                option.value = ward.id;
                                option.innerHTML = ward.name;
                                selectTag.appendChild(option);
                            })
                            selectTag.value = data[0].id;
                        })
                        .catch(err => console.log(err));
                }

            </script>

            <h5 class="col-12">Address</h5>
            <div class="form-group col-4">
                <label for="province">City</label>
                <form:select class="form-control" path="province" value="" id="province" required="true"
                             onchange="onchangeProvince(this.value)">
                    <form:option value="">Choose Province</form:option>
                    <c:forEach var="province" items="${provinces}">
                        <form:option value="${province.id}">${province.name}</form:option>
                    </c:forEach>
                </form:select>
                <span class="p-2" style="color:red"><form:errors path="province"/></span>
            </div>
            <div class="form-group col-4">
                <label for="district">District</label>
                <form:select class="form-control" value="1" required="true" path="district" id="district"
                             onchange="onchangeDistrict(this.value)">
                </form:select>
                <span class="p-2" style="color:red"><form:errors path="district"/></span>
            </div>
            <div class="form-group col-4">
                <label for="ward">Ward</label>
                <form:select class="form-control" value="" required="true" path="ward" id="ward">
                </form:select>
                <span class="p-2" style="color:red"><form:errors path="ward"/></span>
            </div>
            <div class="form-group col-12">
                <label for="address">Details</label>
                <form:input path="detail" required="true" maxlength="255" placeholder="house number, street..."
                            class="form-control" id="address"/>
                <span class="p-2" style="color:red"><form:errors path="detail"/></span>
            </div>
        </div>
        <div class="form-group p-1">
            <label>Feeship default is 5$</label>
        </div>
        <div>
            <input class="btn btn-outline-primary" type="submit" value="Confirm">
            <c:if test="${result != null} ">
                <span style="color: red;">${message}</span>
            </c:if>

            <c:if test="${result == 'success'}">
                <script>
                    setTimeout(function () {
                        window.location.href = "/";
                    }, 3000);
                </script>
            </c:if>

        </div>
    </form:form>
</div>