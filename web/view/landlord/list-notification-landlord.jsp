<%-- 
    Document   : index-demo
    Created on : May 15, 2024, 4:17:03 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <style>    table {
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
            span.dateOut{
                color:pink;
            }
            .app-content {
                padding: 20px;
                background-color: #f8f9fa;
            }
            .app-title {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .app-breadcrumb {
                margin-bottom: 20px;
            }
            #notificationBadge {
                background-color: #dc3545;
                color: #fff;
                padding: 5px 10px;
                border-radius: 50%;
            }
            #search-input {
                border-radius: 25px;
            }
            .table {
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .table th, .table td {
                vertical-align: middle;
            }
            .waiting {
                color: #ffc107;
            }
            .cancelled {
                color: #dc3545;
            }
            .confirmed {
                color: #28a745;
            }
            .dateOut {
                color: #dc3545;
            }
            .text-center {
                margin-top: 20px;
            }
            @media (max-width: 768px) {
                .app-title {
                    flex-direction: column;
                }
                .search-form {
                    margin-bottom: 20px;
                }
            }
                    .non-clickable-button {
                align-content: center;
                border: none;
                cursor: default;
                pointer-events: none; /* Disable mouse events */
                border-radius:10px;
            }

            .pagination {
                display: inline-block;
                position: sticky;
            }

            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
                border: 1px solid #ddd;
            }

            .pagination a.active {
                background-color: #4CAF50;
                color: white;
                border: 1px solid #4CAF50;
            }

            .pagination a:hover:not(.active) {
                background-color: #ddd;
            }
            .button-like {
                display: inline-block;
                padding: 6px 12px;
                margin: 0;
                font-size: 14px;
                font-weight: 400;
                line-height: 1.42857143;
                text-align: center;
                white-space: nowrap;
                vertical-align: middle;
                cursor: pointer;
                background-image: none;
                border: 1px solid transparent;
                border-radius: 4px;
                text-decoration: none;
                color: #ffffff;
                background-color: #337ab7;
                border-color: #2e6da4;
            }

            .button-like:hover,
            .button-like:focus,
            .button-like:active {
                background-color: #286090;
                border-color: #204d74;
                color: #ffffff;
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
                <li><a class="app-nav__item" href="/ManageLodgingHouse/LoginServlet?service=logout"><i class='bx bx-log-out bx-rotate-180'></i> </a>
                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <hr>
            <ul class="app-menu">
  <li>
                    <a id="notificationLink " href="list-notification-landlord" class="app-menu__item active">
                        <i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Thông Báo Mới</span>
                        <span class="badge">${sessionScope.NumberOfNotification}</span> <!-- Example: Replace with dynamic content -->
                    </a>
                </li>
                <li><a class="app-menu__item " href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item " href="/ManageLodgingHouse/management-lodging-houses?index=1"><i class='app-menu__icon bx bx-id-card'></i> <span
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
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/type-of-investment-costs-servlet"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý loại phí đầu tư</span></a></li>                      
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-role-of-staff"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i>
                        <span class="app-menu__label">Quản lý chức vụ</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
                            đặt hệ thống</span></a></li>
            </ul>
        </aside>

        <main class="app-content col-sm-10">
            <c:set var="currentDate" value="<%= new java.util.Date() %>" />
            <fmt:formatDate var="currentDateFormatted" value="${currentDate}" pattern="yyyy-MM-dd" />

            <!-- Format the notification date -->
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
                            <input id="search-input" type="search" class="form-control" placeholder="Search" aria-label="Search" aria-describedby="search-addon"/>
                        </div>
                    </div>
                </div>
                <div id="search-results"></div>
            </div>
                        <div id="table-body">            
            <table class="table table-striped" >
                <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Thông báo</th>
                        <th>Ngày hết hạn xác nhận</th>
                        <th>Phản hồi</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="notification" items="${sessionScope.pagination.getListObject()}">
                        <tr>
                            <td>${notification.getNotificationId()}</td>
                            <td><a href="detatil-notification-landlord?notificationId=${notification.getNotificationId()}">
                                    ${notification.getNotificationMessage()}
                                </a></td>
                            <td>${notification.getNotificationDateTime()}</td>
                            <td>
                                <fmt:formatDate var="notificationDateFormatted" value="${notification.getNotificationDateTime()}" pattern="yyyy-MM-dd" />
                                <c:choose>
                                    <c:when test="${notificationDateFormatted >= currentDateFormatted && notification.isConfirmationStatus() == 0}">
                                        <span class="waiting">Đang chờ xác nhận</span>
                                    </c:when>
                                    <c:when test="${notification.isConfirmationStatus() == 1}">
                                        <span class="cancelled">Đã hủy xác nhận</span>
                                    </c:when>
                                    <c:when test="${notification.isConfirmationStatus() == 2}">
                                        <span class="confirmed">Đã xác nhận</span>
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
                      <div class="pagination">
                    <c:set var="currentPage" value="${sessionScope.pagination.getCurentPage()}"/>
                    <c:if test="${currentPage > 1}">
                        <a href="list-notification-landlord?curentPage=${currentPage - 1}" >&laquo;</a>
                    </c:if>
                    <c:forEach  begin="${sessionScope.pagination.getStart()}" end="${sessionScope.pagination.getEnd()}" var="num">   
                        <c:if test="${num != 0}">
                            <c:if test="${num == currentPage}">
                                <a href="list-notification-landlord?curentPage=${num}"  class="active">${num}</a>
                            </c:if>
                            <c:if test="${num != currentPage}">
                                <a href="list-notification-landlord?curentPage=${num}" >${num}</a>
                            </c:if>   
                        </c:if>
                    </c:forEach>
                    <c:if test="${sessionScope.pagination.getNumberOfPage() > currentPage}">
                        <a href="list-notification-landlord?curentPage=${currentPage + 1}">&raquo;</a>
                    </c:if>
                </div>              
                          </div>       
            <div class="text-center" style="font-size: 13px">
                <p><b>Copyright
                        <script type="text/javascript">
                            document.write(new Date().getFullYear());
                        </script> Quan li tro | SE1824 GROUP 1
                    </b></p>
            </div>
        </main>
        <script src="${pageContext.request.contextPath}/resource/doc/js/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resource/doc/js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resource/doc/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resource/doc/js/main.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/resource/doc/js/plugins/pace.min.js"></script>
        <!--===============================================================================================-->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resource/doc/js/plugins/chart.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!--===============================================================================================-->
             <script>
                            $(document).ready(function () {
                                // Xử lý sự kiện khi input search thay đổi
                                $('#search-input').on('input', function () {
                                    let query = $(this).val();
                                    // Gửi yêu cầu AJAX tới endpoint 'search-notification'
                                    $.ajax({
                                        url: 'search-notification',
                                        type: 'POST',
                                        data: {search: query},
                                        success: function (data) {
                                            $('#table-body').html(data); // Cập nhật nội dung bảng với kết quả tìm kiếm
                                        },
                                        error: function (xhr, status, error) {
                                            console.error('AJAX Error: ', status, error);
                                        }
                                    });
                                });
                            });
                            // Xử lý sự kiện khi click vào các nút phân trang
                            $(document).on('click', '.pagination-btn', function () {
                                var page = $(this).data('page'); // Lấy số trang từ thuộc tính data-page của nút
                                sendRequest(page); // Gọi hàm gửi yêu cầu khi chuyển trang
                            });
                            function sendRequest(page) {
                                $.ajax({
                                    url: 'search-notification', // Đường dẫn đến Servlet của bạn
                                    type: 'GET', // Loại yêu cầu
                                    data: {curentPage: page}, // Dữ liệu gửi đi (trang hiện tại)
                                    success: function (response) {
                                        // Xử lý phản hồi từ máy chủ
                                        // Ví dụ: cập nhật nội dung trang với phản hồi từ máy chủ
                                        $('#table-body').html(response);
                                    },
                                    error: function (xhr, status, error) {
                                        // Xử lý lỗi nếu có
                                        console.error('Lỗi AJAX: ' + status + ' ' + error);
                                    }
                                });
                            }

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
    </body>

</html>
