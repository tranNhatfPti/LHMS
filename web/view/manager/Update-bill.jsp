<%-- 
    Document   : form-insert-brand
    Created on : Mar 1, 2024, 1:41:12 AM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.ServletContext, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession, java.util.List, model.Bill, dal.BillDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<%@page import="model.Contract" %>
<%@page import="dal.ContractDAO" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Cập nhật hoá đơn | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
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
        <script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
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

            .cl-checkbox {
                position: relative;
                display: inline-block;
            }

            /* Input */
            .cl-checkbox > input {
                appearance: none;
                -moz-appearance: none;
                -webkit-appearance: none;
                z-index: -1;
                position: absolute;
                left: -10px;
                top: -8px;
                display: block;
                margin: 0;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                background-color: rgba(0, 0, 0, 0.6);
                box-shadow: none;
                outline: none;
                opacity: 0;
                transform: scale(1);
                pointer-events: none;
                transition: opacity 0.3s, transform 0.2s;
            }

            /* Span */
            .cl-checkbox > span {
                display: inline-block;
                width: 100%;
                cursor: pointer;
            }

            /* Box */
            .cl-checkbox > span::before {
                content: "";
                display: inline-block;
                box-sizing: border-box;
                margin: 2px 11px 0px 0px;
                border: solid 2px;
                /* Safari */
                border-color: rgba(0, 0, 0, 0.6);
                border-radius: 2px;
                width: 18px;
                height: 18px;
                vertical-align: top;
                transition: border-color 0.2s, background-color 0.2s;
            }

            /* Checkmark */
            .cl-checkbox > span::after {
                content: "";
                display: block;
                position: absolute;
                top: 3px;
                left: 1px;
                width: 10px;
                height: 5px;
                border: solid 2px transparent;
                border-right: none;
                border-top: none;
                transform: translate(3px, 4px) rotate(-45deg);
            }

            /* Checked, Indeterminate */
            .cl-checkbox > input:checked,
            .cl-checkbox > input:indeterminate {
                background-color: #018786;
            }

            .cl-checkbox > input:checked + span::before,
            .cl-checkbox > input:indeterminate + span::before {
                border-color: #018786;
                background-color: #018786;
                margin-top: 2px;
                margin-left: 0px;
            }

            .cl-checkbox > input:checked + span::after,
            .cl-checkbox > input:indeterminate + span::after {
                border-color: #fff;
            }

            .cl-checkbox > input:indeterminate + span::after {
                border-left: none;
                transform: translate(4px, 3px);
            }

            /* Hover, Focus */
            .cl-checkbox:hover > input {
                opacity: 0.04;
            }

            .cl-checkbox > input:focus {
                opacity: 0.12;
            }

            .cl-checkbox:hover > input:focus {
                opacity: 0.16;
            }

            /* Active */
            .cl-checkbox > input:active {
                opacity: 1;
                transform: scale(0);
                transition: transform 0s, opacity 0s;
            }

            .cl-checkbox > input:active + span::before {
                border-color: #85b8b7;
            }

            .cl-checkbox > input:checked:active + span::before {
                border-color: transparent;
                background-color: rgba(0, 0, 0, 0.6);
            }

            /* Disabled */
            .cl-checkbox > input:disabled {
                opacity: 0;
            }

            .cl-checkbox > input:disabled + span {
                color: rgba(0, 0, 0, 0.38);
                cursor: initial;
            }

            .cl-checkbox > input:disabled + span::before {
                border-color: currentColor;
            }

            .cl-checkbox > input:checked:disabled + span::before,
            .cl-checkbox > input:indeterminate:disabled + span::before {
                border-color: transparent;
                background-color: currentColor;
            }

        </style>
        <!-- Declare variable -->
        <%
            HttpSession s = request.getSession();
            InformationOfUserDAO id = new InformationOfUserDAO();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            BillDAO bd = new BillDAO();
            ContractDAO cd = new ContractDAO();
            
            Account account = (Account) s.getAttribute("account");
            int accountId = account.getAccountID();
            InformationOfUser informationOfUser = id.getInformationByAccountID(accountId);
            
            int lodgingHouseId = (int) s.getAttribute("lodgingID");
                   
            String roomId = request.getParameter("roomId");
            String monthYear = request.getParameter("monthYear");
            
            Bill billNeedUpdate = bd.getBillByRoomIdAndMonthYear(roomId, monthYear);
        %>
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button-->
            <a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">
                <!-- User Menu-->
                <li><a class="app-nav__item" href="/index.html"><i class='bx bx-log-out bx-rotate-180'></i></a></li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user">
                <%
                    if(informationOfUser.getAvatar() != null){
                %>
                <img class="app-sidebar__user-avatar" src="<%=informationOfUser.getAvatar()%>" width="100px" alt="User Image">
                <%        
                    } else {
                %>
                <img class="app-sidebar__user-avatar" src="${pageContext.request.contextPath}/Resource/images/avatar-default.jpg" width="100px" alt="User Image">
                <%
                    }
                %>              
                <div>
                    <p class="app-sidebar__user-name"><b><%=informationOfUser.getFullName()!=null?informationOfUser.getFullName():""%></b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Chỉ số nước</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>

                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/investment-costs-servlet"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lí khoản phí đầu tư</span></a>
                </li> 
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/service-of-lodginghouse"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Dịch vụ trọ</span></a></li>
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý nhân viên</span></a></li>
                <li><a class="app-menu__item active" href="${pageContext.request.contextPath}/bill"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Hoá đơn thanh toán</span></a></li>        
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="#">Cập nhật hoá đơn</a></li>
                </ul>
            </div>  
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 style="margin-bottom: 20px" class="tile-title">Cập nhật hoá đơn</h3>
                        <div style="color: red; margin-bottom: 20px; font-size: 12px">
                            <strong> *Lưu ý:</strong><br>        
                            <strong> - Khi bạn cập nhật SỐ NƯỚC MỚI và SỐ ĐIỆN MỚI vào 1 phòng thì hệ thống sẽ tự động tính tổng tiền cho phòng đó.</strong><br>
                            <strong> - Chỉ được cập nhật TRẠNG THÁI THANH TOÁN sau khi đã cập nhật số điện và số nước.</strong><br>
                            <strong> - Sau khi cập nhật trạng thái ĐÃ THANH TOÁN hoặc ĐÃ THANH TOÁN MỘT PHẦN sẽ không được cập nhật lại số liệu điện và nước.</strong><br>
                            <strong> - Sau khi cập nhật số nước và điện mới thì hệ thống sẽ tự động gửi Mail thông báo đến người thuê.</strong><br>
                            <strong> - Nếu muốn gửi lại Mail thông báo cho người thuê khi chưa thanh toán, vui lòng
                                click LƯU LẠI khi chưa cập nhật trạng thái ĐÃ THANH TOÁN hoặc ĐÃ THANH TOÁN MỘT PHẦN.</strong><br>
                        </div>
                        <div class="tile-body">
                            <form action="/ManageLodgingHouse/bill" method="post" id="form" class="row" onsubmit="return checkValidate()">
                                <div class="form-group col-md-3">
                                    <label class="control-label">Số nước cũ</label>
                                    <input id="old-water" class="form-control" type="number" value="<%=billNeedUpdate.getWaterOld()%>" disabled="">
                                </div>

                                <div class="form-group col-md-3 validate-input">
                                    <label class="control-label">Số nước mới</label>
                                    <input id="new-water" class="form-control" type="number" name="newWater" placeholder="Nhập số nước mới" 
                                           value="<%=billNeedUpdate.getWaterNew()==0?"":billNeedUpdate.getWaterNew()%>"
                                           <%=bd.checkUpdateBill(roomId, monthYear)?"":"disabled"%> required="" max="999999">
                                    <small>Error message</small>
                                </div>

                                <div class="form-group col-md-3">
                                    <label class="control-label">Số điện cũ</label>
                                    <input id="old-electronic" class="form-control" type="number" value="<%=billNeedUpdate.getElectronicOld()%>" disabled="">
                                </div>

                                <div class="form-group col-md-3 validate-input">
                                    <label class="control-label">Số điện mới</label>
                                    <input id="new-electronic" class="form-control" type="number" name="newElectronic" placeholder="Nhập số điện mới"
                                           value="<%=billNeedUpdate.getElectronicNew()==0?"":billNeedUpdate.getElectronicNew()%>"
                                           <%=bd.checkUpdateBill(roomId, monthYear)?"":"disabled"%> required="" max="999999">
                                    <small>Error message</small>
                                </div>                      

                                <%
                                    boolean checkPaid = true;
                                    
                                    if(billNeedUpdate.getWaterNew() == 0 || billNeedUpdate.getElectronicNew() == 0) {
                                        checkPaid = false;
                                    }
                                    
                                    if(billNeedUpdate.getStatus() == 2){
                                        checkPaid = false;
                                    }
                                %>
                                <div class="form-group col-md-3">
                                    <label class="control-label">Trạng thái</label><br>
                                    <select id="status" class="form-control" name="status" <%=checkPaid?"":"disabled"%>
                                            onchange="checkPaid()">
                                        <option value="1" <%=billNeedUpdate.getStatus()==1?"selected":""%> <%=billNeedUpdate.getStatus()==3?"disabled":""%>>
                                            Chưa thanh toán
                                        </option>
                                        <option value="2" <%=billNeedUpdate.getStatus()==2?"selected":""%>>
                                            Đã thanh toán
                                        </option>
                                        <option value="3" <%=billNeedUpdate.getStatus()==3?"selected":""%>>
                                            Đã thanh toán một phần
                                        </option>
                                    </select>
                                </div>

                                <div id="form-paid" style="visibility: hidden" class="form-group col-md-3 validate-input">
                                    <label class="control-label">Số tiền được nhận</label>
                                    <input id="paid" class="form-control" type="number" name="paid" placeholder="Nhập số tiền đã trả"
                                           min="1000" max="<%=billNeedUpdate.getMissing()%>">
                                    <small>Error message</small>
                                </div>

                                <div class="form-group col-md-6">
                                </div> 

                                <div class="form-group col-md-12">
                                    <label class="cl-checkbox" style="font-family: math; font-weight: bold; color: darkslateblue">
                                        <input checked="" type="checkbox" value="createBillForNewMonth" name="statusCreate">
                                        <span>Tạo hoá đơn tháng mới sau khi cập nhật (nếu không sẽ tiếp tục tháng hiện tại)</span>
                                    </label>
                                </div>
                                    
                                <button <%=billNeedUpdate.getStatus()==2?"disabled":""%> 
                                    style="margin-left: 15px" class="btn btn-save" id="submit-form">Lưu lại</button>     
                                <input type="hidden" name="service" value="updateBill">
                                <input type="hidden" name="roomId" value="<%=roomId%>">
                                <input type="hidden" name="monthYear" value="<%=monthYear%>">
                                <a style="margin-left: 10px" class="btn btn-cancel" href="${pageContext.request.contextPath}/bill">Hủy bỏ</a>
                            </form>
                        </div>
                    </div>
                    </main>


                    <script src="js/jquery-3.2.1.min.js"></script>
                    <script src="js/popper.min.js"></script>
                    <script src="js/bootstrap.min.js"></script>
                    <script src="js/main.js"></script>
                    <script src="js/plugins/pace.min.js"></script>
                    <script>
                                                const inpFile = document.getElementById("inpFile");
                                                const loadFile = document.getElementById("loadFile");
                                                const previewContainer = document.getElementById("imagePreview");
                                                const previewContainer = document.getElementById("imagePreview");
                                                const previewImage = previewContainer.querySelector(".image-preview__image");
                                                const previewDefaultText = previewContainer.querySelector(".image-preview__default-text");
                                                inpFile.addEventListener("change", function () {
                                                    const file = this.files[0];
                                                    if (file) {
                                                        const reader = new FileReader();
                                                        previewDefaultText.style.display = "none";
                                                        previewImage.style.display = "block";
                                                        reader.addEventListener("load", function () {
                                                            previewImage.setAttribute("src", this.result);
                                                        });
                                                        reader.readAsDataURL(file);
                                                    }
                                                });

                    </script>

                    <script type="text/javascript">
                        const newWaterEle = document.getElementById('new-water');
                        const newElecEle = document.getElementById('new-electronic');
                        const oldWaterEle = document.getElementById('old-water');
                        const oldElecEle = document.getElementById('old-electronic');
                        const statusEle = document.getElementById('status');
                        const fPaidEle = document.getElementById('form-paid');
                        const paidEle = document.getElementById('paid');
                        const btnRegister = document.getElementById('submit-form');

                        btnRegister.addEventListener('click', function () {
                            const inputEles = document.querySelectorAll('.validate-input');
                            Array.from(inputEles).map((ele) =>
                                ele.classList.remove('success', 'error')
                            );
                        });

                        function checkValidate() {
                            let isCheck = true;

                            if (!checkValue(oldWaterEle, newWaterEle)) {
                                setError(newWaterEle, 'Số nước mới phải lớn hơn hoặc bằng số nước cũ');
                                isCheck = false;
                            }

                            if (!checkValue(oldElecEle, newElecEle)) {
                                setError(newElecEle, 'Số điện mới phải lớn hơn hoặc bằng số điện cũ');
                                isCheck = false;
                            }

                            return isCheck;
                        }

                        function checkValue(oldEle, newEle) {
                            var oldVal = parseFloat(oldEle.value);
                            var newVal = parseFloat(newEle.value);

                            if (newVal >= oldVal) {
                                return true;
                            }
                            return false;
                        }

                        function setError(ele, message) {
                            let parentEle = ele.parentNode;
                            parentEle.classList.add('error');
                            parentEle.querySelector('small').innerText = message;
                            parentEle.querySelector('small').style.color = '#e74c3c';
                        }

                        function checkPaid() {
                            var statusVal = parseInt(statusEle.value);

                            if (statusVal === 3) {
                                fPaidEle.style.visibility = 'visible';
                                paidEle.required = true;
                            } else {
                                fPaidEle.style.visibility = 'hidden';
                                paidEle.required = false;
                            }
                        }
                    </script>

                    <script type="text/javascript">
                        document.getElementById('form').addEventListener('submit', function (event) {
                            event.preventDefault();

                            if (checkValidate()) {
                                swal({
                                    title: "Cảnh báo",
                                    text: "Hãy chắc chắn các số liệu trước khi cập nhật hoá đơn?",
                                    icon: "warning",
                                    buttons: ["Hủy bỏ", "Đồng ý"]
                                })
                                        .then((acceptInsert) => {
                                            if (acceptInsert) {
                                                document.getElementById('form').submit();
                                            } else {
                                                swal({
                                                    title: "Cập nhật không thành công!",
                                                    text: "Bạn đã huỷ cập nhật hoá đơn.",
                                                    icon: "error"
                                                });
                                            }
                                        });
                            }
                        });
                    </script>
                    <script>
                        var ws;

                        // gửi tham số idAccount đến server
                        ws = new WebSocket('ws://localhost:9999/ManageLodgingHouse/socket?idAccount=<%=accountId%>');

                        ws.onopen = function (event) {
                            console.log('WebSocket Opened');
                        };

                        ws.onmessage = function (event) {

                        };

                        ws.onclose = function (event) {
                            console.log('WebSocket Closed');
                        };

                    </script>
                    </body>

                    </html>
