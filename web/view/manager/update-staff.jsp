<%-- 
    Document   : test
    Created on : May 17, 2024, 9:09:09 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    </head>

    <body class="app sidebar-mini rtl">
        <style>
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
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
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
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="list-staff">Danh sách nhân viên</a></li>
                    <li class="breadcrumb-item"><a href="update-staff">Cập nhật thông tin nhân viên</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Nhân viên</h3>
                        <form class="row" action="update-staff" id="form" method="POST">

                            <div class="tile-body"></div>
                            <div class="form-group col-md-4">
                                <label class="control-label">Tên nhân viên</label>
                                <input hidden="" name="staffID" value="${requestScope.getStaffByID.staffID}"/>
                                <input class="form-control" maxlength="150" type="text" placeholder="${requestScope.getStaffByID.nameStaff}" id="name" name="nameStaff">
                                <span id="form-message" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-4">
                                <label class="control-label">Email</label>
                                <input class="form-control" type="text" maxlength="150" placeholder="${requestScope.getStaffByID.email}" id="email" name="email" required>
                                <span id="form-message-email" class="form-message">
                                    <c:if test="${requestScope.existEmail != null}">${requestScope.existEmail}</c:if>
                                    </span>
                                </div>  
                                <div class="form-group col-md-4">
                                    <label class="control-label">Số điện thoại</label>
                                    <input class="form-control" maxlength="15" id="phoneNumber" type="number" placeholder="${requestScope.getStaffByID.phoneNumber}" name="phoneNumber" required>
                                <span id="form-message-phone" class="form-message">
                                    <c:if test="${requestScope.existPhoneNumber != null}">${requestScope.existPhoneNumber}</c:if>
                                    </span>
                                </div> 
                                <div class="form-group col-md-3" id="exampleSelect2" required>
                                    <label class="control-label">Thành phố/Tỉnh</label>

                                    <select class="form-control" id="tinh" name="tinh" title="Chọn Tỉnh Thành" required>
                                        <option value="0" >${requestScope.getStaffByID.province}</option>
                                </select> 
                                <span id="form-message-tinh" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-3" id="exampleSelect2" required>
                                <label class="control-label">Quận/Huyện</label>
                                <select class="form-control" id="quan" name="quan" title="Chọn Quận Huyện" required>
                                    <option value="0" title="">${requestScope.getStaffByID.district}</option>
                                </select> 
                                <span id="form-message-quan" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-3" id="exampleSelect2" required>
                                <label class="control-label">Phường/Xã</label>
                                <select class="form-control" id="phuong" name="phuong" title="Chọn Phường Xã" required>
                                    <option value="0">${requestScope.getStaffByID.ward}</option>
                                </select>
                                <span id="form-message-phuong" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-3" >
                                <label class="control-label">Địa chỉ cụ thể</label>
                                <input class="form-control" maxlength="150" type="text" placeholder="${requestScope.getStaffByID.addressDetail}" name="addressDetail" required>
                                <span id="form-message-address" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Tên chức vụ</label>
                                <select name="roleStaff" class="form-control" id="exampleSelect1" required="">
                                    <option value="0">-- Chọn chức vụ --</option>
                                    <c:forEach items="${requestScope.getAllRoleStaff}" var="role">                                                 
                                        <option value="${role.roleStaffID}">${role.roleStaffName}</option>
                                    </c:forEach>
                                    
                                </select>
                                <span id="form-message-role-staff" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Lương</label>
                                <input class="form-control" type="number" id="salaryInput" placeholder="<fmt:formatNumber value="${requestScope.getStaffByID.salary}" type="number" pattern="#,##0"/>" min="1000" maxlength="10" max="20000000" name="salaryInput" required>
                                <span id="form-message-salary" class="form-message"></span>
                            </div>
                            <div class="form-group col-md-12">

                                <input class="form-control" type="text" value="${requestScope.getLodgingHouseByID.lodgingHouseId}" name="lodgingHouse" hidden readonly="">
                            </div>

                            <div class="form-group  col-md-12">
                                <input class="btn btn-save" value="Lưu lại" type="submit" style="margin-left: 15px; margin-right: 15px;"/>
                                <a class="btn btn-cancel" href="list-staff">Hủy bỏ</a>
                            </div>
                        </form>
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
            function deleteRow(r) {
                var i = r.parentNode.parentNode.rowIndex;
                document.getElementById("myTable").deleteRow(i);
            }
            jQuery(function () {
                jQuery(".trash").click(function () {
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn là muốn xóa sản phẩm này?",
                        buttons: ["Hủy bỏ", "Đồng ý"],
                    })
                            .then((willDelete) => {
                                if (willDelete) {
                                    swal("Đã xóa thành công.!", {

                                    });
                                }
                            });
                });
            });
            oTable = $('#sampleTable').dataTable();
            $('#all').click(function (e) {
                $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                e.stopImmediatePropagation();
            });
        </script>
        <script>
            //Form validation enter fullname
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
                    if (/[^a-zA-Z\s\u00C0-\u024F\u1E00-\u1EFF]/.test(input)) {
                        document.getElementById('form-message').textContent = 'Họ và tên chỉ có thể chứa chữ cái.';
                    } else if (input === '') {
                        document.getElementById('form-message').textContent = 'Vui lòng nhập họ và tên.';
                    } else if (input.split(' ').length === 1) {
                        document.getElementById('form-message').textContent = 'Vui lòng nhập cả họ và tên.';
                    } else if (input.split(' ').some(word => word === '')) {
                        document.getElementById('form-message').textContent = 'Họ và tên không thể chứa khoảng trắng ở đầu hoặc cuối.';
                    } else {
                        // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                        document.getElementById('form-message').textContent = '';
                    }
                    event.preventDefault();
                } else {
                    // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                    document.getElementById('form-message').textContent = '';
                }
            });

            // validate for email form
            document.getElementById('email').addEventListener('input', function () {
                const emailInput = this.value.trim();

                if (emailInput === '') {
                    document.getElementById('form-message-email').textContent = '';//Vui lòng nhập địa chỉ email.
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
                    if (emailInput === '') {
                        document.getElementById('form-message-email').textContent = 'Vui lòng nhập địa chỉ email.';//
                    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput)) {
                        document.getElementById('form-message-email').textContent = 'Địa chỉ email không hợp lệ.';
                    } else {
                        document.getElementById('form-message-email').textContent = '';
                    }
                    event.preventDefault();
                } else {
                    document.getElementById('form-message-email').textContent = '';
                }
            });
            // validate for address detail
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
                    // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                    if (input === '') {
                        document.getElementById('form-message-address').textContent = 'Vui lòng nhập địa chỉ cụ thể.';
                    } else {
                        document.getElementById('form-message-address').textContent = '';
                    }
                    event.preventDefault();
                } else {
                    document.getElementById('form-message-address').textContent = '';
                }
            });


            // validate for phone number form
            document.getElementById('phoneNumber').addEventListener('input', function () {
                const phoneNumber = this.value.trim();

                if (phoneNumber === '') {
                    document.getElementById('form-message-phone').textContent = '';
                } else if (!/^\d{10}$/.test(phoneNumber)) {
                    document.getElementById('form-message-phone').textContent = 'Số điện thoại không hợp lệ.';
                } else {
                    document.getElementById('form-message-phone').textContent = '';
                }
            });

            <c:if test="${requestScope.notice != null}">
            var notice = '<%= request.getAttribute("notice") %>';
            if (notice) {
                // Sử dụng SweetAlert để hiển thị thông báo
                swal({

                }).then((value) => {
                    // Sau khi click vào close, chuyển hướng đến list-role-of-staff servlet
                    window.location.href = "list-staff";
                });
            }
            </c:if>

        </script>
        <script>
            document.getElementById('form').addEventListener('submit', function (event) {
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
                    // return false;
                }
                //return true;
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
        <script>
        </script>
        <script src="https://esgoo.net/scripts/jquery.js"></script>
    </body>

</html>
