<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="jakarta.servlet.ServletContext, dal.AccountInRoomDAO, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession, java.util.List, model.ServiceOfLodgingHouse, dal.ServiceOfLodgingHouseDAO, model.Service, dal.ServiceDAO, model.Bill, dal.BillDAO, dal.RoomDAO, model.Room, dal.ServiceOfRoomDAO, model.ServiceOfRoom"%>
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
            .notifications-container {
                width: 350px;
                height: auto;
                font-size: 12px;
                line-height: 1.25rem;
                display: flex;
                flex-direction: column;
                gap: 1rem;
            }

            .flex {
                display: flex;
            }

            .flex-shrink-0 {
                flex-shrink: 0;
            }

            .success {
                padding: 1rem;
                border-radius: 0.375rem;
                background-color: rgb(192 249 210);
            }

            .succes-svg {
                color: rgb(74 222 128);
                width: 1.25rem;
                height: 1.25rem;
            }

            .success-prompt-wrap {
                margin-left: 0.75rem;
            }

            .success-prompt-heading {
                font-weight: bold;
                color: rgb(22 101 52);
            }

            .success-prompt-prompt {
                margin-top: 0.5rem;
                color: rgb(21 128 61);
            }

            .success-button-container {
                display: flex;
                margin-top: 0.875rem;
                margin-bottom: -0.375rem;
                margin-left: -0.5rem;
                margin-right: -0.5rem;
            }

            .success-button-main {
                padding-top: 0.375rem;
                padding-bottom: 0.375rem;
                padding-left: 0.5rem;
                padding-right: 0.5rem;
                background-color: #ECFDF5;
                color: rgb(22 101 52);
                font-size: 11px;
                line-height: 1.25rem;
                font-weight: bold;
                border-radius: 0.375rem;
                border: none
            }

            .success-button-main:hover {
                background-color: #D1FAE5;
            }

            .success-button-secondary {
                padding-top: 0.375rem;
                padding-bottom: 0.375rem;
                padding-left: 0.5rem;
                padding-right: 0.5rem;
                margin-left: 0.75rem;
                background-color: #ECFDF5;
                color: #337ab7;
                font-size: 11px;
                line-height: 1.25rem;
                border-radius: 0.375rem;
                font-weight: bold;
                border: none;
            }

            .show {
                visibility: visible;
                transform: translateX(-100%);
                animation: show 2s forwards;
            }

            .hide {
                opacity: 1;
                transform: translateX(0);
                animation: hide 2s forwards;
            }

            @keyframes show {
                from {
                    opacity: 0;
                    transform: translateX(-100%);
                }
                to {
                    opacity: 1;
                    transform: translateX(0);
                }
            }

            @keyframes hide {
                from {
                    opacity: 1;
                    transform: translateX(0);
                }
                to {
                    opacity: 0;
                    transform: translateX(-100%);
                }
            }
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <%
            HttpSession s = request.getSession();
            InformationOfUserDAO id = new InformationOfUserDAO();
            ServiceOfLodgingHouseDAO sld = new ServiceOfLodgingHouseDAO();
            ServiceDAO sd = new ServiceDAO();
            RoomDAO rd = new RoomDAO();
            BillDAO bd = new BillDAO();
            AccountInRoomDAO ard = new AccountInRoomDAO();
            ServiceOfRoomDAO srd = new ServiceOfRoomDAO();
            
            Account account = (Account) s.getAttribute("account");
            int accountId = account.getAccountID();
            InformationOfUser informationOfUser = id.getInformationByAccountID(accountId);
            
        %>
        <!-- Navbar-->
        <div>
            <%@include file="header.jsp" %>
        </div>
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
                <li><a class="app-menu__item ${tagMenu=="showRoom"?"active":""}" href="room?service=showRoomInfor"><i class='app-menu__icon  bx bxs-home'></i><span
                            class="app-menu__label">Thông Tin Phòng</span></a></li>
                <li><a class="app-menu__item ${tagMenu=="showProfile"?"active":""}" href="account?service=showProfile"><i class='app-menu__icon bx bx-id-card'></i><span
                            class="app-menu__label">Hồ Sơ</span></a></li>
                <li><a class="app-menu__item ${tagMenu=="changePassword"?"active":""}" href="account?service=showChangePassword"><i class='app-menu__icon bx bxs-lock '></i> <span
                            class="app-menu__label">Mật Khẩu</span></a></li>
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/bill-of-tenant"><i class='app-menu__icon bx bx-task'></i><span
                            class="app-menu__label">Hóa Đơn Thanh Toán</span></a></li>
                <li><a class="app-menu__item" href="account?service=logout"><i class='app-menu__icon bx bx-log-out'></i><span
                            class="app-menu__label">Đăng Xuất</span></a></li>        
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">

                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách hoá đơn từng tháng</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button" style="justify-content: end; margin-right: 15px;">
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                            class="fas fa-print"></i> In dữ liệu</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm print-file js-textareacopybtn" type="button" title="Sao chép"><i
                                            class="fas fa-copy"></i> Sao chép</a>
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

                            <form class="row" action="/ManageLodgingHouse/bill-of-tenant" id="search" method="post">
                                <div class="form-group col-md-2">
                                    <label class="control-label">Năm/tháng: </label>
                                    <input class="form-control" id="monthYear" name="searchDate" type="month" onchange="searchBill()"
                                           value="<%=request.getAttribute("monthYear")!=null?request.getAttribute("monthYear"):""%>">    
                                </div>    
                                <div class="form-group col-md-2">
                                    <label class="control-label">Mã phòng: </label>
                                    <select id="room-id" class="form-control" name="roomId" onchange="searchBill()">
                                        <option value="0"
                                                <%=request.getAttribute("roomId")!=null&&request.getAttribute("roomId").equals("0")?"selected":""%>>
                                            Tất cả
                                        </option>
                                        <%
                                            List<String> listRoomId = ard.getRoomIdByAccountIdRented(accountId);
                                            for(String roomId : listRoomId){
                                        %>
                                        <option value="<%=roomId%>" 
                                                <%=request.getAttribute("roomId")!=null&&request.getAttribute("roomId").equals(roomId)?"selected":""%>>
                                            <%=roomId%>
                                        </option>
                                        <%}%>
                                    </select>

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
                                <strong> - Giá các dịch vụ sẽ tính theo 1 đơn vị(điện:số, nước:khối và vệ sinh, internet, giặt: tháng).</strong><br>
                                <strong> - Bấm vào thanh toán để thanh toán hoá đơn.</strong><br>
                                <strong> - Những giá tiền dịch vụ 0.0 là những dịch vụ của trọ bạn không đăng ký sử dụng.</strong><br>
                            </div>
                            <div id="content">

                                <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                                       id="sampleTable">                              
                                    <thead>
                                        <tr>                                         
                                            <th>Mã phòng</th>
                                            <th>Năm/tháng</th>
                                            <th>Số nước cũ</th>
                                            <th>Số nước mới</th>
                                            <th>Tiền nước</th>
                                            <th>Số điện cũ</th>
                                            <th>Số điện mới</th>
                                            <th>Tiền điện</th>
                                            <th>Tiền phòng</th>
                                                <%
                                                    List<String> listServiceName = sld.getNameOfServiceInLodgingHouseByAccountId(accountId);
                                                    for(String name : listServiceName){
                                                %>
                                            <th>Tiền <%=name%></th>
                                                <%}%>
                                            <th>Tổng tiền</th>
                                            <th>Trạng thái</th>
                                            <th width="70">Thanh toán</th>
                                        </tr>
                                    </thead>
                                    <tbody>                                               
                                        <%
                                            List<Bill> listBill = (List<Bill>) request.getAttribute("listBillOfTenant");
                                                
                                            if(listBill != null){
                                                for(Bill bill : listBill){
                                        %>
                                        <tr>
                                            <td style="font-weight: bold"><%=bill.getRoomId()%></td>
                                            <td><%=bill.getMonthYear()%></td>
                                            <td style="color: green"><%=bill.getWaterOld()%></td>
                                            <td style="color: blue"><%=bill.getWaterNew()%></td>
                                            <td style="color: firebrick"><%=bill.getPriceWater()%></td>
                                            <td style="color: green"><%=bill.getElectronicOld()%></td>
                                            <td style="color: blue"><%=bill.getElectronicNew()%></td>
                                            <td style="color: firebrick"><%=bill.getPriceElectronic()%></td>
                                            <%
                                                double priceOfRoom = rd.getRoomsById(bill.getRoomId()).getPrice();
                                            %>
                                            <td><%=priceOfRoom%></td>
                                            <%
                                                List<ServiceOfRoom> listSOR = srd.getAllServiceOfRoomByRoomId(bill.getRoomId());
                                                for(String name : listServiceName){
                                                    boolean checkService = true;
                                                    for(ServiceOfRoom sor : listSOR){       
                                                        if(sd.getServiceById(sor.getServiceId()).getServiceName().equals(name)){
                                            %>
                                            <td><%=sor.getPrice()%></td>
                                            <%
                                                checkService = false;
                                                break;
                                                }
                                            %>
                                            <%}
                                            if(checkService){%> 
                                            <td>0.0</td>   
                                            <%}}%>
                                            <td><%=bd.calculateTotalAmountOfBill(bill.getRoomId(), bill.getMonthYear())%></td>
                                            <%
                                                if(bill.getStatus() == 2){
                                            %>
                                            <td>
                                                <span class="badge bg-success">Đã thanh toán</span>
                                            </td>
                                            <%    
                                                } else if(bill.getStatus() == 1){
                                            %>
                                            <td>
                                                <span class="badge bg-danger">Chưa thanh toán</span>
                                            </td>
                                            <%    
                                                } else {
                                            %>  
                                            <td>
                                                <span class="badge bg-warning">Đã thanh toán một phần</span>
                                            </td>
                                            <%}%>
                                            <td>
                                                <%
                                                if((bill.getStatus() == 1 || bill.getStatus() == 3) && bill.getWaterNew() != 0 && bill.getElectronicNew() != 0){
                                                %>
                                                <button class="btn btn-primary btn-sm edit" type="button" title="Xem chi tiết" id="show-emp" data-toggle="modal"
                                                        onclick="redirectToPaymantPage('<%=bill.getRoomId()%>', '<%=bill.getMonthYear()%>')">Thanh Toán
                                                </button>
                                                <%}%>
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
            <div id="notify-receipt" class="notifications-container" 
                 style="margin-left: -20px; position: absolute; bottom: 25px; visibility: hidden">
                <div class="success">
                    <div class="flex">
                        <div class="flex-shrink-0">
                            <svg aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg" class="succes-svg">
                            <path clip-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" fill-rule="evenodd"></path>
                            </svg>
                        </div>
                        <div class="success-prompt-wrap">
                            <p class="success-prompt-heading">
                                Quản lý trọ đã thêm hoá đơn tháng mới!
                            </p>
                            <div class="success-prompt-prompt">
                                <p>Xem chi tiết và xác nhận hoá đơn?</p>
                            </div>
                            <div class="success-button-container">
                                <button class="success-button-main" type="button">
                                    <a id="my-link" style="text-decoration: none">Xem chi tiết hoá đơn</a>
                                </button>
                                <button class="success-button-secondary" type="button" onclick="hideNotify()" style="margin-left: 20px; cursor: pointer">
                                    Bỏ qua                                            
                                </button>
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

            function redirectToPaymantPage(roomId, monthYear) {
                window.location.href = '${pageContext.request.contextPath}/bill-of-tenant?roomId=' + roomId + '&monthYear=' + monthYear + '&service=payment';
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
            function editRowById(id) {
                if (id) {
                    // Chuyển trang
                    window.location.href = "update-investment-costs-servlet?id=" + id;
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
        </script>
        <script type="text/javascript">
            const form = document.getElementById("search");

            function searchBill() {
                form.submit();
            }
        </script>

        <script>
            var ws;

            // gửi tham số idAccount đến server
            ws = new WebSocket('ws://localhost:9999/ManageLodgingHouse/socket?idAccount=<%=accountId%>');

            ws.onopen = function (event) {
                console.log('WebSocket Opened');
            };

            ws.onmessage = function (message) {
                var notice = message.data.split('&');

                if (notice[0] === 'notice') {
                    var notifyEle = document.getElementById('notify-receipt');
                    notifyEle.style.visibility = 'visible';
                    notifyEle.classList.remove('hide');
                    notifyEle.classList.add('show');
                    
                    document.getElementById('my-link').href = '${pageContext.request.contextPath}/bill-of-tenant?roomId=' + notice[1] + '&monthYear=' + notice[2] + '&service=payment';
                }
            };

            ws.onclose = function (event) {
                console.log('WebSocket Closed');
            };

            function hideNotify() {
                var notifyEle = document.getElementById('notify-receipt');
                notifyEle.classList.add('hide');
            }

        </script>
    </body>

</html>