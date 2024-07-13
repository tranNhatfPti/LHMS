<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Chi Phí Phát Sinh</title>
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
            F
        </style>
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <!-- Sidebar menu-->
        <%@ include file="header.jsp" %>
        <%@ include file="left-menu-tenant.jsp" %>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="cost-incurred?service=showListCost&index=1&pay=all"><b>Chi Phí Phát Sinh</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm nhap-tu-file" href="cost-incurred?service=showListCost&index=1&pay=all" title="all"><i class="fas fa-list" ></i>
                                        Tất Cả</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm pdf-file" href="cost-incurred?service=showListCost&index=1&pay=unpaid" title="dpay"><i
                                            class="fas fa-times"></i> Chưa Thanh Toán</a>
                                </div>
                                <div class="col-sm-2">
                                    <a class="btn btn-add btn-sm" href="cost-incurred?service=showListCost&index=1&pay=paid" title="pay"><i class="fas fa-check"></i>
                                        Đã Thanh Toán</a>
                                </div>
                            </div>
                            <table class="table table-hover table-bordered" id="sampleTable">
                                <thead>
                                    <tr>
                                        <th>Mã Biên Lai</th>
                                        <th>Mô Tả</th>
                                        <th>Thời Gian</th>
                                        <th>Giá Tiền</th>
                                        <th>Tình trạng</th>
                                        <th>Hành Động</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listReceipt}" var="lr">
                                        <tr>
                                            <td>${lr.receiptId}</td>
                                            <td>${lr.description}</td>
                                            <td>${lr.dateTime}</td>
                                            <td>${lr.price} VNĐ</td>
                                            <td><span class=" ${lr.status==true?"badge bg-success":"badge bg-danger"}">${lr.status==true?"Đã Thanh Toán":"Chưa Thanh Toán"}</span></td>
                                            <td><c:if test="${lr.status!=true}"><a href="cost-incurred?service=pay&id=${lr.receiptId}"><span class="badge bg-success">Thanh Toán</span></a></c:if></td>
                                            </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-md-5">
                                <div class="dataTable_info" id="sampleTable_info" role="status" aria-live="polite">Tổng ${totalReceipt} Chi Phí Phát Sinh ${pay=="paid" ? 'Đã Thanh Toán' : (pay=="unpaid" ? 'Chưa Thanh Toán' : '')}</div>
                            </div>
                            <div class="col-sm-12 col-md-7">
                                <div class="dataTables_paginate paging_simple_numbers" id="sampleTable_paginate">
                                    <ul class="pagination">
                                        <li class="paginate_button page-item previous ${index==1?"disabled":""}" id="sampleTable_previous"><a href="cost-incurred?service=showListCost&index=${index-1}&pay=${pay}" aria-controls="sampleTable" data-dt-idx="0" tabindex="0" class="page-link">Lùi</a>
                                        </li>
                                        <c:forEach begin="1" end="${endPage}" var="i">
                                            <li class="paginate_button page-item ${i == index?"active":""}"><a href="cost-incurred?service=showListCost&index=${i}&pay=${pay}" aria-controls="sampleTable" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="paginate_button page-item next ${index==endPage || endPage==0?"disabled":""}" id="sampleTable_next"><a href="cost-incurred?service=showListCost&index=${index+1}&pay=${pay}" aria-controls="sampleTable" data-dt-idx="3" tabindex="0" class="page-link">Tiếp</a>
                                        </li>
                                    </ul>
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
</body>

</html> 