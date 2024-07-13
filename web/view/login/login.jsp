<%-- 
    Document   : index-demo
    Created on : May 15, 2024, 4:09:43 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.ServletContext, model.Account, jakarta.servlet.http.Cookie" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <title>Đăng nhập hệ thống</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/vendor/animate/animate.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/vendor/css-hamburgers/hamburgers.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/vendor/select2/select2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/css/util.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/css/main.css">
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-sweetalert/1.0.1/sweetalert.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

        <style>
            /* Shared */
            .loginBtn {
                box-sizing: border-box;
                position: relative;
                /* width: 13em;  - apply for fixed size */
                margin: 0.2em;
                padding: 0 15px 0 46px;
                border: none;
                text-align: left;
                line-height: 34px;
                white-space: nowrap;
                border-radius: 0.2em;
                font-size: 16px;
                color: #FFF;
            }
            .loginBtn:before {
                content: "";
                box-sizing: border-box;
                position: absolute;
                top: 0;
                left: 0;
                width: 34px;
                height: 100%;
            }
            .loginBtn:focus {
                outline: none;
            }
            .loginBtn:active {
                box-shadow: inset 0 0 0 32px rgba(0,0,0,0.1);
            }


            /* Facebook */
            .loginBtn--facebook {
                background-color: #4C69BA;
                background-image: linear-gradient(#4C69BA, #3B55A0);
                /*font-family: "Helvetica neue", Helvetica Neue, Helvetica, Arial, sans-serif;*/
                text-shadow: 0 -1px 0 #354C8C;
            }
            .loginBtn--facebook:before {
                border-right: #364e92 1px solid;
                background: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/14082/icon_facebook.png') 6px 6px no-repeat;
            }
            .loginBtn--facebook:hover,
            .loginBtn--facebook:focus {
                background-color: #5B7BD5;
                background-image: linear-gradient(#5B7BD5, #4864B1);
            }


            /* Google */
            .loginBtn--google {
                /*font-family: "Roboto", Roboto, arial, sans-serif;*/
                background: #DD4B39;
            }
            .loginBtn--google:before {
                border-right: #BB3F30 1px solid;
                background: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/14082/icon_google.png') 6px 6px no-repeat;
            }
            .loginBtn--google:hover,
            .loginBtn--google:focus {
                background: #E74B37;
            }

            .google {
                display: flex;
                justify-content: center;
                margin-top: 25px;
            }

            .custom-line {
                border: none; /* Loại bỏ đường kẻ mặc định */
                height: 1px; /* Độ dày của đường kẻ */
                background-color: #000; /* Màu của đường kẻ */
                margin: 20px 0; /* Khoảng cách trên và dưới của đường kẻ */
            }

            input:checked + label {
                color: green;
            }

            .validate-input.error input {
                border-color: #e74c3c;
            }

            small {
                color: #e74c3c;
                bottom: -20px;
                left: 0;
                visibility: hidden;
                font-style: italic;
                font-size: 10px;
            }

            .validate-input.error small {
                visibility: visible;
            }

        </style>

    </head>

    <body>
        <!-- Declare you variable -->
        <c:set var="cookie" value="${pageContext.request.cookies}"/>       
        <%   
            // Lấy mảng cookie từ request
        Cookie[] cookies = request.getCookies();

        // Khởi tạo biến để lưu giá trị của cookie mong muốn
        String passwordToken = null;

        // Kiểm tra nếu cookies không null
        if (cookies != null) {
            // Duyệt qua từng cookie
            for (Cookie cookie : cookies) {
                // Kiểm tra tên cookie
                if ("passwordLHMS".equals(cookie.getName())) {
                    // Lấy giá trị của cookie
                    passwordToken = cookie.getValue();
                    break;
                }
            }
        }
            
            // Lấy ServletContext từ pageContext
            ServletContext servletContext = pageContext.getServletContext();
            
            // lấy ra mật khẩu tương ứng
            String password = "";
            if(passwordToken != null){
                password = (String) servletContext.getAttribute(passwordToken);
            }
        %>

        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100">
                    <div class="login100-pic js-tilt" data-tilt>
                        <img src="${pageContext.request.contextPath}/Resource/images/team.jpg" alt="IMG">
                    </div>
                    <!--=====TIÊU ĐỀ======-->
                    <div class="login100-form validate-form">
                        <span class="login100-form-title">
                            <b>ĐĂNG NHẬP HỆ THỐNG</b>
                        </span>
                        <!--=====FORM INPUT TÀI KHOẢN VÀ PASSWORD======-->
                        <form action="/ManageLodgingHouse/LoginServlet" method="post" id="form-login">
                            <div class="wrap-input100 validate-input" style="margin-bottom: 0;">
                                <input class="input100" type="text" placeholder="Tài Khoản" name="username"
                                       id="username" value="${cookie.usernameLHMS.value}" required="">
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i style="margin-bottom: 25px" class='bx bx-user'></i>
                                </span>
                                <small>haha</small>
                            </div>                   
                            <div class="wrap-input100 validate-input" style="margin-bottom: 0;">
                                <input autocomplete="off" class="input100" type="password" placeholder="Mật khẩu"
                                       name="password" id="password-field" value="<%=password!=null?password:""%>" required="">
                                <span toggle="#password-field" class="bx fa-fw bx-hide field-icon click-eye"></span>
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i style="margin-bottom: 25px" class='bx bx-key'></i>
                                </span>
                                <small>haha</small>
                            </div>
                            <div class="checkbox-container" style="margin-bottom: 5px;">
                                <input type="checkbox" id="remember-me" name="remember-me" value="remembe-me">
                                <label for="remember-me" style="font-size: 12px; font-style: italic; margin: 0">Nhớ mật khẩu</label>
                            </div>

                            <!--=====ĐĂNG NHẬP======-->
                            <div class="container-login100-form-btn">
                                <input id="submit-form" type="button" value="Đăng nhập" onclick="checkCorrectPassword()" style="background: lightsalmon; border: silver;"/>
                            </div>
                            <!--=====LINK TÌM MẬT KHẨU======-->
                            <div class="text-right p-t-12" style="margin-top: 5px">
                                <a class="txt2" href="/ManageLodgingHouse/view/login/forgot.jsp">
                                    Bạn quên mật khẩu?
                                </a>
                            </div>
                            <div class="text-right p-t-12">
                                <a class="txt2" href="/ManageLodgingHouse/view/login/register.jsp">
                                    Bạn chưa đăng kí?
                                </a>
                            </div>
                            <hr class="custom-line">
                            <div class="google">
                                <div>
                                    <button class="loginBtn loginBtn--facebook">
                                        <a href="https://www.facebook.com/v19.0/dialog/oauth?scope=email&client_id=388338590876128&redirect_uri=http://localhost:9999/ManageLodgingHouse/LoginByFacebook" 
                                           style="color: white">Login with Facebook</a>
                                    </button>
                                </div>
                                <div>
                                    <button class="loginBtn loginBtn--google">
                                        <a href="https://accounts.google.com/o/oauth2/auth?scope=openid profile email&redirect_uri=http://localhost:9999/ManageLodgingHouse/LoginByGoogle&response_type=code&client_id=381681113764-hsaf7orbp826di9c40annfk7fidqmgiq.apps.googleusercontent.com&approval_prompt=force" 
                                           style="color: white">Login with Google</a>              
                                    </button>
                                </div>                                         
                            </div>
                            <input type="hidden" name="service" value="login">
                        </form>                 
                    </div>
                    <!--=====FOOTER======-->
                    <div class="text-center p-t-70 txt2">
                        Phần mềm quản lý phòng trọ <i class="far fa-copyright" aria-hidden="true"></i>
                        <script type="text/javascript">document.write(new Date().getFullYear());</script>
                    </div>
                </div>
            </div>
        </div>
        <!--Javascript-->
        <script src="${pageContext.request.contextPath}/Resource/js/main.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/vendor/jquery/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/vendor/bootstrap/js/popper.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/vendor/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/Resource/vendor/select2/select2.min.js"></script>
        <script type="text/javascript">
                            //show - hide mật khẩu
                            function myFunction() {
                                var x = document.getElementById("myInput");
                                if (x.type === "password") {
                                    x.type = "text";
                                } else {
                                    x.type = "password";
                                }
                            }
                            $(".click-eye").click(function () {
                                $(this).toggleClass("bx-show bx-hide");
                                var input = $($(this).attr("toggle"));
                                if (input.attr("type") == "password") {
                                    input.attr("type", "text");
                                } else {
                                    input.attr("type", "password");
                                }
                            });
        </script>
        <script type="text/javascript">
            const usernameEle = document.getElementById('username');
            const passwordEle = document.getElementById('password-field');

            function checkCorrectPassword() {
                const usernameEle = document.getElementById('username');
                const passwordEle = document.getElementById('password-field');

                if (usernameEle.value !== "" && passwordEle.value !== "") {
                    fetch('/ManageLodgingHouse/LoginServlet?username=' + usernameEle.value + '&password=' + passwordEle.value + '&service=checkCorrectPassword')
                            .then(response => {
                                if (response.ok) {
                                    return response.text();
                                }
                            })
                            .then(data => {
                                // Xử lý phản hồi từ servlet
                                if (data === 'WRONG') {
                                    setError(passwordEle, 'Tài khoản hoặc mật khẩu không đúng');
                                    setError(usernameEle, 'Tài khoản hoặc mật khẩu không đúng');
                                } else {
                                    // khi dùng fetch api không được đặt id:submit cho input:submit vì sẽ gây ra xung đột và không nộp được form
                                    document.getElementById('form-login').submit();
                                }
                            })
                            .catch(error => {
                                console.error('Error:', error);
                            });
                }

                if (usernameEle.value === "") {
                    setError(usernameEle, 'Vui lòng nhập tài khoản');
                }

                if (passwordEle.value === "") {
                    setError(passwordEle, 'Vui lòng nhập mật khẩu');
                }
            }

            function setError(ele, message) {
                let parentEle = ele.parentNode;
                parentEle.classList.add('error');
                parentEle.querySelector('small').innerText = message;
            }
        </script>
    </body>

</html>
