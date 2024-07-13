<%-- 
    Document   : room-information
    Created on : May 25, 2024, 11:12:58 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="en">

    <head>
        <title>Thông Tin Phòng Trọ</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
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
            .avatarHeader{
                width: 100px !important;
                height: 100px !important;
                border-radius: 50% !important;
                object-fit: cover !important;
            }
            .img-container {
                width: 100%;
                height: 100%;
                overflow: hidden;
            }

            .img-container img {
                width: 100%;
                height: auto;
            }
        </style>
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <div>
            <%@include file="header.jsp" %>
        </div>
        <div class="row">
            <!-- Navbar-->
            <div class="col-sm-2">
                <%@include file="left-menu-tenant.jsp" %>
            </div>
            <!-- Edit Profile -->
            <main class="app-content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="app-title">
                            <ul class="app-breadcrumb breadcrumb">
                                <li class="breadcrumb-item"><a href="room?servicee=showRoomInfor"><b>Thông Tin Phòng Trọ</b></a></li>
                            </ul>
                            <div id="clock"></div>
                        </div>
                    </div>
                </div>
                <c:if test="${not empty checkRoom}">
                    <h1>Hello, ${userInfor.fullName}!, bạn chưa được chủ trọ thêm vào phòng</h1>
                </c:if>
                <c:if test="${empty checkRoom}">
                    <div class="row">
                        <!--Left-->
                        <div class="col-md-12 col-lg-6">
                            <div class="row">
                                <!-- col-6 -->
                                <div class="col-md-6">
                                    <div class="widget-small primary coloured-icon"><i class='icon bx bx-door-open fa-3x'></i>
                                        <div class="info">
                                            <h4>Tên Phòng</h4>
                                            <p><b>Phòng ${room.roomId}</b></p>
                                            <p class="info-tong">${room.description}</p>
                                        </div>
                                    </div>
                                </div>
                                <!-- col-6 -->
                                <div class="col-md-6">
                                    <div class="widget-small info coloured-icon"><i class='icon bx bxs-home fa-3x'></i>
                                        <div class="info">
                                            <h4>Tên Khu Trọ</h4>
                                            <p><b>${room.lodgingHouseName}</b></p>
                                            <p class="info-tong">${room.numberOfRoom} phòng</p>
                                        </div>
                                    </div>
                                </div>
                                <!-- col-6 -->
                                <div class="col-md-6">
                                    <div class="widget-small warning coloured-icon"><i class='icon bx bxs-dollar-circle fa-3x'></i>
                                        <div class="info">
                                            <h4>Giá Phòng Trọ</h4>
                                            <p><b>${room.price} vnđ</b></p>
                                            <p class="info-tong">Chưa bao gồm dịch vụ</p>
                                        </div>
                                    </div>
                                </div>
                                <!-- col-6 -->
                                <div class="col-md-6">
                                    <div class="widget-small danger coloured-icon"><i class='icon bx bxs-map fa-3x'></i>
                                        <div class="info">
                                            <h4>Địa Chỉ</h4>
                                            <p><b>${room.ward}, ${room.district}, ${room.province}</b></p>
                                            <p class="info-tong">${room.addressDetail}</p>
                                        </div>
                                    </div>
                                </div>
                                <!-- col-12 -->
                                <div class="col-md-12">
                                    <div class="tile">
                                        <h3 class="tile-title">Thông Tin Người Thuê Trọ</h3>
                                        <div>
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>Họ và Tên</th>
                                                        <th>Số Điện Thoại</th>
                                                        <th>Email</th>
                                                        <th>Địa Chỉ</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>${userInfor.fullName}</td>
                                                        <td>${userInfor.phoneNumber}</td>
                                                        <td>
                                                            ${account.email}
                                                        </td>
                                                        <td>${userInfor.ward}, ${userInfor.district}, ${userInfor.province}</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!-- / div trống-->
                                    </div>
                                </div>
                                <!-- / col-12 -->

                            </div>
                        </div>
                        <!--END left-->
                        <!--Right-->
                        <div class="col-md-12 col-lg-6">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="tile">
                                        <h3 class="tile-title text-center">Hình Ảnh Phòng Trọ</h3>
                                        <div class="img-container">
                                            <img src="${room.image}" class="img-fluid" alt="Hình Ảnh Phòng">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </c:if>    

                <!--End Edit Profile -->
                <script src="${pageContext.request.contextPath}/Resource/doc/js/jquery-3.2.1.min.js"></script>
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
                <script type="text/javascript" src="${pageContext.request.contextPath}/Resource/doc/js/plugins/chart.js"></script>
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
