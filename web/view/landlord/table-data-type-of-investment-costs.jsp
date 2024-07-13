<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh sách loại chi phí đầu tư</title>
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
            .pagination {
                display: inline-block;
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
            <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="/images/hay.jpg" width="50px"
                                                alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b>Võ Trường</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item active" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Chỉ số nước</span></a></li>             
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/type-of-investment-costs-servlet"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý loại phí đầu tư</span></a></li>           
                <li><a class="app-menu__item" href="account?service=showProfile"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Thông tin cá nhân</span></a>
                </li> 
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách loại phí đầu tư</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <form id="deleteForm" method="post" action="delete-type-of-investment-costs-servlet">
                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">

                                <div class="row element-button">
                                    <div class="col-sm-2">

                                        <a class="btn btn-add btn-sm" href="add-type-of-investment-cost-servlet" title="Thêm"><i class="fas fa-plus"></i>
                                            Tạo mới loại phí đầu tư</a>
                                    </div>
                                    <div class="col-sm-3">

                                        <button class="btn btn-delete btn-sm" style="color: black" type="button" title="Xóa" onclick="submitDeleteForm()">
                                            <i class="fas fa-trash-alt"></i> Xóa tất cả
                                        </button>
                                    </div>

                                    <div class="input-group col-sm-5">
                                        <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" style="padding:0;"/>
                                    </div>
                                </div>


                                <div id="content">

                                    <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                                           id="sampleTable">
                                        <thead>
                                            <tr>
                                                <th width="10"><input type="checkbox" value="0" id="all" name="delete1"></th>
                                                <!--                                                <th>ID  phí đầu tư</th>-->
                                                <th width="150">Tên phí đầu tư</th>
                                                <th width="100">Tính năng</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${sessionScope.paginationTypeOfInvestmentCost.getListObject()}" var="o">
                                                <tr>
                                                    <td width="10"><input type="checkbox" name="delete" id="${o.getId()}" value="${o.getId()}"></td>
<!--                                                    <td style="width: 20px">${o.getId()}</td>-->
                                                    <td>${o.getName()}</td>        
                                                    <td class="table-td-center" >
                                                        <button class="btn btn-primary btn-sm trash" type="button" title="Xóa" data-id="${o.getId()}">
                                                            <i class="fas fa-trash-alt"></i>
                                                        </button>
                                                        <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" onclick="editRowById(${o.getId()})">
                                                            <i class="fas fa-edit"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>

                                    </table>

                                    <div class="pagination">
                                        <c:set var="currentPage" value="${sessionScope.paginationTypeOfInvestmentCost.getCurentPage()}"/>
                                        <c:if test="${currentPage > 1}">
                                            <a href="type-of-investment-costs-servlet?curentPage=${currentPage - 1}" >&laquo;</a>
                                        </c:if>
                                        <c:forEach  begin="${sessionScope.paginationTypeOfInvestmentCost.getStart()}" end="${sessionScope.paginationTypeOfInvestmentCost.getEnd()}" var="num">   
                                            <c:if test="${num != 0}">
                                                <c:if test="${num == currentPage}">
                                                    <a href="type-of-investment-costs-servlet?curentPage=${num}"  class="active">${num}</a>
                                                </c:if>
                                                <c:if test="${num != currentPage}">
                                                    <a href="type-of-investment-costs-servlet?curentPage=${num}" >${num}</a>
                                                </c:if>   
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${sessionScope.paginationTypeOfInvestmentCost.getNumberOfPage() > currentPage}">
                                            <a href="type-of-investment-costs-servlet?curentPage=${currentPage + 1}">&raquo;</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div> 
                        </div>
                    </div>
                </div>
            </form>
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
        <!--        <script type="text/javascript">$('#sampleTable').DataTable();</script>-->

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
                                                            $(document).ready(function () {
                                                                $(document).on('click', '.pagination-btn', function () {
                                                                    var page = $(this).data('page');
                                                                    sendRequest(page);
                                                                });
                                                            });

                                                            function sendRequest(page) {
                                                                $.ajax({
                                                                    url: 'search-type-of-investment-costs-servlet', // Đường dẫn đến Servlet của bạn
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
                // Khi checkbox "all" thay đổi
                $(document).on('change', '#all', function () {
                    var checked = $(this).is(':checked');
                    $("input[name='delete']").prop('checked', checked);
                });
                // Khi bất kỳ checkbox con nào thay đổi
                $(document).on('change', "input[name='delete']", function () {
                    var anyUnchecked = $("input[name='delete']").filter(function () {
                        return !$(this).is(':checked');
                    }).length > 0;
                    $("#all").prop('checked', !anyUnchecked);
                });
            });


            function submitDeleteForm() {
                var checkboxes = document.querySelectorAll('input[name="delete"]');
                var ids = [];
                checkboxes.forEach(function (checkbox) {
                    if (checkbox.checked) {
                        ids.push(checkbox.value);
                    }
                });
                if (ids.length > 0) {
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn muốn xóa những khoản phí đầu tư đã chọn?\n\
                        Nếu xóa, tất cả những chi phí đầu tư sẽ thuộc loại phí đầu tư này sẽ bị bỏ trống!",
                        buttons: ["Hủy bỏ", "Đồng ý"],
                    }).then((willDelete) => {
                        if (willDelete) {
                            var idsString = ids.join(",");
                            $.ajax({
                                type: "POST",
                                url: "/ManageLodgingHouse/delete-type-of-investment-costs-servlet",
                                data: {
                                    ids: idsString
                                }, // Gửi danh sách các ID sang servlet
                                success: function (response) {
                                    swal(response).then(() => {
                                        window.location = "type-of-investment-costs-servlet";
                                    });
                                },
                                error: function (xhr, status, error) {
                                    swal("Đã xảy ra lỗi!", "Vui lòng thử lại sau.", "error");
                                }
                            });
                        }
                    });
                } else {
                    swal("Thông báo", "Vui lòng chọn ít nhất một khoản phí đầu tư để xóa.", "warning");
                }
            }


            function searchByKeyword(param, targetElementId) {
                var searchInfo = param.value.trim();
                $.ajax({
                    url: "/ManageLodgingHouse/search-type-of-investment-costs-servlet",
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


            function deleteRowById(id) {
                swal({
                    title: "Cảnh báo",
                    text: "Bạn có chắc chắn là muốn xóa loại phí đầu tư này? \n\
               Nếu xóa, tất cả loại chi phí đầu tư thuộc loại này trong tháng\n\
               sẽ bị sửa đổi!",
                    buttons: ["Hủy bỏ", "Đồng ý"],
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                swal("Đã xóa thành công.!", {});
                                window.location = "delete-type-of-investment-costs-servlet?id=" + id;
                            }
                        });
            }

            jQuery(function () {
                jQuery(document).on('click', '.trash', function () {
                    var id = jQuery(this).attr('data-id');
                    deleteRowById(id);
                });
            });
            oTable = $('#sampleTable').dataTable();
            $('#all').click(function (e) {
                $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                e.stopImmediatePropagation();
            });


            function editRowById(id) {
                if (id) {
                    // Chuyển trang
                    window.location.href = "update-type-of-investment-costs-servlet?id=" + id;
                } else {
                    console.log("ID is undefined or null.");
                }
            }

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
            //In dữ liệu
            var myApp = new function () {
                this.printTable = function () {
                    var tab = document.getElementById('sampleTable');
                    var win = window.open('', '', 'height=700,width=700');
                    win.document.write(tab.outerHTML);
                    win.document.close();
                    win.print();
                }
            }


            //Modal
            $("#show-emp").on("click", function () {
                $("#ModalUP").modal({backdrop: false, keyboard: false})
            }
            );
        </script>
    </body>

</html>