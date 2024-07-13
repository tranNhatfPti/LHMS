<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="jakarta.servlet.ServletContext, java.util.Date, java.text.SimpleDateFormat, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession, java.util.List, model.Bill, dal.BillDAO, model.Contract, dal.ContractDAO"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh sách hoá đơn</title>
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

            #bottone1 {
                font-size: 12px;
                /* padding-left: 33px; */
                /* padding-right: 33px; */
                padding-bottom: 5px;
                padding-top: 5px;
                border-radius: 9px;
                background: #d5f365;
                border: none;
                font-family: inherit;
                text-align: center;
                cursor: pointer;
                transition: 0.4s;
                width: 100%;
                color: darkblue;
            }

            #bottone1:hover {
                box-shadow: 0px 0px 20px 0px #C3D900;
            }

            #bottone1:active {
                transform: scale(0.97);
                box-shadow: 7px 5px 56px -10px #C3D900;
            }
            
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <%
            HttpSession s = request.getSession();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            InformationOfUserDAO id = new InformationOfUserDAO();
            BillDAO bd = new BillDAO();
            ContractDAO cd = new ContractDAO();
            
            Account account = (Account) s.getAttribute("account");
            int accountId = account.getAccountID();
            InformationOfUser informationOfUser = id.getInformationByAccountID(accountId);
            
            int lodgingHouseId = (int) s.getAttribute("lodgingID");
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
                <li><a class="app-menu__item" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Chỉ số nước</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>

                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/investment-costs-servlet"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lí khoản phí đầu tư</span></a>
                </li> 
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/service-of-lodginghouse"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Dịch vụ trọ</span></a></li>
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/bill"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Hoá đơn thanh toán</span></a></li>        
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">

                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách hoá đơn thanh toán</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button" style="justify-content: end; margin-right: 15px;">
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm nhap-tu-file" type="button" title="Thêm"
                                       href="${pageContext.request.contextPath}/view/manager/insert-bill.jsp"><i
                                            class="fas fa-plus-circle"></i> Thêm hoá đơn</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                            class="fas fa-print"></i> In dữ liệu</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm nhap-tu-file" type="button" title="Nhập" onclick="myFunction(this)"><i
                                            class="fas fa-file-upload"></i> Tải từ file</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-excel btn-sm" href="" title="In"><i class="fas fa-file-excel"></i> Xuất Excel</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm pdf-file" type="button" title="In" onclick="myFunction(this)"><i
                                            class="fas fa-file-pdf"></i> Xuất PDF</a>
                                </div>
                            </div> 
                            <form class="row" action="/ManageLodgingHouse/bill" id="search" method="post">
                                <div class="form-group col-md-2">
                                    <label class="control-label">Năm/tháng: </label>
                                    <input class="form-control" id="monthYear" name="searchDate" type="month" onchange="searchBill()"
                                           value="<%=request.getAttribute("monthYear")!=null?request.getAttribute("monthYear"):""%>">    
                                </div>    
                                <div class="form-group col-md-2">
                                    <label class="control-label">Mã phòng: </label>
                                    <input class="form-control" id="room-id" name="roomId" type="text"
                                           value="<%=request.getAttribute("roomId")!=null?request.getAttribute("roomId"):""%>">    
                                </div>  
                                <div class="form-group col-md-2">
                                    <label class="control-label">Trạng thái thanh toán: </label> 
                                    <select id="status" class="form-control" name="status" onchange="searchBill()">
                                        <%
                                            Integer status = (Integer) request.getAttribute("status");
                                            if(status == null){
                                                status = 0;
                                            }
                                        %>
                                        <option value="0" <%=status==0?"selected":""%>>
                                            Tất cả
                                        </option>
                                        <option value="1" <%=status==1?"selected":""%>>
                                            Chưa thanh toán
                                        </option>
                                        <option value="2" <%=status==2?"selected":""%>>
                                            Đã thanh toán
                                        </option>
                                        <option value="3" <%=status==3?"selected":""%>>
                                            Đã thanh toán một phần
                                        </option>
                                    </select>
                                </div> 
                                <input type="hidden" name="service" value="searchBill">
                            </form>

                            <div style="color: red; margin-bottom: 15px; font-size: 12px">
                                <strong> *Lưu ý:</strong><br>
                                <strong> - Để tạo ra hoá đơn mới trong tháng hoặc tháng tiếp theo bạn phải cập nhật 
                                    số điện và số nước của từng hoá đơn.</strong><br>    
                                <strong> - Nếu phòng không có người thuê thì bạn không cần cập nhật trạng thái thanh toán.</strong><br>
                                <strong> - Nếu muốn xoá bất kì 1 hoá đơn nào hãy chắc chắn trước khi xoá.</strong><br>
                                <strong> - Khi xoá 1 hoá đơn, nếu muốn tạo lại hoá đơn đấy, bạn có thể CẬP NHẬT lại số liệu cũ hoá đơn tháng trước hoặc
                                    click vào TẠO HOÁ ĐƠN.</strong><br>
                            </div>
                            <div id="content">

                                <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                                       id="sampleTable">                              
                                    <thead>
                                        <tr>
                                            <th width="10"><input type="checkbox" value="0" id="all" name="deleteAll"></th>
                                            <th>Mã phòng</th>
                                            <th>Năm/tháng</th>
                                            <th>Số nước cũ</th>
                                            <th>Số nước mới</th>
                                            <th>Tiền nước</th>
                                            <th>Số điện cũ</th>
                                            <th>Số điện mới</th>
                                            <th>Tiền điện</th>
                                            <th>Tiền dịch vụ khác</th>
                                            <th>Số tiền còn thiếu</th>
                                            <th>Số tiền đã trả</th>
                                            <th>Trạng thái</th>
                                            <th>Người thuê</th>
                                            <th width="100">Tính năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>                                               
                                        <%
                                            List<Bill> listBill = (List<Bill>) request.getAttribute("listOfBill");
                                                
                                            if(listBill != null){
                                                for(Bill bill : listBill){
                                        %>
                                        <tr>
                                            <td><input type="checkbox" name="name"></td>
                                            <td style="font-weight: bold"><%=bill.getRoomId()%></td>
                                            <td><%=bill.getMonthYear()%></td>
                                            <td style="color: green"><%=bill.getWaterOld()%></td>
                                            <td style="color: blue"><%=bill.getWaterNew()%></td>
                                            <td style="color: firebrick"><%=bill.getPriceWater()%></td>
                                            <td style="color: green"><%=bill.getElectronicOld()%></td>
                                            <td style="color: blue"><%=bill.getElectronicNew()%></td>
                                            <td style="color: firebrick"><%=bill.getPriceElectronic()%></td>
                                            <td style="color: firebrick"><%=bill.getPriceOtherServices()%></td>
                                            <td><%=bill.getMissing()%></td>
                                            <td><%=bill.getPaid()%></td>             
                                            <%
                                                if(bill.getStatus() == 2){
                                            %>
                                            <td style="color: green">
                                                <span class="badge bg-success">Đã thanh toán</span>
                                            </td>
                                            <%    
                                                } else if (bill.getStatus() == 1){
                                            %>
                                            <td style="color: red">
                                                <span class="badge bg-danger">Chưa thanh toán</span>
                                            </td>
                                            <%    
                                                } else {
                                            %>    
                                            <td style="color: red">
                                                <span class="badge bg-warning">Đã thanh toán một phần</span>
                                            </td>
                                            <%}%>
                                            <%
                                                // lấy ra account thuê phòng tại thời điểm của bill
                                                Date date = formatter.parse(bill.getMonthYear());
                                                Contract contract = cd.getContractByDateAndRoomID(bill.getRoomId(), date);
                                                
                                                if(contract != null){
                                                    int idOfTenant = contract.getTenantId().getAccountID();
                                                    InformationOfUser inOfTenant = id.getInformationByAccountID(idOfTenant);
                                            %>
                                            <td>        
                                                <button id="bottone1"><strong><a href="bill?service=viewInforOfTenant&tenantId=<%=idOfTenant%>"><%=inOfTenant.getFullName()%></a></strong></button>
                                            </td>
                                            <%} else {%>
                                            <td style="color: red">
                                                Không
                                            </td>
                                            <%}%>
                                            <td>
                                                <button class="btn btn-primary btn-sm trash" type="button" title="Xóa"
                                                        onclick="deleteBill('<%=bill.getRoomId()%>', '<%=bill.getMonthYear()%>')"><i class="fas fa-trash-alt"></i> 
                                                </button>
                                                <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                        onclick="updateBill('<%=bill.getRoomId()%>', '<%=bill.getMonthYear()%>')"><i class="fas fa-edit"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <% 
                                            }}
                                        %>
                                    </tbody>
                                </table>
                            </div>   
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/Resource/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/js/bootstrap.min.js"></script>
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
        <script type="text/javascript">$('#sampleTable').DataTable();</script>

        <script type="text/javascript">
            document.addEventListener('DOMContentLoaded', function () {
                var searchDate = document.getElementById('monthYear');
                searchDate.value = '<%=request.getAttribute("monthYear")%>';

                searchDate.addEventListener('change', function () {
                    document.getElementById('search').submit();
                });
            });

            function updateBill(roomId, monthYear) {
                window.location.href = 'view/manager/Update-bill.jsp?roomId=' + roomId + '&monthYear=' + monthYear;
            }

            function deleteBill(roomId, monthYear) {
                swal({
                    title: "Cảnh báo",
                    text: "Xoá hoá đơn này bạn sẽ mất toàn bộ dữ liệu về hoá đơn này. \n\
        Bạn có chắc chắn là muốn xoá hoá đơn này không?",
                    icon: "warning",
                    buttons: ["Hủy bỏ", "Đồng ý"]
                })
                        .then((acceptInsert) => {
                            if (acceptInsert) {
                                var url = '/ManageLodgingHouse/bill?service=deleteBill' +
                                        '&roomId=' + roomId + '&monthYear=' + monthYear;

                                // Gửi dữ liệu tới servlet bằng Fetch API
                                fetch(url)
                                        .then(response => {
                                            return response.text();
                                        }) // hoặc response.json() nếu servlet trả về JSON
                                        .then(data => {
//                                            console.log(data);
                                            if (data === "OK") {
                                                swal({
                                                    title: "Xoá thành công!",
                                                    text: "Bạn đã xoá hoá đơn thành công.",
                                                    icon: "success"
                                                }).then((result) => {
                                                    if (result) {
                                                        location.reload();
                                                    }
                                                });
                                            } else {
                                                swal({
                                                    title: "Xoá không thành công!",
                                                    text: "Bạn đã xoá hoá đơn không thành công.",
                                                    icon: "error"
                                                });
                                            }
                                        })
                                        .catch((error) => {
                                            console.error('Error:', error);
                                        });
                            } else {
                                swal({
                                    title: "Xoá không thành công!",
                                    text: "Bạn đã huỷ xoá hoá đơn.",
                                    icon: "error"
                                });
                            }
                        });
            }
        </script>

        <script>
            jQuery(function () {
                jQuery(document).on('click', '.trash', function () {
                    var id = jQuery(this).attr('data-id');
                    var date = jQuery(this).attr('data-date');
                    compareDate(new Date(date), id, "delete");
                });
                jQuery(document).on('click', '.edit', function () {
                    var id = jQuery(this).attr('data-id');
                    var date = jQuery(this).attr('data-date');
                    compareDate(new Date(date), id, "update");
                });
            });
            oTable = $('#sampleTable').dataTable();
            $('#all').click(function (e) {
                $('#sampleTable tbody :checkbox').prop('checked', $(this).is(':checked'));
                e.stopImmediatePropagation();
            });

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
        <script type="text/javascript">
            const inputField = document.getElementById("room-id");
            const form = document.getElementById("search");

            document.addEventListener("DOMContentLoaded", function () {

                let timeout = null;

                inputField.addEventListener("input", function () {
                    // Clear any previously set timeout
                    if (timeout) {
                        clearTimeout(timeout);
                    }

                    // Set a new timeout to submit the form after 2 seconds
                    timeout = setTimeout(function () {
                        form.submit();
                    }, 2000);
                });
            });

            function searchBill() {
                form.submit();
            }
        </script>
    </body>

</html>