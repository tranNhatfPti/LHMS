<%-- 
    Document   : Information-of-tenant
    Created on : Jun 22, 2024, 3:43:53 PM
    Author     : ASUS ZenBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.ServletContext, model.Account, model.InformationOfUser, dal.InformationOfUserDAO, jakarta.servlet.http.HttpSession, java.util.List, model.Bill, dal.BillDAO"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Thông tin người thuê | Quản trị Admin</title>
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
        </style>
        <!-- Declare variable -->
        <%
            HttpSession s = request.getSession();
            InformationOfUserDAO id = new InformationOfUserDAO();
            
            Account account = (Account) s.getAttribute("account");
            int accountId = account.getAccountID();
            InformationOfUser informationOfUser = id.getInformationByAccountID(accountId);
            
            int lodgingHouseId = (int) s.getAttribute("lodgingID");
                   
            Account accountOfTenant = (Account) request.getAttribute("accountOfTenant");
            InformationOfUser inforOfTenant = (InformationOfUser) request.getAttribute("inforOfTenant");
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
                    <li class="breadcrumb-item"><a href="#">Thông tin người thuê</a></li>
                </ul>
            </div>  
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 style="margin-bottom: 20px" class="tile-title">Thông tin người thuê</h3>
                        <div style="color: red; margin-bottom: 20px; font-size: 12px">
                        </div>
                        <div class="tile-body">
                            <form action="" method="post" id="form" class="row">
                                <div class="form-group col-md-3">
                                    <label class="control-label">Họ và tên</label>
                                    <input class="form-control" type="text" value="<%=inforOfTenant.getFullName()%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                    <label class="control-label">Email</label>
                                    <input class="form-control" type="text" value="<%=accountOfTenant.getEmail()%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                    <label class="control-label">Số điện thoại</label>
                                    <input class="form-control" type="number" value="<%=inforOfTenant.getPhoneNumber()%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                    <label class="control-label">Ngày sinh</label>
                                    <input class="form-control" type="date" value="<%=inforOfTenant.getDob()%>" disabled="">
                                </div>
                                
                                <%
                                    String gender = "";
                                    if(inforOfTenant.getGender() == 1){
                                        gender = "Nam";
                                    } else if(inforOfTenant.getGender() == 2){
                                        gender = "Nữ";
                                    } else {
                                        gender = "Không xác định";
                                    }
                                %>
                                <div class="form-group col-md-3">
                                    <label class="control-label">Giới tính</label>
                                    <input class="form-control" type="text" value="<%=gender%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                    <label class="control-label">Căn cước công dân</label>
                                    <input class="form-control" type="number" value="<%=inforOfTenant.getCic()%>" disabled="">
                                </div>

                                <div class="form-group col-md-3">
                                    <label class="control-label">Tỉnh/Thành phố</label>
                                    <input class="form-control" type="text" 
                                           value="<%=inforOfTenant.getProvince()!=null?inforOfTenant.getProvince():""%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                    <label class="control-label">Quận/Huyện/Thị xã</label>
                                    <input class="form-control" type="text" 
                                           value="<%=inforOfTenant.getDistrict()!=null?inforOfTenant.getDistrict():""%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                    <label class="control-label">Phường/Xã/Thị trấn</label>
                                    <input class="form-control" type="text" 
                                           value="<%=inforOfTenant.getWard()!=null?inforOfTenant.getWard():""%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                    <label class="control-label">Địa chị cụ thể</label>
                                    <input class="form-control" type="text" 
                                           value="<%=inforOfTenant.getAddressDetail()!=null?inforOfTenant.getAddressDetail():""%>" disabled="">
                                </div>
                                
                                <div class="form-group col-md-3">
                                </div>
                                
                                <div class="form-group col-md-3">
                                </div>

                                <button style="margin-left: 15px" class="btn btn-save" id="submit" disabled="">Lưu lại</button>     
                                <a style="margin-left: 10px" class="btn btn-cancel" href="${pageContext.request.contextPath}/bill">Quay lại</a>
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

                    </body>

                    </html>

