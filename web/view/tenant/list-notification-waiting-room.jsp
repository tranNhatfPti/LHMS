<%-- 
    Document   : room-information
    Created on : May 25, 2024, 11:12:58 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            span.waiting {
                color: orange; /* Màu sắc cho trạng thái "Đang chờ xác nhận" */
            }

            span.cancelled {
                color: red; /* Màu sắc cho trạng thái "Đã hủy xác nhận" */
            }

            span.confirmed {
                color: green; /* Màu sắc cho trạng thái "Đã xác nhận" */
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
                <c:if test="${not empty message}">
                    <!-- Modal Bootstrap -->
                    <div id="myModal" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Thông báo</h4>
                                </div>
                                <div class="modal-body">
                                    ${message}
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-md-12">
                        <div class="app-title">
                            <ul class="app-breadcrumb breadcrumb">
                                <li class="breadcrumb-item">
                                    <div class="new-noti">
                                        <div class="badge" id="notificationBadge">${requestScope.numOfNotification}</div>
                                    </div>
                                    <a id="notificationLink" href="list-notification">
                                        <b>Thông Báo Mới</b>
                                    </a>
                                </li>
                            </ul>

                            <div id="clock"></div>
                        </div>
                    </div>
                </div>
                <div class="container">
                    <div class="row search-form" style="margin-bottom: 10px">
                        <div class="col-md-10">
                            <div class="input-group">
                                <input id="search-input" type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon"/>
                            </div>
                        </div>
                    </div>
                    <div id="search-results"></div>
                </div>
                <c:set var="currentDate" value="<%= new java.util.Date() %>" />
                <fmt:formatDate var="currentDateFormatted" value="${currentDate}" pattern="yyyy-MM-dd" />
                <table id="table-body">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Thông báo</th>
                            <th>Ngày hết hạn xác nhận</th>
                            <th>Phản hồi</th>
                        </tr></thead>
                    <tbody >
                        <c:forEach var="notification" items="${listN}">
                            <tr>
                                <td>${notification.getNotificationId()}</td>
                                <c:if test="${notification.getTypeOfNotification()==1}">
                                    <td>
                                        <a href="detail-notification?notificationId=${notification.getNotificationId()}">
                                            ${notification.getNotificationMessage()}
                                        </a>
                                    </td>
                                </c:if>

                                <td>${notification.getNotificationDateTime()}</td>
                                <td>
                                    <fmt:formatDate var="notificationDateFormatted" value="${notification.getNotificationDateTime()}" pattern="yyyy-MM-dd" />

                                    <c:choose>

                                        <c:when test="${notification.isConfirmationStatus() == 1}">
                                            <span class="cancelled">Đã hủy xác nhận</span>
                                        </c:when>
                                        <c:when test="${notification.isConfirmationStatus() == 2}">
                                            <span class="confirmed">Đã xác nhận</span>
                                        </c:when>
                                        <c:when test="${notificationDateFormatted >= currentDateFormatted && notification.isConfirmationStatus() == 0&&notification.getTypeOfNotification()==1}">
                                            <a href="update-notification?notificationId=${notification.getNotificationId()}&amp;action=check" class="check-notification">
                                                <span>&#10003;</span> <!-- Check mark symbol -->
                                            </a>
                                            <a href="update-notification?notificationId=${notification.getNotificationId()}&amp;action=cross" class="cross-notification">
                                                <span>&#10007;</span> <!-- Cross mark symbol -->
                                            </a>
                                        </c:when>
                                        <c:when test="${notificationDateFormatted <= currentDateFormatted && notification.isConfirmationStatus() == 0}">
                                            <span class="dateOut">Đã quá hạn</span>
                                        </c:when>
                                    </c:choose>
                                </td>

                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
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
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script>
            $(document).ready(function () {
                // Check if message is not empty and display it using alert
            <c:if test="${not empty message}">
                alert("${message}");
            </c:if>
            });
        </script>



        <!-- Remove this script if it was used for triggering Bootstrap modal -->
        <script>
            $(document).ready(function () {
                $('#myModal').modal('show');
            });
        </script>

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
