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
                <li><a class="app-nav__item" href="/index.html"><i class='bx bx-log-out bx-rotate-180'></i> </a>

                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user">
                <img class="app-sidebar__user-avatar" src="${requestScope.account.getAvartar()}" width="100px" alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b>${requestScope.account.fullName}</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">

                <li>
                    <a id="notificationLink" href="list-notification-for-manager" class="app-menu__item">
                        <i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Thông Báo Mới</span>
                        <span class="badge">${sessionScope.NumberOfNotification}</span> <!-- Example: Replace with dynamic content -->
                    </a>
                </li>
                <li><a class="app-menu__item " href="/ManageLodgingHouse/home-manager"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Chỉ số nước</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>

                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/investment-costs-servlet"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lí khoản phí đầu tư</span></a>
                </li> 
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách thu</b></a></li>
                    <li class="breadcrumb-item active"><a href="#"><b>Tổng số tiền:</b></a></li>

                </ul>
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

                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                            class="fas fa-print"></i> In dữ liệu</a>
                                </div>

                                <div class="col-sm-2">
                                    <a class="btn btn-excel btn-sm" href="" title="In"><i class="fas fa-file-excel"></i> Xuất Excel</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm pdf-file" type="button" title="In" onclick="myFunction(this)"><i
                                            class="fas fa-file-pdf"></i> Xuất PDF</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm" type="button" title="Xóa" onclick="myFunction(this)"><i
                                            class="fas fa-trash-alt"></i> Xóa tất cả </a>
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
                                        <option value="Lowest">Price: Lowest</option>
                                        <option value="Highest">Price: Highest</option>
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
                                            <th width="10"><input type="checkbox" id="all"></th>
                                            <th>ID</th>
                                            <th>Mô tả</th>
                                            <th>Số tiền</th>
                                            <th>Thời gian</th>
                                            <th>Email</th>
                                            <th>Trạng thái</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.pagination.getListObject()}" var="c">
                                            <tr>
                                                <td width="10"><input type="checkbox" name="check1" value="1"></td>
                                                <td>${c.getReceiptId()}</td>
                                                <td>${c.getDescription()}</td>
                                                <td>
                                                    <fmt:setLocale value="vi_VN" />
                                                    <fmt:formatNumber value="${c.getPrice()}" type="number" pattern="#,##0" />
                                                </td>
                                                <td>${c.getDateTime()}</td>
                                                <c:forEach items="${requestScope.listAccount}" var="acc">
                                                    <c:if test="${c.getAccountId()==acc.getAccountID()}">
                                                        <td>${acc.getEmail()}</td>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${c.isStatus()}">
                                                    <td>
                                                        <button class="non-clickable-button" style="background: greenyellow; color: white;">Đã Thanh Toán</button>
                                                    </td>
                                                </c:if>
                                                <c:if test="${!c.isStatus()}">
                                                    <td>
                                                        <button class="non-clickable-button" style="background: red; color: white;">Chưa Thanh Toán</button>
                                                    </td>
                                                </c:if>
                                                <td>
                                                    <form action="/ManageLodgingHouse/delete-receipt" method="post" style="display:inline;">
                                                        <input type="hidden" name="lodgingId" value="${c.getReceiptId()}">
                                                        <button class="btn btn-primary btn-sm trash " type="submit" title="Xóa">
                                                            <i class="fas fa-trash-alt"></i>
                                                        </button>
                                                    </form>
                                                    <a href="update-receipt?receiptId=${c.getReceiptId()}" class="button-like">Update</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div class="pagination">
                                    <c:set var="currentPage" value="${sessionScope.pagination.getCurentPage()}"/>
                                    <c:if test="${currentPage > 1}">
                                        <a href="manager-list-receipt?curentPage=${currentPage - 1}" >&laquo;</a>
                                    </c:if>
                                    <c:forEach  begin="${sessionScope.pagination.getStart()}" end="${sessionScope.pagination.getEnd()}" var="num">   
                                        <c:if test="${num != 0}">
                                            <c:if test="${num == currentPage}">
                                                <a href="manager-list-receipt?curentPage=${num}"  class="active">${num}</a>
                                            </c:if>
                                            <c:if test="${num != currentPage}">
                                                <a href="manager-list-receipt?curentPage=${num}" >${num}</a>
                                            </c:if>   
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${sessionScope.pagination.getNumberOfPage() > currentPage}">
                                        <a href="manager-list-receipt?curentPage=${currentPage + 1}">&raquo;</a>
                                    </c:if>
                                </div>

                            </div>
                        </div>
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
    // Hàm thực thi khi trang đã tải hoàn toàn
    $(document).ready(function () {
        // Lắng nghe sự kiện khi input search thay đổi
        $('#search-input').on('input', function () {
            performSearch(); // Gọi hàm tìm kiếm
        });

        // Lắng nghe sự kiện khi select search type thay đổi
        $('#search-type').on('change', function () {
            performSearch(); // Gọi hàm tìm kiếm
        });

        // Hàm thực hiện tìm kiếm dữ liệu
        function performSearch() {
            var searchText = $('#search-input').val(); // Lấy giá trị từ input search
            var searchType = $('#search-type').val(); // Lấy giá trị từ select search type

            // Gửi yêu cầu AJAX
            $.ajax({
                url: 'search-receipt', // Đường dẫn đến Servlet hoặc file xử lý trên máy chủ
                type: 'GET', // Phương thức yêu cầu (ở đây là GET, có thể sử dụng POST tùy vào yêu cầu của bạn)
                data: {
                    searchText: searchText,
                    searchType: searchType
                },
                success: function (response) {
                    $('#content').html(response); // Cập nhật nội dung kết quả tìm kiếm vào phần tử có id là 'content'
                },
                error: function (xhr, status, error) {
                    console.error('Lỗi AJAX: ' + status + ' ' + error); // Xử lý lỗi nếu có
                }
            });
        }

        // Lắng nghe sự kiện khi click vào các nút phân trang
        $(document).on('click', '.pagination-btn', function () {
            var page = $(this).data('page'); // Lấy số trang từ thuộc tính data-page của nút
            sendRequest(page); // Gọi hàm gửi yêu cầu khi chuyển trang
        });
    });
</script>

        <script>

                                        function handleChange() {
                                            var select = document.getElementById("short").value;
                                            $.ajax({
                                                url: "get-receipt-by-select",
                                                type: "get",
                                                data: {
                                                    selected: select // Pass the selected value
                                                },
                                                success: function (responseData) {
                                                    document.getElementById("content").innerHTML = responseData;
                                                }
                                            });
                                        }
                                        $(document).ready(function () {
                                            $(document).on('click', '.pagination-btn', function () {
                                                var page = $(this).data('page');
                                                sendRequest(page);
                                            });
                                        });
</script>
        <script>

                                        jQuery(function () {
                                            jQuery(".trash").click(function (event) {
                                                event.preventDefault(); // Prevent the default form submission
                                                var button = this; // Reference to the button clicked
                                                var form = $(button).closest('form'); // Reference to the closest form

                                                Swal.fire({
                                                    title: "Cảnh báo",
                                                    text: "Bạn có chắc chắn là muốn xóa hóa đơn này?\n\
            Bạn cần phải gửi thông báo cho người nộp tiền",
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
        <script>

            function searchByDateRange() {
                var dateMin = document.getElementById("dateMin").value;
                var dateMax = document.getElementById("dateMax").value;
                $.ajax({
                    url: "/ManageLodgingHouse/search-receipt",
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
                    url: 'search-receipt', // Đường dẫn đến Servlet của bạn
                    type: 'POST', // Loại yêu cầu
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


        </script>

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
            function deleteRow(r) {
                var i = r.parentNode.parentNode.rowIndex;
                document.getElementById("myTable").deleteRow(i);
            }

            jQuery(function () {
                jQuery(".trash").click(function () {
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn là muốn xóa phiếu thu này?\n\n\
            Bạn cần gửi thông báo cho người nộp tiền",
                        buttons: ["Hủy bỏ", "Đồng ý"],
                    }).then((willDelete) => {
                        if (willDelete) {
                            // Chuyển hướng sang servlet delete-receipt
                            window.location.href = "delete-receipt"; // Điều chỉnh đường dẫn nếu cần thiết
                        }
                    });
                });
            });

            oTable = $('#sampleTable').dataTable();

            $('#all').click(function (e) {
                $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                e.stopImmediatePropagation();
            });
        </script>

    </body>

</html>