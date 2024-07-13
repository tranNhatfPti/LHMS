<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="jakarta.servlet.ServletContext, java.util.Date, java.text.SimpleDateFormat, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession, java.util.List, model.Bill, dal.BillDAO, model.Contract, dal.ContractDAO"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Tin nhắn</title>
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

        <link
            href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
            rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
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

            .right-side .user-contact {
                padding-left: 10px;
                padding-right: 10px;
                padding-bottom: 5px;
                padding-top: 5px;
            }

            .form-send-message {
                padding-bottom: 20px;
                padding-left: 15px;
                position: absolute;
                bottom: 0;
                width: 100%;
            }

            #message {
                border-radius: 50px;
                background-color: #e5e5e5;
                height: 40px;
                margin-right: 10px;
            }

            .list-user-search input {
                margin: 0;
            }

            .list-messages-contain {
                margin-top: 5px;
            }

            .btn .icon, .btn .fa{
                font-size: 18px;
            }

            .btn-image, .btn-send input{
                background-color: white;
            }

            .right-side .user-contact{
                box-shadow: 0rem .2rem .4rem rgb(1 1 1 / 31%);
            }

            .message.right .message-text {
                background: #6169db;
                color: #ffffff;
                border-radius: 50px;
            }

            .message .message-text {
                background: gray;
                color: white;
                border-radius: 50px;
                padding-left: 15px;
                padding-right: 15px;
            }

            a {
                color: rgb(0 255 29);
            }

            /* The Modal (background) */
            .modal {
                display: none; /* Hidden by default */
                position: fixed; /* Stay in place */
                z-index: 12; /* Sit on top */
                padding-top: 100px; /* Location of the box */
                left: 0;
                top: 0;
                width: 100%; /* Full width */
                height: 100%; /* Full height */
                overflow: auto; /* Enable scroll if needed */
                background-color: rgb(0,0,0); /* Fallback color */
                background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
            }

            /* Modal Content (image) */
            .modal-content {
                margin: auto;
                display: block;
                width: 80%;
                max-width: 1300px;
                height: 100%;
                position: relative;
                bottom: 50px;
            }

            /* Add Animation */
            .modal-content {
                -webkit-animation-name: zoom;
                -webkit-animation-duration: 0.6s;
                animation-name: zoom;
                animation-duration: 0.6s;
            }

            @-webkit-keyframes zoom {
                from {
                    -webkit-transform: scale(0)
                }
                to {
                    -webkit-transform: scale(1)
                }
            }

            @keyframes zoom {
                from {
                    transform: scale(0.1)
                }
                to {
                    transform: scale(1)
                }
            }

            /* The Close Button */
            .close {
                position: absolute;
                top: 15px;
                right: 35px;
                color: #f1f1f1;
                font-size: 40px;
                font-weight: bold;
                transition: 0.3s;
            }

            .close:hover,
            .close:focus {
                color: #bbb;
                text-decoration: none;
                cursor: pointer;
            }

            .btn-donate {
                position: relative;
                bottom: 300px;
                --clr-font-main: hsla(0 0% 20% / 100);
                --btn-bg-1: hsla(194 100% 69% / 1);
                --btn-bg-2: hsla(217 100% 56% / 1);
                --btn-bg-color: hsla(360 100% 100% / 1);
                --radii: 0.5em;
                cursor: pointer;
                padding: 0.9em 1.4em;
                min-width: 150px;
                min-height: 50px;
                font-size: 12px;
                font-family: "Segoe UI", system-ui, sans-serif;
                font-weight: 500;
                transition: 0.8s;
                background-size: 280% auto;
                background-image: linear-gradient(325deg, var(--btn-bg-2) 0%, var(--btn-bg-1) 55%, var(--btn-bg-2) 90%);
                border: none;
                border-radius: var(--radii);
                color: var(--btn-bg-color);
                box-shadow: 0px 0px 20px rgba(71, 184, 255, 0.5), 0px 5px 5px -1px rgba(58, 125, 233, 0.25), inset 4px 4px 8px rgba(175, 230, 255, 0.5), inset -4px -4px 8px rgba(19, 95, 216, 0.35);
            }

            .btn-donate:hover {
                background-position: right top;
            }

            .btn-donate:is(:focus, :focus-visible, :active) {
                outline: none;
                box-shadow: 0 0 0 3px var(--btn-bg-color), 0 0 0 6px var(--btn-bg-2);
            }

            @media (prefers-reduced-motion: reduce) {
                .btn-donate {
                    transition: linear;
                }
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
                <div style="display: flex; justify-content: center">
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
                </div>      
                <div>
                    <p class="app-sidebar__user-name"><b><%=informationOfUser.getFullName()!=null?informationOfUser.getFullName():""%></b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/investment-costs-servlet"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lí khoản phí đầu tư</span></a>
                </li> 
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/service-of-lodginghouse"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Dịch vụ trọ</span></a></li>
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/bill"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Hoá đơn thanh toán</span></a></li>  
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/chat"><i class='app-menu__icon bx bx-mail-send'></i>
                        <span class="app-menu__label">Tin nhắn</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">

                    <li class="breadcrumb-item active"><a href="#"><b>Tin nhắn</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="tile" style="padding: 0; margin-left: 0; margin-right: 0; margin-bottom: 0">
                        <div>
                            <div class="conversation-container">
                                <div class="left-side col-md-3 active" style="padding-top: 14px;">
                                    <p id="username" style="display: none">${sessionScope.account.accountID}</p>
                                    <p id="userAvatar" style="display: none">
                                        ${pageContext.request.contextPath}/Resource/images/avatar-default.jpg
                                    </p>
                                    <div class="list-user-search">
                                        <input type="text" class="txt-input" data-type="user" placeholder="Tìm kiếm người dùng..." onkeyup="searchUser(this)">
                                    </div>
                                    <div class="list-user">
                                        <ul>
                                            <c:forEach var="friend" items="${friends}">
                                                <li id=${friend.accountID} onclick="setReceiver(this)">
                                                    <div class="user-contain">
                                                        <div class="user-img">
                                                            <img id="img-${friend.accountID}"
                                                                 src="${pageContext.request.contextPath}/Resource/images/avatar-default.jpg"
                                                                 alt="Image of user">
                                                            <div id="status-${friend.accountID}" class="user-img-dot"></div>
                                                        </div>
                                                        <div class="user-info">
                                                            <span class="user-name">${friend.fullName}</span>
                                                        </div>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="right-side col-md-9" id="receiver" style="padding: 0; background-color: white;
                                     position: relative;">
                                    <!-- dùng JS để thêm nội dùng vào right side -->
                                </div>
                            </div>

                            <!-- The Modal -->
                            <div id="imageModal" class="modal">
                                <span class="close" onclick="closeModal()">&times;</span>
                                <img class="modal-content" id="modalImage">
                            </div>
                        </div>        
                    </div>
                </div>

            </div> 
        </main>                                

        <script type="text/javascript"
        src="<c:url value="/static/js/chatbox.js" />" charset="utf-8"></script>
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
    </body>

</html>