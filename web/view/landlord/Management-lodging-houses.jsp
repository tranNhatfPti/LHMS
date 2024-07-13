<%-- 
    Document   : index-demo
    Created on : May 15, 2024, 4:17:03 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
        <style>
            .house-icon {
                font-size: 50px;
                color: #007bff;
            }

            .card-house {
                text-align: center;
                height: 100%;
                display: flex;
                flex-direction: column;
            }

            .card-house .card-body {
                padding: 1rem;
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            .card-title {
                flex-grow: 1;
            }

            .card-house .btn {
                margin-top: auto;
            }

            .pagination {
                display: flex;
                justify-content: center;
                margin: 20px 0;
                padding: 0;
                list-style-type: none;
            }

            .pagination li {
                margin: 0 5px;
            }

            .pagination a {
                color: #007bff;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color 0.3s, color 0.3s;
                border: 1px solid #dee2e6;
                border-radius: 4px;
            }

            .pagination a:hover {
                background-color: #e9ecef;
                color: #0056b3;
            }

            .pagination .active a {
                background-color: #007bff;
                color: white;
                border-color: #007bff;
            }

            .pagination .disabled a {
                color: #6c757d;
                pointer-events: none;
                cursor: not-allowed;
            }

            .pagination li:first-child a,
            .pagination li:last-child a {
                border-radius: 4px;
            }

            button {
                padding: 15px 25px;
                min-width: 120px;
                border: unset;
                border-radius: 15px;
                color: #212121;
                background: #e8e8e8;
                position: relative;
                font-weight: 1000;
                font-size: 17px;
                box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
                transition: all 250ms;
                overflow: hidden;
            }

            button:hover {
                color: green;
                box-shadow: 6px 10px 25px -3px rgba(0, 0, 0, 0.27);
            }

            button::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                height: 100%;
                width: 0;
                border-radius: 15px;
                background-color: #212121;
                z-index: -1;
                box-shadow: 4px 8px 19px -3px rgba(0, 0, 0, 0.27);
                transition: all 250ms;
            }

            button:hover::before {
                width: 100%;
            }
            .main-content {
                padding: 20px;
            }

            .search-form {
                margin-bottom: 20px;
            }

            .date-range-form {
                margin-bottom: 10px;
            }

            .card-house {
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                transition: box-shadow 0.3s ease;
            }

            .card-house:hover {
                box-shadow: 0 0 15px rgba(0, 0, 0,0);
            }

            .card-house .card-body {
                padding: 20px;
            }

            .card-house .card-title {
                font-size: 18px;
                font-weight: bold;
            }

            .card-house .card-text {
                font-size: 14px;
                color: #555;
                margin-bottom: 15px;
            }

            .card-house .btn {
                font-size: 14px;
                padding: 8px 16px;
                margin-right: 5px;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }

            .card-house:hover {
                transform: scale(1.05); /* Phóng to khi trỏ chuột vào */
                transition: transform 0.3s ease; /* Hiệu ứng chuyển đổi mượt mà */
            }


            .pagination_style {
                display: flex;
                justify-content: center;
                margin-top: 20px;
            }

            .page_number {
                font-size: 20px;
            }

            .page_index {
                background-color: #007bff;
                color: white;
                border: 1px solid #007bff;
                width: 40px;
                height: 40px;
                display: inline-flex;
                justify-content: center;
                align-items: center;
                margin-right: 10px;
                font-size: 20px;
                border-radius: 5px;
            }

            .page_index:hover {
                background-color: #0056b3;
            }

            .page_number a {
                padding: 5px 10px;
                margin-right: 10px;
                font-size: 20px;
            }

            .page_number span {
                font-size: 20px;
            }
            .list_button {
                margin-bottom: 20px;
            }

            .nav {
                display: flex;
                list-style-type: none;
                padding: 0;
            }

            .nav li {
                margin-right: 10px;
            }

            .nav a {
                display: inline-block;
                width: 40px;
                height: 40px;
                font-size: 24px;
                text-align: center;
                line-height: 40px;
                color: #000;
                background-color: #f0f0f0;
                border-radius: 50%;
                transition: background-color 0.3s, color 0.3s;
            }

            .nav a.active,
            .nav a:hover {
                background-color: #007bff;
                color: #fff;
            }
            .badge {
                width: 20px; /* Điều chỉnh chiều rộng của badge */
                height: 20px; /* Điều chỉnh chiều cao của badge */
                background-color: red; /* Màu nền của badge */
                color: white; /* Màu chữ của badge */
                border-radius: 50%; /* Bo tròn viền của badge để tạo hình tròn */
                text-align: center; /* Căn giữa nội dung của badge */
                position: absolute;
                top: -0px; /* Điều chỉnh vị trí theo chiều dọc và dịch lên một ít */
                right: 0px; /* Điều chỉnh vị trí theo chiều ngang và dịch qua trái một ít */
                font-size: 12px; /* Điều chỉnh kích thước chữ */
                line-height: 20px; /* Điều chỉnh chiều cao dòng để căn giữa chữ */
                display: flex; /* Sử dụng flexbox để căn giữa */
                justify-content: center; /* Căn giữa theo chiều ngang */
                align-items: center; /* Căn giữa theo chiều dọc */
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

                <li><a class="app-menu__item" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item active " href="management-lodging-houses?index=1"><i class='app-menu__icon bx bx-id-card'></i> <span
                            class="app-menu__label">Quản lí các khu trọ</span></a></li>
                <li>
                    <a id="notificationLink" href="list-notification-landlord" class="app-menu__item">
                        <i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Thông Báo Mới</span>
                        <span class="badge">${sessionScope.NumberOfNotification}</span> <!-- Example: Replace with dynamic content -->
                    </a>
                </li>


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
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
                            đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="container mt-5">
                <h1 class="text-center">Lodging Houses</h1>
                <div class="row search-form">
                    <div class="col-md-6">
                        <div class="input-group">
                            <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-primary  add-lodging-button" data-toggle="modal" data-target="#addModal"style="width:100%">
                            <a href="add-lodging-house" style="width:100%;color:white">Thêm nhà trọ mới</a>
                        </button>
                    </div>
                </div>

                <div class="row date-range-form" style="margin-bottom: 10px">
                    <div class="col-md-6">
                        <label for="dateMin">Thời gian bắt đầu</label>
                        <input oninput="searchByDateRange()" type="date" id="dateMin" name="dateHoaDon" class="form-control mb-0" style="width:100%">
                    </div>
                    <div class="col-md-6"">
                        <label for="dateMax">Thời gian kết thúc</label>
                        <input oninput="searchByDateRange()" type="date" id="dateMax" name="dateHoaDon" class="form-control mb-0" style="width:100%">
                    </div></div>


                <div class="row date-range-form" style="margin-bottom: 10px">

                    <div class="col-md-6">
                        <button class="btn btn-primary  add-lodging-button" data-toggle="modal" data-target="#addModal"style="width:100%">
                            <a href="add-new-manager-house" style="width:100%;color:white">Thêm người quản lí</a>
                        </button>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-primary  add-lodging-button" data-toggle="modal" data-target="#addModal"style="width:100%">
                            <a href="management-manager-lodging-house" style="width:100%;color:white">Danh sách người quản lí</a>
                        </button>  
                    </div>
                </div>
                <div class="shop_toolbar mb-35">

                    <div class="list_button">
                        <ul class="nav" role="tablist">
                            <li>
                                <a class="active" href="#" onclick="navigateToIndex(1)" role="tab" aria-controls="large" aria-selected="true"><i class="fa fa-th-large"></i></a>
                            </li>
                            <li>
                                <a href="#" onclick="navigateTo('management-lodging-houses', 'post')" role="tab" aria-controls="list" aria-selected="false"><i class="fa fa-th-list"></i></a>
                            </li>
                        </ul>
                    </div>


                </div>
                <div class="container">

                    <div id="content" >
                        <div class="row">
                            <c:forEach items="${list}" var="lodgingHouse">
                                <c:if test="${lodgingHouse.isStatusDelete() == true}">
                                    <div class="col-md-4 mb-4" >
                                        <div class="card card-house">
                                            <div class="card-body">
                                                <a href="home-manager?LodgingHouseID=${lodgingHouse.getLodgingHouseId()}">
                                                    <i class="fas fa-home house-icon"></i>
                                                </a>
                                                <h5 class="card-title mt-2">${lodgingHouse.getNameLodgingHouse()}</h5>
                                                <p class="card-text">Địa chỉ: ${lodgingHouse.getProvince()}-${lodgingHouse.getDistrict()}-${lodgingHouse.getWard()}</p>
                                                <a class="btn btn-warning btn-sm" href="/ManageLodgingHouse/update-info-lodging-house?lodgingHouseID=${lodgingHouse.getLodgingHouseId()}">Sửa</a>
                                                <a href="#" class="btn btn-info btn-sm view-detail" data-toggle="modal" data-target="#detailModal"
                                                   data-name="${lodgingHouse.getNameLodgingHouse().toUpperCase()}"
                                                   data-province="${lodgingHouse.getProvince()}"
                                                   data-district="${lodgingHouse.getDistrict()}"
                                                   data-ward="${lodgingHouse.getWard()}"
                                                   data-room="${lodgingHouse.getNumberOfRoom()}"
                                                   data-status="${lodgingHouse.isStatusDelete() ? 'Còn phòng ' : 'Hết phòng'}"
                                                   data-img="${lodgingHouse.getImg()}">View Detail</a>
                                                <form action="/ManageLodgingHouse/delete-lodging-house" method="post" style="display:inline;">
                                                    <input type="hidden" name="lodgingHouseId" value="${lodgingHouse.getLodgingHouseId()}">
                                                    <button class="btn btn-primary btn-sm trash " type="submit" title="Xóa">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </button>
                                                </form>

                                            </div>
                                        </div>
                                    </div>     
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="pagination_style shop_page" id ="padging">                        
                            <div class="page_number">
                                <span style="font-size: 20px">Pages: </span>
                                <c:if test="${tag > 1}">
                                    <a href="management-lodging-houses?index=${tag - 1}" style="padding: 5px 10px; margin-right: 10px; font-size: 20px;">Previous</a>
                                </c:if>

                                <c:forEach begin="1" end="${endPage}" var="i">
                                    <c:choose>
                                        <c:when test="${tag == i}">
                                            <a class="pageIndex" href="management-lodging-houses?index=${i}" style="background-color: green; color: white; border: 1px solid greenyellow; width: 40px; height: 40px; display: inline-flex; justify-content: center; align-items: center; margin-right: 10px; font-size: 20px;">${i}</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="management-lodging-houses?index=${i}" style="padding: 5px 10px; margin-right: 10px; font-size: 20px;">${i}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                                <c:if test="${tag < endPage}">
                                    <a href="management-lodging-houses?index=${tag + 1}" style="padding: 5px 10px; margin-right: 10px; font-size: 20px;">Next</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

    <c:if test="${not empty alertMessage}">
        <script>
            // Display alert using JavaScript
            alert("${alertMessage}");
        </script>
    </c:if>

        </main>
        <div id="detailModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="detailModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="detailModalLabel">Lodging House Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <img id="modalImage" src="" alt="Lodging House Image" class="img-fluid mb-3" style="max-width:50%; height: 50%;">
                        <p><strong>Tên nhà trọ:</strong> <span id="modalName"></span></p>
                        <p><strong>Thành phố/Tỉnh:</strong> <span id="modalProvince"></span></p>
                        <p><strong>Quận/Huyện:</strong> <span id="modalDistrict"></span></p>
                        <p><strong>Phường/Xã:</strong> <span id="modalWard"></span></p>
                        <p><strong>Số luọng phòng:</strong> <span id="modalRooms"></span></p>
                        <p><strong>Trạng thái phòng:</strong> <span id="modalStatus"></span></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>




        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/doc/js/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/main.js"></script>
        <!--===============================================================================================-->
        <script src="${pageContext.request.contextPath}/Resource/doc/js/plugins/pace.min.js"></script>
        <!--===============================================================================================-->
        <script type="text/javascript" src="${pageContext.request.contextPath}/Resource/doc/js/plugins/chart.js"></script>
        <!--===============================================================================================-->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                                    function navigateToIndex(index) {
                                        window.location.href = 'management-lodging-houses?index=' + index;
                                    }
        </script>
        <script>
            function navigateTo(url, method) {
                var form = document.createElement('form');
                form.method = method;
                form.action = url;
                document.body.appendChild(form);
                form.submit();
            }
        </script>
        <script>  function searchByKeyword(param, targetElementId) {
                var searchInfo = param.value.trim();
                $.ajax({
                    url: "/ManageLodgingHouse/search-lodging-houses",
                    type: "get", // Gửi qua phương thức GET
                    data: {
                        keyword: searchInfo
                    },
                    success: function (responseData) {
                        document.getElementById(targetElementId).innerHTML = responseData;
                    }
                });
            }
            ;

            document.addEventListener("DOMContentLoaded", function () {
                var searchInput = document.querySelector('input[type="search"]');
                searchInput.addEventListener('input', function () {
                    searchByKeyword(this, "content");
                });
            });
        </script>
        <script>

            function searchByDateRange() {
                var dateMin = document.getElementById("dateMin").value;
                var dateMax = document.getElementById("dateMax").value;
                $.ajax({
                    url: "/ManageLodgingHouse/search-lodging-houses",
                    type: "post",
                    data: {
                        dateMin: dateMin,
                        dateMax: dateMax
                    },
                    success: function (data) {
                        var row = document.getElementById("content");
                        row.innerHTML = data;
                    },
                    error: function (xhr) {
                        // Do something to handle error
                        console.error("Error occurred: ", xhr);
                    }
                });
            }

            $(document).ready(function () {
                $(document).on('click', '.pagination-btn', function () {
                    var page = $(this).data('page');
                    sendRequest(page);
                });
            });

            function sendRequest(page) {
                $.ajax({
                    url: 'search-lodging-houses', // Đường dẫn đến Servlet của bạn
                    type: 'GET', // Loại yêu cầu
                    data: {curentPage: page}, // Dữ liệu gửi đi (trang hiện tại)
                    success: function (response) {
                        // Xử lý phản hồi từ máy chủ
                        // Ví dụ: cập nhật nội dung trang với phản hồi từ máy chủ
                        $('#content').html(response);
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi nếu có
                        console.error('Lỗi AJAX: ' + status + ' ' + error);
                    }
                });
            }

        </script>

        <script>
            $(document).ready(function () {
                // Attach click event handler to the view detail buttons
                $('.view-detail').click(function () {
                    var name = $(this).data('name');
                    var province = $(this).data('province');
                    var district = $(this).data('district');
                    var ward = $(this).data('ward');
                    var rooms = $(this).data('room');
                    var status = $(this).data('status');
                    var img = $(this).data('img');

                    // Set the modal content
                    $('#modalName').text(name);
                    $('#modalProvince').text(province);
                    $('#modalDistrict').text(district);
                    $('#modalWard').text(ward);
                    $('#modalRooms').text(rooms);
                    $('#modalStatus').text(status);
                    $('#modalImage').attr('src', img);

                    // Show the modal
                    $('#detailModal').modal('show');
                });
            });
        </script>
        <script>

            document.addEventListener("DOMContentLoaded", function () {
                const urlParams = new URLSearchParams(window.location.search);
                const deleteSuccess = urlParams.get('deleteSuccess');

                if (deleteSuccess === 'true') {
                    Swal.fire({
                        title: "Success",
                        text: "Đã xóa thành công!",
                        icon: "success"
                    });
                } else if (deleteSuccess === 'false') {
                    Swal.fire({
                        title: "Error",
                        text: "Xóa thất bại!\n\
        Vẫn còn phòng đang còn hạn sử dụng",
                        icon: "error"
                    });
                }

                // Remove the query parameter from the URL
                if (deleteSuccess !== null) {
                    const newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
                    window.history.replaceState({}, document.title, newUrl);
                }
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <script>
            jQuery(function () {
                jQuery(".trash").click(function (event) {
                    event.preventDefault(); // Prevent the default form submission
                    var button = this; // Reference to the button clicked
                    var form = $(button).closest('form'); // Reference to the closest form

                    Swal.fire({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn là muốn xóa nhà trọ?",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonText: "Đồng ý",
                        cancelButtonText: "Hủy bỏ"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            form.submit(); // Submit the form if the user confirms
                        }
                    });
                });
            });
        </script>

    </body>

</html>
