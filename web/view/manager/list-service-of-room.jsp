<%-- 
    Document   : notification-waiting-room.jsp
    Created on : Jun 23, 2024, 6:01:47 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html lang="en">

    <head>
        <title>Danh sách các dịch vụ</title>
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


        </style>
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <header class="app-header">
            <!-- Sidebar toggle button-->
            <a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
               aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">


                <!-- User Menu-->
                <li><a class="app-nav__item" href="/ManageLodgingHouse/LoginServlet?service=logout"><i class='bx bx-log-out bx-rotate-180'></i> </a>

                </li>
            </ul>
        </header>
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <c:if test="${account.getRoleId()==2}">
            <div class="col-sm-2">
                <%@include file="left-menu-manager.jsp" %>
            </div></c:if>
        <c:if test="${account.getRoleId()==3}">
            <div class="col-sm-2">
                <%@include file="left-tenant.jsp" %>
            </div></c:if>
            <!-- Edit Profile -->
            <main class="app-content col-sm-10">
                <div class="app-title">

                    </ul>
                    <div id="clock"></div>
                </div>
                <div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="tile">
                                <div class="tile-body">
                                    <div class="row element-button">
                                        <!--                                <div class="input-group col-sm-7">
                                                                            <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" />
                                                                        </div>-->

                                    </div>
                                    <div class="row" style="margin-bottom: 20px;">
                                        <div class="col-md-12">
                                            <div class="app-title">
                                                <ul class="app-breadcrumb breadcrumb">
                                                <c:if test="${sessionScope.account.roleId == 3}">
                                                    <li class="breadcrumb-item"><a href="tenant-contract-servlet?statusAccept=1">Hợp đồng</a></li>
                                                    <li class="breadcrumb-item"><a href="tenant-contract-servlet?statusAccept=2">Hợp đồng đã được duyệt</a></li>
                                                    <li class="breadcrumb-item"><a href="room-detail-servlet?id=${sessionScope.roomId}">Chi tiết phòng</a></li>
                                                    <li class="breadcrumb-item"><a href="service-in-room-servlet?id=${sessionScope.roomId}&roleType=${sessionScope.account}&statusAccept=0">Các dịch vụ</a></li>
                                                    </c:if>
                                                    <c:if test="${sessionScope.account.roleId == 2}">
                                                    <li class="breadcrumb-item"><a href="room-detail-servlet?id=${sessionScope.roomId}">Chi tiết phòng</a></li>
                                                    <li class="breadcrumb-item"><a href="service-in-room-servlet?id=${sessionScope.roomId}&roleType=${sessionScope.account}&statusAccept=0">Các dịch vụ</a></li>
                                                    </c:if>
                                            </ul>
                                            <div id="content" class="full-height">
                                                <!-- Content will be updated here -->
                                            </div>
                                            <div id="clock"></div>
                                        </div>
                                    </div>
                                </div>

                                <div id="content">

                                    <c:if test="${sessionScope.account.roleId == 3 && requestScope.serviceOfLodgingHouse != null}">
                                        <form id="serviceForm" method="post" action="service-in-room-servlet">
                                            <table class="table table-hover table-bordered" id="sampleTable">
                                                <thead>
                                                    <tr>
                                                        <th width="10"></th>
                                                        <th>Tên dịch vụ</th>
                                                        <th>Phí dịch vụ (vnđ)</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.serviceOfLodgingHouse}" var="list">
                                                        <tr>
                                                            <td width="10">
                                                                <input type="checkbox" class="mandatory-service" id="${list.serviceId}" value="${list.serviceId}" name="service" 
                                                                       data-service-id="${list.serviceId}"
                                                                       <c:forEach items="${requestScope.listServiceAccept}" var="accept">
                                                                           <c:if test="${accept.getServiceOfLodgingHouse().getServiceId() == list.serviceId}">
                                                                               checked
                                                                           </c:if>
                                                                       </c:forEach>
                                                                       <c:if test="${list.serviceId == 6 || list.serviceId == 7 || list.serviceId == 1 || list.serviceId == 2}">checked</c:if>
                                                                           >
                                                                </td>
                                                            <c:forEach items="${requestScope.listServiceName}" var="list2">
                                                                <c:if test="${list.getServiceId() == list2.getServiceId()}">
                                                                    <td>${list2.getServiceName()}</td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <fmt:setLocale value="vi_VN" />
                                                            <td>
                                                                <fmt:formatNumber value="${list.price}" type="number" pattern="#,##0" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:if test="${tenantService.getStatusAccept() == 2 && tenantService.getStatusDelete() == 1 && tenantService.getTypeOfAccept() == 1}">
                                                <input type="submit" class="btn btn-add" value="Gửi yêu cầu"/>
                                            </c:if>
                                        </form>
                                    </c:if> 
                                    <c:if test="${sessionScope.active == 1}">
                                        <c:if test="${listServiceAccept != null}">
                                            <table class="table table-hover table-bordered" id="sampleTable">
                                                <thead>
                                                    <tr>

                                                        <th>Tên dịch vụ</th>
                                                        <th>Phí dịch vụ (vnđ)</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.listServiceAccept}" var="list">
                                                        <tr>

                                                            <c:forEach items="${requestScope.listServiceName}" var="list2">
                                                                <c:if test="${list.getServiceOfLodgingHouse().getServiceId() == list2.getServiceId()}">
                                                                    <td>${list2.getServiceName()}</td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <fmt:setLocale value="vi_VN" />
                                                            <td>
                                                                <fmt:formatNumber value="${list.price}" type="number" pattern="#,##0" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${sessionScope.account.roleId == 2 && requestScope.listServicePending != null}">
                                        <form id="serviceForm" method="post" action="service-in-room-servlet">
                                            <table class="table table-hover table-bordered" id="sampleTable">
                                                <thead>
                                                    <tr>

                                                        <th>Tên dịch vụ</th>
                                                        <th>Phí dịch vụ (vnđ)</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.listServicePending}" var="list">
                                                        <tr>
                                                            <td width="10" style="display: none;"><input hidden type="checkbox" value="${list.getServiceOfLodgingHouse().getServiceId()}" checked name="service"></td>

                                                            <c:forEach items="${requestScope.listServiceName}" var="list3">
                                                                <c:if test="${list.getServiceOfLodgingHouse().getServiceId() == list3.getServiceId()}">
                                                                    <td>${list3.getServiceName()}</td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <fmt:setLocale value="vi_VN" />
                                                            <td>
                                                                <fmt:formatNumber value="${list.price}" type="number" pattern="#,##0" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <c:if test="${listServicePending.size() != 0}">
                                                <input type="hidden" id="serviceAction" name="serviceAction" value="">
                                                <a href="javascript:void(0);" class="btn btn-success" onclick="submitForm('accept');">Chấp nhận</a>
                                                <a href="javascript:void(0);" class="btn btn-warning" onclick="submitForm('reject');">Hủy bỏ</a>
                                            </c:if> 
                                        </form>

                                    </c:if>
                                    <c:if test="${sessionScope.active == 0 && sessionScope.account.roleId == 1}">
                                        <c:if test="${listServiceAccept != null}">
                                            <table class="table table-hover table-bordered" id="sampleTable">
                                                <thead>
                                                    <tr>

                                                        <th>Tên dịch vụ</th>
                                                        <th>Phí dịch vụ (vnđ)</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.listServiceAccept}" var="list">
                                                        <tr>

                                                            <c:forEach items="${requestScope.listServiceName}" var="list2">
                                                                <c:if test="${list.getServiceOfLodgingHouse().getServiceId() == list2.getServiceId()}">
                                                                    <td>${list2.getServiceName()}</td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <fmt:setLocale value="vi_VN" />
                                                            <td>
                                                                <fmt:formatNumber value="${list.price}" type="number" pattern="#,##0" />
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                    </c:if>
                                </div> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${sessionScope.account.roleId == 1}">
                <div class="pagination">
                    <a href="service-in-room-servlet?id=${sessionScope.roomId}&roleType=${sessionScope.account.roleId}&statusAccept=0"<c:if test="${active == 0}"> class="active" </c:if>>Dịch vụ hiện tại</a>
                    </div>      
            </c:if>
            <c:if test="${sessionScope.account.roleId == 2}">
                <div class="pagination">
                    <a href="service-in-room-servlet?id=${sessionScope.roomId}&roleType=${sessionScope.account.roleId}&statusAccept=0" <c:if test="${active == 0}"> class="active" </c:if>>Yêu cầu</a>
                    <a href="service-in-room-servlet?id=${sessionScope.roomId}&roleType=${sessionScope.account.roleId}&statusAccept=1"<c:if test="${active == 1}"> class="active" </c:if>>Dịch vụ hiện tại</a>
                    </div>      
            </c:if>
            <c:if test="${tenantService.getStatusAccept() == 2 && tenantService.getStatusDelete() == 1 && tenantService.getTypeOfAccept() == 1}">
                <c:if test="${sessionScope.account.roleId == 3}">
                    <div class="pagination">
                        <a href="service-in-room-servlet?id=${sessionScope.roomId}&roleType=${sessionScope.account.roleId}&statusAccept=0" <c:if test="${active == 0}"> class="active" </c:if>>Yêu cầu</a>
                        <a href="service-in-room-servlet?id=${sessionScope.roomId}&roleType=${sessionScope.account.roleId}&statusAccept=1"<c:if test="${active == 1}"> class="active" </c:if>>Dịch vụ hiện tại</a>

                        </div>      
                </c:if>
            </c:if>    
            <!-- Banner Slider End -->


        </main> 
        <script>
            function markNotification(notificationId, action) {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "list-notification", true);
                xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                        console.log("Response from server:", xhr.responseText);
                        // Chuyển hướng sau khi xử lý thành công
                        if (action === "check") {
                            window.location.href = "/Test";
                        } else if (action === "cross") {
                            // Optionally, handle cross action if necessary
                            window.location.href = "notification-waiting-room";
                        }
                    }
                };
                xhr.send("notificationId=" + notificationId + "&action=" + action);
            }

            document.addEventListener("click", function (event) {
                if (event.target.closest(".check-notification")) {
                    event.preventDefault();
                    var notificationId = event.target.closest(".check-notification").getAttribute("data-id");
                    markNotification(notificationId, "check");
                }

                if (event.target.closest(".cross-notification")) {
                    event.preventDefault();
                    var notificationId = event.target.closest(".cross-notification").getAttribute("data-id");
                    markNotification(notificationId, "cross");
                }
            });


        </script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="src/jquery.table2excel.js"></script>
        <script src="js/main.js"></script>
        <!-- The javascript plugin to display page loading on top-->
        <script src="js/plugins/pace.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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
            document.addEventListener('DOMContentLoaded', function () {
                const mandatoryServices = [6, 7]; // Service IDs that are mandatory
                const checkboxes = document.querySelectorAll('.mandatory-service');

                checkboxes.forEach(checkbox => {
                    checkbox.addEventListener('change', function () {
                        const serviceId = parseInt(this.dataset.serviceId);
                        if (mandatoryServices.includes(serviceId) && !this.checked) {
                            swal({
                                title: "Cảnh báo",
                                text: "Điện và nước là dịch vụ bắt buộc của phòng!",
                                icon: "warning",
                                buttons: false, // Hide the cancel button
                                dangerMode: true,
                            }).then(() => {
                                this.checked = true; // Re-check the checkbox
                            });
                        }
                    });
                });
            });
        </script>
        <script>
            function submitForm(action) {
                document.getElementById('serviceAction').value = action;
                document.getElementById('serviceForm').submit();
            }
        </script>
        <script>
            $(document).ready(function () {
                $('#serviceForm').submit(function (event) {
                    var selectedServices = [];
                    var duplicateServices = [];

                    $('.mandatory-service:checked').each(function () {
                        var serviceId = $(this).data('service-id');

                        // Kiểm tra nếu dịch vụ đã được chọn và đã được chấp nhận
                        if ($(this).is(':checked') && $(this).data('accepted')) {
                            // Kiểm tra xem dịch vụ này đã tồn tại trong danh sách đã chọn hay chưa
                            if (selectedServices.indexOf(serviceId) !== -1) {
                                duplicateServices.push(serviceId);
                            } else {
                                selectedServices.push(serviceId);
                            }
                        }
                    });

                    if (duplicateServices.length > 0) {
                        event.preventDefault(); // Ngăn chặn submit form

                        // Hiển thị SweetAlert thông báo các dịch vụ yêu cầu bị trùng lặp
                        var message = 'Các dịch vụ yêu cầu sau đây đã bị trùng lặp: \n';
                        duplicateServices.forEach(function (serviceId) {
                            var serviceName = $('#' + serviceId).closest('tr').find('td:nth-child(2)').text().trim();
                            message += '- ' + serviceName + '\n';
                        });

                        swal({
                            title: 'Thông báo',
                            text: message,
                            icon: 'warning',
                            button: 'Đóng',
                        });
                    }
                });
            });
        </script>
        <script>
            $(document).ready(function () {
                $('.mandatory-service').on('change', function () {
                    var serviceId = $(this).data('service-id');
                    var isChecked = $(this).is(':checked');

                    if (!isChecked && serviceId != 6 && serviceId != 7 && serviceId != 1 && serviceId != 2) {
                        var checkbox = $(this);
                        swal({
                            title: "Bạn có chắc chắn?",
                            text: "Bạn có muốn bỏ chọn dịch vụ này?",
                            icon: "warning",
                            buttons: {
                                cancel: "Hủy bỏ",
                                confirm: "Đồng ý"
                            },
                            dangerMode: true,
                        }).then((willDelete) => {
                            if (willDelete) {
                                checkbox.prop('checked', false);
                            } else {
                                checkbox.prop('checked', true);
                            }
                        });
                    }
                });
            });
        </script>
    </body>
</html>
