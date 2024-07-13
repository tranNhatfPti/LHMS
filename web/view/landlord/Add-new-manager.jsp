<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Danh sách nhân viên | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <style>


            #email-list {
                max-height: 150px; /* Adjust height as needed */
                overflow-y: auto;
            }
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button-->
            <a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">
                <!-- User Menu-->
                <li><a class="app-nav__item" href="/index.html"><i class='bx bx-log-out bx-rotate-180'></i></a></li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <hr>
            <ul class="app-menu">

                <li><a class="app-menu__item" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item active " href="management-lodging-houses?index=1"><i class='app-menu__icon bx bx-id-card'></i> <span
                            class="app-menu__label">Quản lí các khu trọ</span></a></li>
                <li><a class="app-menu__item " href="table-data-table.html"><i class='app-menu__icon bx bx-id-card'></i> <span
                            class="app-menu__label">Dịch vụ</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a>
                </li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span
                            class="app-menu__label">Chỉ số nước</span></a></li>
                <li><a class="app-menu__item" href="table-data-banned.html"><i class='app-menu__icon bx bx-run'></i><span
                            class="app-menu__label">Quản lý nội bộ
                        </span></a></li>
                <li><a class="app-menu__item" href="table-data-money.html"><i class='app-menu__icon bx bx-dollar'></i><span
                            class="app-menu__label">Phát sinh</span></a></li>
                <li><a class="app-menu__item" href="quan-ly-bao-cao.html"><i
                            class='app-menu__icon bx bx-pie-chart-alt-2'></i><span class="app-menu__label">Báo cáo doanh thu</span></a>
                </li>
                <li><a class="app-menu__item" href="page-calendar.html"><i class='app-menu__icon bx bx-calendar-check'></i><span
                            class="app-menu__label">Tính tiền </span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
                            đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="row search-form">              
            </div>
            <div class="container mt-5">
                <h1 class="modal-title" id="addModalLabel"style="text-align:center;">Thư mời quản lí trọ</h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="addLodgingForm" action="add-new-manager-house" method="post">
                <div class="form-group">
                    <label for="email">Email người nhận<span style="color:red;">(*)</span></label>
                    <input list="email-list" class="form-control" id="email" name="email" type="email" required multiple>
                    <datalist id="email-list" style="max-height: 150px; overflow-y: auto;">
                        <c:forEach items="${requestScope.listAc}" var="account">
                            <option value="${account.getEmail()}"></option>
                        </c:forEach>
                    </datalist>
                    <div id="email-error" style="color:red; display:none;">Vui lòng nhập địa chỉ email hợp lệ.</div>
                </div>

                <div class="form-group">
                    <label for="addressDetail">Mô tả<span style="color:red;">(*)</span></label>
                    <input type="text" name="description" class="form-control" id="addressDetail" placeholder="Nhập mô tả" value="Bạn đã được mời vào để quản lí trọ">

                    <span id="form-message-tinh" class="form-message"></span>
                </div>

                <div class="form-group">
                    <label for="nhatro">Nhà trọ<span style="color:red;">(*)</span></label>
                    <select class="form-control" id="nhatro" name="nhatro" required>
                        <c:forEach items="${requestScope.listLodging}" var="lodging">
                            <option value="${lodging.getLodgingHouseId()}">${lodging.getNameLodgingHouse()}</option>
                        </c:forEach>
                    </select>
                    <span id="form-message-phuong" class="form-message"></span>
                </div>

                <button type="submit" class="btn btn-primary">Gửi lời mời</button>
            </form>

        </main>
        <script>
            document.getElementById('email').addEventListener('input', function () {
                var emailField = this;
                var emailError = document.getElementById('email-error');
                emailError.style.display = emailField.validity.valid ? 'none' : 'block';
            });
        </script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
    </body>
</html>
