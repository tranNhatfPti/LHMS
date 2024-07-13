<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Currency" %>
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


        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <style>
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
            .highlight {
                background-color: yellow;
                transition: background-color 2s ease-out;
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
                <li><a class="app-nav__item" href="/index.html"><i class='bx bx-log-out bx-rotate-180'></i> </a>

                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user" ><img class="app-sidebar__user-avatar" src="" width="100px"
                                                 alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b></b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <c:if test="${sessionScope.account.getRoleId() == 2 || sessionScope.account.getRoleId() == 1}">
                <div class="col-sm-2">
                    <%@include file="left-menu-manager.jsp" %>
                </div>
                <c:if test="${sessionScope.account.getRoleId() == 1}">
                    <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-role-of-staff"><i class='app-menu__icon bx bx-id-card'></i>
                            <span class="app-menu__label">Quản lý chức vụ</span></a></li>
                        </c:if>
                    </c:if>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="list-staff"><b>Danh sách nhân viên</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
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
                                <div class="input-group col-sm-7">
                                    <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" />
                                </div>

                            </div>
                            <div id="content">
                                <table class="table table-hover table-bordered" id="sampleTable">
                                    <thead>
                                        <tr>
                                            <th width="10"><input type="checkbox" id="all"></th>
                                            <th>Tên nhân viên</th>
                                            <th>Chức vụ</th>
                                            <th>Số điện thoại</th>
                                            <th>Email</th>
                                            <th>Địa chỉ</th>
                                            <th>Lương</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${sessionScope.pagination.getListObject()}" var="list">
                                            <tr class="${list.staffID == newStaffId ? 'highlight' : ''}" id="staff-${list.staffID}">
                                                <td width="10"><input type="checkbox" name="staffIds" class="staffCheckbox" value="${list.getStaffID()}"></td>
                                                <td>${list.getNameStaff()}</td>
                                                <c:if test="${list.roleStaffID.statusActive == 1}">
                                                    <td>${list.roleStaffID.roleStaffName}</td>
                                                </c:if>
                                                <c:if test="${list.roleStaffID == null}">
                                                    <td style="color: red"><small>Chức vụ này hiện không tồn tại!</small></td>
                                                </c:if>
                                                <td>${list.phoneNumber}</td>
                                                <td>${list.email}</td>
                                                <td>${list.addressDetail}, ${list.ward}, ${list.district}, ${list.province}</td>
                                                <fmt:setLocale value="vi_VN" />
                                                <td><fmt:formatNumber value="${list.salary}" type="number" pattern="#,##0"/> </td>
                                                <c:if test="${sessionScope.account.getRoleId() == 2}">
                                                    <td>
                                                        <button class="btn btn-primary btn-sm trash" type="button" title="Xóa" onclick="myFunction('${list.staffID}')">
                                                            <i class="fas fa-trash-alt"></i>
                                                        </button>
                                                        <a class="btn btn-primary btn-sm edit" title="Sửa" href="load-data-staff?staffID=${list.getStaffID()}">
                                                            <i class="fas fa-edit"></i>
                                                        </a>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                                <div class="pagination">
                                    <c:set var="currentPage" value="${sessionScope.pagination.getCurentPage()}"/>

                                    <c:if test="${currentPage > 1}">
                                        <a href="list-staff?curentPage=${currentPage - 1}" >&laquo;</a>
                                    </c:if>

                                    <c:forEach  begin="${sessionScope.pagination.getStart()}" end="${sessionScope.pagination.getEnd()}" var="num">   
                                        <c:if test="${num != 0}">
                                            <c:if test="${num == currentPage}">
                                                <a href="list-staff?curentPage=${num}"  class="active">${num}</a>
                                            </c:if>
                                            <c:if test="${num != currentPage}">
                                                <a href="list-staff?curentPage=${num}" >${num}</a>
                                            </c:if>   
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${sessionScope.pagination.getNumberOfPage() > currentPage}">
                                        <a href="list-staff?curentPage=${currentPage + 1}">&raquo;</a>
                                    </c:if>
                                </div>
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!-- Essential javascripts for application to work-->
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
        <script type="text/javascript">
                                                            $('#sampleTable').DataTable();
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
            function myFunction(id) {

                swal({
                    title: "Cảnh báo",
                    text: "Bạn có chắc chắn muốn xóa nhân viên này?",
                    icon: "warning",
                    buttons: ["Hủy bỏ", "Đồng ý"],
                })
                        .then((willDelete) => {
                            if (willDelete) {

                                window.location.href = "delete-staff?staffID=" + id;
                                swal("Đã xóa thành công.!", {
                                    title: "Đã xóa thành công!",
                                    icon: "success",
                                    timer: 10000, // Hiển thị trong 2 giây
                                    buttons: false // Ẩn các nút
                                });
                            }
                        });
            }
            jQuery(function () {
                jQuery(".trash").click(function () {

                });
            });
            function deleteRow(r) {
                var i = r.parentNode.parentNode.rowIndex;
                document.getElementById("myTable").deleteRow(i);
            }
            oTable = $('#sampleTable').dataTable();
            $('#all').click(function (e) {
                $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                e.stopImmediatePropagation();
            });
        </script>
        <script>
            function deleteSelectedStaff() {
                var checkboxes = document.querySelectorAll('input[name="staffIds"]:checked');
                var staffIds = [];

                checkboxes.forEach(function (checkbox) {
                    staffIds.push(checkbox.value);
                });

                if (staffIds.length > 0) {
                    var form = document.createElement('form');
                    form.method = 'POST';
                    form.action = 'delete-staff';

                    staffIds.forEach(function (id) {
                        var input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = 'staffIds';
                        input.value = id;
                        form.appendChild(input);
                    });

                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn muốn xóa?",
                        icon: "warning",
                        buttons: ["Hủy bỏ", "Đồng ý"]
                    })
                            .then((willDelete) => {
                                if (willDelete) {
                                    document.body.appendChild(form);
                                    form.submit();
                                }
                            });
                } else {
                    swal({
                        icon: "warning",
                        title: "Cảnh báo!",
                        text: "Vui lòng chọn ít nhất một nhân viên để xóa.",
                        button: "Close"
                    });
                }
            }
            // Delete all when click to checkbox in head-title of table
            document.getElementById('all').addEventListener('change', function () {
                var checkboxes = document.querySelectorAll('.staffCheckbox');
                for (var checkbox of checkboxes) {
                    checkbox.checked = this.checked;
                }
            });

            $(document).ready(function () {
                // Khi checkbox "all" thay đổi
                $(document).on('change', '#all', function () {
                    var checked = $(this).is(':checked');
                    $("input[name='staffIds']").prop('checked', checked);
                });
                // Khi bất kỳ checkbox con nào thay đổi
                $(document).on('change', "input[name='staffIds']", function () {
                    var anyUnchecked = $("input[name='staffIds']").filter(function () {
                        return !$(this).is(':checked');
                    }).length > 0;
                    $("#all").prop('checked', !anyUnchecked);
                });
            });

        </script>
        <script>
            var searchKeyword = ''; // Biến toàn cục để lưu trữ giá trị tìm kiếm

            $(document).ready(function () {
                $(document).on('click', '.pagination-btn', function () {
                    var page = $(this).data('page');
                    sendRequest(page, searchKeyword);
                });
            });

            function sendRequest(page, keyword) {
                $.ajax({
                    url: 'search-staff',
                    type: 'GET',
                    data: {
                        curentPage: page,
                        keyword: keyword
                    },
                    success: function (response) {
                        // Xóa phân trang cũ và cập nhật nội dung mới
                        $('#content').html(response);
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi nếu có
                        console.error('Lỗi AJAX: ' + status + ' ' + error);
                    }
                });
            }

            function searchByKeyword(param) {
                var searchInfo = param.value.trim();
                searchKeyword = searchInfo; // Cập nhật biến toàn cục

                var url = "/ManageLodgingHouse/search-staff";
                var targetElementId = "content";

                // Nếu độ dài của chuỗi sau khi loại bỏ khoảng trắng là 0 hoặc không có kết quả từ servlet, thì hiển thị kết quả trên bảng sampleTable
                if (searchInfo.length === 0) {
                    targetElementId = "sampleTable";
                }

                $.ajax({
                    url: url,
                    type: "get", // Gửi qua phương thức GET
                    data: {
                        keyword: searchInfo
                    },
                    success: function (responseData) {
                        document.getElementById(targetElementId).innerHTML = responseData;
                        // Cập nhật biến hasData dựa trên có dữ liệu mới hay không
                        hasData = responseData.trim() !== '';
                        // Nếu không có dữ liệu trả về, thì kích hoạt plugin DataTable trên bảng sampleTable
                        if (!hasData && targetElementId === "sampleTable") {
                            $('#sampleTable').DataTable();
                        }
                    }
                });
            }

            document.addEventListener("DOMContentLoaded", function () {
                var searchInput = document.querySelector('input[type="search"]');
                searchInput.addEventListener('input', function () {
                    searchByKeyword(this);
                });
            });

        </script>
        <script>
            $(document).ready(function () {
                $(document).on('click', '.pagination-btn', function () {
                    var page = $(this).data('page');
                    sendRequest(page);
                });
            });

            function sendRequest(page) {
                $.ajax({
                    url: 'search-staff',
                    type: 'GET',
                    data: {curentPage: page},
                    success: function (response) {
                        // Xóa phân trang cũ và cập nhật nội dung mới
                        $('#content').html(response);
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi nếu có
                        console.error('Lỗi AJAX: ' + status + ' ' + error);
                    }
                });
            }
        </script>
    </body>

</html>