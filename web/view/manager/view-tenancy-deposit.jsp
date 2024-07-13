<%-- 
    Document   : room-detail.jsp
    Created on : Jun 13, 2024, 2:15:04 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip-utils/0.1.0/jszip-utils.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/docxtemplater/3.25.1/docxtemplater.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.0/FileSaver.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- SweetAlert CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <!-- SweetAlert JS -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <style>
            .fixed-size-image {
                width: 100%;
                height: auto;
                max-width: 100%; /* Ensures the image fits within the column */
            }
            .contract-container {
                margin: 20px;
                font-family: 'Times New Roman', Times, serif;
            }
            .contract-header {
                text-align: center;
                font-weight: bold;
            }
            .contract-section {
                margin-top: 20px;
            }
            .contract-section .title {
                font-weight: bold;
                text-decoration: underline;
            }
            .signature-section {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
            }
            .signature-section div {
                text-align: center;
                width: 45%;
            }

            .toast-message {
                position: fixed;
                top: 20px;
                right: 20px;
                background-color: #ffffff; /* Màu nền trắng */
                color: #000000; /* Màu chữ đen */
                padding: 15px;
                border: 2px solid #4CAF50; /* Viền màu xanh */
                border-radius: 5px;
                z-index: 1000;
                display: none;
                animation: slideIn 0.3s ease-in-out;
            }

            .toast-message h3 {
                margin-top: 0;
                font-size: 18px;
            }

            .toast-message p {
                margin-bottom: 0;
            }

            @keyframes slideIn {
                from {
                    transform: translateY(-100%);
                }
                to {
                    transform: translateY(0);
                }
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
            <ul class="app-menu">
                <li><a class="app-menu__item haha" href="phan-mem-ban-hang.html"><i class='app-menu__icon bx bx-cart-alt'></i>
                        <span class="app-menu__label">POS Bán Hàng</span></a></li>
                <li><a class="app-menu__item " href="index.html"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item " href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý khách hàng</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a>
                </li>
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span
                            class="app-menu__label">Quản lý đơn hàng</span></a></li>
                <li><a class="app-menu__item" href="table-data-banned.html"><i class='app-menu__icon bx bx-run'></i><span
                            class="app-menu__label">Quản lý nội bộ
                        </span></a></li>
                <li><a class="app-menu__item" href="table-data-money.html"><i class='app-menu__icon bx bx-dollar'></i><span
                            class="app-menu__label">Bảng kê lương</span></a></li>
                <li><a class="app-menu__item" href="quan-ly-bao-cao.html"><i
                            class='app-menu__icon bx bx-pie-chart-alt-2'></i><span class="app-menu__label">Báo cáo doanh thu</span></a>
                </li>
                <li><a class="app-menu__item" href="page-calendar.html"><i class='app-menu__icon bx bx-calendar-check'></i><span
                            class="app-menu__label">Lịch công tác </span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
                            đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <!--                <ul class="app-breadcrumb breadcrumb side">
                                    <li class="breadcrumb-item active"><a href="list-staff"><b>Danh sách sản phẩm</b></a></li>
                                </ul>-->
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <c:if test="${sessionScope.account.getRoleId() == 2}">
                                    <div class="col-sm-2">
<!--                                        <a class="btn btn-primary btn-sm"  href="add-tenancy-deposit?roomId=${sessionScope.roomId}">
                                            <i class="fa fa-plus-square" aria-hidden="true"></i>
                                            Thêm hợp đồng đặt cọc
                                        </a>-->
                                        <c:if test="${requestScope.tenantService == null}">
                                        <a class="btn btn-primary btn-sm" onclick="handleButtonClick(event)" href="#">
                                            <i class="fa fa-plus-square" aria-hidden="true"></i>
                                            Thêm hợp đồng đặt cọc
                                        </a>
                                        </c:if>    
                                    </div>


                                    <c:if test="${requestScope.tenancyDepositByRoomID.getTenancyDepositID() != null}">  
                                        <c:if test="${requestScope.tenancyDepositByRoomID.statusDeposit == 0 && requestScope.tenancyDepositByRoomID.statusDelete == 0}">
                                            <div class="col-sm-2">
                                                <button class="btn btn-container btn-sm" data-toggle="modal" data-target="#myModal">
                                                    <i class="fa fa-print" aria-hidden="true"></i>${requestScope.tenancyDepositByRoomID.statusDeposit == 1 ? ' Xem hợp đồng' :  ' In hợp đồng'}</button>
                                            </div>
                                            <div class="col-sm-2">
                                                <a id="deleteLink" class="btn btn-danger btn-sm" href="delete-tenancy-deposit?id=${requestScope.tenancyDepositByRoomID.getTenancyDepositID()}">
                                                    <i class="fa fa-trash" aria-hidden="true"></i>   
                                                    Xóa hợp đồng đặt cọc
                                                </a>
                                            </div>
                                            <div class="col-sm-2">
                                                <a id="changeStatusLink" class="btn btn-excel btn-sm" href="">
                                                    Thay đổi trạng thái hợp đồng 
                                                </a>
                                            </div>
                                        </c:if>

                                    </c:if> 
                                    <c:if test="${requestScope.tenancyDepositByRoomID.statusDeposit == 1}">
                                        <div class="col-sm-2">
                                            <a id="changeConfirmLink" class="btn btn-excel btn-sm" href=""><i class="fa fa-check-circle" aria-hidden="true"></i>
                                                Xác nhận hợp đồng
                                            </a>
                                        </div>
                                    </c:if>

                                </c:if>
                                <div class="col-sm-2">
                                    <a class="btn btn-secondary btn-sm" href="list-history-tenancy-deposit?roomId=${sessionScope.roomId}&statusAccept=1"><i class="fa fa-history" aria-hidden="true"></i>
                                        Xem lịch sử</a>
                                </div>  
                            </div>
                            <div class="row">

                                <div class="col-md-12">
                                    <h4><strong>Xem thông tin người đặt cọc</strong></h4>
                                    <c:if test="${requestScope.tenancyDepositByRoomID == null}">
                                        <c:if test="${requestScope.tenantService == null}"><span class="form-message-status">Phòng trọ chưa có người đặt cọc giữ phòng.</span></c:if>
                                        <c:if test="${requestScope.tenantService != null}"><span class="form-message-status">Phòng trọ đã có hợp đồng.</span></c:if>
                                    </c:if>
                                    <c:if test="${requestScope.tenancyDepositByRoomID != null}">

<!--                                        <form class="row" method="GET" enctype="multipart/form-data" action="update-tenancy-deposit?id=${requestScope.tenancyDepositByRoomID.tenancyDepositID}">-->
                                        <div class="row">
                                            <input class="form-control" type="text" maxlength="150" value="${sessionScope.roomId}" name="roomId" hidden>
                                            <div class="form-group col-sm-4">
                                                <label class="control-label">Họ và tên: (*)</label>
                                                <input class="form-control" type="text" maxlength="150" id="name" value="${requestScope.tenancyDepositByRoomID.getFullName()}" name="name" readonly="">
                                                <span id="form-message" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label class="control-label">Email: (*)</label>
                                                <input class="form-control" type="text" maxlength="150" id="email" value="${requestScope.tenancyDepositByRoomID.getEmail()}" name="email"readonly="" >
                                                <span id="form-message-email" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label class="control-label">Số điện thoại: (*)</label>
                                                <input class="form-control" type="text" maxlength="150" id="phoneNumber" value="${requestScope.tenancyDepositByRoomID.getPhoneNumber()}" name="phoneNumber" readonly="">
                                                <span id="form-message-phone" class="form-message"></span>
                                            </div>
                                            <fmt:setLocale value="vi_VN" />
                                            <div class="form-group col-sm-4">

                                                <label class="control-label">Tiền cọc(vnđ): (*)</label>
                                                <c:set var="formattedDepositMoney">
                                                    <fmt:formatNumber value="${requestScope.tenancyDepositByRoomID.getDepositMoney()}" type="number" pattern="#,##0" />
                                                </c:set>
                                                <input class="form-control" type="text" maxlength="150" id="depositMoney" 
                                                       value="${formattedDepositMoney}" name="depositMoney" readonly="">
                                                <span id="form-message-deposit" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label class="control-label">Ngày sinh: (*)</label>
                                                <input class="form-control" type="date" maxlength="150" id="dateOfBirth" value="${requestScope.tenancyDepositByRoomID.getDateOfBirth()}" name="dateOfBirth" readonly="">
                                                <span id="form-message-date" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-4">
                                                <label class="control-label">Căn cước công dân: (*)</label>
                                                <input class="form-control" type="text" maxlength="150" id="cic" value="${requestScope.tenancyDepositByRoomID.getCIC()}" name="cic" readonly="">
                                                <span id="form-message-cic" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label class="control-label">Ngày đặt cọc: (*)</label>
                                                <input class="form-control" type="date" maxlength="150" id="bookingDate" value="${requestScope.tenancyDepositByRoomID.getBookingDate()}" name="bookingDate" readonly="">
                                                <span id="form-message-bookingDate" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label class="control-label">Ngày đến nhận: (*)</label>
                                                <input class="form-control" type="date" maxlength="150" id="arriveDate" value="${requestScope.tenancyDepositByRoomID.getArriveDate()}" name="arriveDate" readonly="">
                                                <span id="form-message-arrivedate" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-12">
                                                <label class="control-label">Mô tả: (*)</label>
                                                <input class="form-control" type="text" maxlength="150" id="description" value="${requestScope.tenancyDepositByRoomID.getDescription()}" name="description" readonly="">
                                                <span id="form-message-description" class="form-message"></span>
                                            </div>
                                            <div class="form-group col-sm-12">
                                                <label class="control-label">Trạng thái: </label>
                                                <c:if test="${requestScope.tenancyDepositByRoomID.statusDeposit == 0}">
                                                    <span id="form-message-statusDeposit" class="form-message" style="color: red">Chưa ký</span>   
                                                </c:if>
                                                <c:if test="${requestScope.tenancyDepositByRoomID.statusDeposit == 1}">
                                                    <span id="form-message-statusDeposit" class="form-message primary" style="color: green"> Đã ký</span>   
                                                </c:if>
                                            </div>
                                            <!--                                        <div class="form-group col-sm-6">
                                                                                        <label class="btn btn-primary">
                                                                                            <input type="file" name="file">  Input type file hidden   
                                                                                        </label>
                                                                                    </div>-->
                                        </div>   
                                    </c:if>
                                    <div class="row">
                                        <c:if test="${requestScope.tenancyDepositByRoomID != null}">
                                            <c:if test="${requestScope.tenancyDepositByRoomID.statusDeposit == 0}">
                                                <div class="form-group col-sm-3">
                                                    <!--                                                <input type="submit" class="form-control btn btn-save" style=" text-align: center; margin: 10px" value="Thay đổi thông tin" />-->
                                                    <a href="update-tenancy-deposit?id=${requestScope.tenancyDepositByRoomID.tenancyDepositID}" class="form-control btn btn-save" type="submit" style=" text-align: center; margin: 10px">Thay đổi thông tin</a>
                                                </div>
                                            </c:if>
                                        </c:if>
                                        <div class="form-group col-sm-3">
                                            <a href="room-detail-servlet?id=${sessionScope.roomId}" class="form-control btn btn-warning" type="btn" style=" text-align: center; margin: 10px">Quay lại</a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </main>

        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Hợp đồng</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="contract-container">
                            <div class="contract-header">
                                <p>CỘNG HOÀ XÃ HỘI CHỦ NGHĨA VIỆT NAM</p>
                                <p>Độc Lập - Tự Do - Hạnh Phúc</p>
                                <p>---oOo---</p>
                                <p>HỢP ĐỒNG NHẬN TIỀN ĐẶT CỌC</p>
                                <p>( Về việc: Giao kết thuê/cho thuê phòng trọ)</p>
                                <p>- Căn cứ Bộ luật Dân sự 2015.</p>
                                <p>- Căn cứ Luật Thương mại năm 2005</p>
                                <p style="text-align: left;">Hôm nay ngày: ${requestScope.datePrintContract}. Tại địa chỉ: ${requestScope.lodgingHouseById.getAddressDetail()}, ${requestScope.lodgingHouseById.getWard()}, ${requestScope.lodgingHouseById.getDistrict()}, ${requestScope.lodgingHouseById.getProvince()}.</p>
                                <p style="text-align: left;">Chúng tôi những người ký tên dưới đây gồm:</p>
                            </div>

                            <div class="contract-section">
                                <p class="title">BÊN A : BÊN NHẬN TIỀN ĐẶT CỌC</p>
                                <p>Ông/bà: ${requestScope.managerInformation.getFullName()}.</p>
                                <p>Năm sinh: ${requestScope.managerInformation.getDob()}.</p>
                                <p>CMND số: ${requestScope.managerInformation.getCic()}.</p>
                                <p>Địa chỉ email: ${requestScope.managerAccount.getEmail()}.</p>
                                <p>Điện thoại: ${requestScope.managerInformation.getPhoneNumber()}.</p>
                                <p>Là người được ủy quyền quản lý toàn bộ căn phòng trọ nêu tại Điều 1 dưới đây.</p>
                                <p>(Sau đây gọi tắt là “Bên A”)</p>
                                <p>Và</p>
                                <p class="title">BÊN B : BÊN GIAO TIỀN ĐẶT CỌC</p>
                                <p>Ông/bà: ${requestScope.tenancyDepositByRoomID.getFullName()}.</p>
                                <p>Năm sinh: ${requestScope.tenancyDepositByRoomID.getDateOfBirth()}.</p>
                                <p>CMND số: ${requestScope.tenancyDepositByRoomID.getCIC()}.</p>
                                <p>Địa chỉ email: ${requestScope.tenancyDepositByRoomID.getEmail()}.</p>
                                <p>Điện thoại: ${requestScope.tenancyDepositByRoomID.getPhoneNumber()}.</p>
                                <p>(Sau đây gọi tắt là “Bên B”)</p>
                            </div>

                            <div class="contract-section">
                                <p>Hai Bên cùng nhau tiến hành lập hợp đồng này và thực hiện việc giao nhận tiền đặt cọc theo thỏa thuận bên A cho bên B thuê căn phòng trọ nêu tại Điều 1 theo các điều, khoản thỏa thuận của “Hợp đồng thuê phòng trọ” phù hợp quy định của pháp luật Việt Nam hiện hành cùng với các thỏa thuận quy định sau:</p>
                            </div>

                            <div class="contract-section">
                                <p class="title">Điều 1: NỘI DUNG GIAO KẾT</p>
                                <p>Bên A đồng ý cho bên B thuê căn phòng trọ tại địa chỉ: ${requestScope.lodgingHouseById.getAddressDetail()}, ${requestScope.lodgingHouseById.getWard()}, ${requestScope.lodgingHouseById.getDistrict()}, ${requestScope.lodgingHouseById.getProvince()}.</p>
                                <p>- Giá cho thuê / tháng:  <fmt:formatNumber value="${requestScope.tenancyDepositByRoomID.getDepositMoney()}" type="number" pattern="#,##0"/> vnđ.</p>
                                <p>- Số người ở tối đa/phòng: ${requestScope.room.getMaxOfQuantity()}.</p>
                                <p>(Sau đây gọi tắt là “Căn phòng”)</p>
                            </div>

                            <div class="contract-section">
                                <p class="title">Điều 2: TIỀN ĐẶT CỌC VÀ THỜI HẠN GIAO KẾT</p>
                                <p>2.1 Bên A đã nhận đủ số tiền do bên B giao là <fmt:formatNumber value="${requestScope.tenancyDepositByRoomID.getDepositMoney()}" type="number" pattern="#,##0"/>  vnđ; Tiền đặt cọc này sẽ được bên A hoàn trả lại bên B sau khi ký “Hợp đồng thuê phòng trọ” chính thức.</p>
                                <p>2.2 Thời hạn giao kết hai Bên sẽ ký kết “Hợp đồng thuê phòng trọ/nhà trọ” là ${requestScope.tenancyDepositByRoomID.getArriveDate()}.</p>
                            </div>

                            <div class="contract-section">
                                <p class="title">Điều 3: CAM KẾT CỦA HAI BÊN</p>
                                <p>3.1 Cam kết của bên A:</p>
                                <p>Bên A cam kết căn phòng nêu tại điều 1.1 là tài sản thuộc quyền sở hữu và sử dụng của mình, các thành viên có quyền đồng sở hữu và sử dụng hoàn toàn đồng ý cho bên B thuê, tại thời điểm hai bên giao kết biên bản này căn phòng nêu trên không bị thế chấp, kê biên hoặc tranh chấp.</p>
                                <p>Bên A không cho bất kỳ một bên nào khác thuê căn phòng nêu tại Điều 1.1 trong thời hạn giao kết.</p>
                                <p>3.2 Cam kết của bên B:</p>
                                <p>Bên B chắc chắn thuê căn phòng của bên A nêu tại Điều 1 trong thời hạn giao kết; Khi thuê Căn phòng không sử dụng phòng thuê để kinh doanh các mặt hàng quốc cấm.</p>
                            </div>

                            <div class="contract-section">
                                <p class="title">Điều 4: PHẠT DO VI PHẠM CAM KẾT</p>
                                <p>4.1 Bên A vi phạm cam kết sẽ phải trả lại cho bên B toàn bộ số tiền bên B đã đặt cọc nêu tại Điều 2.1; Đồng thời bên A phải bồi thường cho bên B một khoản tiền bằng với số tiền bên B đã đặt cọc cho bên A để bên B tìm địa điểm thuê khác.</p>
                                <p>4.2 Bên B vi phạm cam kết sẽ bị mất toàn bộ số tiền bên B đã đặt cọc cho bên A nêu tại Điều 2.1 để bên A tìm khách hàng thuê khác.</p>
                            </div>

                            <div class="contract-section">
                                <p class="title">Điều 5: ĐIỀU KHOẢN CHUNG</p>
                                <p>5.1 Hai bên xác định hoàn toàn tự nguyện khi giao kết biên bản này, cam kết cùng nhau thực hiện nghiêm túc những điều đã thỏa thuận trong biên bản này.</p>
                                <p>5.2. Nếu có phát sinh tranh chấp, hai bên cùng nhau thương lượng giải quyết trên nguyên tắc hòa giải, cùng có lợi. Nếu không giải quyết được, thì một trong hai bên có quyền khởi kiện để yêu cầu tòa án có thẩm quyền giải quyết theo quy định của pháp luật. Bên thua kiện phải chịu trả toàn bộ các chi phí liên quan đến vụ kiện, kể cả chi phí thuê luật sư cho bên thắng kiện.</p>
                                <p>5.3 Ngoài các điều khoản đã nêu trong hợp đồng này; các điều khoản khác được thực hiện theo quy định của pháp luật.</p>
                                <p>5.4 Hợp đồng này có hiệu lực kể từ ngày ký; Được lập thành 02 (hai) bản, có giá trị pháp lý như nhau, mỗi bên giữ 01 (một) bản.</p>
                            </div>
                            <p style="text-align: right">${requestScope.lodgingHouseById.getProvince()}, ${requestScope.datePrintContract}.</p>
                            <div class="signature-section">
                                <div class="container">
                                    <p>BÊN NHẬN TIỀN ĐẶT CỌC (BÊN A)</p>
                                    <p style="text-align: center">(ký tên)</p>
                                </div>
                                <div class="container">
                                    <p>BÊN GIAO TIỀN ĐẶT CỌC (BÊN B)</p>
                                    <p style="text-align: center">(ký tên)</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        <c:if test="${requestScope.tenancyDepositByRoomID.statusDeposit != 1}">
                            <button type="button" class="btn btn-primary" id="printContractButton">Lưu thay đổi</button>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>                              
        <c:if test="${not empty notice}">
            <div class="toast-message" id="toastMessage">
                <h3>Thông báo</h3> <!-- Thay "Title of the Toast" bằng title thực của bạn -->
                <p>${notice}</p> <!-- Hiển thị nội dung toast message -->
            </div>
        </c:if>                    
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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>


        <!-- Thư viện jsPDF -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.3.1/jspdf.umd.min.js"></script>

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
            $(document).ready(function () {
                $('#deleteLink').on('click', function (event) {
                    event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

                    var href = $(this).attr('href'); // Lấy URL từ thuộc tính href của thẻ a

                    // Hiển thị SweetAlert
                    Swal.fire({
                        title: 'Bạn có chắc chắn?',
                        text: "Chưa đến hạn 15 ngày kể từ khi liên hệ, bạn có muốn xóa hợp đồng giữ phòng!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Đồng ý',
                        cancelButtonText: 'Hủy'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Nếu người dùng nhấn nút "Đồng ý", chuyển hướng đến trang servlet
                            window.location.href = href;
                        }
                    });
                });
            });
        </script>
        <script>
            $(document).ready(function () {
                var tenancyDepositID = ${requestScope.tenancyDepositByRoomID.getTenancyDepositID()}; // Lấy giá trị tenancyDepositID từ server

                $('#changeStatusLink').on('click', function (event) {
                    event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

                    Swal.fire({
                        title: 'Thay đổi trạng thái hợp đồng giữ phòng.',
                        text: "Chọn hành động của bạn",
                        icon: 'question',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Chấp nhận hợp đồng',
                        cancelButtonText: 'Hủy'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Hiển thị SweetAlert thông báo xác nhận thứ hai
                            Swal.fire({
                                title: 'Xác nhận chấp nhận hợp đồng',
                                text: 'Bạn có thể chấp nhận hợp đồng đặt cọc khi đã kí với người đặt cọc. Bạn có muốn tiếp tục?',
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonColor: '#3085d6',
                                cancelButtonColor: '#d33',
                                confirmButtonText: 'Chấp nhận',
                                cancelButtonText: 'Hủy bỏ'
                            }).then((innerResult) => {
                                if (innerResult.isConfirmed) {
                                    // Điều hướng đến servlet chấp nhận hợp đồng với tenancyDepositID
                                    window.location.href = 'change-status-tenancy-deposit?services=accept&tenancyDepositID=' + tenancyDepositID;
//                                    $.get('change-status-tenancy-deposit?services=accept&tenancyDepositID=' + tenancyDepositID, function (response) {
//                                        Swal.fire({
//                                            title: 'Thành công!',
//                                            text: 'Chấp nhận đặt cọc thành công.',
//                                            icon: 'success'
//                                        });
//                                    });
                                } // Nếu người dùng nhấp vào "Hủy bỏ", không làm gì thêm
                            });
                        }
                        //                        else if (result.dismiss === Swal.DismissReason.cancel) {
                        //                            // Hiển thị SweetAlert thông báo khác khi người dùng nhấp vào nút Hủy hợp đồng
                        //                            Swal.fire({
                        //                                title: 'Thông báo!',
                        //                                text: 'Bạn chỉ có thể hủy khi người thuê đã hủy hợp đồng đặt cọc phòng.',
                        //                                icon: 'info',
                        //                                confirmButtonText: 'Hủy hợp đồng'
                        //                            }).then((innerResult) => {
                        //                                // Chỉ gửi yêu cầu tới servlet nếu điều kiện canCancel là true
                        //                                if (innerResult.isConfirmed) {
                        //                                    $.get('change-status-tenancy-deposit?services=cancel&tenancyDepositID=' + tenancyDepositID, function (response) {
                        //                                        Swal.fire({
                        //                                            title: 'Thành công!',
                        //                                            text: 'Hủy đặt cọc thành công.',
                        //                                            icon: 'success'
                        //                                        }).then(() => {
                        //                                            // Chuyển hướng sau khi hiển thị thông báo thành công
                        //                                            window.location.href = 'view-tenancy-deposit?roomID=' + ${sessionScope.roomId};
                        //                                        });
                        //                                    });
                        //                                }
                        //                            });
                        //                        }
                    });
                });
            });
            $(document).ready(function () {
                var tenancyDepositID = ${requestScope.tenancyDepositByRoomID.getTenancyDepositID()}; // Lấy giá trị tenancyDepositID từ server

                $('#changeConfirmLink').on('click', function (event) {
                    event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết
                    Swal.fire({
                        title: 'Chấp nhận',
                        text: "Người đặt cọc giữ phòng đã đến ký hợp đồng ?",
                        icon: 'question',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Xác nhận',
                        cancelButtonText: 'Hủy'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            // Hiển thị SweetAlert thông báo xác nhận thứ hai
                            Swal.fire({
                                title: 'Người đặt cọc đến nhận phòng đúng hạn ?',
                                text: '',
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonColor: '#3085d6',
                                cancelButtonColor: '#d33',
                                confirmButtonText: 'Chấp nhận',
                                cancelButtonText: 'Hủy bỏ'
                            }).then((innerResult) => {
                                if (innerResult.isConfirmed) {
                                    window.location.href = 'change-status-tenancy-deposit?services=confirm&tenancyDepositID=' + tenancyDepositID;
                                    // Điều hướng đến servlet chấp nhận hợp đồng với tenancyDepositID
//                                    $.get('change-status-tenancy-deposit?services=confirm&tenancyDepositID=' + tenancyDepositID, function (response) {
//                                        Swal.fire({
//                                            title: 'Thành công!',
//                                            text: 'Chấp nhận đặt cọc thành công.',
//                                            icon: 'success'
//                                        });
//                                    });
                                } // Nếu người dùng nhấp vào "Hủy bỏ", không làm gì thêm
                            });
                        }
                    });
                });
            });
        </script>
        <script>
            const tenancyDepositByRoomID = ${requestScope.tenancyDepositByRoomID != null ? 'true' : 'false'};
            const roomId = '${sessionScope.roomId}';
            const contractRoom = ${requestScope.tenantService != null};
            function handleButtonClick(event) {
                event.preventDefault(); // Ngăn chặn hành động mặc định của thẻ a

                if ((tenancyDepositByRoomID === true)) {
                    // Hiển thị SweetAlert nếu đã có hợp đồng đặt cọc
                    Swal.fire({
                        icon: 'info',
                        title: 'Thông báo',
                        text: 'Phòng này đã có hợp đồng cọc giữ phòng.'
                    });
                }  else {
                    // Chuyển hướng đến đường dẫn nếu không có hợp đồng đặt cọc
                    window.location.href = `add-tenancy-deposit?roomId=${roomId}`;
                }
            }

//            && (contractRoom.getStatusAccept() !== 3 && contractRoom.getStatusDelete() === 1)
        </script>
        <script>
            document.getElementById('printContractButton').addEventListener('click', function () {
                // Trích xuất nội dung của modal-body
                var modalBodyContent = document.querySelector('#myModal .modal-body').innerHTML;

                // Mở một cửa sổ mới
                var win = window.open('', '', 'height=700,width=700');

                // Ghi nội dung vào cửa sổ mới
                win.document.write('<html><head>');
                win.document.write('<title>Hợp đồng</title>'); // Bạn có thể thay đổi tiêu đề nếu muốn
                win.document.write('<style>');
                win.document.write('body { font-family: Arial, sans-serif; margin: 20px; }'); // Thêm bất kỳ style nào bạn cần
                win.document.write('.fixed-size-image { width: 100%; height: auto; max-width: 100%; }'); // Kiểu dáng cho ảnh cố định
                win.document.write('.contract-container { margin: 20px; font-family: \'Times New Roman\', Times, serif; }'); // Kiểu dáng cho phần container
                win.document.write('.contract-header { text-align: center; font-weight: bold; }'); // Kiểu dáng cho tiêu đề
                win.document.write('.contract-section { margin-top: 20px; }'); // Kiểu dáng cho các phần
                win.document.write('.contract-section .title { font-weight: bold; text-decoration: underline; }'); // Kiểu dáng cho tiêu đề của các phần
                win.document.write('.signature-section { display: flex; justify-content: space-between; margin-top: 20px; }'); // Kiểu dáng cho phần chữ ký
                win.document.write('.signature-section div { text-align: center; width: 45%; }'); // Kiểu dáng cho các phần chữ ký
                win.document.write('@page { size: auto; margin: 15mm 10mm; }'); // Đặt lề của trang in và margin
                win.document.write('@media print { body { -webkit-print-color-adjust: exact; } }'); // Đảm bảo in màu sắc chính xác trên Google Chrome
                win.document.write('</style></head><body>');
                win.document.write(modalBodyContent);
                win.document.write('</body></html>');

                // Đóng tài liệu để hoàn tất việc ghi
                win.document.close();

                // Chờ một chút cho tài liệu tải xong rồi mới in
                win.onload = function () {
                    win.print();
                    //win.close();
                };
            });
        </script>
        <script>
            window.onload = function () {
                var toastMessage = document.getElementById("toastMessage");
                if (toastMessage) {
                    toastMessage.style.display = "block";
                    setTimeout(function () {
                        toastMessage.style.display = "none";
                    }, 10000); // 3 seconds, adjust as needed
                }
            };
        </script>

    </body>
</html>
