<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="en">

    <head>
        <title>Thay Đổi Mật Khẩu</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/css/change_password.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <style>
            .changePassword{
                font-style: italic;
                color: red;
            }
            .changeSuccess{
                font-weight: bolder;
                color: green;
            }
            .avatarHeader{
                width: 100px !important;
                height: 100px !important;
                border-radius: 50% !important;
                object-fit: cover !important;
            }
        </style>
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <div>
            <%@ include file="header.jsp" %>
        </div>
        <div class="row">
            <!-- Navbar-->
            <div class="col-sm-2">
                <%@ include file="left-menu-tenant.jsp" %>
            </div>
            <!-- Edit Profile -->
            <main class="app-content col-sm-10">
                <div class="row">
                    <div class="col-md-12">
                        <div class="app-title">
                            <ul class="app-breadcrumb breadcrumb">
                                <li class="breadcrumb-item"><a href="account?service=showChangePassword"><b>Thay đổi mật khẩu</b></a></li>
                            </ul>
                            <div id="clock"></div>
                        </div>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-6 col-lg-4">
                        <form action="account" method="post">
                            <div class="form-group">
                                <!--<h4 style="font-weight: bold;">UserName: ${account.username}</h4>-->
                            </div>
                            <div class="form-group">
                                <label>Mật Khẩu Hiện Tại</label>
                                <div class="pass_show">
                                    <input type="password" class="form-control" placeholder="Mật khẩu hiện tại" name="currentPassword" value="${password}">
                                    <p class="changePassword">${messagePassword}</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Mật khẩu Mới</label>
                                <div class="pass_show">
                                    <input type="password" class="form-control" placeholder="Mật khẩu mới" name="newPassword">
                                </div>
                                <p class="changePassword">${messageNewPassword}</p>
                            </div>
                            <div class="form-group">
                                <label>Xác Nhận Mật Khẩu Mới</label>
                                <div class="pass_show">
                                    <input type="password" class="form-control" placeholder="Xác nhận mật khẩu mới" name="reNewPassword">
                                </div>
                                <p class="changePassword">${messageReNewPassword}</p>
                            </div>
                            <div class="form-group">
                                <br>
                                <button class="btn btn-lg btn-success" type="submit"><i class="glyphicon glyphicon-ok-sign"></i>Thay Đổi</button>
                                <input type="hidden" name="service" value="changePassword">
                                <p class="changeSuccess">${messageChangeSuccess}</p>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div>
        <!--End Edit Profile -->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/js/change_password.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/main.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/profile.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/plugins/pace.min.js"></script>
        <!--===============================================================================================-->
        <script type="text/javascript" src="${pageContext.request.contextPath}/Resource/js/plugins/chart.js"></script>
        <!--===============================================================================================-->
        <script type="text/javascript">
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
    </body>

</html>