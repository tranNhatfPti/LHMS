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
            .rating {
                position: relative;
                display: flex;
            }

            .rating {
                display: flex;
                gap: 8px; /* Adjust spacing between stars if needed */
            }

            .star {
                width: 40px;
                height: 40px;
                display: inline-block;
                background: url('https://icon-library.com/images/star-png-icon/star-png-icon-0.jpg') no-repeat center center;
                background-size: contain;
                position: relative;
            }

            .star::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQK_x0xsTpZtA8UkG6hGE7Y8E-EXVXwT8NWbY0KymT2o_0pxmI&s') no-repeat center center;
                background-size: contain;
                clip-path: polygon(0 0, var(--percent, 0%) 0, var(--percent, 0%) 100%, 0 100%);
            }
            .labelStar {
                font-size: 16px;
                font-weight: bold;
                margin-bottom: 10px;
            }
            .ratingBlock {
                display: flex;
                flex-direction: column;
                align-items: flex-start;
                align-items: center;
                gap: 10px; /* Adjust this value to change the spacing between elements */
            }
            .star1{
                display: inline-block;
                width: 30px;
                height: 30px;
                margin-right: 10px;
                background-image: url('https://icon-library.com/images/star-png-icon/star-png-icon-0.jpg');
                background-size: contain;
                cursor: pointer;
            }
            .star1:hover,
            .star1.active{
                background-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQK_x0xsTpZtA8UkG6hGE7Y8E-EXVXwT8NWbY0KymT2o_0pxmI&s');
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
        <%
            
            Integer star1 = (Integer) request.getAttribute("star1");
    Integer star2 = (Integer) request.getAttribute("star2");
    Integer star3 = (Integer) request.getAttribute("star3");
    Integer star4 = (Integer) request.getAttribute("star4");
    Integer star5 = (Integer) request.getAttribute("star5");
    
            Integer clean1 = (Integer) request.getAttribute("clean1");
    Integer clean2 = (Integer) request.getAttribute("clean2");
    Integer clean3 = (Integer) request.getAttribute("clean3");
    Integer clean4 = (Integer) request.getAttribute("clean4");
            
            Integer electric1 = (Integer) request.getAttribute("electric1");
    Integer electric2 = (Integer) request.getAttribute("electric2");
    Integer electric3 = (Integer) request.getAttribute("electric3");
    Integer electric4 = (Integer) request.getAttribute("electric4");
    
       Integer water1 = (Integer) request.getAttribute("water1");
    Integer water2 = (Integer) request.getAttribute("water2");
    Integer water3 = (Integer) request.getAttribute("water3");
    Integer water4 = (Integer) request.getAttribute("water4");
            
    Integer price1 = (Integer) request.getAttribute("price1");
    Integer price2 = (Integer) request.getAttribute("price2");
    Integer price3 = (Integer) request.getAttribute("price3");
    Integer price4 = (Integer) request.getAttribute("price4");
    Integer internet1 = (Integer) request.getAttribute("internet1");
    Integer internet2 = (Integer) request.getAttribute("internet2");
    Integer internet3 = (Integer) request.getAttribute("internet3");
    Integer internet4 = (Integer) request.getAttribute("internet4");
        %>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="feedback?service=showStatistic"><b>Thống Kê</b></a></li>
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
                                
                            </div>
                            <div class="row">
                                <div class="col-sm-6 ratingBlock" style="display:flex;justify-content: center;">
                                    <div><p class='labelStar'>Đánh Giá Của Người Dùng</p></div>
                                    <div id="averageRating" class="rating">
                                        <span class="star" data-rating="1"></span>
                                        <span class="star" data-rating="2"></span>
                                        <span class="star" data-rating="3"></span>
                                        <span class="star" data-rating="4"></span>
                                        <span class="star" data-rating="5"></span>
                                    </div>
                                    <input type="hidden" name="rating" id="averageRatingInput" value="${averageStar}">
                                    <div class="row">
                                        <div class="col-md-12 table_service">
                                            <div id="star" style="width: 500px;"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6" id="clean" style="width: 900px; height: 500px;"></div>

                            </div>
                            <div class="row">
                                <div class="col-sm-6" id="electric" style="width: 900px; height: 500px;"></div>
                                <div class="col-sm-6" id="water" style="width: 900px; height: 500px;"></div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6" id="internet" style="width: 900px; height: 500px;"></div>
                                <div class="col-sm-6" id="price" style="width: 900px; height: 500px;"></div>
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
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
const stars = document.querySelectorAll(".star1");
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
    });</script>
<script type="text/javascript">
        google.charts.load("current", {packages: ["corechart"]});
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Task', 'Hours per Day'],
                ['Sạch Sẽ', <%= clean4 != null ? clean4 : 0 %>],
                ['Khá Sạch', <%= clean3 != null ? clean3 : 0 %>],
                ['Chưa Được Sạch Lắm', <%= clean2 != null ? clean2 : 0 %>],
                ['Còn Bẩn Rất Nhiều', <%= clean1 != null ? clean1 : 0 %>],
            ]);

            var options = {
                title: 'Đánh Giá Dịch Vụ Vệ Sinh',
                is3D: true,
            };

            var chart = new google.visualization.PieChart(document.getElementById('clean'));
            chart.draw(data, options);
        }
</script>
<script type="text/javascript">
    google.charts.load("current", {packages: ["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Khỏe, ổn định', <%= electric4 != null ? electric4 : 0 %>],
            ['Tạm ổn', <%= electric3 != null ? electric3 : 0 %>],
            ['Yếu', <%= electric2 != null ? electric2 : 0 %>],
            ['Thường xuyên bị cắt điện', <%= electric1 != null ? electric1 : 0 %>],
        ]);

        var options = {
            title: 'Đánh Giá Dịch Vụ Điện',
            is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('electric'));
        chart.draw(data, options);
    }
</script>
<script type="text/javascript">
    google.charts.load("current", {packages: ["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Sạch Sẽ, ổn định', <%= water4 != null ? water4 : 0 %>],
            ['Tạm Ổn', <%= water3 != null ? water3 : 0 %>],
            ['Bẩn', <%= water2 != null ? water2 : 0 %>],
            ['Thường xuyên bị cắt nước', <%= water1 != null ? water1 : 0 %>],
        ]);

        var options = {
            title: 'Đánh Giá Dịch Vụ Nước',
            is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('water'));
        chart.draw(data, options);
    }
</script>
<script type="text/javascript">
    google.charts.load("current", {packages: ["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Mạng Khỏe', <%= internet4 != null ? internet4 : 0 %>],
            ['Tạm ổn', <%= internet3 != null ? internet3 : 0 %>],
            ['Yếu', <%= internet2 != null ? internet2 : 0 %>],
            ['Hay bị mất mạng', <%= internet1 != null ? internet1 : 0 %>],
        ]);

        var options = {
            title: 'Đánh Giá Internet',
            is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('internet'));
        chart.draw(data, options);
    }
</script>




<script type="text/javascript">
    google.charts.load("current", {packages: ["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Rẻ, phù hợp với sinh viên và người lao động', <%= price4 != null ? price4 : 0 %>],
            ['Hợp lý', <%= price3 != null ? price3 : 0 %>],
            ['Hơi Đắt', <%= price2 != null ? price2 : 0 %>],
            ['Rất Đắt', <%= price1 != null ? price1 : 0 %>],
        ]);

        var options = {
            title: 'Đánh Giá Phí Dịch Vụ',
            is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('price'));
        chart.draw(data, options);
    }
</script>
<script type="text/javascript">
    google.charts.load("current", {packages: ["corechart"]});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['5 Sao', <%= star5 != null ? star5 : 0 %>],
            ['4 Sao', <%= star4 != null ? star4 : 0 %>],
            ['3 Sao', <%= star3 != null ? star3 : 0 %>],
            ['2 Sao', <%= star2 != null ? star2 : 0 %>],
            ['1 Sao', <%= star1 != null ? star1 : 0 %>]
        ]);

        var options = {
            is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('star'));
        chart.draw(data, options);
    }
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const stars = document.querySelectorAll("#averageRating .star");
        const averageRatingInput = document.getElementById("averageRatingInput");
        const averageRating = parseFloat(averageRatingInput.value);

        stars.forEach(star => {
            const starRating = parseFloat(star.getAttribute("data-rating"));
            if (starRating <= averageRating) {
                star.style.setProperty('--percent', '100%');
            } else if (starRating - 1 < averageRating && averageRating < starRating) {
                const percent = ((averageRating % 1) * 100) + '%';
                star.style.setProperty('--percent', percent);
            } else {
                star.style.setProperty('--percent', '0%');
            }
        });
    });
</script>


</body>

</html> 