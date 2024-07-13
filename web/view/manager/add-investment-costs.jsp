

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Thêm sản phẩm | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <!--  <link rel="stylesheet" type="text/css" href="../Resource/doc/css/main.css">-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script type="text/javascript" src="/admin/ckeditor/ckeditor.js"></script>
        <script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>
        <script>

            function readURL(input, thumbimage) {
                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimage").attr('src', e.target.result);
                    }
                    reader.readAsDataURL(input.files[0]);
                } else { // Sử dụng cho IE
                    $("#thumbimage").attr('src', input.value);

                }
                $("#thumbimage").show();
                $('.filename').text($("#uploadfile").val());
                $('.Choicefile').css('background', '#14142B');
                $('.Choicefile').css('cursor', 'default');
                $(".removeimg").show();
                $(".Choicefile").unbind('click');

            }
            $(document).ready(function () {
                $(".Choicefile").bind('click', function () {
                    $("#uploadfile").click();

                });
                $(".removeimg").click(function () {
                    $("#thumbimage").attr('src', '').hide();
                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                    $(".removeimg").hide();
                    $(".Choicefile").bind('click', function () {
                        $("#uploadfile").click();
                    });
                    $('.Choicefile').css('background', '#14142B');
                    $('.Choicefile').css('cursor', 'pointer');
                    $(".filename").text("");
                });
            })
        </script>
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
                    <p class="app-sidebar__user-name"><b>Võ Trường</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item haha" href="phan-mem-ban-hang.html"><i class='app-menu__icon bx bx-cart-alt'></i>
                        <span class="app-menu__label">POS Bán Hàng</span></a></li>
                <li><a class="app-menu__item " href="index.html"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item " href="table-data-table.html"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
                <li><a class="app-menu__item " href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý khách hàng</span></a></li>
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
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/investment-costs-servlet"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lí khoản phí đầu tư</span></a>
                </li>                 
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
                            đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item">Danh sách các khoản chi phí đầu tư</li>
                    <li class="breadcrumb-item"><a href="#">Chỉnh sửa khoản chi phí đầu tư</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Chỉnh sửa khoản chi phí đầu tư</h3>
                        <div class="tile-body">
                            <form class="row" action="add-investment-costs-servlet" method="post" id="form-1">
                                <div class="form-group col-md-6">
                                    <label class="control-label">Số tiền đầu tư</label>
                                    <input class="form-control" type="number" min="0" placeholder="Thêm số tiền đầu tư" name="price" required>                                   
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Miêu tả</label>
                                    <input class="form-control" type="text" id="inputField" placeholder="Thêm miêu tả" name="description" required>
                                    <div id="errorMessage" class="hidden">Nhập đúng theo quy tắc (Không để toàn khoảng trắng, phần từ đầu và cuối không là khoảng trắng, giữa 2 chữ không có nhiều hơn 1 khoảng trắng, ...)</div>
                                </div>


                                <div class="form-group col-md-4">
                                    <label class="control-label">Người quản lí</label>        
                                    <input class="form-control" value="${sessionScope.account.getFullName()}" readonly="">
                                    <input class="form-control" value="${sessionScope.account.getAccountID()}" type="hidden" name="managerId" >                                   
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="control-label">Tên trọ</label>                                  
                                    <input class="form-control" value="${requestScope.lodgingHouse.getNameLodgingHouse()}" readonly="">
                                    <input class="form-control" value="${requestScope.lodgingHouse.getLodgingHouseId()}" type="hidden" name="lodgingHouseId" >                                 
                                </div>
                                <div class="form-group col-md-4">
                                    <label class="control-label">Loại chi phí đầu tư</label>
                                    <select class="form-control" required="" name="typeOfInvestmentCost">
                                        <c:forEach items="${requestScope.lisTypeOfInvestmentCosts}" var="c">
                                            <option value="${c.getId()}"> ${c.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <diV>
                                    <button class="btn btn-save" id="submitButton"  type="submit" style="margin-right: 10px; ">Lưu lại</button>
                                </div>
                                <button class="btn btn-cancel" type="reset" style="margin-right: 10px">Xóa</button>
                            </form>
                        </div>
                    </div>
                    </main>

                    <script src="js/jquery-3.2.1.min.js"></script>
                    <script src="js/popper.min.js"></script>
                    <script src="js/bootstrap.min.js"></script>
                    <!--                    <script src="/admin/js/main.js"></script>-->
                    <script src="js/plugins/pace.min.js"></script>
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


                    </body>

                    </html>