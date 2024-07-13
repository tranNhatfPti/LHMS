<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Hồ Sơ Người Dùng</title>
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
            .changeAvatar{
                font-weight: bold;
                color: black;
            }
            .error {
                color: red;
                font-style: italic;
            }
            .avatar-container {
                position: relative;
                display: inline-block;
            }

            .avatar {
                width: 200px;
                height: 200px;
                border-radius: 50%;
                object-fit: cover;
            }

            .overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                border-radius: 50%;
                background: rgba(0, 0, 0, 0.5);
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                opacity: 0;
                transition: opacity 0.3s;
            }

            .avatar-container:hover .overlay {
                opacity: 1;
            }

            .file-upload {
                position: absolute;
                width: 100%;
                height: 100%;
                top: 0;
                left: 0;
                opacity: 0;
                cursor: pointer;
            }

            .upload-info {
                margin-top: 10px;
                text-align: center;
            }

            .upload-info progress {
                width: 100%;
                height: 20px;
                border-radius: 10px;
                overflow: hidden;
            }

            .upload-info span {
                display: block;
                margin-top: 10px;
                font-weight: bold;
                color: black
            }

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
                    <li class="breadcrumb-item active"><a href="account?service=showProfile"><b>Hồ Sơ Cá Nhân</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="container bootstrap snippet">
                        <div class="row">
                            <div class="col-sm-4 text-center">
                                <div class="avatar-container">
                                    <img src="${userInfor.avatar}" class="avatar img-circle img-thumbnail" alt="avatar">
                                    <div class="overlay">
                                        <span>Change Avatar</span>
                                        <input type="file" class="file-upload" value="upload" accept=".jpg" id="fileButton">
                                    </div>
                                </div>
                                <div class="upload-info">
                                    <progress value="0" max="100" id="uploader" style="display: none;">0%</progress>
                                    <span id="avatarMessage" class="avatarMessage"></span>
                                </div>
                            </div>

                            <div class="col-sm-8">
                                <div class="tab-content">
                                    <form class="form" action="account" method="post" id="registrationForm" onsubmit="return validateForm()">
                                        <c:if test="${account.getTypeOfLogin()=='username'}">
                                            <div class="form-group row">
                                                <label for="userName" class="col-sm-3 col-form-label"><h4>User Name</h4></label>
                                                <div class="col-sm-6">
                                                    <input type="text" class="form-control" name="userName" id="userName" placeholder="User Name" title="enter your user name." value="${account.username}" readonly>
                                                    <span id="userNameError" class="error"></span>
                                                    <span id="userNameError1" class="error1"></span>
                                                </div>
                                            </div>
                                        </c:if>
                                        <div class="form-group row">
                                            <label for="email" class="col-sm-3 col-form-label"><h4>Email</h4></label>
                                            <div class="col-sm-6">
                                                <input type="email" class="form-control" name="email" id="email" placeholder="Email" title="enter your email." value="${account.email}" readonly>
                                                <span id="emailError" class="error"></span>
                                                <span id="emailError1" class="error1"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="full_name" class="col-sm-3 col-form-label"><h4>Họ và Tên</h4></label>
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" name="fullName" id="first_name" placeholder="Họ và Tên" title="enter your first name if any." value="${userInfor.fullName}" <c:if test="${checkContract eq 'True'}">readonly</c:if>>
                                                <span id="fullNameError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="phone" class="col-sm-3 col-form-label"><h4>Số Điện Thoại</h4></label>
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" name="phone" id="last_name" placeholder="Số Điện Thoại" title="enter your phone number." value="${userInfor.phoneNumber}" <c:if test="${checkContract eq 'True'}">readonly</c:if>>
                                                <span id="phoneError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="dob" class="col-sm-3 col-form-label"><h4>Ngày Sinh</h4></label>
                                            <div class="col-sm-6">
                                                <input type="date" class="form-control" name="dob" id="dob" placeholder="Ngày Sinh" title="enter your date of birth." value="${userInfor.dob}" <c:if test="${checkContract eq 'True'}">readonly</c:if>>
                                                <span id="dobError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="gender" class="col-sm-3 col-form-label"><h4>Giới Tính</h4></label>
                                            <div class="col-sm-6">
                                                <select class="form-control" id="gender" name="gender" title="Chọn Giới Tính" required <c:if test="${checkContract eq 'True'}">disabled</c:if>>
                                                    <c:choose>
                                                        <c:when test="${userInfor.gender == 1}">
                                                            <option value="1">Nam</option>
                                                            <option value="2">Nữ</option>
                                                            <option value="3">Khác</option>
                                                        </c:when>
                                                        <c:when test="${userInfor.gender == 2}">
                                                            <option value="2">Nữ</option>
                                                            <option value="1">Nam</option>
                                                            <option value="3">Khác</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="3">Khác</option>
                                                            <option value="1">Nam</option>
                                                            <option value="2">Nữ</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="province" class="col-sm-3 col-form-label"><h4>Tỉnh Thành</h4></label>
                                            <div class="col-sm-6">
                                                <select class="form-control" id="tinh" name="province" title="Chọn Tỉnh Thành" <c:if test="${checkContract eq 'True'}">disabled</c:if>>
                                                    <option value="${userInfor.province}" selected>${userInfor.province}</option>
                                                    <!-- Options loaded dynamically -->
                                                </select>
                                                <span id="provinceError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="district" class="col-sm-3 col-form-label"><h4>Quận Huyện</h4></label>
                                            <div class="col-sm-6">
                                                <select class="form-control" id="quan" name="district" title="Chọn Quận Huyện" <c:if test="${checkContract eq 'True'}">disabled</c:if>>
                                                    <option value="${userInfor.district}" selected>${userInfor.district}</option>
                                                    <!-- Options loaded dynamically -->
                                                </select>
                                                <span id="districtError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="ward" class="col-sm-3 col-form-label"><h4>Phường Xã</h4></label>
                                            <div class="col-sm-6">
                                                <select class="form-control" id="phuong" name="ward" title="Chọn Xã Phường" <c:if test="${checkContract eq 'True'}">disabled</c:if>>
                                                    <option value="${userInfor.ward}" selected>${userInfor.ward}</option>
                                                    <!-- Options loaded dynamically -->
                                                </select>
                                                <span id="wardError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="address" class="col-sm-3 col-form-label"><h4>Địa Chỉ Cụ Thể</h4></label>
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" name="address" id="address" placeholder="Nhập Địa Chỉ Cụ Thể" title="enter your address." value="${userInfor.addressDetail}" <c:if test="${checkContract eq 'True'}">readonly</c:if>>
                                                <span id="addressError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label for="cic" class="col-sm-3 col-form-label"><h4>Số CCCD</h4></label>
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" name="cic" id="cic" placeholder="Căn Cước Công Dân" title="enter your CIC." value="${userInfor.cic}" <c:if test="${checkContract eq 'True'}">readonly</c:if>>
                                                <span id="cicError" class="error"></span>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <div class="col-sm-6">
                                                <input class="form-control" name="avatar" type="text" id="avatar123" value="${userInfor.avatar}" style="display : none">
                                                <div id="imgDiv" style="display : none"></div>
                                            </div>
                                        </div>
                                        <c:if test="${requestProfile eq 'Yes'}"><input type="hidden" name="sendInfor" value="sendInfor"></c:if>        
                                        <c:if test="${checkContract eq 'False'}">
                                            <div class="form-group row">
                                                <div class="col-xs-12">
                                                    <c:if test="${requestProfile eq 'No'}"><button class="btn btn-lg btn-success" type="submit"><i class="glyphicon glyphicon-ok-sign"></i>Lưu Lại Thông Tin</button></c:if>
                                                    <c:if test="${requestProfile eq 'Yes'}"><button class="btn btn-lg btn-success" type="submit"><i class="glyphicon glyphicon-ok-sign"></i>Gửi Thông Tin Cho Quản Lý</button>
                                                    </c:if>
                                                    <button class="btn btn-lg" type="reset"><i class="glyphicon glyphicon-repeat"></i>Đặt Lại</button>
                                                </div>
                                                <input type="hidden" name="service" id="service" value="update">
                                            </div>     
                                        </c:if>
                                        <c:if test="${checkContract eq 'True'}">
                                            <div class="form-group row">
                                                <div class="col-xs-12">
                                                    <button class="btn btn-lg btn-success" type="submit"><i class="glyphicon glyphicon-ok-sign"></i>Yêu Cầu Chỉnh Sửa Thông Tin</button>
                                                </div>
                                                <input type="hidden" name="service" id="service" value="requestUpdate">
                                            </div>     
                                        </c:if>
<!--                                        <div>${test}</div>-->
                                    </form>
                                </div><!--/tab-content-->
                            </div><!--/col-9-->
                        </div><!--/row-->
                    </div><!--/container-->
                </div>
            </div>
        </main>
        <!-- Essential javascripts for application to work-->
        <script src="https://www.gstatic.com/firebasejs/4.2.0/firebase.js"></script>
        <script src="https://esgoo.net/scripts/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/js/address.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/js/validate_profile.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/Resource/js/uploadImage.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/js/profile.js"></script>
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