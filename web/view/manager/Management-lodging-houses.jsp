<%-- 
    Document   : index-demo
    Created on : May 15, 2024, 4:17:03 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="jakarta.servlet.ServletContext, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession" %>
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

        <style>
            .house-icon {
                font-size: 50px;
                color: #007bff;
            }
            .card-house {
                text-align: center;
            }
            .card-house .card-body {
                padding: 1rem;
            }
            .card-house {
                height: 100%;
                display: flex;
                flex-direction: column;
            }

            .card-body {
                flex: 1;
                display: flex;
                flex-direction: column;
            }

            .card-title {
                flex-grow: 1;
            }

            .card-house .btn {
                margin-top: auto;
            }/* CSS để chỉnh kích thước ảnh trong modal */
            #detailModal .modal-body #modalImage {
                max-width: 100%;
                max-height: 100vh; /* Đảm bảo ảnh không vượt quá chiều cao của màn hình */
                width: auto; /* Đảm bảo ảnh tự động điều chỉnh chiều rộng để giữ tỷ lệ */
                height: auto; /* Đảm bảo ảnh tự động điều chỉnh chiều cao để giữ tỷ lệ */
                object-fit: contain; /* Hiển thị ảnh trong phạm vi kích thước của phần tử */
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

            .search-form {
                max-width: 600px;
                margin: auto;
                padding: 10px;
            }
            .search-input {
                padding: 0;
                border-top-right-radius: 0;
                border-bottom-right-radius: 0;
            }
            .search-button {
                border-top-left-radius: 0;
                border-bottom-left-radius: 0;
            }
            .search-form {
                padding: 10px;
            }
            .search-input {
                padding: 0;
                border-top-right-radius: 0;
                border-bottom-right-radius: 0;
            }
            .search-button {
                border-top-left-radius: 0;
                border-bottom-left-radius: 0;
            }
            .add-lodging-button a {
                color: white;
                text-decoration: none;
            }
            .date-range-form {
                margin-bottom: 10px;
            }
            .date-range-label {
                margin-bottom: 5px;
            }
            .date-range-input {
                width: 100%;
            }

            .cards .card {
                cursor: pointer;
                transition: 400ms;
            }

            .cards .card:hover {
                transform: scale(1.1, 1.1);
            }

            .cards:hover .card:not(:hover) {
                filter: blur(10px);
                transform: scale(0.9, 0.9);
            }

            .delete-button {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                background-color: rgb(20, 20, 20);
                border: none;
                font-weight: 600;
                display: flex;
                align-items: center;
                justify-content: center;
                box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.164);
                cursor: pointer;
                transition-duration: 0.3s;
                overflow: hidden;
                position: relative;
            }

            .delete-svgIcon {
                width: 15px;
                transition-duration: 0.3s;
            }

            .delete-svgIcon path {
                fill: white;
            }

            .delete-button:hover {
                width: 90px;
                border-radius: 50px;
                transition-duration: 0.3s;
                background-color: rgb(255, 69, 69);
                align-items: center;
            }

            .delete-button:hover .delete-svgIcon {
                width: 20px;
                transition-duration: 0.3s;
                transform: translateY(60%);
                -webkit-transform: rotate(360deg);
                -moz-transform: rotate(360deg);
                -o-transform: rotate(360deg);
                -ms-transform: rotate(360deg);
                transform: rotate(360deg);
            }

            .delete-button::before {
                display: none;
                content: "Delete";
                color: white;
                transition-duration: 0.3s;
                font-size: 2px;
            }

            .delete-button:hover::before {
                display: block;
                padding-right: 10px;
                font-size: 13px;
                opacity: 1;
                transform: translateY(0px);
                transition-duration: 0.3s;
            }

            .button2 {
                display: inline-block;
                transition: all 0.2s ease-in;
                position: relative;
                overflow: hidden;
                z-index: 1;
                color: #090909;
                cursor: pointer;
                font-size: 15px;
                border-radius: 0.5em;
                background: #e8e8e8;
                border: 1px solid #e8e8e8;
                box-shadow: 6px 6px 12px #c5c5c5, -6px -6px 12px #ffffff;
            }

            .button2:active {
                color: #666;
                box-shadow: inset 4px 4px 12px #c5c5c5, inset -4px -4px 12px #ffffff;
            }

            .button2:before {
                content: "";
                position: absolute;
                left: 50%;
                transform: translateX(-50%) scaleY(1) scaleX(1.25);
                top: 100%;
                width: 140%;
                height: 180%;
                background-color: rgba(0, 0, 0, 0.05);
                border-radius: 50%;
                display: block;
                transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
                z-index: -1;
            }

            .button2:after {
                content: "";
                position: absolute;
                left: 55%;
                transform: translateX(-50%) scaleY(1) scaleX(1.45);
                top: 180%;
                width: 160%;
                height: 190%;
                background-color: #17a2b8;
                border-radius: 50%;
                display: block;
                transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
                z-index: -1;
            }

            .button2:hover {
                color: #ffffff;
                border: 1px solid #17a2b8;
            }

            .button2:hover:before {
                top: -35%;
                background-color: #17a2b8;
                transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
            }

            .button2:hover:after {
                top: -45%;
                background-color: #17a2b8;
                transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
            }

        </style>
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <%
            HttpSession s = request.getSession();
            InformationOfUserDAO id = new InformationOfUserDAO();
            
            Account account = (Account) s.getAttribute("account");
            int accountId = account.getAccountID();
            InformationOfUser informationOfUser = id.getInformationByAccountID(accountId);
        %>
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
            <div class="app-sidebar__user">
                <%
                    if(informationOfUser.getAvatar() != null){
                %>
                <img class="app-sidebar__user-avatar" src="<%=informationOfUser.getAvatar()%>" width="100px" alt="User Image">
                <%        
                    } else {
                %>
                <img class="app-sidebar__user-avatar" src="${pageContext.request.contextPath}/Resource/images/avatar-default.jpg" width="100px" alt="User Image">
                <%
                    }
                %>              
                <div>
                    <p class="app-sidebar__user-name"><b><%=informationOfUser.getFullName()!=null?informationOfUser.getFullName():""%></b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item active" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item " href="management-lodging-houses?index=1"><i class='app-menu__icon bx bx-id-card'></i> <span class="app-menu__label">Quản lí các khu trọ</span></a></li>
                <li><a class="app-menu__item " href="table-data-table.html"><i class='app-menu__icon bx bx-id-card'></i> <span class="app-menu__label">Dịch vụ</span></a></li>
                <li><a class="app-menu__item" href="quan-ly-bao-cao.html"><i class='app-menu__icon bx bx-pie-chart-alt-2'></i><span class="app-menu__label">Báo cáo doanh thu</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="row">
                <div class="col-md-12">
                    <div class="app-title">
                        <ul class="app-breadcrumb breadcrumb">
                            <li class="breadcrumb-item"><a href="#"><b>Quản lí nhà trọ</b></a></li>
                        </ul>
                        <div id="clock"></div>
                    </div>
                </div>
            </div>       
            <div class="container mt-5">
                <h1 class="text-center">Quản lí nhà trọ</h1>
                <div class="row search-form">
                    <div class="col-md-7">
                        <div class="input-group col-sm-6">
                            <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" style="padding:0;"/>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <button class="btn btn-primary mb-3 add-lodging-button" data-toggle="modal" data-target="#addModal">
                            <a href="add-lodging-house">Thêm nhà trọ mới</a>
                        </button>
                    </div>
                </div>

                <div class="row date-range-form">
                    <div class="col-md-6">
                        <label for="dateMin">Thời gian bắt đầu</label>
                        <input oninput="searchByDateRange()" type="date" id="dateMin" name="dateHoaDon" class="form-control mb-0" style="width:60%">
                    </div>
                    <div class="col-md-6"">
                        <label for="dateMax">Thời gian kết thúc</label>
                        <input oninput="searchByDateRange()" type="date" id="dateMax" name="dateHoaDon" class="form-control mb-0" style="width:60%">
                    </div></div>
            </div>
            <div class="container">
                <div class="row cards"id="content" style="margin-top: 50px; margin-bottom: 40px">
                    <c:forEach items="${list}" var="lodgingHouse">
                        <c:if test="${lodgingHouse.isStatusDelete() == true}">
                            <div class="col-md-4 mb-4" >
                                <div class="card card-house" style="border-radius: 10px; box-shadow: inset 0 0em 0em rgb(0 0 0 / 10%), 0 0 0 2px rgb(219 219 219), 0.3em 0.3em 1em rgb(0 0 0 / 50%);">
                                    <div class="card-body">
                                        <a href="home-manager?LodgingHouseID=${lodgingHouse.getLodgingHouseId()}">
                                            <i class="fas fa-home house-icon"></i>
                                        </a>
                                        <h5 class="card-title mt-2" style="font-family: math; color: crimson">TRỌ: ${lodgingHouse.getNameLodgingHouse()}</h5>
                                        <p class="card-text" style="font-family: math; color: darkblue">ĐỊA CHỈ: ${lodgingHouse.getProvince()}-${lodgingHouse.getDistrict()}-${lodgingHouse.getWard()}</p>
                                        <button class="button2" style="margin: 8px"><a href="/ManageLodgingHouse/update-info-lodging-house?lodgingHouseID=${lodgingHouse.getLodgingHouseId()}">Sửa</a></button>   
                                        <button class="button2" style="margin: 8px"><a href="#" data-toggle="modal" data-target="#detailModal"
                                                                                       data-name="${lodgingHouse.getNameLodgingHouse().toUpperCase()}"
                                                                                       data-province="${lodgingHouse.getProvince()}"
                                                                                       data-district="${lodgingHouse.getDistrict()}"
                                                                                       data-ward="${lodgingHouse.getWard()}"
                                                                                       data-room="${lodgingHouse.getNumberOfRoom()}"
                                                                                       data-status="${lodgingHouse.isStatusDelete() ? 'Còn phòng ' : 'Hết phòng'}"
                                                                                       data-img="${lodgingHouse.getImg()}">View Detail</a></button>   
                                        <form action="/ManageLodgingHouse/delete-lodging-house" method="post" style="display: flex; justify-content: center; margin-top: 5px">
                                            <input type="hidden" name="lodgingHouseId" value="${lodgingHouse.getLodgingHouseId()}">
                                            <button class="delete-button trash" type="submit">
                                                <svg class="delete-svgIcon" viewBox="0 0 448 512">
                                                <path d="M135.2 17.7L128 32H32C14.3 32 0 46.3 0 64S14.3 96 32 96H416c17.7 0 32-14.3 32-32s-14.3-32-32-32H320l-7.2-14.3C307.4 6.8 296.3 0 284.2 
                                                      0H163.8c-12.1 0-23.2 6.8-28.6 17.7zM416 128H32L53.2 467c1.6 25.3 22.6 45 47.9 45H346.9c25.3 0 46.3-19.7 47.9-45L416 128z"></path>
                                                </svg>
                                            </button>
                                        </form>

                                    </div>
                                </div>
                            </div>     
                        </c:if>
                    </c:forEach>                    
                </div>
            </div>
        </div>
        <div class="pagination_style shop_page" id ="padging" style="display: flex; justify-content: center">                        
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
    =


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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
                    text: "Xóa thất bại!",
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
