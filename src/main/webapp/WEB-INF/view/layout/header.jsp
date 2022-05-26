<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header class="bg-dark">
    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="javascript:void(0)">Logo</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="mynavbar">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/contact">Contact</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="javascript:void(0)">About US</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="javascript:void(0)" role="button"
                           data-toggle="dropdown">Account</a>
                        <ul class="dropdown-menu">
                            <c:if test="${cookie['user_login'] != null}">
                                <li><a class="dropdown-item" href="/edit_profile">Profile</a></li>
                                <li><a class="dropdown-item" href="/change_password">Change Password</a></li>
                                <li><a class="dropdown-item" href="/order/my_orders">My Orders</a></li>
                                <li><a class="dropdown-item" href="/logout">Logout</a></li>

                                <c:if test="${cookie['role'].getValue().equalsIgnoreCase('admin')}}">
                                    <li><a class="dropdown-item" href="/admin">System Management</a></li>
                                </c:if>
                            </c:if>

                            <c:if test="${cookie['user_login'] == null}">
                                <li><a class="dropdown-item" href="/login">Login</a></li>
                                <li><a class="dropdown-item" href="javascript:void(0)">Forgot Password</a></li>
                            </c:if>
                        </ul>
                    </li>
                </ul>
                <form class="d-flex" action="/search">
                    <input class="form-control me-2" name="q" type="text" placeholder="Search">
                    <input class="btn btn-primary" type="submit" value="Search"/>
                </form>
            </div>
        </div>
    </nav>
</header>

<script>
    function confirmModal(message, callback) {
        var confirmIndex = true;

        var newMessage = message.replace(/(?:\r\n|\r|\n)/g, "<br>");
        jQuery('#modal_confirm_dialog_body').html("" + newMessage + "");
        jQuery('#modal_confirm_dialog').modal('show');

        jQuery('#confirm_cancle').on("click", function () {
            if (confirmIndex) {
                callback(false);
                jQuery('#modal_confirm_dialog').modal('hide');
                confirmIndex = false;
            }
        });

        jQuery('#confirm_ok').on("click", function () {
            if (confirmIndex) {
                callback(true);
                jQuery('#modal_confirm_dialog').modal('hide');
                confirmIndex = false;
            }
        });
    }
</script>