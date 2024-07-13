<%-- 
    Document   : table-contract-of-tenant
    Created on : Jun 16, 2024, 10:54:17 PM
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    <body>

        <div class="col-sm-2">
            <aside class="app-sidebar">
                <div class="app-sidebar__user" ><img class="app-sidebar__user-avatar" src="" width="100px"
                                                     alt="User Image">
                    <div>
                        <p class="app-sidebar__user-name"><b></b></p>
                        <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                    </div>
                </div>
                <hr>
                <ul class="app-menu">

                    <li><a class="app-menu__item " href="index.html"><i class='app-menu__icon bx bx-tachometer'></i><span
                                class="app-menu__label">Bảng điều khiển</span></a></li>
                    <li><a class="app-menu__item " href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                                class="app-menu__label">Quản lý khách hàng</span></a></li>
                    <li><a class="app-menu__item" href="table-data-product.html"><i
                                class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a>
                    </li>
                    <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/tenant-contract-servlet?statusAccept=1"><i class='app-menu__icon bx bx-id-card'></i>
                            <span class="app-menu__label">Quản lí hợp đồng</span></a></li>
                    <li><a class="app-menu__item" href="${pageContext.request.contextPath}/room-tenant-servlet"><i class='app-menu__icon bx bx-id-card'></i>
                            <span class="app-menu__label">Quản lí phòng</span></a></li>
                </ul>
            </aside>
        </div>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách các hợp đồng</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">

                            <div class="row element-button">

                                <div class="input-group col-sm-5">
                                    <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" style="padding:0;"/>
                                    <input type="hidden" value="${requestScope.active}">
                                </div>
                                <div class="input-group col-sm-5">
                                    <a class="btn btn-primary" href="view-old-contract-tenant-servlet">
                                        Xem hợp đồng cũ
                                    </a>
                                </div>
                            </div>

                            <div id="content">
                                <form id="contractForm" action="${pageContext.request.contextPath}/send-contract-servlet" method="post" style="display: none;">
<!--                                    <input type="hidden" id="lodgingHouseId" name="lodgingHouseId" value="${requestScope.lodgingHouse.getLodgingHouseId()}">-->
                                    <input type="hidden" id="room" name="room">
                                    <input type="hidden" id="email" name="email">
                                    <input type="hidden" id="dateFrom" name="dateFrom">
                                    <input type="hidden" id="dateTo" name="dateTo">
                                    <input type="hidden" id="deposit" name="deposit">
                                    <input type="hidden" id="check" name="check" value="1">
                                </form>

                                <table id="sampleTable" class="table table-hover table-bordered js-copytextarea">
                                    <thead>
                                        <tr>
                                            <th>Email người thuê</th>
                                            <th>Phòng</th>
                                            <th>Ngày bắt đầu</th>
                                            <th>Ngày kết thúc</th>
                                            <th>Tiền cọc</th>                   
                                            <th>Loại yêu cầu từ quản lí</th>
                                            <th>Xem hợp đồng</th>
                                                <c:if test="${requestScope.active == 2}">
                                                <th>Xóa hợp đồng</th>
                                                </c:if>
                                                <c:if test="${requestScope.active == 1}">
                                                <th>Phê duyệt</th>
                                                </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${requestScope.paginationContractOfTenant.getListObject()}" var="o">
                                            <tr>
                                                <td>${o.getTenantId().getEmail()}</td>
                                                <c:if test="${o.getStatusAccept() == 1}">
                                                    <td><a href="room-detail-servlet?id=${o.getRoom().getRoomId()}">${o.getRoom().getRoomName()} </a></td>
                                                </c:if>  
                                                <c:if test="${o.getStatusAccept() == 2}">
                                                    <c:if test="${o.getTypeOfAccept() == 3}">
                                                        <td>${o.getRoom().getRoomName()}</td>
                                                    </c:if>
                                                    <c:if test="${o.getTypeOfAccept() != 3}">
                                                        <td><a href="room-detail-servlet?id=${o.getRoom().getRoomId()}">${o.getRoom().getRoomName()} </a></td>
                                                    </c:if>
                                                </c:if>      
                                                <c:if test="${o.getStatusAccept() == 3}">
                                                    <td>${o.getRoom().getRoomName()}</td>
                                                </c:if>                                                    
                                                <td>${o.getDateFrom()}</td>
                                                <td>${o.getDateTo()}</td>
                                                <td> <fmt:formatNumber value="${o.getContractDeposit()}" type="number" pattern="#,##0"/> VND</td>
                                                <c:if test="${o.getTypeOfAccept() == 1}">
                                                    <td>Thêm</td>
                                                </c:if>
                                                <c:if test="${o.getTypeOfAccept() == 3}">
                                                    <td>Xóa</td>
                                                </c:if>
                                                <td class="table-td-center">
                                                    <button class="btn btn-primary send-form-btn" type="button"                                                           
                                                            data-room="${o.getRoom().getRoomId()}"
                                                            data-email="${o.getTenantId().getEmail()}"
                                                            data-date-from="${o.getDateFrom()}"
                                                            data-date-to="${o.getDateTo()}"
                                                            data-deposit="${o.getContractDeposit()}">
                                                        <i class='bx bx-edit'></i>
                                                    </button>       
                                                </td>
                                                <c:if test="${o.getStatusAccept() == 2}">
                                                    <c:if test="${o.getTypeOfAccept() != 3}">
                                                        <td>
                                                            <button class="btn btn-primary send-form-btnDelete" type="button" onclick="confirmDelete('${o.getRoom().getRoomId()}', '${o.getTenantId().getAccountID()}', '${o.getDateFrom()}', '${o.getDateTo()}', '${o.getTypeOfAccept()}')">

                                                                <i class="fas fa-trash-alt"></i>
                                                            </button>
                                                        </td>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${o.getRoleCreateContract() == 2}">
                                                    <c:if test="${o.getStatusAccept() == 1}">
                                                        <td class="table-td-center" >
                                                            <button class="btn btn-primary btn-sm" type="button" title="Đồng ý" onclick="confirmAccept('${o.getRoom().getRoomId()}', '${o.getTenantId().getAccountID()}', '${o.getDateFrom()}', '${o.getDateTo()}', 2, '${o.getTypeOfAccept()}')">
                                                                <i class="fas fa-check fa-flip" style="--fa-primary-color: #d91c1c; --fa-secondary-color: #d91c1c;"></i>
                                                            </button>
                                                            <button class="btn btn-primary btn-sm" type="button" title="Không đồng ý" onclick="confirmAccept('${o.getRoom().getRoomId()}', '${o.getTenantId().getAccountID()}', '${o.getDateFrom()}', '${o.getDateTo()}', 3, '${o.getTypeOfAccept()}')">
                                                                <i class="fa fa-window-close"></i>
                                                            </button>
                                                        </td>
                                                    </c:if>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <div class="pagination">
                                    <c:set var="currentPage" value="${requestScope.paginationContractOfTenant.getCurentPage()}"/>
                                    <c:if test="${currentPage > 1}">
                                        <a href="tenant-contract-servlet?curentPage=${currentPage - 1}" >&laquo;</a>
                                    </c:if>
                                    <c:forEach  begin="${requestScope.paginationContractOfTenant.getStart()}" end="${requestScope.paginationContractOfTenant.getEnd()}" var="num">   
                                        <c:if test="${num != 0}">
                                            <c:if test="${num == currentPage}">
                                                <a href="tenant-contract-servlet?curentPage=${num}"  class="active">${num}</a>
                                            </c:if>
                                            <c:if test="${num != currentPage}">
                                                <a href="tenant-contract-servlet?curentPage=${num}" >${num}</a>
                                            </c:if>   
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${requestScope.paginationContractOfTenant.getNumberOfPage() > currentPage}">
                                        <a href="tenant-contract-servlet?curentPage=${currentPage + 1}">&raquo;</a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%@ include file="../manager/modal-contract.jsp" %>

            <div class="pagination">
                <a href="tenant-contract-servlet?statusAccept=1"<c:if test="${requestScope.active == 1}"> class="active" </c:if>>Chờ phê duyệt</a>                  
                <a href="tenant-contract-servlet?statusAccept=2" <c:if test="${requestScope.active== 2}"> class="active" </c:if>>Đã phê duyệt</a>
                <a href="tenant-contract-servlet?statusAccept=3" <c:if test="${requestScope.active== 3}"> class="active" </c:if>>Bị hủy bỏ</a>
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

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


            <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.js"></script>
            <script src="${pageContext.request.contextPath}/Resource/js/contractPDF.js"></script>

        <script>
                                                                $(document).ready(function () {
                                                                    $(document).on('click', '.pagination-btn', function () {
                                                                        var page = $(this).data('page');
                                                                        sendRequest(page);
                                                                    });
                                                                });

                                                                function sendRequest(page) {
                                                                    $.ajax({
                                                                        url: 'search-contract-servlet', // Đường dẫn đến Servlet của bạn
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
        <script type="text/javascript">
            function confirmDelete(roomId, tenantId, dateFrom, dateTo, typeOfAccept) {
                let swalTitle = "Cảnh báo";
                let swalText = "Bạn có chắc chắn là muốn XÓA hợp đồng này?";

                swal({
                    title: swalTitle,
                    text: swalText,
                    buttons: ["Hủy bỏ", "Accept"]
                }).then((willAccept) => {
                    if (willAccept) {
                        $.ajax({
                            type: "POST",
                            url: '${pageContext.request.contextPath}/delete-contract-servlet',
                            data: {
                                roomId: roomId,
                                tenantId: tenantId,
                                dateFrom: dateFrom,
                                dateTo: dateTo,
                                typeOfAccept: typeOfAccept
                            },
                            success: function (response) {
                                swal("Đã gửi yêu cầu xóa thành công!", {
                                    icon: "success",
                                }).then(() => {
                                    location.reload();  // Reload the page on success
                                });
                            },
                            error: function (xhr, status, error) {
                                swal("Đã xảy ra lỗi. Vui lòng thử lại!", {
                                    icon: "error",
                                });
                            }
                        });
                    }
                });
            }
        </script>


        <script type="text/javascript">
            function confirmAccept(roomId, tenantId, dateFrom, dateTo, service, typeAccept) {
                let swalTitle, swalText, servletUrl;

                if (service === 2) {
                    swalTitle = "Cảnh báo";
                    swalText = "Bạn có chắc chắn là muốn chấp nhận hợp đồng này?";
                    servletUrl = "tenant-contract-servlet";
                } else {
                    swalTitle = "Cảnh báo";
                    swalText = "Bạn có chắc chắn là muốn từ chối hợp đồng này?";
                    servletUrl = "tenant-contract-servlet";
                }

                swal({
                    title: swalTitle,
                    text: swalText,
                    buttons: ["Hủy bỏ", "Accept"]
                }).then((willAccept) => {
                    if (willAccept) {
                        $.ajax({
                            type: "POST",
                            url: servletUrl,
                            data: {
                                roomId: roomId,
                                tenantId: tenantId,
                                dateFrom: dateFrom,
                                dateTo: dateTo,
                                service: service,
                                typeAccept: typeAccept
                            },
                            success: function (response) {
                                swal("Đã thực hiện!", {
                                    icon: "success",
                                }).then(() => {
                                    location.reload();  // Reload the page on success
                                });
                            },
                            error: function (xhr, status, error) {
                                swal("Đã xảy ra lỗi. Vui lòng thử lại!", {
                                    icon: "error",
                                });
                            }
                        });
                    }
                });
            }
        </script>

        <script type="text/javascript">
            $(document).ready(function () {
                $('.send-form-btn').on('click', function (e) {
                    e.preventDefault();
                    // Lấy dữ liệu từ các thuộc tính data- của button
                    var room = $(this).data('room');
                    var manager = $(this).data('manager');
                    var email = $(this).data('email');
                    var dateFrom = $(this).data('date-from');
                    var dateTo = $(this).data('date-to');
                    var deposit = $(this).data('deposit');
                    // Đưa dữ liệu vào form
                    $('#room').val(room);
                    $('#email').val(email);
                    $('#dateFrom').val(dateFrom);
                    $('#dateTo').val(dateTo);
                    $('#deposit').val(deposit);
                    // Gửi yêu cầu AJAX đến Servlet create-contract-servlet
                    $.ajax({
                        url: '${pageContext.request.contextPath}/create-contract-servlet',
                        type: 'POST',
                        data: $('#contractForm').serialize(),
                        success: function (response) {
                            // Hiển thị kết quả trả về trong modal
                            $('#modalBody').html(response); // Đưa dữ liệu vào modal
                            $('#contractModal').modal('show'); // Hiển thị modal
                        },
                        error: function () {
                            // Xử lý lỗi nếu có
                            Swal.fire('Lỗi', 'Không thể gửi hợp đồng', 'error');
                        }
                    });
                });
            });
        </script>


        <script>
            function searchByKeyword(param, targetElementId, activeValue) {
                var searchInfo = param.value.trim();
                $.ajax({
                    url: "/ManageLodgingHouse/search-contract-servlet",
                    type: "get", // Gửi qua phương thức GET
                    data: {
                        keyword: searchInfo,

                    },
                    success: function (responseData) {
                        document.getElementById(targetElementId).innerHTML = responseData;
                    }
                });
            }

            document.addEventListener("DOMContentLoaded", function () {
                var searchInput = document.querySelector('input[type="search"]');

                searchInput.addEventListener('input', function () {
                    searchByKeyword(this, "content");
                });
            });
        </script>
    </body>
</html>
