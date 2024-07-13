<%-- 
    Document   : register
    Created on : May 15, 2024, 10:14:41 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            :root {
                --w_316:350px;
                --w300:340px;
            }

            .container-login100-form-btn {
                padding-top: 0;
            }

            input:checked + label {
                color: green;
            }

            #form-register div {
                margin: 0;
            }

            #form-register div {
                margin-bottom: 10px;
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
                font-size: 12px;
                font-style: italic;
                font-size: 10px;
            }

            .validate-input.error small {
                visibility: visible;
            }

            .validate-input.success small {
                visibility: visible;
            }

            .bx{
                margin-bottom: 22px;
            }

            #submit {
                transition: background-color 0.5s ease;
            }

            #submit:hover {
                background-color: yellowgreen;
                border-color: yellowgreen;
            }

            #submit:active {
                transition: 0.2s;
                transform: scale(0.93);
            }
        </style>

    </head>

    <body>
        <div class="limiter">
            <div class="container-login100">
                <div class="wrap-login100" style="justify-content: center;">
                    <span class="login100-form-title">
                        <b>ĐĂNG KÍ HỆ THỐNG</b>
                    </span>
                    <form action="/ManageLodgingHouse/LoginServlet" method="post" style="display: flex" id="form-register">
                        <div class="login100-pic js-tilt" data-tilt style="margin-right: 15px">

                            <div class="wrap-input100 validate-input">
                                <input class="input100" type="text" placeholder="Tài Khoản" name="username"
                                       id="username" maxlength="21" required="">
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i class='bx bx-user'></i>
                                </span>
                                <small id="username-ms">Error message</small>
                            </div>     

                            <div class="wrap-input100 validate-input password">
                                <input autocomplete="off" class="input100" type="password" placeholder="Mật khẩu"
                                       name="password" id="password" maxlength="31" required="">                         
                                <span toggle="#password-field" class="bx fa-fw bx-hide field-icon click-eye" id="show-pass1"></span>
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i class='bx bx-key'></i>
                                </span>
                                <small id="password-ms">Error message</small>
                            </div>

                            <div class="wrap-input100 validate-input">
                                <input autocomplete="off" class="input100" type="password" placeholder="Nhập lại mật khẩu"
                                       name="repeat-password" id="password2" maxlength="31" required="">
                                <span id="show-pass2" toggle="#password-field" class="bx fa-fw bx-hide field-icon click-eye"></span>
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i style="margin-bottom: 25px" class='bx bx-key'></i>
                                </span>
                                <small>Error message</small>
                            </div>

                            <div class="wrap-input100 validate-input">
                                <input autocomplete="off" class="input100" type="email" placeholder="Email"
                                       name="email" id="email">          
                                <span class="focus-input100"></span>
                                <span class="symbol-input100">
                                    <i class='bx bx-mail-send'></i>
                                </span>
                                <small>Error message</small>
                            </div>                                                          

                        </div>
                        <!-- -------------------------------------------------------------------- -->
                        <!-- -------------------------------------------------------------------- -->

                        <input type="hidden" name="service" value="register">
                        <!--<input type="submit" name="name">-->
                    </form>     
                    <div class="container-login100-form-btn">
                        <input type="button" value="Đăng Kí" id="submit" style="width: 15%"/>
                    </div>
                    <div class="text-right p-t-12" style="margin-top: 15px; font-size: 14px">
                        <a class="txt2" href="/ManageLodgingHouse/view/login/login.jsp">
                            Quay trở về đăng nhập?
                        </a>
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

        <!-- API lấy thông tin tất cả tỉnh thành -->
        <script src="https://esgoo.net/scripts/jquery.js"></script>
        <style type="text/css">
            .css_select_div{
                text-align: center;
            }
            .css_select{
                display: inline-table;
                width: 25%;
                padding: 5px;
                margin: 5px 2%;
                border: solid 1px #686868;
                border-radius: 5px;
            }
        </style>
        <script>
                            $(document).ready(function () {
                                //Lấy tỉnh thành
                                $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                                    if (data_tinh.error == 0) {
                                        $.each(data_tinh.data, function (key_tinh, val_tinh) {
                                            $("#tinh").append('<option value="' + val_tinh.id + val_tinh.full_name + '">' + val_tinh.full_name + '</option>');
                                        });
                                        $("#tinh").change(function (e) {
                                            var idtinh = $(this).val().substring(0, 2);
                                            //Lấy quận huyện
                                            $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                                                if (data_quan.error == 0) {
                                                    $("#quan").html('<option value="0">Quận Huyện</option>');
                                                    $("#phuong").html('<option value="0">Phường Xã</option>');
                                                    $.each(data_quan.data, function (key_quan, val_quan) {
                                                        $("#quan").append('<option value="' + val_quan.id + val_quan.full_name + '">' + val_quan.full_name + '</option>');
                                                    });
                                                    //Lấy phường xã  
                                                    $("#quan").change(function (e) {
                                                        var idquan = $(this).val().substring(0, 3);
                                                        $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                                                            if (data_phuong.error == 0) {
                                                                $("#phuong").html('<option value="0">Phường Xã</option>');
                                                                $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                                                    $("#phuong").append('<option value="' + val_phuong.id + val_phuong.full_name + '">' + val_phuong.full_name + '</option>');
                                                                });
                                                            }
                                                        });
                                                    });

                                                }
                                            });
                                        });

                                    }
                                });
                            });
        </script>

        <!-- validate data -->
        <script type="text/javascript">
            // Truy cập vào dữ liệu các ô input
            const usernameEle = document.getElementById('username');
            const passwordEle = document.getElementById('password');
            const emailEle = document.getElementById('email');
            const password2Ele = document.getElementById('password2');

            const btnRegister = document.getElementById('submit');

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

            // DOMContentLoaded: xảy ra ngay khi HTML hoàn thành phân tích cú pháp
            document.addEventListener('DOMContentLoaded', (event) => {
                // validate length of username
                validateLengthInput(usernameEle, 'Tài khoản không được quá 20 kí tự');

                // validate existence of Account when registering
                checkExistenceUsername(usernameEle);

                // validate length of password
                validateLengthInput(passwordEle, 'Mật khẩu không được quá 30 kí tự');

                // validate strength of password
                validateStrengthPassword(passwordEle);

                // check existence of Email when registering
                checkExistenceEmail(emailEle);

                // check simalarity for 2 password
                checkSimilarity();
            });

            function checkValidate() {
                let usernameValue = usernameEle.value;
                let passwordValue = passwordEle.value;
                let emailValue = emailEle.value;

                let isCheck = true;

                let messageUsername = (usernameEle.parentNode.querySelector('small').textContent === 'Tài khoản hợp lệ');
                if (usernameValue === '') {
                    setError(usernameEle, 'Tài khoản không được để trống');
                    isCheck = false;
                } else if (!validateStrengthUsername(usernameEle.value)) {
                    setError(usernameEle, 'Tài khoản ít nhất 8 kí tự');
                    isCheck = false;
                } else if (!messageUsername) {
                    setError(usernameEle, 'Tài khoản đã tồn tại');
                    isCheck = false;
                } else {
                    setSuccess(usernameEle, 'Tài khoản hợp lệ');
                }

                if (passwordValue === '') {
                    setError(passwordEle, 'Mật khẩu không được để trống');
                    isCheck = false;
                } else if (!validateStrengthPasswordDetail(passwordEle.value)) {
                    setError(passwordEle, 'Mật khẩu không đúng định dạng');
                    isCheck = false;
                } else {
                    setSuccess(passwordEle, 'Hợp lệ');
                }

                let msRepeatPass = (password2Ele.parentNode.querySelector('small').textContent === 'Mật khẩu trùng khớp');
                if (password2Ele.value === '') {
                    setError(password2Ele, 'Mật khẩu không được để trống');
                    isCheck = false;
                } else if (!msRepeatPass) {
                    setError(password2Ele, 'Mật khẩu không trùng khớp');
                    isCheck = false;
                }

                let messageEmail = (emailEle.parentNode.querySelector('small').textContent === 'Email hợp lệ');
                if (emailValue === '') {
                    setError(emailEle, 'Email không được để trống');
                    isCheck = false;
                } else if (!isEmail(emailValue)) {
                    setError(emailEle, 'Email không đúng định dạng');
                    isCheck = false;
                } else if (!messageEmail) {
                    setError(emailEle, 'Email đã được đăng kí');
                    isCheck = false;
                } else {
                    setSuccess(emailEle, 'Email hợp lệ');
                }

                return isCheck;
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

            function isEmail(email) {
                return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
                        email
                        );
            }

            function isPhone(number) {
                return /(84|0[3|5|7|8|9])+([0-9]{8})\b/.test(number);
            }

            function checkSimilarity() {
                password2Ele.addEventListener('input', () => {
                    if (password2Ele.value !== passwordEle.value) {
                        setError(password2Ele, 'Mật khẩu không trùng khớp');
                    } else {
                        // xoá đi cảnh báo mật khẩu trước đó và đổi màu border
                        password2Ele.parentNode.classList.remove('success', 'error');
                        setSuccess(password2Ele, 'Mật khẩu trùng khớp');
                    }
                });
            }

            function validateLengthInput(inputEle, message) {
                inputEle.addEventListener('input', () => {
                    if (inputEle.value.length >= inputEle.maxLength) {
                        inputEle.value = inputEle.value.substring(0, inputEle.value.length - 1);
                        setError(inputEle, message);
                    } else {
                        const inputEles = document.querySelectorAll('.validate-input');
                        Array.from(inputEles).map((ele) =>
                            ele.classList.remove('success', 'error')
                        );
                    }
                });
            }

            function validateNumeric(inputEle, message) {
                inputEle.addEventListener('input', () => {
                    var currentValue = inputEle.value;
                    // replace all the character not in [0-9] to empty
                    var numericValue = currentValue.replace(/\D/g, '');

                    if (currentValue !== numericValue) {
                        setError(inputEle, message);
                    } else {
                        if (currentValue.length !== inputEle.maxLength - 1) {
                            const inputEles = document.querySelectorAll('.validate-input');
                            Array.from(inputEles).map((ele) =>
                                ele.classList.remove('success', 'error')
                            );
                        }
                    }
                    // set the input value to numeric value
                    inputEle.value = numericValue;
                });
            }

            function validateStrengthPassword(inputEle) {
                inputEle.addEventListener('input', () => {
                    inputEle.value = inputEle.value.replace(/ /g, '');

                    const passwordValue = inputEle.value;
                    var message = 'Mật khẩu gồm ít nhất 8 kí tự, chữ thường, chữ hoa, số và kí tự đặc biệt';
                    setError(inputEle, message);

                    if (validateStrengthPasswordDetail(passwordValue)) {
                        if (passwordValue.length !== inputEle.maxLength - 1) {
                            // xoá đi cảnh báo mật khẩu trước đó
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

            function checkExistenceUsername(inputEle) {
                inputEle.addEventListener('input', () => {
                    if (inputEle.value.length !== inputEle.maxLength - 1) {
                        inputEle.value = inputEle.value.replace(/ /g, '');

                        // xoá đi cảnh báo mật khẩu trước đó
                        const inputEles = document.querySelectorAll('.validate-input');
                        Array.from(inputEles).map((ele) =>
                            ele.classList.remove('success', 'error')
                        );

                        fetch('/ManageLodgingHouse/LoginServlet?usernameNeedCheck=' + inputEle.value + '&service=checkUsernameRegister')
                                .then(response => {
                                    if (response.ok) {
                                        return response.text();
                                    }
                                })
                                .then(data => {
                                    // Xử lý phản hồi từ servlet
                                    if (data === 'OK') {
                                        // kiểm tra nhập khoảng trắng ở đầu
                                        if (inputEle.value.length === 0) {
                                            setError(inputEle, 'Không được nhập khoảng trắng');
                                        } else if (inputEle.value.length < 8) {
                                            setError(inputEle, 'Tài khoản ít nhất 8 kí tự');
                                        } else {
                                            setSuccess(inputEle, 'Tài khoản hợp lệ');
                                        }
                                    } else {
                                        setError(inputEle, 'Tài khoản đã tồn tại');
                                    }
                                })
                                .catch(error => {
                                    console.error('Error:', error);
                                });

//                        sendUsernameToCheck(inputEle)
//                                .then(result => {
//                                    // set lại thông báo
//                                    if (result) {
//                                        setSuccess(inputEle, 'Tài khoản hợp lệ');
//                                    } else {
//                                        setError(inputEle, 'Tài khoản đã tồn tại');
//                                    }
//                                })
//                                .catch(error => {
//                                    // Xử lý lỗi nếu có
//                                    console.error('Error:', error);
//                                });

                    } else {
                        setError(inputEle, 'Tài khoản không được quá 20 kí tự');
                    }
                });
            }

            function checkExistenceEmail(inputEle) {
                inputEle.addEventListener('input', () => {
                    inputEle.value = inputEle.value.replace(/ /g, '');

                    // xoá đi cảnh báo mật khẩu trước đó
                    const inputEles = document.querySelectorAll('.validate-input');
                    Array.from(inputEles).map((ele) =>
                        ele.classList.remove('success', 'error')
                    );

                    fetch('/ManageLodgingHouse/LoginServlet?emailNeedCheck=' + inputEle.value + '&service=checkEmailRegister')
                            .then(response => {
                                if (response.ok) {
                                    return response.text();
                                }
                            })
                            .then(data => {
                                if (data === 'OK') {
                                    // kiểm tra nhập khoảng trắng ở đầu
                                    if (inputEle.value.length !== 0) {
                                        setSuccess(inputEle, 'Email hợp lệ');
                                    } else {
                                        setError(inputEle, 'Không được nhập khoảng trắng');
                                    }
                                } else {
                                    setError(inputEle, 'Email đã được đăng kí');
                                }
                            })
                            .catch(error => {
                                console.error('Error:', error);
                            });

//                        sendUsernameToCheck(inputEle)
//                                .then(result => {
//                                    // set lại thông báo
//                                    if (result) {
//                                        setSuccess(inputEle, 'Tài khoản hợp lệ');
//                                    } else {
//                                        setError(inputEle, 'Tài khoản đã tồn tại');
//                                    }
//                                })
//                                .catch(error => {
//                                    // Xử lý lỗi nếu có
//                                    console.error('Error:', error);
//                                });
                });
            }

            function validateStrengthUsername(usernameValue) {
                if (usernameValue.length >= 8) {
                    return true;
                }
                return false;
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

//            async function sendUsernameToCheck(inputEle) {
//                var url = "/LHMS/LoginServlet?usernameNeedCheck=" + inputEle.value + "&service=checkUsernameRegister";
//
//                let check = false;
//
//                try {
//                    const response = await fetch(url);
//
//                    if (response.ok) {
//                        const data = await response.text();
//                        check = data === 'OK';
//                    }
//                } catch (e) {
//                    console.error(e);
//                }
//
//                return check;            
//            }
        </script>

        <script type="text/javascript">
            // show và hidden mật khẩu khi đăng kí
            document.getElementById('show-pass1').addEventListener('click', function () {
                const passwordInput = document.getElementById('password');
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
    </body>

</html>

