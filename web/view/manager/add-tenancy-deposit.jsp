<%-- 
    Document   : room-detail.jsp
    Created on : Jun 13, 2024, 2:15:04 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <title>Danh sách nhân viên | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip-utils/0.1.0/jszip-utils.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/docxtemplater/3.25.1/docxtemplater.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.0/FileSaver.min.js"></script>
        <style>
            .fixed-size-image {
                width: 100%;
                height: auto;
                max-width: 100%; /* Ensures the image fits within the column */
            }
            .form-message {
                color: #dc3545; /* Màu đỏ */
                font-size: 0.875rem; /* Kích thước chữ nhỏ */
                margin-top: 0.25rem; /* Khoảng cách từ input */
            }
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">


                <!-- User Menu-->
                <li><a class="app-nav__item" href="/index.html"><i class='bx bx-log-out bx-rotate-180'></i> </a>

                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user" ><img class="app-sidebar__user-avatar" src="" width="100px"
                                                 alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b></b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item haha" href="phan-mem-ban-hang.html"><i class='app-menu__icon bx bx-cart-alt'></i>
                        <span class="app-menu__label">POS Bán Hàng</span></a></li>
                <li><a class="app-menu__item " href="index.html"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item " href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý khách hàng</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a>
                </li>
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span
                            class="app-menu__label">Quản lý đơn hàng</span></a></li>
                <li><a class="app-menu__item" href="table-data-banned.html"><i class='app-menu__icon bx bx-run'></i><span
                            class="app-menu__label">Quản lý nội bộ
                        </span></a></li>
                <li><a class="app-menu__item" href="table-data-money.html"><i class='app-menu__icon bx bx-dollar'></i><span
                            class="app-menu__label">Bảng kê lương</span></a></li>
                <li><a class="app-menu__item" href="quan-ly-bao-cao.html"><i
                            class='app-menu__icon bx bx-pie-chart-alt-2'></i><span class="app-menu__label">Báo cáo doanh thu</span></a>
                </li>
                <li><a class="app-menu__item" href="page-calendar.html"><i class='app-menu__icon bx bx-calendar-check'></i><span
                            class="app-menu__label">Lịch công tác </span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
                            đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <!--                <ul class="app-breadcrumb breadcrumb side">
                                    <li class="breadcrumb-item active"><a href="list-staff"><b>Danh sách sản phẩm</b></a></li>
                                </ul>-->
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <c:if test="${sessionScope.account.getRoleId() == 2}">
                                    <div class="col-sm-2">
                                    </div>
                                </c:if>
                            </div>
                            <div class="row">

                                <div class="col-md-12">
                                    <h4><strong>Thêm thông tin người đặt cọc</strong></h4>

                                    <form id="form" class="row" method="POST" action="add-tenancy-deposit">
                                        <input class="form-control" type="text" maxlength="150" value="${sessionScope.roomId}" name="roomId" hidden>
                                        <div class="form-group col-sm-4">
                                            <label class="control-label">Họ và tên:(*)</label>
                                            <input class="form-control" type="text" maxlength="150" id="name" name="name" required="">
                                            <span id="form-message-fullname" class="form-message"></span>
                                        </div>
                                        <div class="form-group col-sm-4">
                                            <label class="control-label">Email:(*)</label>
                                            <input class="form-control" type="text" maxlength="150" id="email" name="email" required>
                                            <span id="form-message-email" class="form-message"></span>
                                        </div>
                                        <div class="form-group col-sm-4">
                                            <label class="control-label">Số điện thoại:(*)</label>
                                            <input class="form-control" type="number" maxlength="150" id="phoneNumber" name="phoneNumber" required>
                                            <span id="form-message-phone" class="form-message"></span>
                                        </div>

                                        <div class="form-group col-sm-4">
                                            <label class="control-label">Tiền cọc:(*)</label>
                                            <input class="form-control" type="number" maxlength="150" id="depositMoney" min="1000" max="20000000" name="depositMoney" required>
                                            <span id="form-message-deposit" class="form-message"></span>
                                        </div>
                                        <div class="form-group col-sm-4">
                                            <label class="control-label">Ngày sinh:(*)</label>
                                            <input class="form-control" type="date" maxlength="150" id="dateOfBirth" name="dateOfBirth" required>
                                            <span id="form-message-date" class="form-message"></span>
                                        </div>
                                        <div class="form-group col-sm-4">
                                            <label class="control-label">Căn cước công dân:(*)</label>
                                            <input class="form-control" type="text" maxlength="150" id="cic" name="cic" required>
                                            <span id="form-message-cic" class="form-message"></span>
                                        </div>
                                        <div class="form-group col-sm-12">
                                            <label class="control-label">Mô tả:(*)</label>
                                            <textarea class="form-control" id="description" name="description" rows="5" cols="10" required></textarea>
                                            <span id="form-message-description" class="form-message"></span>
                                        </div>
                                        <!--                                        <div class="form-group col-sm-6">
                                                                                    <label class="btn btn-primary">
                                                                                        <input type="file" name="file">  Input type file hidden   
                                                                                    </label>
                                                                                </div>-->
                                        <div class="form-group col-sm-3">
                                            <input type="submit" value="Lưu lại" class="form-control btn btn-save" style=" text-align: center; margin: 10px">
                                        </div>
                                        <div class="form-group col-sm-3">
                                            <a href="view-tenancy-deposit?roomId=${sessionScope.roomId}" class="form-control btn btn-warning" type="btn" style=" text-align: center; margin: 10px">Quay lại</a>
                                        </div>
                                    </form>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </main>
        <!-- Essential javascripts for application to work-->
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="src/jquery.table2excel.js"></script>
        <script src="js/main.js"></script>
        <!-- The javascript plugin to display page loading on top-->
        <script src="js/plugins/pace.min.js"></script>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!--        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip-utils/0.1.0/jszip-utils.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/docxtemplater/3.25.1/docxtemplater.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.0/FileSaver.min.js"></script>-->
        <!-- Thư viện jsPDF -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.3.1/jspdf.umd.min.js"></script>

        <script type="text/javascript">
        $('#sampleTable').DataTable();
        //Thời Gian
        function time() {
            var today = new Date();
            var weekday = new Array(7);
            weekday[0] = "Chủ Nhật";
            weekday[1] = "Thứ Hai";
            weekday[2] = "Thứ Ba";
            weekday[3] = "Thứ Tư";
            weekday[4] = "Thứ Năm";
            weekday[5] = "Thứ Sáu";
            weekday[6] = "Thứ Bảy";
            var day = weekday[today.getDay()];
            var dd = today.getDate();
            var mm = today.getMonth() + 1;
            var yyyy = today.getFullYear();
            var h = today.getHours();
            var m = today.getMinutes();
            var s = today.getSeconds();
            m = checkTime(m);
            s = checkTime(s);
            nowTime = h + " giờ " + m + " phút " + s + " giây";
            if (dd < 10) {
                dd = '0' + dd
            }
            if (mm < 10) {
                mm = '0' + mm
            }
            today = day + ', ' + dd + '/' + mm + '/' + yyyy;
            tmp = '<span class="date"> ' + today + ' - ' + nowTime +
                    '</span>';
            document.getElementById("clock").innerHTML = tmp;
            clocktime = setTimeout("time()", "1000", "Javascript");

            function checkTime(i) {
                if (i < 10) {
                    i = "0" + i;
                }
                return i;
            }
        }
        </script>
        <script>
            document.getElementById('name').addEventListener('input', function () {
                const input = this.value.trim();///\d/

                if (/[^a-zA-Z\s\u00C0-\u024F\u1E00-\u1EFF]/.test(input)) {
                    document.getElementById('form-message-fullname').textContent = 'Họ và tên chỉ có thể chứa chữ cái.';
                } else if (input === '') {
                    document.getElementById('form-message-fullname').textContent = '';
                } else if (input.split(' ').length === 1) {
                    document.getElementById('form-message-fullname').textContent = 'Vui lòng nhập cả họ và tên.';
                } else if (input.split(' ').some(word => word === '')) {
                    document.getElementById('form-message-fullname').textContent = 'Họ và tên không thể chứa khoảng trắng ở đầu hoặc cuối.';
                } else {
                    // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                    document.getElementById('form-message-fullname').textContent = '';
                }
            });

            // Gắn sự kiện "submit" lên form
            document.getElementById("form").addEventListener("submit", function (event) {
                const input = document.getElementById('name').value.trim();
                if (/[^a-zA-Z\s\u00C0-\u024F\u1E00-\u1EFF]/.test(input) || input === '' || input.split(' ').length === 1 || input.split(' ').some(word => word === '')) {
                    // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form

                    if (input === '') {
                        document.getElementById('form-message-fullname').textContent = 'Vui lòng nhập họ và tên.';
                    } else if (/[^a-zA-Z\s\u00C0-\u024F\u1E00-\u1EFF]/.test(input)) {
                        document.getElementById('form-message-fullname').textContent = 'Họ và tên chỉ có thể chứa chữ cái.';
                    } else if (input.split(' ').length === 1) {
                        document.getElementById('form-message-fullname').textContent = 'Vui lòng nhập cả họ và tên.';
                    } else if (input.split(' ').some(word => word === '')) {
                        document.getElementById('form-message-fullname').textContent = 'Họ và tên không thể chứa khoảng trắng ở đầu hoặc cuối.';
                    }
                    event.preventDefault();
                } else {
                    // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                    document.getElementById('form-message-fullname').textContent = '';
                }
            });


            document.getElementById('email').addEventListener('input', function () {
                const emailInput = this.value.trim();

                if (emailInput === '') {
                    document.getElementById('form-message-email').textContent = '';
                } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput)) {
                    document.getElementById('form-message-email').textContent = 'Địa chỉ email không hợp lệ.';
                } else {
                    document.getElementById('form-message-email').textContent = '';
                }
            });

            document.getElementById("form").addEventListener("submit", function (event) {
                const input = document.getElementById('email').value.trim();
                if (input === '' || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input)) {
                    // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                    if (input === '') {
                        document.getElementById('form-message-email').textContent = 'Vui lòng nhập địa chỉ email.';
                    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input)) {
                        document.getElementById('form-message-email').textContent = 'Địa chỉ email không hợp lệ.';
                    }
                    event.preventDefault();
                } else {
                    document.getElementById('form-message-email').textContent = '';
                }
            });

            document.getElementById('phoneNumber').addEventListener('input', function () {
                const phoneNumber = this.value.trim();
                if (phoneNumber === '') {
                    document.getElementById('form-message-phone').textContent = '';
                } else if (!/(84|0[3|5|7|8|9])+([0-9]{8})\b/.test(phoneNumber)) {
                    document.getElementById('form-message-phone').textContent = 'Số điện thoại không hợp lệ.';
                } else {
                    document.getElementById('form-message-phone').textContent = '';
                }
            });

            document.getElementById("form").addEventListener("submit", function (event) {
                const input = document.getElementById('phoneNumber').value.trim();
                if (input === '' || !/(84|0[3|5|7|8|9])+([0-9]{8})\b/.test(input)) {
                    // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                    if (input === '') {
                        document.getElementById('form-message-phone').textContent = 'Vui lòng nhập số điện thoại.';
                    } else if (!/(84|0[3|5|7|8|9])+([0-9]{8})\b/.test(input)) {
                        document.getElementById('form-message-phone').textContent = 'Số điện thoại không hợp lệ.';
                    }
                    event.preventDefault();
                } else {
                    document.getElementById('form-message-phone').textContent = '';
                }
            });

            document.getElementById('cic').addEventListener('input', function () {
                const cic = this.value.trim();
                if (cic === '') {
                    document.getElementById('form-message-cic').textContent = '';
                } else if (!/^\d{9}$|^\d{12}$/.test(cic)) {
                    document.getElementById('form-message-cic').textContent = 'Căn cước công dân không hợp lệ.';
                } else if (/[^0-9]/.test(cic)) {
                    document.getElementById('form-message-cic').textContent = 'Căn cước công dân không chứa chữ.';
                } else {
                    document.getElementById('form-message-cic').textContent = '';
                }
            });

            document.getElementById("form").addEventListener("submit", function (event) {
                const input = document.getElementById('cic').value.trim();
                if (input === '' || !/^\d{9}$|^\d{12}$/.test(input)) {
                    // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                    if (input === '') {
                        document.getElementById('form-message-cic').textContent = 'Vui lòng nhập căn cước công dân.';
                    } else if (!/^\d{9}$|^\d{12}$/.test(input)) {
                        if (/[^0-9]/.test(input)) {
                            document.getElementById('form-message-cic').textContent = 'Căn cước công dân chỉ được chứa chữ số.';
                        } else {
                            document.getElementById('form-message-cic').textContent = 'Căn cước công dân không hợp lệ.';
                        }
                    }
                    event.preventDefault();
                } else {
                    document.getElementById('form-message-cic').textContent = '';
                }
            });

            document.getElementById('description').addEventListener('input', function () {
                const description = this.value.trim();
                if (description === '') {
                    document.getElementById('form-message-description').textContent = '';
                } else {
                    document.getElementById('form-message-description').textContent = '';
                }
            });
            document.getElementById("form").addEventListener("submit", function (event) {
                const input = document.getElementById('description').value.trim();
                if (input === '') {
                    // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                    document.getElementById('form-message-description').textContent = 'Vui lòng nhập mô tả.';
                    event.preventDefault();
                } else {
                    document.getElementById('form-message-description').textContent = '';
                }
            });


        </script>
        <script>
//            document.addEventListener('DOMContentLoaded', function () {
//                const depositInput = document.getElementById('depositMoney');
//
//                function formatCurrency(value) {
//                    return new Intl.NumberFormat('vi-VN').format(value);
//                }
//
//                depositInput.addEventListener('input', function () {
//                    let value = depositInput.value.replace(/\D/g, ''); // Loại bỏ mọi ký tự không phải số
//                    if (value) {
//                        depositInput.value = formatCurrency(value);
//                    } else {
//                        depositInput.value = '';
//                    }
//                });
//
//                depositInput.addEventListener('blur', function () {
//                    let value = depositInput.value.replace(/\D/g, '');
//                    if (value) {
//                        depositInput.value = formatCurrency(value);
//                    } else {
//                        depositInput.value = '';
//                    }
//                });
//            });
        </script>
        <script>
//            function formatDate() {
//                const dateOfBirthInput = document.getElementById('dateOfBirth');
//                const messageSpan = document.getElementById('form-message-date');
//                const submitInput = document.getElementById('submit');
//
//                const dateOfBirth = new Date(dateOfBirthInput.value);
//                const today = new Date();
//                let age = today.getFullYear() - dateOfBirth.getFullYear();
//                const monthDifference = today.getMonth() - dateOfBirth.getMonth();
//                const dayDifference = today.getDate() - dateOfBirth.getDate();
//
//                if (monthDifference < 0 || (monthDifference === 0 && dayDifference < 0)) {
//                    age--;
//                }
//
//                if (age < 18) {
//                    messageSpan.textContent = 'Người đặt cọc chưa đủ 18 tuổi';
//                    submitInput.disabled = true;
//                } else {
//                    messageSpan.textContent = '';
//                    submitInput.disabled = false;
//                }
//            }
        </script>
        <script>

            document.getElementById('dateOfBirth').addEventListener('input', function () {
                const dateOfBirthInput = document.getElementById('dateOfBirth').value;
                const dateOfBirth = new Date(dateOfBirthInput);
                const today = new Date();
                const age = today.getFullYear() - dateOfBirth.getFullYear();
                const monthDiff = today.getMonth() - dateOfBirth.getMonth();
                const dayDiff = today.getDate() - dateOfBirth.getDate();

                if (dateOfBirthInput === '') {
                    document.getElementById('form-message-date').textContent = '';
                } else if (age < 18 || (age === 18 && (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)))) {
                    document.getElementById('form-message-date').textContent = "Người này chưa đủ 18 tuổi.";

                } else {
                    document.getElementById('form-message-date').textContent = "";

                }
            });
            document.getElementById("form").addEventListener("submit", function (event) {
                const dateOfBirthInput = document.getElementById('dateOfBirth').value;
                const dateOfBirth = new Date(dateOfBirthInput);
                const today = new Date();
                const age = today.getFullYear() - dateOfBirth.getFullYear();
                const monthDiff = today.getMonth() - dateOfBirth.getMonth();
                const dayDiff = today.getDate() - dateOfBirth.getDate();

                if (dateOfBirthInput === '') {
                    document.getElementById('form-message-date').textContent = '';
                } else if (age < 18 || (age === 18 && (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)))) {
                    document.getElementById('form-message-date').textContent = "Người này chưa đủ 18 tuổi.";
                    event.preventDefault();
                } else {
                    document.getElementById('form-message-date').textContent = "";

                }
            });

            function validateAge() {
                const dateOfBirthInput = document.getElementById('dateOfBirth').value;
                const dateOfBirth = new Date(dateOfBirthInput);
                const today = new Date();
                const age = today.getFullYear() - dateOfBirth.getFullYear();
                const monthDiff = today.getMonth() - dateOfBirth.getMonth();
                const dayDiff = today.getDate() - dateOfBirth.getDate();

                // Kiểm tra nếu tuổi nhỏ hơn 18
                if (age < 18 || (age === 18 && (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)))) {
                    document.getElementById('form-message-date').textContent = "Người này chưa đủ 18 tuổi.";
                    return false;
                } else {
                    document.getElementById('form-message-date').textContent = "";
                    return true;
                }
            }

            function handleSubmit(event) {
                if (!validateAge()) {
                    event.preventDefault(); // Ngăn không cho submit form
                }
            }

            document.addEventListener('DOMContentLoaded', function () {
                document.getElementById('myForm').addEventListener('submit', handleSubmit);
            });
        </script>

        <script>
//            document.getElementById('saveAndDownloadBtn').addEventListener('click', function () {
//                var templateFileInput = document.getElementById('templateFileInput');
//                templateFileInput.click();
//
//                templateFileInput.addEventListener('change', function () {
//                    var templateFile = templateFileInput.files[0];
//
//                    if (templateFile) {
//                        var reader = new FileReader();
//
//                        reader.onload = function (event) {
//                            var templateContent = event.target.result;
//
//                            // Thay thế các đoạn <<>> trong nội dung file mẫu bằng giá trị từ các input
//                            var filledContent = templateContent.replace(/<<date>>/g, new Date().toLocaleDateString())
//                                    .replace(/<<ward>>/g, 'Ward Placeholder')
//                                    .replace(/<<district>>/g, 'District Placeholder')
//                                    .replace(/<<province>>/g, 'Province Placeholder')
//                                    .replace(/<<fullNameA>>/g, 'FullNameA Placeholder')
//                                    .replace(/<<birthDateA>>/g, 'BirthDateA Placeholder')
//                                    .replace(/<<CICA>>/g, 'CICA Placeholder')
//                                    .replace(/<<emailA>>/g, 'EmailA Placeholder')
//                                    .replace(/<<phoneNumberA>>/g, 'PhoneNumberA Placeholder')
//                                    .replace(/<<fullNameB>>/g, document.getElementById('name').value)
//                                    .replace(/<<birthDateB>>/g, document.getElementById('dateOfBirth').value)
//                                    .replace(/<<CICB>>/g, document.getElementById('cic').value)
//                                    .replace(/<<emailB>>/g, document.getElementById('email').value)
//                                    .replace(/<<phoneNumberB>>/g, document.getElementById('phoneNumber').value)
//                                    .replace(/<<price>>/g, 'Price Placeholder')
//                                    .replace(/<<maxQuantity>>/g, 'MaxQuantity Placeholder')
//                                    .replace(/<<arriveDate>>/g, document.getElementById('arriveDate').value);
//
//                            // Xác định loại MIME của tệp
//                            var mimeType = templateFile.type || 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';
//
//                            // Tạo một Blob mới từ nội dung đã điền thông tin và tạo một URL tạm thời
//                            var blob = new Blob([filledContent], {type: mimeType});
//                            var timestamp = new Date().getTime();
//
//                            // Tạo một link tạm thời để tải xuống file
//                            var a = document.createElement('a');
//                            a.style.display = 'none';
//                            a.href = URL.createObjectURL(blob);
//                            
//                            a.download = 'filled'+timestamp+'_document.docx';
//                            document.body.appendChild(a);
//                            a.click();
//                            document.body.removeChild(a);
//                        };
//
//                        // Đọc nội dung của file mẫu dưới dạng văn bản
//                        reader.readAsText(templateFile);
//                    } else {
//                        alert('Vui lòng chọn file mẫu để điền thông tin.');
//                    }
//                }, {once: true});
//            });

//            document.getElementById('saveAndDownloadBtn').addEventListener('click', function () {
//                var templateFileInput = document.getElementById('templateFileInput');
//
//                // Trigger click event on file input to ensure change event is fired
//                templateFileInput.click();
//
//                // Listen for change event on file input
//                templateFileInput.addEventListener('change', function () {
//                    var templateFile = templateFileInput.files[0];
//
//                    // Check if a file is selected
//                    if (templateFile) {
//                        var reader = new FileReader();
//
//                        reader.onload = function (event) {
//                            var templateContent = event.target.result;
//
//                            // Replace placeholders in templateContent with values from inputs
//                            var filledContent = templateContent.replace(/<<([^<>]+)>>/g, function (match, placeholder) {
//                                switch (placeholder) {
//                                    case 'date':
//                                        return new Date().toLocaleDateString();
//                                    case 'ward':
//                                        return 'Ward Placeholder';
//                                    case 'district':
//                                        return 'District Placeholder';
//                                    case 'province':
//                                        return 'Province Placeholder';
//                                        // Add more cases for other placeholders
//                                    default:
//                                        return match; // Keep the placeholder if no match
//                                }
//                            });
//
//                            // Example: create and download a PDF using jsPDF
//                            var doc = new jsPDF();
//                            doc.text(filledContent, 10, 10);
//                            var timestamp = new Date().getTime();
//                            doc.save('filled_' + timestamp + '_document.pdf');
//                        };
//
//                        // Read the content of the template file as text
//                        reader.readAsText(templateFile);
//                    } else {
//                        alert('Vui lòng chọn file mẫu để điền thông tin.');
//                    }
//                }, {once: true}); // Use { once: true } to ensure the event listener is removed after first execution
//            });

        </script>

    </body>
</html>
