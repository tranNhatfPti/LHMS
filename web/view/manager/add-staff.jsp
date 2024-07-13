<%-- 
    Document   : test
    Created on : May 17, 2024, 9:09:09 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>

<html lang="en">

    <head>
        <title>Thêm nhân viên | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <!--  <link rel="stylesheet" type="text/css" href="css/main.css">-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

        <script>

            function readURL(input, thumbimage) {
                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimage").attr('src', e.target.result);
                    }
                    reader.readAsDataURL(input.files[0]);
                } else { // Sử dụng cho IE
                    $("#thumbimage").attr('src', input.value);
                }
                $("#thumbimage").show();
                $('.filename').text($("#uploadfile").val());
                $('.Choicefile').css('background', '#14142B');
                $('.Choicefile').css('cursor', 'default');
                $(".removeimg").show();
                $(".Choicefile").unbind('click');
            }
            $(document).ready(function () {
                $(".Choicefile").bind('click', function () {
                    $("#uploadfile").click();
                });
                $(".removeimg").click(function () {
                    $("#thumbimage").attr('src', '').hide();
                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                    $(".removeimg").hide();
                    $(".Choicefile").bind('click', function () {
                        $("#uploadfile").click();
                    });
                    $('.Choicefile').css('background', '#14142B');
                    $('.Choicefile').css('cursor', 'pointer');
                    $(".filename").text("");
                });
            });
            $(document).ready(function () {
                //Lấy tỉnh thành
                $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                    if (data_tinh.error == 0) {
                        $.each(data_tinh.data, function (key_tinh, val_tinh) {
                            $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
                        });
                        $("#tinh").change(function (e) {
                            var idtinh = $(this).val();
                            //Lấy quận huyện
                            $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                                if (data_quan.error == 0) {
                                    $("#quan").html('<option value="0">Quận Huyện</option>');
                                    $("#phuong").html('<option value="0">Phường Xã</option>');
                                    $.each(data_quan.data, function (key_quan, val_quan) {
                                        $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                                    });
                                    //Lấy phường xã  
                                    $("#quan").change(function (e) {
                                        var idquan = $(this).val();
                                        $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                                            if (data_phuong.error == 0) {
                                                $("#phuong").html('<option value="0">Phường Xã</option>');
                                                $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                                    $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
                                                });
                                            }
                                        });
                                    });
                                }
                            });
                        });
                    }
                });
            });
        </script>

        <style type="text/css">
            .css_select_div{
                text-align: center;
            }
            .css_select{
                display: inline-table;
                width: 25%;
                padding: 5px;
                margin: 5px 2%;
                border: solid 1px #686868;
                border-radius: 5px;
            }
            .Choicefile {
                display: block;
                background: #14142B;
                border: 1px solid #fff;
                color: #fff;
                width: 150px;
                text-align: center;
                text-decoration: none;
                cursor: pointer;
                padding: 5px 0px;
                border-radius: 5px;
                font-weight: 500;
                align-items: center;
                justify-content: center;
            }


            .Choicefile:hover {
                text-decoration: none;
                color: white;
            }



            #uploadfile,
            .removeimg {
                display: none;
            }

            #thumbbox {
                position: relative;
                width: 100%;
                margin-bottom: 20px;
            }

            .removeimg {
                height: 25px;
                position: absolute;
                background-repeat: no-repeat;
                top: 5px;
                left: 5px;
                background-size: 25px;
                width: 25px;
                /* border: 3px solid red; */
                border-radius: 50%;

            }

            .removeimg::before {
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                content: '';
                border: 1px solid red;
                background: red;
                text-align: center;
                display: block;
                margin-top: 11px;
                transform: rotate(45deg);
            }

            .removeimg::after {
                /* color: #FFF; */
                /* background-color: #DC403B; */
                content: '';
                background: red;
                border: 1px solid red;
                text-align: center;
                display: block;
                transform: rotate(-45deg);
                margin-top: -2px;
            }

            .form-message {
                color: #dc3545; /* Màu đỏ */
                font-size: 0.875rem; /* Kích thước chữ nhỏ */
                margin-top: 0.25rem; /* Khoảng cách từ input */
            }
        </style>
    </head>

    <body class="app sidebar-mini rtl">

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
            <c:if test="${sessionScope.account.getRoleId() == 2 || sessionScope.account.getRoleId() == 1}">
            <div class="col-sm-2">
                <%@include file="left-menu-manager.jsp" %>
            </div>
                <c:if test="${sessionScope.account.getRoleId() == 1}">
                    <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-role-of-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý chức vụ</span></a></li>
                </c:if>
            </c:if>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="list-staff">Danh sách nhân viên</a></li>
                    <li class="breadcrumb-item active"><a href="add-staff">Thêm nhân viên</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Thêm nhân viên mới</h3>
                        <div class="tile-body">
                        </div>
                        <form class="row" action="add-staff" method="POST" id="form">
                            <div class="form-group col-md-4">
                                <label class="control-label">Họ và tên</label>
                                <input class="form-control" type="text" maxlength="150" id="name" name="name" >
                                <span id="form-message" class="form-message"></span>
                            </div>

                            <div class="form-group col-md-4">
                                <label class="control-label">Địa chỉ email</label>
                                <input class="form-control" type="text" id="email" maxlength="150" name="email" required>
                                <span id="form-message-email" class="form-message">
                                    <c:if test="${requestScope.duplicatedEmail != null}">${requestScope.duplicatedEmail}</c:if>
                                    </span>
                                </div>

                                <div class="form-group col-md-4">
                                    <label class="control-label">Số điện thoại</label>
                                    <input class="form-control" type="number" id="phoneNumber" maxlength="15" name="phoneNumber" required>
                                    <span id="form-message-phone" class="form-message">
                                    <c:if test="${requestScope.duplicatedPhoneNumber != null}">${requestScope.duplicatedPhoneNumber}</c:if>
                                    </span>
                                </div> 
                                <div class="form-group col-md-3">
                                    <label for="exampleSelect1" class="control-label">Thành phố/Tỉnh</label>
                                    <select class="form-control" id="tinh" name="tinh" title="Chọn Tỉnh Thành">
                                        <option value="0">Tỉnh Thành</option>
                                    </select>
                                    <span id="form-message-tinh" class="form-message"></span>
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="exampleSelect1" class="control-label">Quận/Huyện</label>
                                    <select class="form-control" id="quan" name="quan" title="Chọn Quận Huyện">
                                        <option value="0">Quận Huyện</option>
                                    </select>
                                    <span id="form-message-quan" class="form-message"></span>
                                </div>
                                <div class="form-group col-md-3">
                                    <label for="exampleSelect1" class="control-label">Phường/Xã</label>
                                    <select class="form-control" id="phuong" name="phuong" title="Chọn Phường Xã">
                                        <option value="0">Phường Xã</option>
                                    </select>
                                    <span id="form-message-phuong" class="form-message"></span>
                                </div>
                                <div class="form-group col-md-3">
                                    <label class="control-label">Địa chỉ cụ thể</label>
                                    <input class="form-control" type="text" maxlength="150" id="addressDetail" name="addressDetail" required>
                                    <span id="form-message-address" class="form-message"></span>
                                </div>
                                <input class="form-control" type="text" hidden name="lodgingHouse" value="${requestScope.lodgingHouseID}">
                            <div class="form-group col-md-6">
                                <label for="exampleSelect1" class="control-label">Chức vụ</label>
                                <select class="form-control" id="exampleSelect1" name="roleStaff" required="">
                                    <option value="0">-- Chọn chức vụ --</option>
                                    <c:forEach items="${requestScope.listAllRoleStaff}" var="role">
                                        <option value="${role.getRoleStaffID()}">${role.getRoleStaffName()}</option>
                                    </c:forEach>
                                </select>
                                <span id="form-message-role-staff" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Lương</label>
                                <input class="form-control" name="salary" id="salaryInput" min="1000" max="20000000" type="number" required>
                                <span id="form-message-salary" class="form-message"></span>
                            </div>

                            <div class="form-group  col-md-12">
                                <input type="submit" value="Lưu lại" class="btn btn-save" style="margin-left: 15px; margin-right: 15px;"/>
                                <a class="btn btn-cancel" href="list-staff">Hủy bỏ</a>
                            </div>
                        </form>   
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
                document.getElementById('form-message').textContent = 'Họ và tên chỉ có thể chứa chữ cái.';
            } else if (input === '') {
                document.getElementById('form-message').textContent = '';
            } else if (input.split(' ').length === 1) {
                document.getElementById('form-message').textContent = 'Vui lòng nhập cả họ và tên.';
            } else if (input.split(' ').some(word => word === '')) {
                document.getElementById('form-message').textContent = 'Họ và tên không thể chứa khoảng trắng ở đầu hoặc cuối.';
            } else {
                // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                document.getElementById('form-message').textContent = '';
            }
        });

        // Gắn sự kiện "submit" lên form
        document.getElementById("form").addEventListener("submit", function (event) {
            const input = document.getElementById('name').value.trim();
            if (/[^a-zA-Z\s\u00C0-\u024F\u1E00-\u1EFF]/.test(input) || input === '' || input.split(' ').length === 1 || input.split(' ').some(word => word === '')) {
                // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form

                if (input === '') {
                    document.getElementById('form-message').textContent = 'Vui lòng nhập họ và tên.';
                } else if (/[^a-zA-Z\s\u00C0-\u024F\u1E00-\u1EFF]/.test(input)) {
                    document.getElementById('form-message').textContent = 'Họ và tên chỉ có thể chứa chữ cái.';
                } else if (input.split(' ').length === 1) {
                    document.getElementById('form-message').textContent = 'Vui lòng nhập cả họ và tên.';
                } else if (input.split(' ').some(word => word === '')) {
                    document.getElementById('form-message').textContent = 'Họ và tên không thể chứa khoảng trắng ở đầu hoặc cuối.';
                }
                event.preventDefault();
            } else {
                // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                document.getElementById('form-message').textContent = '';
            }
        });

        // validate for email form
        var listEmail = JSON.parse('<%= request.getAttribute("listEmail") %>');
        document.getElementById('email').addEventListener('input', function () {
            const emailInput = this.value.trim();

            if (emailInput === '') {
                document.getElementById('form-message-email').textContent = '';
            } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput)) {
                document.getElementById('form-message-email').textContent = 'Địa chỉ email không hợp lệ.';
            } else if (listEmail.includes(emailInput)) {
                document.getElementById('form-message-email').textContent = 'Địa chỉ email đã tồn tại.';
            } else {
                document.getElementById('form-message-email').textContent = '';
            }
        });

        document.getElementById("form").addEventListener("submit", function (event) {
            const input = document.getElementById('email').value.trim();
            if (input === '' || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input) || listEmail.includes(input)) {
                // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                if (input === '') {
                    document.getElementById('form-message-email').textContent = 'Vui lòng nhập địa chỉ email.';
                } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input)) {
                    document.getElementById('form-message-email').textContent = 'Địa chỉ email không hợp lệ.';
                } else if (listEmail.includes(input)) {
                    document.getElementById('form-message-email').textContent = 'Địa chỉ email đã tồn tại.';
                }
                event.preventDefault();
            } else {
                document.getElementById('form-message-email').textContent = '';
            }
        });


        // validate for phone number form /(84|0[3|5|7|8|9])+([0-9]{8})\b/
        var listPhoneNumber = JSON.parse('<%= request.getAttribute("listPhoneNumber") %>');
        document.getElementById('phoneNumber').addEventListener('input', function () {
            const phoneNumber = this.value.trim();

            if (phoneNumber === '') {
                document.getElementById('form-message-phone').textContent = '';
            } else if (!/^\d{10}$/.test(phoneNumber)) {
                document.getElementById('form-message-phone').textContent = 'Số điện thoại không hợp lệ.';
            } else if (listPhoneNumber.includes(phoneNumber)) {
                document.getElementById('form-message-phone').textContent = 'Số điện thoại đã tồn tại.';
            } else {
                document.getElementById('form-message-phone').textContent = '';
            }
        });

        document.getElementById("form").addEventListener("submit", function (event) {
            const input = document.getElementById('phoneNumber').value.trim();
            if (input === '' || !/^\d{10}$/.test(input) || listPhoneNumber.includes(input)) {
                // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                if (phoneNumber === '') {
                    document.getElementById('form-message-phone').textContent = 'Vui lòng nhập số điện thoại.';
                } else if (!/^\d{10}$/.test(phoneNumber)) {
                    document.getElementById('form-message-phone').textContent = 'Số điện thoại không hợp lệ.';
                } else if (listPhoneNumber.includes(phoneNumber)) {
                    document.getElementById('form-message-phone').textContent = 'Số điện thoại đã tồn tại.';
                }
                event.preventDefault();
            } else {
                document.getElementById('form-message-phone').textContent = '';
            }
        });

        // validate for choice address detail
        document.getElementById('addressDetail').addEventListener('input', function () {
            const input = this.value.trim();

            if (input === '') {
                document.getElementById('form-message-address').textContent = '';
            } else {
                document.getElementById('form-message-address').textContent = '';
            }
        });

        document.getElementById("form").addEventListener("submit", function (event) {
            const input = document.getElementById('addressDetail').value.trim();
            if (input === '') {
                document.getElementById('form-message-address').textContent = 'Vui lòng nhập địa chỉ cụ thể.';
                event.preventDefault();
            } else {
                // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                document.getElementById('form-message-address').textContent = '';
            }
        });



        // validate for choice location
        document.querySelector('form').addEventListener('submit', function (event) {
            const selectedTinh = document.getElementById('tinh').value;
            const selectedQuan = document.getElementById('quan').value;
            const selectedPhuong = document.getElementById('phuong').value;


            // Đặt một biến để kiểm tra xem có lỗi không
            let hasError = false;

            if (selectedTinh === '0') {
                document.getElementById('form-message-tinh').textContent = 'Vui lòng chọn Tỉnh/Thành.';
                hasError = true; // Đặt biến lỗi thành true
            } else {
                document.getElementById('form-message-tinh').textContent = ''; // Xóa thông báo lỗi nếu không có lỗi
            }

            if (selectedQuan === '0') {
                document.getElementById('form-message-quan').textContent = 'Vui lòng chọn Quận/Huyện.';
                hasError = true; // Đặt biến lỗi thành true
            } else {
                document.getElementById('form-message-quan').textContent = ''; // Xóa thông báo lỗi nếu không có lỗi
            }

            if (selectedPhuong === '0') {
                document.getElementById('form-message-phuong').textContent = 'Vui lòng chọn Phường/Xã.';
                hasError = true; // Đặt biến lỗi thành true
            } else {
                document.getElementById('form-message-phuong').textContent = ''; // Xóa thông báo lỗi nếu không có lỗi
            }

            // Nếu có lỗi, ngăn chặn sự kiện submit mặc định của form
            if (hasError) {
                event.preventDefault();
            }
        });

    </script>
    <script>
        document.getElementById('form').addEventListener('blur', function (event) {
            // Kiểm tra lương
            var salaryInput = document.getElementById('salaryInput');
            var salaryValue = parseInt(salaryInput.value, 10);
            var salaryErrorMessage = document.getElementById('form-message-salary');

            // Chuyển đổi giá trị thành chuỗi được định dạng
            var formattedSalary = parseInt(salaryValue).toLocaleString('vi-VN'); // Định dạng số theo ngôn ngữ Việt Nam
            salaryInput.value = formattedSalary;

            if (salaryValue > 20000000) {
                salaryErrorMessage.textContent = 'Lương không được vượt quá 20.000.000';
            } else {
                salaryErrorMessage.textContent = '';
            }
        });

        document.getElementById('form').addEventListener('submit', function (event) {
            // Kiểm tra chức vụ
            const selectedRoleStaff = document.getElementById('exampleSelect1').value;
            const roleErrorMessage = document.getElementById('form-message-role-staff');
            let hasError = false;

            if (selectedRoleStaff === '0') {
                roleErrorMessage.textContent = 'Vui lòng chọn chức vụ.';
                hasError = true;
            } else {
                roleErrorMessage.textContent = '';
            }

            // Kiểm tra lương
            var salaryInput = document.getElementById('salaryInput');
            var salaryValue = parseInt(salaryInput.value.replace(/,/g, ''), 10);
            var salaryErrorMessage = document.getElementById('form-message-salary');

            if (salaryValue > 20000000) {
                salaryErrorMessage.textContent = 'Lương không được vượt quá 20.000.000';
                hasError = true;
            } else {
                salaryErrorMessage.textContent = '';
            }

            // Nếu có lỗi, ngăn chặn sự kiện submit mặc định của form
            if (hasError) {
                event.preventDefault();
            }
        });

    </script>
    <script src="https://esgoo.net/scripts/jquery.js"></script>
</body>

</html>
