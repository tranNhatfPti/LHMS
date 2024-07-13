<%-- 
    Document   : test
    Created on : May 17, 2024, 9:09:09 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
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
            })
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
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-role-of-staff"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i>
                        <span class="app-menu__label">Quản lý chức vụ</span></a></li>
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
                    <li class="breadcrumb-item"><a href="list-role-of-staff">Danh sách chức vụ</a></li>
                    <li class="breadcrumb-item"><a href="update-role-of-staff">Thêm chức vụ</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="col-md-12">

                    <div class="tile">
                        <form action="update-role-of-staff" method="POST" id="form">
                            <h3 class="tile-title">Chức vụ</h3>
                            <div class="tile-body">
                                <div class="form-group col-md-6">
                                    <label class="control-label">Tên chức vụ muốn thay đổi</label>
                                    <input hidden="" name="roleID"  value="${requestScope.updateRole.roleStaffID}"/>
                                    <input class="form-control" id="roleToUpdate" type="text" value="${requestScope.updateRole.roleStaffName}" readonly>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Tên chức vụ mới</label>
                                    <input class="form-control" id="newRole" type="text" maxlength="150" name="rolenamestaff" required>
                                    <span id="form-message" class="form-message"></span>
                                </div>
                            </div>
                            <input class="btn btn-save" value="Lưu lại" type="submit"/>
                            <a class="btn btn-cancel" href="list-role-of-staff">Hủy bỏ</a>
                        </form>
                    </div>
                </div>
            </div>
        </main>
        <!-- Essential javascripts for application to work-->
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>
        <!-- The javascript plugin to display page loading on top-->
        <script src="js/plugins/pace.min.js"></script>

    </body>
    <script>
            document.getElementById('roleName').addEventListener('input', function () {
                const input = this.value.trim();

                // Kiểm tra xem có ký tự nào không phải là chữ cái không
                if (input === '') {
                    // Nếu có, hiển thị thông báo lỗi
                    document.getElementById('form-message').textContent = 'Vui lòng nhập nhập chữ cái hoặc số.';
                } else {
                    // Nếu dữ liệu hợp lệ, xóa thông báo lỗi
                    document.getElementById('form-message').textContent = '';
                }
            });

            document.getElementById("form").addEventListener("submit", function (event) {
                const input = document.getElementById('roleName').value.trim();
                if (input === '') {
                    // Nếu dữ liệu không hợp lệ, ngăn chặn mặc định của form
                    event.preventDefault();
                }
            });



        <c:if test="${requestScope.notice != null}">
            var notice = '<%= request.getAttribute("notice") %>';
            if (notice) {
                // Sử dụng SweetAlert để hiển thị thông báo
                swal({
                    title: "Thông báo",
                    text: notice,
                    icon: "error",
                    button: "Close",
                }).then((value) => {
                    window.location.href = "list-role-of-staff";
                });
            }
        </c:if>
        <c:if test="${requestScope.wrongUpdateRoleStaff != null}">
            var notice = '<%= request.getAttribute("wrongUpdateRoleStaff") %>';
            if (notice) {
                // Sử dụng SweetAlert để hiển thị thông báo
                swal({
                    title: "Thông báo",
                    text: notice,
                    icon: "error",
                    button: "Close",
                }).then((value) => {
                    // Sau khi click vào close, chuyển hướng đến list-role-of-staff servlet
                    window.location.href = "list-role-of-staff";
                });
            }
        </c:if>


    </script>
    <script>
        var listRoleOfStaff = JSON.parse('<%= request.getAttribute("listRoleOfStaff") %>');
        function isRoleValid(roleToUpdate, newRole) {
            // Chuyển đổi các role trong listRoleOfStaff thành chữ thường để so sánh không phân biệt chữ hoa và chữ thường
            var lowerCaseRoles = listRoleOfStaff.map(role => role.toLowerCase());

            // Kiểm tra xem new Role có trong danh sách roles khác roleToUpdate không
            if (lowerCaseRoles.includes(newRole.toLowerCase()) && newRole.toLowerCase() !== roleToUpdate.toLowerCase()) {
                return true;
            }
            return false;
        }

        document.getElementById("form").addEventListener("input", function (event) {
            const roleToUpdate = document.getElementById('roleToUpdate').defaultValue.trim().toLowerCase();
            const newRole = document.getElementById('newRole').value.trim().toLowerCase();

            // Kiểm tra xem role mới có hợp lệ không
            if (isRoleValid(roleToUpdate, newRole)) {
                document.getElementById('form-message').textContent = 'Chức vụ không hợp lệ hoặc đã tồn tại.';

            } else if (newRole === '') {
                document.getElementById('form-message').textContent = '';
            } else {
                document.getElementById('form-message').textContent = ''; // Xóa thông báo lỗi nếu role hợp lệ
            }
        });

        document.getElementById("form").addEventListener("submit", function (event) {
            const roleToUpdate = document.getElementById('roleToUpdate').defaultValue.trim().toLowerCase();
            const newRole = document.getElementById('newRole').value.trim().toLowerCase();

            // Kiểm tra xem role mới có hợp lệ không
            if (isRoleValid(roleToUpdate, newRole)) {
                document.getElementById('form-message').textContent = 'Chức vụ không hợp lệ hoặc đã tồn tại.';
                event.preventDefault(); // Ngăn chặn mặc định của form nếu role không hợp lệ
            } else if (newRole === '') {
                document.getElementById('form-message').textContent = '';
            } else {
                document.getElementById('form-message').textContent = ''; // Xóa thông báo lỗi nếu role hợp lệ
            }
        });

    </script>

</html>
