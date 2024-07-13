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
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <style>
            .room-card {
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 20px;
                margin-bottom: 20px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .room-card h4 {
                margin-top: 0;
            }
            .room-card .btn {
                margin-right: 10px;
            }
            .btn-edit {
                background-color: #f0ad4e;
                color: white;
            }
            .btn-edit:hover {
                background-color: #ec971f;
                color: white;
            }
            .btn-delete {
                background-color: #d9534f;
                color: white;
            }
            .btn-delete:hover {
                background-color: #c9302c;
                color: white;
            }
            .btn-add {
                background-color: #5cb85c;
                color: white;
            }
            .btn-add:hover {
                background-color: #4cae4c;
                color: white;
            }

            .button1 {
                background: linear-gradient(140.14deg, #ec540e 15.05%, #d6361f 114.99%)
                    padding-box,
                    linear-gradient(142.51deg, #ff9465 8.65%, #af1905 88.82%) border-box;
                border-radius: 7px;
                border: 2px solid transparent;

                text-shadow: 1px 1px 1px #00000040;
                box-shadow: 8px 8px 20px 0px #45090059;

                padding: 10px 40px;
                line-height: 20px;
                cursor: pointer;
                transition: all 0.3s;
                color: white;
                font-size: 18px;
                font-weight: 500;
            }

            .button1:hover {
                box-shadow: none;
                opacity: 80%;
            }

            .button2 {
                background: linear-gradient(140.14deg, #8ae88d 15.05%, #3db83a 114.99%)
                    padding-box,
                    linear-gradient(142.51deg, #abffbb 8.65%, #2e8d2c 88.82%) border-box;
                border-radius: 7px;
                border: 2px solid transparent;
                text-shadow: 1px 1px 1px #00000040;
                box-shadow: 8px 8px 20px 0px #45090059;
                padding: 10px 40px;
                line-height: 20px;
                cursor: pointer;
                transition: all 0.3s;
                color: white;
                font-size: 18px;
                font-weight: 500;
            }


            .button2:hover {
                box-shadow: none;
                opacity: 80%;
            }

            .button3 {
                background: linear-gradient(140.14deg, #ff80bf 15.05%, #ff1493 114.99%)
                    padding-box,
                    linear-gradient(142.51deg, #ff99cc 8.65%, #ff69b4 88.82%) border-box;
                border-radius: 7px;
                border: 2px solid transparent;

                text-shadow: 1px 1px 1px #00000040;
                box-shadow: 8px 8px 20px 0px #45090059;

                padding: 10px 40px;
                line-height: 20px;
                cursor: pointer;
                transition: all 0.3s;
                color: white;
                font-size: 18px;
                font-weight: 500;
            }

            .button3:hover {
                box-shadow: none;
                opacity: 80%;
            }
            .button4 {
                background: linear-gradient(140.14deg, #ffaa80 15.05%, #ff7f00 114.99%)
                    padding-box,
                    linear-gradient(142.51deg, #ffc299 8.65%, #ff4500 88.82%) border-box;
                border-radius: 7px;
                border: 2px solid transparent;

                text-shadow: 1px 1px 1px #00000040;
                box-shadow: 8px 8px 20px 0px #45090059;

                padding: 10px 40px;
                line-height: 20px;
                cursor: pointer;
                transition: all 0.3s;
                color: white;
                font-size: 18px;
                font-weight: 500;
            }

            .button4:hover {
                box-shadow: none;
                opacity: 80%;
            }


            .button1,
            .button2,
            .button3,
            .button4 {
                padding: 6px 20px; /* Adjust padding to make buttons smaller */
                font-size: 14px; /* Adjust font size to make text smaller */
            }
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button-->
            <a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">
                <!-- User Menu-->
                <li><a class="app-nav__item" href="/index.html"><i class='bx bx-log-out bx-rotate-180'></i></a></li>
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
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <c:if test="${sessionScope.account.getRoleId() == 2}">
                                    <div class="col-sm-2">
                                        <a class="btn btn-add btn-sm" href="add-staff" title="Thêm nhân viên"><i class="fas fa-plus"></i>
                                            Thêm nhân viên mới</a>
                                    </div>
                                    <div class="col-sm-2">
                                        <a class="btn btn-delete btn-sm" type="button" title="Xóa" name="all" onclick="deleteSelectedStaff()"><i
                                                class="fas fa-trash-alt"></i> Xóa tất cả </a>
                                    </div>
                                </c:if>
                              <div class="row search-form">
                    <div class="col-md-12">
                        <div class="input-group">
                            <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon"/>
                        </div>
                    </div>
           
                    </div>
                </div>

                            </div>
                            <div id="content">
                                <table class="table table-hover table-bordered" id="sampleTable">
                                    <thead>
                                        <tr>
                                            <th>Email người quản lí</th>
                                            <th>Tên đăng nhập</th>
                                            <th>Tên nhà trọ</th>
                                            <th>Hành động </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="account" items="${requestScope.listAc}">
                                            <tr>
                                                <td>${account.getEmail()}</td>
                                                <td>${account.getEmail()}</td>
                                                <td>
                                                    <select>
                                                        <c:forEach var="lodgingHouse" items="${requestScope.listLodgingHouse}"> 
                                                            <c:if test="${account.getAccountID() == lodgingHouse.getManageId()}">
                                                                <option>${lodgingHouse.getNameLodgingHouse()}</option>
                                                            </c:if>          Account ID: ${account.getAccountID()} | Lodging House Manager ID: ${lodgingHouse.getManageId()}
                                                        </c:forEach>
                                                    </select>
                                                    <!-- Debug output -->
                                          
                                                </td>
                                                
                                                
                                                <td> <form action="/ManageLodgingHouse/delete-manager" method="post" style="display:inline;">
                                                        <input type="hidden" name="receipt" value="${lodgingHouse.getManageId()}">
                                                        <button class="btn btn-primary btn-sm trash " type="submit" title="Xóa">
                                                            <i class="fas fa-trash-alt"></i>
                                                        </button>
                                                    </form></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </main>
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
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
    </body>
</html>
