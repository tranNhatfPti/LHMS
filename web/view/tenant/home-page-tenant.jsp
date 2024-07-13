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
        <title>Trang Chủ</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

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
        <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        <script src="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js"></script>
        <link rel="stylesheet" href="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.css" />

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
            .ggmap{
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                border: 0;
            }
            .btn-lg {
                padding: 10px 20px;
                font-size: 1.25rem;
            }

            .btn-block {
                display: block;
                width: 100%;
            }
            .icon-small {
                font-size: 2rem; /* Điều chỉnh kích thước biểu tượng ở đây */
                vertical-align: middle; /* Căn chỉnh biểu tượng với văn bản */
            }
            a.numberzalo {
                position: fixed;
                bottom: 15px;
                left: 110px;
                cursor: pointer;
                z-index: 9999;
            }
            a.numberzalo img {
                width: 50px;
                border-radius: 50%;
            }
            .full-width-table {
                width: 95%;
                max-width: 95%;
                table-layout: fixed;
                margin: 0 auto; /* Đảm bảo bảng được căn giữa */
            }
            .full-width-table th, .full-width-table td {
                word-wrap: break-word;
                text-align: center;
            }
            .table_service {
                padding-left: 15px;
                padding-right: 15px;
                margin-left: 5px;
            }
            #sampleTable {
                margin-left: 15px; /* Điều chỉnh giá trị này nếu cần */
            }
            .rating {
                position: relative;
                display: flex;
            }

            .rating {
                display: flex;
                gap: 5px; /* Adjust spacing between stars if needed */
            }

            .star {
                width: 20px;
                height: 20px;
                display: inline-block;
                background: url('https://icon-library.com/images/star-png-icon/star-png-icon-0.jpg') no-repeat center center;
                background-size: contain;
                position: relative;
            }

            .star::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQK_x0xsTpZtA8UkG6hGE7Y8E-EXVXwT8NWbY0KymT2o_0pxmI&s') no-repeat center center;
                background-size: contain;
                clip-path: polygon(0 0, var(--percent, 0%) 0, var(--percent, 0%) 100%, 0 100%);
            }
            #map {
                height: 500px;
                width: 100%;
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
                                <li class="breadcrumb-item"><a href="home-tenant?service=showLodgingInfor"><b>Trang Chủ</b></a></li>
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
                                <div class="col-md-12">
                                    <div class="row element-button">
                                        <div class="col-sm-6">
                                            <a class="btn btn-delete btn-lg btn-block nhap-tu-file" href="home-tenant?service=showService" title="Thêm"><i class="fas fa-file-upload"></i>
                                                Dịch Vụ</a>
                                        </div>
                                        <div class="col-sm-6">
                                            <a class="btn btn-delete btn-lg btn-block print-file" href="room?service=showRoomInfor" title="Thêm"><i class="fas fa-print"></i>
                                                Thông Tin Phòng</a>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${showService == 'no'}">
                                    <div class="col-md-6">
                                        <div class="widget-small info coloured-icon"><i class='icon bx bxs-home fa-3x'></i>
                                            <div class="info">
                                                <h4>Tên Khu Trọ</h4>
                                                <p><b>${lodging.nameLodgingHouse}</b></p>
                                                <p class="info-tong"><div id="averageRating" class="rating">
                                                    <span class="star" data-rating="1"></span>
                                                    <span class="star" data-rating="2"></span>
                                                    <span class="star" data-rating="3"></span>
                                                    <span class="star" data-rating="4"></span>
                                                    <span class="star" data-rating="5"></span>
                                                </div>
                                                <input type="hidden" id="averageRatingInput" value="${star}"></p>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="widget-small danger coloured-icon">
                                            <i class='icon bx bx-user fa-3x'></i>
                                            <div class="info">
                                                <h4>Quản Lý</h4>
                                                <p><b>${manageInfor.fullName}</b></p>
                                                <p class="info-tong">
                                                    <a href="https://zalo.me/${manageInfor.phoneNumber}" target="_blank">
                                                        <img src="./Resource/images/zalo.png" alt="Zalo Icon" style="width: 20px; height: 20px; vertical-align: middle;">${manageInfor.phoneNumber}
                                                    </a>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="tile">
                                                    <h3 class="tile-title text-center"><i class='icon bx bxs-map fa-3x icon-small'>${lodging.addressDetail},${lodging.ward}, ${lodging.district}, ${lodging.province}</i></h3>
                                                    <div class="img-container" style="position: relative; width: 100%; padding-bottom: 56.25%; height: 0; overflow: hidden;">
                                                        <!--<iframe class="ggmap" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1935.3589683633932!2d105.52467938301383!3d21.01280329238004!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc60e7d3f19%3A0x2be9d7d0b5abcbf4!2sFPT%20University!5e0!3m2!1sen!2s!4v1718619495468!5m2!1sen!2s" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>-->
                                                        <div id="map"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${showService == 'yes'}">
                                    <div class="row">
                                        <div class="col-md-12 table_service">
                                            <table class="table table-hover table-bordered full-width-table" id="sampleTable">
                                                <thead>
                                                    <tr>
                                                        <th>Tên Dịch Vụ</th>
                                                        <th>Giá</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="entry" items="${serviceNameAndPrice}">
                                                        <tr>
                                                            <td>${entry.key}</td>
                                                            <td>${entry.value} VNĐ</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <!--END left-->
                        <!--Right-->
                        <div class="col-md-12 col-lg-6">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="tile">
                                        <h3 class="tile-title text-center">Hình Ảnh Khu Trọ</h3>
                                        <div class="img-container">
                                            <img src="${lodging.img}" class="img-fluid" alt="Hình Ảnh Phòng">
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
                <script>
        var map = L.map('map').setView([0, 0], 2);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);

        var geocoder = L.Control.Geocoder.nominatim();
        var address = '${lodging.district}, ${lodging.province}';

        geocoder.geocode(address, function (results) {
            if (results.length > 0) {
                var result = results[0];
                var latlng = result.center;
                L.marker(latlng).addTo(map)
                        .bindPopup(result.name)
                        .openPopup();
                map.setView(latlng, 15);
            } else {
                alert('Address not found');
            }
        });
                </script>

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
                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        const stars = document.querySelectorAll("#averageRating .star");
                        const averageRatingInput = document.getElementById("averageRatingInput");
                        const averageRating = parseFloat(averageRatingInput.value);

                        stars.forEach(star => {
                            const starRating = parseFloat(star.getAttribute("data-rating"));
                            if (starRating <= averageRating) {
                                star.style.setProperty('--percent', '100%');
                            } else if (starRating - 1 < averageRating && averageRating < starRating) {
                                const percent = ((averageRating % 1) * 100) + '%';
                                star.style.setProperty('--percent', percent);
                            } else {
                                star.style.setProperty('--percent', '0%');
                            }
                        });
                    });





                </script>
                </body>
                </html>
