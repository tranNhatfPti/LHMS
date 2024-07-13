<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Quản Lý Phản Hồi</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <style>
            .avatar{
                width: 200px !important;
                height: 200px !important;
                border-radius: 50% !important;
                object-fit: cover !important;
            }
            .avatarHeader{
                width: 100px !important;
                height: 100px !important;
                border-radius: 50% !important;
                object-fit: cover !important;
            }
            .badge {
                display: inline-block;
                padding: 7px;
                font-size: 12px;
                font-weight: 500;
                line-height: 1;
                text-align: center;
                white-space: nowrap;
                vertical-align: baseline;
                border-radius: 0.25rem;
                color: white;
            }

            .form-control {
                width: 100%;
                box-sizing: border-box;

            }
            .styled-select {
                width: 100px;
                padding: 6px;
                font-size: 14px;
                line-height: 1.2;
                border: 1px solid #ccc;
                border-radius: 4px;
                background-color: #fff;
                -webkit-appearance: none;
                -moz-appearance: none;
                appearance: none;
                background-repeat: no-repeat;
                background-position: right 10px center;
                background-size: 16px 16px;
            }
            .styled-select::-ms-expand {
                display: none;
            }
        </style>
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <!-- Sidebar menu-->
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
        <%@ include file="left-menu-manager.jsp" %>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="feedback?service=showManageFeedback"><b>Quản Lý Đánh Giá</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm nhap-tu-file" href="feedback?service=showStatistic&year=${year}&month=${month}" title="all"><i class="fas fa-list" ></i>
                                        Thống Kê</a>
                                </div>
                                <div>
                                    <select  id="select-year" name="year" class="styled-select btn btn-excel btn-sm">
                                        <option value="" disabled selected>Chọn Năm</option>
                                        <option value="2024">2024</option>
                                        <option value="2023">2023</option>
                                        <option value="2022">2022</option>
                                        <option value="2021">2021</option>
                                    </select>
                                </div>
                                <div  id="month-select-container" >
                                    <select  id="select-month" name="month" class="styled-select btn btn-delete btn-sm print-file" style="margin-left: 14px;">
                                        <option value="" disabled selected>Chọn Tháng</option>
                                        <option value="1">Tháng 1</option>
                                        <option value="2">Tháng 2</option>
                                        <option value="3">Tháng 3</option>
                                        <option value="4">Tháng 4</option>
                                        <option value="5">Tháng 5</option>
                                        <option value="6">Tháng 6</option>
                                        <option value="7">Tháng 7</option>
                                        <option value="8">Tháng 8</option>
                                        <option value="9">Tháng 9</option>
                                        <option value="10">Tháng 10</option>
                                        <option value="11">Tháng 11</option>
                                        <option value="12">Tháng 12</option>
                                    </select>
                                </div>
                                <div class="col-sm-auto">
                                    <form action="feedback" method="GET" style="display: flex;">
                                        <input type="hidden" name="service" value="showManageFeedback">
                                        <input type="hidden" name="month" value="${month}">
                                        <input type="hidden" name="year" value="${year}">
                                        <input type="text" name="roomSearch" class="form-control" placeholder="Tìm kiếm theo phòng" aria-label="Tìm kiếm..." style="flex: 1 1 auto; height: 31px;">
                                        <button class="btn btn-delete btn-sm pdf-file" type="submit" title="Tìm kiếm" style="flex: 1 1 auto; margin-left:0px; height: 31px;"><i class="fas fa-search"></i></button>
                                    </form>
                                </div>
                            </div>
                            <table class="table table-hover table-bordered" id="sampleTable">
                                <thead>
                                    <tr>
                                        <th>Phòng</th>
                                        <th>Tên Người Thuê Trọ</th>
                                        <th>Thời Gian</th>
                                        <th>Trạng Thái Đánh Giá</th>
                                        <th>Hành Động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listFeedback}" var="lf">
                                        <tr>
                                            <td>${lf.roomName}</td>
                                            <td>${lf.name}</td>
                                            <td>${lf.monthYear}</td>
                                            <td><span class=" ${lf.statusFeedback==1?"badge bg-success":"badge bg-danger"}">${lf.statusFeedback==1?"Đã Đánh Giá":"Chưa Đánh Giá"}</span></td>
                                            <td><c:if test="${lf.statusFeedback==1}"><a><a href="feedback?service=showFeedbackDetail&tenantId=${lf.tenantId}&monthYear=${lf.monthYear}"><span class="badge bg-info">Xem Chi Tiết Đánh Giá</span></a></c:if></td>
                                            </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <c:if test="${checkEmpty>0 && check==false}">
                                <div class="row element-button">
                                    <div class="col-sm-2">
                                        <a class="btn btn-add btn-sm" href="create-feedback" title="all"><i class="fas fa-plus" ></i>
                                            Tạo Đánh Giá Cho Tháng Này</a>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-md-5">
                                <div class="dataTable_info" id="sampleTable_info" role="status" aria-live="polite">Tổng ${totalRoom} Phòng</div>
                            </div>
                            <div class="col-sm-12 col-md-7">
                                <div class="dataTables_paginate paging_simple_numbers" id="sampleTable_paginate">
                                    <ul class="pagination">
                                        <li class="paginate_button page-item previous ${index==1?"disabled":""}" id="sampleTable_previous"><a href="feedback?service=showManageFeedback&year=${year}&month=${month}&index=${index-1}" aria-controls="sampleTable" data-dt-idx="0" tabindex="0" class="page-link">Lùi</a>
                                        </li>
                                        <c:forEach begin="1" end="${endPage}" var="i">
                                            <li class="paginate_button page-item ${i == index?"active":""}"><a href="feedback?service=showManageFeedback&year=${year}&month=${month}&index=${i}" aria-controls="sampleTable" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="paginate_button page-item next ${index==endPage || totalRoom==0 ?"disabled":""}" id="sampleTable_next"><a href="feedback?service=showManageFeedback&year=${year}&month=${month}&index=${index+1}" aria-controls="sampleTable" data-dt-idx="3" tabindex="0" class="page-link">Tiếp</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- Essential javascripts for application to work-->
<script src="https://esgoo.net/scripts/jquery.js"></script>
<script src="${pageContext.request.contextPath}/Resource/doc/js/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="${pageContext.request.contextPath}/Resource/doc/js/popper.min.js"></script>
<script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
<!--===============================================================================================-->
<script src="${pageContext.request.contextPath}/Resource/doc/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="${pageContext.request.contextPath}/Resource/doc/js/main.js"></script>
<!--===============================================================================================-->
<script src="${pageContext.request.contextPath}/Resource/js/profile.js"></script>
<!--===============================================================================================-->
<script src="${pageContext.request.contextPath}/Resource/doc/js/plugins/pace.min.js"></script>
<!--===============================================================================================-->
<script type="text/javascript" src="${pageContext.request.contextPath}/Resource/doc/js/plugins/chart.js"></script>
<script type="text/javascript">
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
    document.addEventListener('DOMContentLoaded', function () {
        var yearSelect = document.getElementById('select-year');
        var monthSelect = document.getElementById('select-month');
        var monthSelectContainer = document.getElementById('month-select-container');

        // Khi chọn năm


        // Khi chọn tháng
        monthSelect.addEventListener('change', function () {
            var year = yearSelect.value;
            var month = monthSelect.value;

            if (year && month) {
                var url = 'feedback?service=showManageFeedback';
                url += '&year=' + year;
                url += '&month=' + month;
                window.location.href = url;
            }
        });
    });
</script>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Function to get URL parameter
        function getParameterByName(name, url = window.location.href) {
            name = name.replace(/[\[\]]/g, '\\$&');
            var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
                    results = regex.exec(url);
            if (!results)
                return null;
            if (!results[2])
                return '';
            return decodeURIComponent(results[2].replace(/\+/g, ' '));
        }

        // Set selected value for year
        var selectedYear = getParameterByName('year');
        if (selectedYear) {
            document.getElementById('select-year').value = selectedYear;
        }

        // Set selected value for month
        var selectedMonth = getParameterByName('month');
        if (selectedMonth) {
            document.getElementById('select-month').value = selectedMonth;
        }
    });
</script>



</body>

</html> 