<%-- 
    Document   : forgot
    Created on : May 20, 2024, 9:48:37 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Khôi phục mật khẩu | Website quản trị v2.0</title>
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
            .wrap-input100{
                margin-bottom: 2px;
            }
        </style>
    </head>

    <body>
        <%
            String fromServlet = (String)request.getAttribute("fromServlet");
        %>

        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100">
                    <div class="login100-pic js-tilt" data-tilt>
                        <img src="${pageContext.request.contextPath}/Resource/images/fg-img.png" alt="IMG">
                    </div>
                    <form action="/ManageLodgingHouse/LoginServlet" method="post" class="login100-form validate-form">
                        <span class="login100-form-title">
                            <b>KHÔI PHỤC MẬT KHẨU</b>
                        </span>
                        <form action="custommer.html">
                            <div class="wrap-input100 validate-input"
                                 data-validate="Bạn cần nhập đúng thông tin như: ex@abc.xyz">
                                <input class="input100" type="email" placeholder="Nhập email" name="email"
                                       id="email" value="" />
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i class='bx bx-mail-send' ></i>
                                </span>
                            </div>
                            <%
                                String msForgotEmail = (String) request.getAttribute("msForgotEmail");
                                String msEmailGG = (String) request.getAttribute("msEmailGG");
                                if(msForgotEmail != null){
                            %>
                            <strong style="font-size: 12px;
                                    font-style: italic;
                                    color: red;"><%=msForgotEmail%></strong>
                            <%
                                } else if (msEmailGG != null) {
                            %>
                            <strong style="font-size: 12px;
                                    font-style: italic;
                                    color: red;"><%=msEmailGG%></strong>
                            <%         
                                }
                            %>
                            <div class="container-login100-form-btn" style="margin-top: 8px">
                                <input id="submit" type="submit" onclick="return RegexEmail('emailInput')" value="Lấy mật khẩu" style="background: lightsalmon; border: silver;"/>
                                <input type="hidden" name="service" value="forgot-password">
                            </div>

                            <div class="text-center p-t-12">
                                <a class="txt2" href="/ManageLodgingHouse/view/login/login.jsp">
                                    Trở về đăng nhập
                                </a>
                            </div>
                        </form>
                        <div class="text-center p-t-70 txt2">
                            Phần mềm quản lý phòng trọ <i class="far fa-copyright" aria-hidden="true"></i>
                            <script type="text/javascript">document.write(new Date().getFullYear());</script>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!--===============================================================================================-->
        <script src="/js/main.js"></script>
        <!--===============================================================================================-->
        <script src="vendor/jquery/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="vendor/bootstrap/js/popper.js"></script>
        <!--===============================================================================================-->
        <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="vendor/select2/select2.min.js"></script>
        <!--===============================================================================================-->

    </body>
</html>
