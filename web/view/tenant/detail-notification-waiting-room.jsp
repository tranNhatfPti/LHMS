<%-- 
    Document   : room-information
    Created on : May 25, 2024, 11:12:58 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css">
        <!-- Link Owl Carousel Theme CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css">
        <!-- Link your custom CSS file if any -->
        <link rel="stylesheet" href="styles.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <!-- Link Owl Carousel JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>
        <!-- Your custom JavaScript file -->
        <script src="scripts.js"></script>
        <style>

            .single_slider {
                position: relative;
                width: 100%;
                height: 70vh; /* Adjust height as needed */
                overflow: hidden; /* Hide overflow content */
            }

            .background_image {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-size: cover;
                background-position: center;
            }

            .slider_content {
                position: absolute;
                bottom: 20px; /* Adjust vertical position */
                left: 20px; /* Adjust horizontal position */
                color: #fff; /* Text color */
                z-index: 1; /* Ensure content is above background */
            }

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

            .single_slider {
                background-size: cover;
                background-position: center;
                width: 70%;
                height: 70vh; /* Set the height to cover the full viewport height */
            }
            .new-noti {
                position: relative;

            }

            .badge {
                width: 20px; /* Điều chỉnh chiều rộng của badge */
                height: 20px; /* Điều chỉnh chiều cao của badge */
                background-color: red; /* Màu nền của badge */
                color: white; /* Màu chữ của badge */
                border-radius: 50%; /* Bo tròn viền của badge để tạo hình tròn */
                text-align: center; /* Căn giữa nội dung của badge */
                position: absolute;
                top: -15px; /* Điều chỉnh vị trí theo chiều dọc và dịch lên một ít */
                right: -15px; /* Điều chỉnh vị trí theo chiều ngang và dịch qua trái một ít */
                font-size: 12px; /* Điều chỉnh kích thước chữ */
                line-height: 20px; /* Điều chỉnh chiều cao dòng để căn giữa chữ */
                display: flex; /* Sử dụng flexbox để căn giữa */
                justify-content: center; /* Căn giữa theo chiều ngang */
                align-items: center; /* Căn giữa theo chiều dọc */
                transform: translate(10px, 5px); /* Dịch chuyển badge về phía trái và phía trên 50% của chính nó */
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            tr:hover {
                background-color: #ddd;
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
                <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
                <aside class="app-sidebar">

                    <hr>
                    <ul class="app-menu">
                        <li><a class="app-menu__item ${tagMenu=="showRoom"?"active":""}" href="room?service=showRoomInfor"><i class='app-menu__icon  bx bxs-home'></i><span
                                    class="app-menu__label">Thông Tin Cá nhân</span></a></li>
                        <li><a class="app-menu__item ${tagMenu=="showProfile"?"active":""}" href="account?service=showProfile"><i class='app-menu__icon bx bx-id-card'></i><span
                                    class="app-menu__label">Hồ Sơ</span></a></li>
                        <li><a class="app-menu__item ${tagMenu=="changePassword"?"active":""}" href="account?service=showChangePassword"><i class='app-menu__icon bx bxs-lock '></i> <span
                                    class="app-menu__label">Mật Khẩu</span></a></li>
                        <li><a class="app-menu__item" href="account?service=logout"><i class='app-menu__icon bx bx-log-out'></i><span
                                    class="app-menu__label">Đăng Xuất</span></a></li>
                </aside>
            </div>
            <!-- Edit Profile -->
            <main class="app-content col-sm-10">

                <c:set var="currentDate" value="<%= new java.util.Date() %>" />
                <fmt:formatDate var="currentDateFormatted" value="${currentDate}" pattern="yyyy-MM-dd" />

                <div class="container mt-5">
                    <h2>Thông báo chi tiết</h2>
                    <form action="detail-notification" method="post">  
                        <c:set value="${requestScope.lod}" var="lod" />
                        <c:set value="${requestScope.noti}" var="notification" />
                        <fmt:formatDate var="notificationDateFormatted" value="${notification.getNotificationDateTime()}" pattern="yyyy-MM-dd" />
                        <div class="form-group">
                            <label for="notificationId">Notification ID</label>
                            <input type="text" class="form-control" id="notificationId" name="notificationId" value="${noti.getNotificationId()}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="notificationMessage">Thông báo</label>
                            <textarea class="form-control" id="notificationMessage" name="notificationMessage" rows="3" readonly >${noti.getNotificationMessage()}</textarea>
                        </div>
                        <div class="form-group">
                            <label for="landlordName">Nhà trọ tên</label>
                            <input type="text" class="form-control" id="landlordName" name="landlordName" value="${lod.getNameLodgingHouse()}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="notificationImage">Hình ảnh nhà trọ</label><br>
                            <img src="${lod.getImg()}" alt="Lodging House Image" class="img-thumbnail">
                        </div>
                        <div class="form-group">
                            <label for="address">Địa chỉ trọ</label>
                            <input type="text" class="form-control" id="address" name="address" value="${lod.getProvince()}-${lod.getDistrict()}-${lod.getWard()}"readonly >
                        </div>

                        <div class="form-group">
                            <label for="notificationDateTime">Thời hạn xác nhận</label>
                            <input type="text" class="form-control" id="notificationDateTime" name="notificationDateTime" value="${noti.getNotificationDateTime()}" readonly>
                        </div>
                            <c:choose>
                        <c:when test="${notificationDateFormatted >= currentDateFormatted && notification.isConfirmationStatus() == 0&&notification.getTypeOfNotification()==1}">
                            <button type="submit" name="action" value="accept" class="btn btn-success mr-2">Đồng ý</button>
                            <button type="submit" name="action" value="reject" class="btn btn-danger">Không đồng ý</button>

                        </c:when>
                        </c:choose>
                    </form>
                </div>


            </main>    </div>

        <script>
            $(document).ready(function () {
                $('#search-input').on('input', function () {
                    let query = $(this).val();

                    // Perform the AJAX request
                    $.ajax({
                        url: 'search-notification', // Replace with the URL to your search endpoint
                        type: 'GET',
                        data: {search: query},
                        success: function (data) {
                            // Update the table body with the search results
                            $('#table-body').html(data);
                        },
                        error: function (xhr, status, error) {
                            console.error('AJAX Error: ', status, error);
                        }
                    });
                });
            });
        </script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

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
        !-- JavaScript Libraries -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"></script>

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
