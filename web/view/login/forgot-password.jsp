<%-- 
    Document   : index-demo
    Created on : May 15, 2024, 4:09:43 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.ServletContext, model.Account" %>
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
            .wrap-login100{
                justify-content: center;
            }

            .validate-input.success input {
                border-color: #2ecc71;
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

            .validate-input.success small {
                visibility: visible;
            }

            h3{
                color: brown;
                font-style: italic;
                font-family: cursive;
            }
        </style>

    </head>

    <body>
        <%
            // lấy token từ tham số truyền đến từ mail để lấy ra account object muốn đổi mật khẩu
            String user_token = (String) request.getParameter("user_token");
            
            // Lấy ServletContext từ pageContext
            ServletContext servletContext = pageContext.getServletContext();
            
            Account account = (Account) servletContext.getAttribute(user_token);      
        %>
        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100">
                    <%
                        // nếu account khác null(chưa xoá trong servletcontext) sẽ điền vào form đổi mk
                        if(account != null){
                    %>
                    <!--=====TIÊU ĐỀ======-->
                    <div class="login100-form validate-form">
                        <span class="login100-form-title">
                            <b>LẤY LẠI MẬT KHẨU</b>
                        </span>                      
                        <!--=====FORM INPUT TÀI KHOẢN VÀ PASSWORD======-->
                        <form action="/ManageLodgingHouse/LoginServlet" method="post" id="form-register">
                            <div class="wrap-input100 validate-input">
                                <input autocomplete="off" class="input100" type="password" placeholder="Nhập mật khẩu mới" name="new-password"
                                       id="password1" maxlength="31" required="">
                                <span id="show-pass1" toggle="#password-field" class="bx fa-fw bx-hide field-icon click-eye"></span>
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i style="margin-bottom: 25px" class='bx bx-key'></i>
                                </span>
                                <small>a</small>
                            </div>                   
                            <div class="wrap-input100 validate-input">
                                <input autocomplete="off" class="input100" type="password" placeholder="Nhập lại mật khẩu"
                                       name="repeat-password" id="password2" maxlength="31" required="">
                                <span id="show-pass2" toggle="#password-field" class="bx fa-fw bx-hide field-icon click-eye"></span>
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i style="margin-bottom: 25px" class='bx bx-key'></i>
                                </span>
                                <small>a</small>
                            </div>

                            <!--=====ĐĂNG NHẬP======-->
                            <div class="container-login100-form-btn" style="margin-top: 20px">
                                <input type="button" value="Lưu" id="button-submit" onclick="validate()" style="background: lightsalmon; border: silver;"/>
                            </div>                        

                            <input type="hidden" name="user_token" value="<%= request.getParameter("user_token") %>">
                            <input type="hidden" name="service" value="change-forgot-password">
                        </form>                      
                    </div>
                    <%} else {%>
                    <h3>Bạn đã thay đổi mật khẩu thành công</h3>
                    <%}%>
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
                                    x.type = "text"
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
        <!-- validate password with length <= 30, strength of password, new-pass and repeat-pass is similar -->
        <script type="text/javascript">
            // show và hidden mật khẩu khi đăng kí
            document.getElementById('show-pass1').addEventListener('click', function () {
                const passwordInput = document.getElementById('password1');
                const check = (passwordInput.parentNode.querySelector('.bx-hide') !== null);

                // nếu không có class name: bx-hide nào có nghĩa là đang show (đang là class name: bx-hid)
                if (check) {
                    passwordInput.type = 'password';
                } else {
                    passwordInput.type = 'text';
                }
            });

            document.getElementById('show-pass2').addEventListener('click', function () {
                const passwordInput = document.getElementById('password2');
                const check = (passwordInput.parentNode.querySelector('.bx-hide') !== null);

                // nếu không có class name: bx-hide nào có nghĩa là đang show (đang là class name: bx-hid)
                if (check) {
                    passwordInput.type = 'password';
                } else {
                    passwordInput.type = 'text';
                }
            });
        </script>
        <script type="text/javascript">
            const password1Ele = document.getElementById('password1');
            const password2Ele = document.getElementById('password2');

            const btnRegister = document.getElementById('button-submit');

            btnRegister.addEventListener('click', function () {
                const inputEles = document.querySelectorAll('.validate-input');
                Array.from(inputEles).map((ele) =>
                    ele.classList.remove('success', 'error')
                );

                // gọi đến hàm checkValidate()
                let isValid = checkValidate();

                if (isValid) {
                    document.getElementById('form-register').submit();
                }
            });

            function checkValidate() {
                let isCheck = true;

                if (password1Ele.value === '') {
                    setError(password1Ele, 'Mật khẩu không được để trống');
                    isCheck = false;
                } else if (!validateStrengthPasswordDetail(password1Ele.value)) {
                    setError(password1Ele, 'Mật khẩu không đúng định dạng');
                    isCheck = false;
                }

                let msRepeatPass = (password2Ele.parentNode.querySelector('small').textContent === 'Mật khẩu trùng khớp');
                if (password2Ele.value === '') {
                    setError(password2Ele, 'Mật khẩu không được để trống');
                    isCheck = false;
                } else if (!msRepeatPass) {
                    setError(password2Ele, 'Mật khẩu không trùng khớp');
                    isCheck = false;
                }

                return isCheck;
            }

            // DOMContentLoaded: xảy ra ngay khi HTML hoàn thành phân tích cú pháp
            document.addEventListener('DOMContentLoaded', (event) => {
                // validate strength of password
                validateStrengthPassword(password1Ele);

                // validate length of password
                validateLengthPassword(password1Ele);

                // validate length of repeat-password
                validateLengthPassword(password2Ele);

                // check simalarity for 2 password
                checkSimilarity();
            });

            function validateLengthPassword(inputEle) {
                inputEle.addEventListener('input', () => {
                    inputEle.value = inputEle.value.replace(/ /g, '');
                    
                    if (inputEle.value.length >= inputEle.maxLength) {
                        inputEle.value = inputEle.value.substring(0, inputEle.value.length - 1);
                        setError(inputEle, 'Mật khẩu không được quá 30 kí tự');
                    }
                });
            }

            function checkSimilarity() {
                password2Ele.addEventListener('input', () => {
                    if (password2Ele.value !== password1Ele.value) {
                        setError(password2Ele, 'Mật khẩu không trùng khớp');
                    } else {
                        // xoá đi cảnh báo mật khẩu trước đó và đổi màu border
                        password2Ele.parentNode.classList.remove('success', 'error');
                        setSuccess(password2Ele, 'Mật khẩu trùng khớp');
                    }
                });
            }

            function validateStrengthPassword(inputEle) {
                inputEle.addEventListener('input', () => {
                    const passwordValue = inputEle.value;
                    var message = 'Mật khẩu gồm ít nhất 8 kí tự, chữ thường, chữ hoa, số và kí tự đặc biệt';
                    setError(inputEle, message);

                    if (validateStrengthPasswordDetail(passwordValue)) {
                        if (passwordValue.length !== inputEle.maxLength - 1) {
                            // xoá đi cảnh báo mật khẩu trước đó và đổi màu border
                            const inputEles = document.querySelectorAll('.validate-input');
                            Array.from(inputEles).map((ele) =>
                                ele.classList.remove('success', 'error')
                            );
                            // set lại thông báo hợp lệ
                            setSuccess(inputEle, 'Mật khẩu hợp lệ');
                        } else {
                            setError(inputEle, 'Mật khẩu không được quá 30 kí tự');
                        }
                    }
                });
            }

            function validateStrengthPasswordDetail(passwordValue) {
                var countStrength = 0;

                if (passwordValue.length >= 8) {
                    countStrength++;
                }

                if (passwordValue.match(/[a-z]/)) {
                    countStrength++;
                }

                if (passwordValue.match(/[A-Z]/)) {
                    countStrength++;
                }

                if (passwordValue.match(/\d/)) {
                    countStrength++;
                }

                if (passwordValue.match(/[^a-zA-Z\d]/)) {
                    countStrength++;
                }

                if (countStrength === 5) {
                    return true;
                }

                return false;
            }

            function setSuccess(ele, message) {
                let parentEle = ele.parentNode;
                parentEle.classList.add('success');
                parentEle.querySelector('small').innerText = message;
                parentEle.querySelector('small').style.color = 'green';
            }

            function setError(ele, message) {
                let parentEle = ele.parentNode;
                parentEle.classList.add('error');
                parentEle.querySelector('small').innerText = message;
                parentEle.querySelector('small').style.color = '#e74c3c';
            }
        </script>
    </body>

</html>

