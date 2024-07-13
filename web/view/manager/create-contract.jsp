<%-- 
    Document   : create-contract
    Created on : Jun 14, 2024, 3:44:33 PM
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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

        <style>
            .fixed-size-image {
                width: 100%;
                height: auto;
                max-width: 100%; /* Ensures the image fits within the column */
            }
        </style>
    </head>
    <body class="app sidebar-mini rtl">
        <style>
            .Choicefile {
                display: block;
                background: #14142B;
                border: 1px solid #fff;
                color: #fff;
                width: 150px;
                text-align: center;
                text-decoration: none;
                cursor: pointer;
                padding: 5px 0px;
                border-radius: 5px;
                font-weight: 500;
                align-items: center;
                justify-content: center;
            }

            .Choicefile:hover {
                text-decoration: none;
                color: white;
            }

            #uploadfile,
            .removeimg {
                display: none;
            }

            #thumbbox {
                position: relative;
                width: 100%;
                margin-bottom: 20px;
            }

            .removeimg {
                height: 25px;
                position: absolute;
                background-repeat: no-repeat;
                top: 5px;
                left: 5px;
                background-size: 25px;
                width: 25px;
                /* border: 3px solid red; */
                border-radius: 50%;

            }

            .removeimg::before {
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                content: '';
                border: 1px solid red;
                background: red;
                text-align: center;
                display: block;
                margin-top: 11px;
                transform: rotate(45deg);
            }

            .removeimg::after {
                /* color: #FFF; */
                /* background-color: #DC403B; */
                content: '';
                background: red;
                border: 1px solid red;
                text-align: center;
                display: block;
                transform: rotate(-45deg);
                margin-top: -2px;
            }

            .hidden {
                display: none;
            }

            .error {
                border: 2px solid red;
            }

            #errorMessage {
                color: red;
                margin-top: 5px;
            }

        </style>
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
            <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="/images/hay.jpg" width="50px"
                                                alt="User Image">

                <div>

                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>

            </div>
            <hr>
            <ul class="app-menu">
                <c:if test="${sessionScope.account.getRoleId() == 1}">
                    <li><a class="app-menu__item active" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                            </c:if>
                            <c:if test="${sessionScope.account.getRoleId() == 2}">
                    <li><a class="app-menu__item active" href="home-manager?LodgingHouseID=3"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                            </c:if>
                <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Chỉ số nước</span></a></li>                       

                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item">Quản lí hợp đồng</li>
                    <li class="breadcrumb-item"><a href="#">Tạo hợp đồng thuê trọ</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Tạo hợp đồng</h3>
                        <c:forEach items="${requestScope.listTenant}" var="aunt">
                            <option value="${aunt.getEmail()}"></option>
                        </c:forEach>

                        <div class="tile-body">

                            <form class="row" id="form-1" action="send-contract-servlet" method="post">
                                <!-- Các trường thông tin trong form của bạn -->
                                <div class="form-group col-md-6">
                                    <label class="control-label">Tên trọ</label>
                                    <input class="form-control" value="${requestScope.lodgingHouse.getNameLodgingHouse()}" readonly="">
                                    <input class="form-control" value="${requestScope.lodgingHouse.getLodgingHouseId()}" type="hidden" name="lodgingHouseId">
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Phòng</label>
                                    <input class="form-control" type="text" placeholder="Thêm số tiền đầu tư (VND)" value="${requestScope.room.getRoomName()}" readonly="">
                                    <input class="form-control" type="text" placeholder="Thêm số tiền đầu tư (VND)" name="room" value="${requestScope.room.getRoomId()}" hidden="">
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Người quản lí</label>
                                    <input class="form-control" value="${requestScope.manager.getFullName()}" readonly="">
                                    <input class="form-control" value="${requestScope.manager.getAccountID()}" type="hidden" name="managerId">
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Email người thuê<span style="color:red;">(*)</span></label>
                                    <input list="email-list" class="form-control" id="email" name="email" type="email" required>
                                    <datalist id="email-list">
                                        <c:forEach items="${requestScope.listTenant}" var="account">
                                            <option value="${account.getEmail()}"></option>
                                        </c:forEach>
                                    </datalist>
                                    <div id="email-error" style="color:red; display:none;">Vui lòng nhập địa chỉ email hợp lệ.</div>
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label">Từ ngày</label>
                                    <input class="form-control" type="date" name="dateFrom" id="dateFrom" required>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Đến ngày</label>
                                    <input class="form-control" type="date" name="dateTo" id="dateTo" required>
                                </div>
                                <div class="form-group col-md-12">
                                    <label class="control-label">Tiền cọc</label>
                                    <input class="form-control" type="number" min="0" name="deposit" required>
                                </div>
                                <div>
                                    <button class="btn btn-save" id="submitButton" type="submit" style="margin-right: 10px;" >Lưu lại</button>
                                </div>
                                <button class="btn btn-cancel" type="reset" style="margin-right: 10px">Xóa</button>
                            </form>
                        </div>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="contractModal" tabindex="-1" role="dialog" aria-labelledby="contractModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="contractModalLabel">Thông tin hợp đồng</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="modalBody">
                                    <!-- Nội dung trả về từ servlet sẽ được hiển thị ở đây -->
                                </div>
                            </div>
                        </div>
                    </div>


                    </main>

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
                    <!--        <script type="text/javascript">$('#sampleTable').DataTable();</script>-->

                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
                    <script>
                        document.addEventListener('DOMContentLoaded', function () {
                            var dateFrom = document.getElementById('dateFrom');
                            var dateTo = document.getElementById('dateTo');

                            function validateDates() {
                                console.log('Validating dates...');
                                if (dateFrom.value && dateTo.value) {
                                    var fromDate = new Date(dateFrom.value);
                                    var toDate = new Date(dateTo.value);
                                    var today = new Date();
                                    today.setHours(0, 0, 0, 0); // Reset time to midnight for accurate comparison

                                    console.log('From Date:', fromDate);
                                    console.log('To Date:', toDate);
                                    console.log('Today:', today);

                                    if (toDate < today) {
                                        swal({
                                            title: 'Lỗi',
                                            text: '"Ngày kết thúc" không được là ngày trong quá khứ',
                                            icon: 'error',
                                            button: 'OK'
                                        }).then(() => {
                                            dateTo.value = '';
                                            dateTo.focus();
                                        });
                                        return;
                                    }

                                    if (fromDate > toDate) {
                                        swal({
                                            title: 'Lỗi',
                                            text: 'Ngày "Bắt đầu" không được lớn hơn "Ngày kết thúc"',
                                            icon: 'error',
                                            button: 'OK'
                                        }).then(() => {
                                            dateFrom.value = '';
                                            dateTo.value = '';
                                            dateFrom.focus();
                                        });
                                    }
                                }
                            }

                            dateFrom.addEventListener('input', validateDates);
                            dateTo.addEventListener('input', validateDates);
                        });

                    </script>



                    <script>
                        function submitForm() {
                            // Lấy form theo id
                            var form = document.getElementById("form-1");

                            // Submit form
                            if (form) {
                                form.submit();
                            }
                        }
                    </script>

                    <script>
                        function validateInput(inputElement) {
                            // Lấy giá trị từ thẻ input
                            const inputValue = inputElement.value;

                            // Kiểm tra nếu giá trị chỉ toàn khoảng trắng
                            if (inputValue.trim() === '') {
                                return false;
                            }

                            // Kiểm tra nếu phần tử đầu tiên hoặc phần tử cuối cùng là khoảng trắng
                            if (inputValue[0] === ' ' || inputValue[inputValue.length - 1] === ' ') {
                                return false;
                            }
                            const specialCharPattern = /[^a-zA-Z0-9\s\u00C0-\u024F\u1E00-\u1EFF]/;
                            if (specialCharPattern.test(inputValue)) {
                                return false;
                            }
                            const parts = inputValue.split(' ');
                            let spaceCount = 0;

                            for (let i = 0; i < parts.length; i++) {
                                if (parts[i] === '') {
                                    spaceCount++;
                                    if (spaceCount > 0) {
                                        return false;
                                    }
                                } else {
                                    spaceCount = 0;
                                }
                            }

                            return true;
                        }

                        function updateSubmitButtonState() {
                            const inputField = document.getElementById('inputField');
                            const isValid = validateInput(inputField);
                            const errorMessage = document.getElementById('errorMessage');
                            const submitButton = document.getElementById('submitButton');

                            if (!isValid) {
                                inputField.classList.add('error');
                                errorMessage.classList.remove('hidden');
                                submitButton.disabled = true; // Khóa nút submit nếu nhập liệu không hợp lệ
                            } else {
                                inputField.classList.remove('error');
                                errorMessage.classList.add('hidden');
                                submitButton.disabled = false; // Mở khóa nút submit nếu nhập liệu hợp lệ
                            }
                        }

                        document.getElementById('inputField').addEventListener('blur', function () {
                            updateSubmitButtonState();
                        });

                        document.getElementById('inputField').addEventListener('input', function () {
                            updateSubmitButtonState(); // Cập nhật trạng thái nút submit khi có sự kiện input
                        });

                        document.getElementById('submitButton').addEventListener('click', function (event) {
                            const inputField = document.getElementById('inputField');
                            const isValid = validateInput(inputField);
                            const errorMessage = document.getElementById('errorMessage');
                            const form = document.getElementById('form-1');
                            const submitButton = document.getElementById('submitButton');

                            if (!isValid) {
                                event.preventDefault(); // Ngăn chặn việc gửi form nếu có lỗi
                                inputField.classList.add('error');
                                errorMessage.classList.remove('hidden');
                                submitButton.disabled = true; // Khóa nút submit nếu nhập liệu không hợp lệ
                            }
                        });




                    </script>

                    <!-- AJAX script -->
                    <script>
                        $(document).ready(function () {
                            $('#form-1').on('submit', function (e) {
                                e.preventDefault(); // Ngăn chặn submit mặc định của form

                                // Gửi yêu cầu AJAX
                                $.ajax({
                                    url: 'create-contract-servlet',
                                    type: 'POST',
                                    data: $(this).serialize(),
                                    success: function (response) {
                                        // Hiển thị nội dung trả về trong modal
                                        $('#modalBody').html(response);

                                        // Hiển thị modal
                                        $('#contractModal').modal('show');
                                    },
                                    error: function (xhr, status, error) {
                                        // Xử lý lỗi nếu có
                                        alert('Đã xảy ra lỗi: ' + error);
                                    }
                                });
                            });
                        });
                    </script>

                    <script>
                        document.getElementById('email').addEventListener('input', function () {
                            var emailField = this;
                            var emailError = document.getElementById('email-error');
                            emailError.style.display = emailField.validity.valid ? 'none' : 'block';
                        });
                    </script>
                    </body>

                    </html>
