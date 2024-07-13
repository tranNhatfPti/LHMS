<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh sách phiếu thu | Quản trị Admin</title>
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
        <style>.non-clickable-button {
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
       /* CSS Styles for the Add Roommate Button */

    #senddata {
    background-color: #007bff;
    color: #fff;
    padding: 10px 20px;
    font-size: 16px; /* Thiếu dấu chấm phẩy ở đây */
    border: none;
    cursor: pointer;
    border-radius: 5px;
    transition: background-color 0.3s ease;
    
}

.tile {
        position: relative;
    }

    #senddata {
        position: absolute;
        top: 10px; /* Điều chỉnh khoảng cách từ trên xuống */
        right: 10px; /* Điều chỉnh khoảng cách từ phải sang trái */
        background-color: #007bff;
        color: #fff;
        padding: 10px 20px;
        font-size: 16px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        transition: background-color 0.3s ease;
    }

    #senddata:hover {
        background-color: #0056b3;
    }
        </style>
    </head>
   <c:if test="${not empty alertMessage}">
        <script>
            // Display alert using JavaScript
            alert("${alertMessage}");
        </script>
    </c:if>
    <body  class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">
                
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
      <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
                <aside class="app-sidebar">

                    <hr>
                    <ul class="app-menu">

                        <li><a class="app-menu__item ${tagMenu=="showRoom"?"active":""}" href="room-detail-for-tenant?id=${requestScope.roomByID}"><i class='app-menu__icon  bx bxs-home'></i><span
                                    class="app-menu__label">Trang chủ</span></a></li>
                        <li>
                        <li><a class="app-menu__item ${tagMenu=="showRoom"?"active":""}" href="room?service=showRoomInfor"><i class='app-menu__icon  bx bxs-home'></i><span
                                    class="app-menu__label">Thông Tin Cá nhân</span></a></li>
                        <li>
                            <a id="notificationLink " href="list-notification-for-tenant" class="app-menu__item ">
                                <i class='app-menu__icon bx bx-id-card'></i>
                                <span class="app-menu__label">Thông Báo Mới</span>
                                <span class="badge">${sessionScope.NumberOfNotification}</span> <!-- Example: Replace with dynamic content -->
                            </a>
                        </li>
                        <li><a class="app-menu__item ${tagMenu=="showProfile"?"active":""}" href="account?service=showProfile"><i class='app-menu__icon bx bx-id-card'></i><span
                                    class="app-menu__label">Hồ Sơ</span></a></li>
                        <li><a class="app-menu__item ${tagMenu=="changePassword"?"active":""}" href="account?service=showChangePassword"><i class='app-menu__icon bx bxs-lock '></i> <span
                                    class="app-menu__label">Mật Khẩu</span></a></li>
                        <li><a class="app-menu__item" href="account?service=logout"><i class='app-menu__icon bx bx-log-out'></i><span
                                    class="app-menu__label">Đăng Xuất</span></a></li>
                </aside>
        <main class="app-content">
             
            <div class="app-title">
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">

                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm nhap-tu-file" type="button" title="Nhập" onclick="myFunction(this)"><i
                                            class="fas fa-file-upload"></i> Tải từ file</a>
                                </div>


                            </div>

                            <div class="row search-form" style="margin-bottom: 10px">
                                <div class="col-md-6">
                                    <div class="input-group">
                                        <input id="search-input" type="search" class="form-control" placeholder="Search" aria-label="Search" aria-describedby="search-addon"/>
                                        <select id="search-type" class="form-control" aria-label="Search Type">
                                            <option value="description">Mô tả</option>
                                            <option value="account">Email</option>
                                            <option value="price">Số tiền</option>
                                            <!-- Thêm các tùy chọn khác nếu cần -->
                                        </select>
                                    </div>


                                </div>     
                                <div class="col-md-6">

                                    <select name="getproductbyselect" id="short" onchange="handleChange()"  class="form-control">
                                        <option selected="" value="All">All</option>
                                        <option value="Lowest">Tên: A-Z</option>
                                        <option value="Highest">Tên: Z-A</option>
                                        <option value="DateLowest">Date: Lowest</option>
                                        <option value="DaTeHighest">Date: Highest</option>
                                    </select>


                                </div>

                            </div><div class="row search-form" style="margin-bottom: 10px">
                                <div class="col-md-6">
                                    <label for="dateMin">Thời gian bắt đầu</label>
                                    <input oninput="searchByDateRange()" type="date" id="dateMin" name="dateHoaDon" class="form-control mb-0" style="width:100%">
                                </div>
                                <div class="col-md-6">
                                    <label for="dateMax">Thời gian kết thúc</label>
                                    <input oninput="searchByDateRange()" type="date" id="dateMax" name="dateHoaDon" class="form-control mb-0" style="width:100%">
                                </div>
                            </div>
          

                            <div  id="content">
               
                                <table class="table table-hover table-bordered">
                                    <thead>
                                        <tr>
                                            <th width="10"><input type="checkbox" id="selectAll"></th>
                                            <th>ID</th>
                                            <th>Email</th>
                                            <th>Tên đầy đủ</th>
                                            <th>Quê quán</th>
                                            <th>Ngày sinh</th>
                                            <th>Số điện thoại</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.pagination.getListObject()}" var="c">
                                            <tr>
                                                <td><input type="checkbox" class="rowCheckbox"></td>
                                                <td>${c.accountID}</td>
                                                <td>${c.email}</td>
                                                <td>${c.getFullName()}</td>   
                                                <td>${c.getProvince()} - ${c.getDistrict()} - ${c.getWard()}</td>
                                                <td>${c.getDob()}</td>
                                                <td>${c.getPhoneNumber()}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <div class="pagination">
                                    <c:set var="currentPage" value="${sessionScope.pagination.getCurentPage()}"/>
                                    <c:if test="${currentPage > 1}">
                                        <a href="add-roommate?curentPage=${currentPage - 1}" >&laquo;</a>
                                    </c:if>
                                    <c:forEach  begin="${sessionScope.pagination.getStart()}" end="${sessionScope.pagination.getEnd()}" var="num">   
                                        <c:if test="${num != 0}">
                                            <c:if test="${num == currentPage}">
                                                <a href="add-roommate?curentPage=${num}"  class="active">${num}</a>
                                            </c:if>
                                            <c:if test="${num != currentPage}">
                                                <a href="add-roommate?curentPage=${num}" >${num}</a>
                                            </c:if>   
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${sessionScope.pagination.getNumberOfPage() > currentPage}">
                                        <a href="add-roommate?curentPage=${currentPage + 1}">&raquo;</a>
                                    </c:if>
                                </div>
                              
                            </div>
                        </div>       <button id="senddata" onclick="sendDataToServlet()" style="    background-color: #007bff;
    color: #fff;
    padding: 10px 10px;
    font-size: 16px; /* Thiếu dấu chấm phẩy ở đây */
    border: none;
    cursor: pointer;
    border-radius: 5px;
    margin-left: 980px;
    transition: background-color 0.3s ease;">Thêm bạn phòng</button>
                    </div>
                </div>
            </div>
        </main>
                                    
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="src/jquery.table2excel.js"></script>
        <script src="js/main.js"></script>
        <!-- The javascript plugin to display page loading on top-->
        <script src="js/plugins/pace.min.js"></script>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<script>
    function sendDataToServlet() {
        // Array to store selected items
        var selectedData = [];

        // Get all checkboxes that are checked
        var checkboxes = document.querySelectorAll('input.rowCheckbox:checked');

        // Iterate over checked checkboxes and push their data into the array
        checkboxes.forEach(function(checkbox) {
            var rowData = {};
            rowData.accountID = checkbox.closest('tr').querySelector('td:nth-child(2)').textContent.trim(); // Assuming ID is in the second column
            // Add more fields as needed

            selectedData.push(rowData);
        });

        // Send the data to your Servlet using Ajax
        fetch('add-roommate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(selectedData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Handle success if needed
            console.log('Data sent successfully');
        })
        .catch(error => {
            // Handle error
            console.error('Error sending data:', error);
        });
    }
</script>


<script>
    $(document).ready(function() {
        $('#selectAll').change(function() {
            if ($(this).is(':checked')) {
                $('.rowCheckbox').prop('checked', true);
            } else {
                $('.rowCheckbox').prop('checked', false);
            }
        });
    });
</script>

    </body>

</html>