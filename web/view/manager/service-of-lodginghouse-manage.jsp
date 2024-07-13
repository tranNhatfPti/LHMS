<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="jakarta.servlet.ServletContext, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession, java.util.List, model.ServiceOfLodgingHouse, dal.ServiceOfLodgingHouseDAO, model.Service, dal.ServiceDAO"%>
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
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <%
            HttpSession s = request.getSession();
            InformationOfUserDAO id = new InformationOfUserDAO();
            ServiceOfLodgingHouseDAO sld = new ServiceOfLodgingHouseDAO();
            ServiceDAO sd = new ServiceDAO();
            
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
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/service-of-lodginghouse"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Dịch vụ trọ</span></a></li>
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/bill"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Hoá đơn thanh toán</span></a></li>        
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">

                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách dịch vụ của trọ</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm nhap-tu-file" type="button" title="Nhập"
                                       href="${pageContext.request.contextPath}/view/manager/insert-service-of-lodginghouse.jsp">
                                        <i class="fas fa-plus-circle" ></i> Thêm dịch vụ
                                    </a>
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

                            <div style="color: red; margin-bottom: 15px; font-size: 12px">
                                <strong> *Lưu ý:</strong><br>
                                <strong> - Giá các dịch vụ sẽ tính theo 1 đơn vị(điện:số, nước:khối, vệ sinh và internet: tháng).</strong><br>
                                <strong> - Các dịch vụ phải được gán cho từng phòng khi có người thuê để khi tính tiền sẽ có tiền dịch vụ đó.</strong><br>
                            </div>
                            <div id="content">

                                <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                                       id="sampleTable">                              
                                    <thead>
                                        <tr>
                                            <th width="10"><input type="checkbox" value="0" id="all" name="deleteAll"></th>
                                            <th>STT</th>
                                            <th>Tên dịch vụ</th>
                                            <th>Giá</th>
                                            <th width="100">Tính năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>                                               
                                        <%
                                            int rollNumber = 0;
                                            List<ServiceOfLodgingHouse> listServiceOfLodgingHouse = (List<ServiceOfLodgingHouse>) request.getAttribute("listSLD");
                                                
                                            if(listServiceOfLodgingHouse != null){
                                                for(ServiceOfLodgingHouse slh : listServiceOfLodgingHouse){
                                        %>
                                        <tr>
                                            <td><input type="checkbox" name="name"></td>
                                            <td style="font-weight: bold"><%=++rollNumber%></td>
                                            <%
                                                String serviceName = sd.getServiceById(slh.getServiceId()).getServiceName();
                                            %>
                                            <td><%=serviceName%></td>
                                            <td style="color: green"><%=slh.getPrice()%></td>
                                            <td>
                                                <button id="delete" class="btn btn-primary btn-sm trash" type="button" title="Xóa"
                                                        onclick="deleteService(<%=slh.getServiceId()%>)"
                                                        <%=slh.getServiceId()==1||slh.getServiceId()==2?"disabled":""%>><i class="fas fa-trash-alt"></i> 
                                                </button>
                                                <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
                                                        onclick="requestUpdateService(<%=slh.getServiceId()%>)"><i class="fas fa-edit"></i>
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

            function requestUpdateService(serviceId) {
                window.location.href = "${pageContext.request.contextPath}/service-of-lodginghouse?service=requestUpdateService&serviceId=" + serviceId;
            }
        </script>        

        <script type="text/javascript">
            function deleteService(serviceId) {
                swal({
                    title: "Cảnh báo",
                    text: "Bạn có chắc chắn là muốn xoá dịch vụ này không?",
                    icon: "warning",
                    buttons: ["Hủy bỏ", "Đồng ý"]
                })
                        .then((acceptInsert) => {
                            if (acceptInsert) {
                                var url = '/ManageLodgingHouse/service-of-lodginghouse?service=deleteServiceOfLodgingHouse' +
                                        '&serviceId=' + serviceId;

                                // Gửi dữ liệu tới servlet bằng Fetch API
                                fetch(url)
                                        .then(response => {
                                            return response.text();
                                        }) // hoặc response.json() nếu servlet trả về JSON
                                        .then(data => {
                                            console.log(data);
                                            if (data === "OK") {
                                                swal({
                                                    title: "Xoá thành công!",
                                                    text: "Bạn đã xoá dịch vụ thành công.",
                                                    icon: "success",
                                                    buttons: ["Ở lại", "Thoát"]
                                                })
                                                        .then((acceptBack) => {
                                                            if (acceptBack) {
                                                                window.location.href = "${pageContext.request.contextPath}/service-of-lodginghouse";
                                                            }
                                                        });
                                            } else if (data === "USING") {
                                                swal({
                                                    title: "Xoá không thành công!",
                                                    text: "Dịch vụ này đang được sử dụng bởi phòng trọ khác.",
                                                    icon: "error"
                                                });
                                            } else {
                                                swal({
                                                    title: "Xoá không thành công!",
                                                    text: "Bạn đã xoá dịch vụ không thành công.",
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
                                    text: "Bạn đã huỷ xoá dịch vụ.",
                                    icon: "error"
                                });
                            }
                        });
            }
        </script>
    </body>
</html>