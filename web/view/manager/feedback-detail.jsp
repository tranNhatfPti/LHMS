<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Phản Hồi</title>
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
            .star{
                display: inline-block;
                width: 30px;
                height: 30px;
                margin-right: 10px;
                background-image: url('https://icon-library.com/images/star-png-icon/star-png-icon-0.jpg');
                background-size: contain;
                cursor: pointer;
            }
            .star:hover,
            .star.active{
                background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQK_x0xsTpZtA8UkG6hGE7Y8E-EXVXwT8NWbY0KymT2o_0pxmI&s');
            }
            .labelStar {
                font-size: 16px;
                font-weight: bold;
                margin-bottom: 10px;
            }
            .btn {
                margin-top: 10px; /* Adjust this value to change the spacing above the button */

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
                <li><a class="app-nav__item" href="/ManageLodgingHouse/LoginServlet?service=logout"><i class='bx bx-log-out'></i> </a>
                </li>
            </ul>
        </header>
        <%@ include file="left-menu-manager.jsp" %>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="feedback?service=showFeedback"><b>Chi Tiết Phản Hồi</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm nhap-tu-file" href="feedback?service=showManageFeedback&year=${year}&month=${month}" title="all"><i class="fas fa-arrow-left" ></i>
                                        Trở Về</a>
                                </div>
                                <div class="col-sm-4">
                                    <div><p class='labelStar'>Đánh Giá Của Người Dùng</p></div>
                                    <div id="rating" class="rating">
                                        <span class="star" data-rating="1"></span>
                                        <span class="star" data-rating="2"></span>
                                        <span class="star" data-rating="3"></span>
                                        <span class="star" data-rating="4"></span>
                                        <span class="star" data-rating="5"></span>
                                    </div>
                                </div>
                                <input type="hidden" name="rating" id="ratingInput" value="${star}">
                            </div>
                            <form action="feedback" method="post">
                                <table>
                                    <tr style="border-bottom: 0 none">
                                        <td>
                                            <div>
                                                <center>
                                                    <h2>
                                                        Nhận Xét Của Người Dùng</h2>
                                                </center>
                                                <span id="ctl00_mainContent_lblMege" style="color:Red;"></span>
                                                <table border="1">
                                                    <tr>
                                                        <td width="50%">
                                                            Họ Và Tên: 
                                                            <span id="ctl00_mainContent_lblGroup">${tenantInfor.fullName}</span>
                                                        </td>
                                                        <td width="50%">
                                                            Nhận Xét Cho Tháng: <span id="ctl00_mainContent_lblMonth">${feedbackMonthYear}</span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="50%">
                                                            Số Điện Thoại :<span id="ctl00_mainContent_lblSubject">${tenantInfor.phoneNumber}</span>
                                                        </td>
                                                        <td width="50%">
                                                            Email :
                                                            <span id="ctl00_mainContent_lblTeacher">${tenantAccount.email}</span>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table id="ctl00_mainContent_reload" cellspacing="1" cellpadding="2" border="0" style="border-width:1px;border-style:solid;width:100%;">
                                                    <tr>
                                                        <td width="50%">
                                                            <table width="100%" style="background-color: #efefef;" border="0">
                                                                <tr>
                                                                    <td>
                                                                        <font style="font-weight: bold">
                                                                        <span id="ctl00_mainContent_reload_ctl00__GroupQuestion">Dịch Vụ Vệ Sinh</span>
                                                                        </font>
                                                                        <table id="ctl00_mainContent_reload_ctl00_chkList" border="0">
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="clean_4" type="radio" name="clean" value="4" ${feedback.cleaningService==4?"checked":""} disabled/>
                                                                                    <label for="clean_4">Sạch Sẽ</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="clean_3" type="radio" name="clean" value="3" ${feedback.cleaningService==3?"checked":""} disabled/>
                                                                                    <label for="clean_3">Khá Sạch</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="clean_2" type="radio" name="clean" value="2" ${feedback.cleaningService==2?"checked":""} disabled/>
                                                                                    <label for="clean_2">Chưa Được Sạch Lắm</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="clean_1" type="radio" name="clean" value="1" ${feedback.cleaningService==1?"checked":""} disabled/>
                                                                                    <label for="clean_1">Còn Bẩn Rất Nhiều</label>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                        <td width="50%">
                                                            <table width="100%" style="background-color: #efefef;" border="0">
                                                                <tr>
                                                                    <td>
                                                                        <font style="font-weight: bold">
                                                                        <span id="ctl00_mainContent_reload_ctl01__GroupQuestion">Điện</span>
                                                                        </font>
                                                                        <table id="ctl00_mainContent_reload_ctl01_chkList" border="0">
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="electric_4" type="radio" name="electric" value="4" ${feedback.electric==4?"checked":""} disabled/>
                                                                                    <label for="electric_4">Khỏe, ổn định</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="electric_3" type="radio" name="electric" value="3" ${feedback.electric==3?"checked":""} disabled/>
                                                                                    <label for="electric_3">Tạm ổn</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="electric_2" type="radio" name="electric" value="2" ${feedback.electric==2?"checked":""} disabled/>
                                                                                    <label for="electric_2">Yếu</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="electric_1" type="radio" name="electric" value="1" ${feedback.electric==1?"checked":""} disabled/>
                                                                                    <label for="electric_1">Thường xuyên bị cắt điện</label>
                                                                                </td>
                                                                            </tr>
                                                                        </table>

                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="50%">
                                                            <table width="100%" style="background-color: #efefef;" border="0">
                                                                <tr>
                                                                    <td>
                                                                        <font style="font-weight: bold">
                                                                        <span id="ctl00_mainContent_reload_ctl02__GroupQuestion">Nước</span>
                                                                        </font>
                                                                        <table id="ctl00_mainContent_reload_ctl02_chkList" border="0">
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="water_4" type="radio" name="water" value="4" ${feedback.water==4?"checked":""} disabled />
                                                                                    <label for="water_4">Sạch sẽ, ổn định</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="water_3" type="radio" name="water" value="3" ${feedback.water==3?"checked":""} disabled />
                                                                                    <label for="water_3">Tạm ổn</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="water_2" type="radio" name="water" value="2" ${feedback.water==2?"checked":""} disabled />
                                                                                    <label for="water_2">Bẩn</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="water_1" type="radio" name="water" value="1" ${feedback.water==1?"checked":""} disabled />
                                                                                    <label for="water_1">Thường xuyên bị cắt nước</label>
                                                                                </td>
                                                                            </tr>
                                                                        </table>

                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                        <td width="50%">
                                                            <table width="100%" style="background-color: #efefef;" border="0">
                                                                <tr>
                                                                    <td>
                                                                        <font style="font-weight: bold">
                                                                        <span id="ctl00_mainContent_reload_ctl03__GroupQuestion">Mạng</span>
                                                                        </font>
                                                                        <table id="ctl00_mainContent_reload_ctl03_chkList" border="0">
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="internet_4" type="radio" name="internet" value="4" ${feedback.internet==4?"checked":""} disabled />
                                                                                    <label for="internet_4">Mạng Khỏe</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="internet_3" type="radio" name="internet" value="3" ${feedback.internet==3?"checked":""} disabled />
                                                                                    <label for="internet_3">Tạm ổn</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="internet_2" type="radio" name="internet" value="2" ${feedback.internet==2?"checked":""} disabled />
                                                                                    <label for="internet_2">Yếu</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="internet_1" type="radio" name="internet" value="1" ${feedback.internet==1?"checked":""} disabled />
                                                                                    <label for="internet_1">Hay bị mất mạng</label>
                                                                                </td>
                                                                            </tr>
                                                                        </table>

                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="50%">
                                                            <table width="100%" style="background-color: #efefef;" border="0">
                                                                <tr>
                                                                    <td>
                                                                        <font style="font-weight: bold">
                                                                        <span id="ctl00_mainContent_reload_ctl04__GroupQuestion">Giá Dịch Vụ</span>
                                                                        </font>
                                                                        <table id="ctl00_mainContent_reload_ctl04_chkList" border="0">
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="price_4" type="radio" name="price" value="4" ${feedback.servicePrice==4?"checked":""} disabled />
                                                                                    <label for="price_4">Rẻ, phù hợp với sinh viên và người lao động</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="price_3" type="radio" name="price" value="3" ${feedback.servicePrice==3?"checked":""} disabled />
                                                                                    <label for="price_3">Hợp lý</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="price_2" type="radio" name="price" value="2" ${feedback.servicePrice==2?"checked":""} disabled />
                                                                                    <label for="price_2">Hơi Đắt</label>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <input id="price_1" type="radio" name="price" value="1" ${feedback.servicePrice==1?"checked":""} disabled />
                                                                                    <label for="price_1">Rất Đắt</label>
                                                                                </td>
                                                                            </tr>
                                                                        </table>

                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                        <td>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <br/>
                                                <p><font style="font-style: italic; font-weight: bold">Nhận Xét Khác</font></p>
                                                <textarea name="otherResponse" rows="5" cols="70" id="ctl00_mainContent_txtComment" disabled>${feedback.otherResponse}</textarea>
                                                <br/>
                                                <br/>
                                                <hr/>
                                            </div>
                                        </td>
                                    </tr>

                                </table>
                            </form>

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
        const stars = document.querySelectorAll(".star");
        const ratingInput = document.getElementById("ratingInput");
        let initialRating = parseInt(ratingInput.value); // Lấy giá trị rating ban đầu từ input

        // Đánh dấu số sao tương ứng với initialRating
        stars.forEach((star, index) => {
            const starRating = parseInt(star.getAttribute("data-rating"));

            if (starRating <= initialRating) {
                star.classList.add("active");
            } else {
                star.classList.remove("active");
            }
        });
    </script>
</body>

</html> 