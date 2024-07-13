<%-- 
    Document   : add-room
    Created on : Jun 14, 2024, 12:37:07 PM
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <script src="https://www.gstatic.com/firebasejs/4.2.0/firebase.js"></script>
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
                <li><a class="app-menu__item active" href="/ManageLodgingHouse/management-lodging-houses?index=1"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
                <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Chỉ số nước</span></a></li>             
                <li><a class="app-menu__item" href="${pageContext.request.contextPath}/type-of-investment-costs-servlet"><i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Quản lý loại phí đầu tư</span></a></li>           
                <li><a class="app-menu__item" href="account?service=showProfile"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Thông tin cá nhân</span></a>
                </li> 
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>
            </ul>
        </aside>


        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item">Quản lí trọ</li>
                    <li class="breadcrumb-item"><a href="">Chỉnh sửa phòng</a></li>
                </ul>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <h3 class="tile-title">Chỉnh sửa phòng</h3>
                        <div class="tile-body">
                            <form class="row" action="update-room-servlet" method="post" id="form">
                                <div class="form-group col-md-6">

                                    <label class="control-label">Tên phòng</label>
                                    <input class="form-control" type="text" id="inputFieldName" placeholder="Tên phòng" name="name" value="${requestScope.room.getRoomName()}" required>   
                                    <input hidden="" name="roomId" value="${requestScope.room.getRoomId()}" required>   
                                    <div id="errorMessageName" class="hidden">Nhập đúng theo quy tắc (Không để toàn khoảng trắng, phần từ đầu và cuối không là khoảng trắng, giữa 2 chữ không có nhiều hơn 1 khoảng trắng, không có kí tự đặc biệt...)</div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Tiền phòng</label>
                                    <input class="form-control" type="number" min="0" placeholder="Tiền phòng (VND)" name="price" value="${requestScope.room.getPrice()}" required>                               
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Số người ở tối đa</label>
                                    <input class="form-control" type="number" min="0" placeholder="Số người ở tối đa" name="maxOfQuantity" value="${requestScope.room.getPrice()}" required>                               
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label">Trạng thái phòng</label>
                                    <input class="form-control" value="Còn trống" readonly="">                                   
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label">Tên trọ</label>                                  
                                    <input class="form-control" value="${requestScope.lodgingHouse.getNameLodgingHouse()}" readonly="">                                                              
                                </div>

                                <div class="form-group col-md-6">
                                    <label class="control-label">Miêu tả</label>
                                    <input class="form-control" type="text" id="inputField" placeholder="Thêm miêu tả" name="description" value="${requestScope.room.getDescription()}" required>
                                    <div id="errorMessage" class="hidden">Nhập đúng theo quy tắc (Không để toàn khoảng trắng, phần từ đầu và cuối không là khoảng trắng, giữa 2 chữ không có nhiều hơn 1 khoảng trắng, không có kí tự đặc biệt...)</div>
                                </div>

                                <div class="form-group col-md-6">
                                    <button class="btn btn-save" id="submitButton"  type="submit" style="margin-right: 10px; ">Lưu lại</button>
                                    <button class="btn btn-cancel" type="reset" style="margin-right: 10px">Xóa</button>
                                </div>


                                <div class="form-group col-md-6">
                                    <div class="avatar-container">
                                        <img  src="${requestScope.room.getImage()}" class="avatar img-circle img-thumbnail" alt="avatar">
                                        <div class="overlay">
                                            <span>Change Avatar</span>
                                            <input type="file" class="file-upload" value="upload" accept=".jpg" id="fileButton">

                                        </div>
                                    </div>
                                    <div class="upload-info">
                                        <progress value="0" max="100" id="uploader" style="display: none;">0%</progress>
                                        <span id="avatarMessage" class="avatarMessage"></span>
                                    </div>

                                    <div class="col-sm-6">
                                        <input class="form-control" name="avatar" type="text" id="avatar" value="${requestScope.room.getImage()}" style="display : none">
                                        <div id="imgDiv" style="display : none"></div>
                                    </div>

                                </div>


                            </form>
                        </div>

                    </div>
                    </main>
                    <script src="${pageContext.request.contextPath}/Resource/js/profile.js"></script>
                    
                    <script src="js/jquery-3.2.1.min.js"></script>
                    <script src="js/popper.min.js"></script>
                    <script src="js/bootstrap.min.js"></script>
                    <script src="/admin/js/main.js"></script>
                    <script src="js/plugins/pace.min.js"></script>
                    <script src="https://www.gstatic.com/firebasejs/4.2.0/firebase.js"></script>
                    <script>
            function validateInput(inputElement) {
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
                const isValidField = inputField ? validateInput(inputField) : true;
                const errorMessage = document.getElementById('errorMessage');
                const submitButton = document.getElementById('submitButton');
                if (inputField) {
                    if (!isValidField) {
                        inputField.classList.add('error');
                        errorMessage.classList.remove('hidden');
                        errorMessage.textContent = 'Trường này không được để trống và không được chứa ký tự đặc biệt.';
                    } else {
                        inputField.classList.remove('error');
                        errorMessage.classList.add('hidden');
                    }
                }
                submitButton.disabled = !isValidField;
            }

            function updateSubmitButtonStateV2() {
                const inputField = document.getElementById('inputFieldName');
                const isValidField = inputField ? validateInput(inputField) : true;
                const errorMessage = document.getElementById('errorMessageName');
                const submitButton = document.getElementById('submitButton');
                if (inputField) {
                    if (!isValidField) {
                        inputField.classList.add('error');
                        errorMessage.classList.remove('hidden');
                        errorMessage.textContent = 'Trường này không được để trống và không được chứa ký tự đặc biệt.';
                    } else {
                        inputField.classList.remove('error');
                        errorMessage.classList.add('hidden');
                    }
                }
                submitButton.disabled = !isValidField;
            }

            document.getElementById('inputField').addEventListener('blur', updateSubmitButtonState);
            document.getElementById('inputField').addEventListener('input', updateSubmitButtonState);
            document.getElementById('inputFieldName').addEventListener('blur', updateSubmitButtonStateV2);
            document.getElementById('inputFieldName').addEventListener('input', updateSubmitButtonStateV2);

            document.getElementById('submitButton').addEventListener('click', function (event) {
                const inputField = document.getElementById('inputField');
                const inputFieldName = document.getElementById('inputFieldName');

                const isValidField = inputField ? validateInput(inputField) : true;
                const isValidFieldName = inputFieldName ? validateInput(inputFieldName) : true;

                const errorMessage = document.getElementById('errorMessage');
                const errorMessageName = document.getElementById('errorMessageName');

                if (!isValidField || !isValidFieldName) {
                    event.preventDefault(); // Ngăn chặn việc gửi form nếu có lỗi
                    if (inputField && !isValidField) {
                        inputField.classList.add('error');
                        errorMessage.classList.remove('hidden');
                        errorMessage.textContent = 'Trường này không được để trống và không được chứa ký tự đặc biệt.';
                    } else if (inputField) {
                        inputField.classList.remove('error');
                        errorMessage.classList.add('hidden');
                    }

                    if (inputFieldName && !isValidFieldName) {
                        inputFieldName.classList.add('error');
                        errorMessageName.classList.remove('hidden');
                        errorMessageName.textContent = 'Trường này không được để trống và không được chứa ký tự đặc biệt.';
                    } else if (inputFieldName) {
                        inputFieldName.classList.remove('error');
                        errorMessageName.classList.add('hidden');
                    }
                }
            });

                    </script>



                    <script>


                        const firebaseConfig = {
                            apiKey: "AIzaSyBw_90A6Jnaw8MyUwsM3mzv0pauopphy2w",
                            authDomain: "managehouse-df951.firebaseapp.com",
                            projectId: "managehouse-df951",
                            storageBucket: "managehouse-df951.appspot.com",
                            messagingSenderId: "767540445597",
                            appId: "1:767540445597:web:254317747a881714342f74",
                            measurementId: "G-H4GS7YBG2S"
                        };
                        firebase.initializeApp(firebaseConfig);

                        var image = '';
                        // firebase bucket name
                        // REPLACE WITH THE ONE YOU CREATE
                        // ALSO CHECK STORAGE RULES IN FIREBASE CONSOLE
                        var fbBucketName = 'images';

                        // get elements
                        var uploader = document.getElementById('uploader');
                        var fileButton = document.getElementById('fileButton');

                        // listen for file selection
                        fileButton.addEventListener('change', function (e) {

                            // what happened
                            console.log('file upload event', e);

                            // get file
                            var file = e.target.files[0];

                            // create a storage ref
                        <%--var storageRef = firebase.storage().ref(`${fbBucketName}/${file.name}`);--%>
                            const storageRef = firebase.storage().ref(file.name);
                            // upload file
                            var uploadTask = storageRef.put(file);

                            // The part below is largely copy-pasted from the 'Full Example' section from
                            // https://firebase.google.com/docs/storage/web/upload-files

                            // update progress bar
                            uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED, // or 'state_changed'
                                    function (snapshot) {
                                        // Get task progress, including the number of bytes uploaded and the total number of bytes to be uploaded
                                        var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                                        uploader.value = progress;
                                        console.log('Upload is ' + progress + '% done');
                                        switch (snapshot.state) {
                                            case firebase.storage.TaskState.PAUSED: // or 'paused'
                                                console.log('Upload is paused');
                                                break;
                                            case firebase.storage.TaskState.RUNNING: // or 'running'
                                                console.log('Upload is running');
                                                break;
                                        }
                                    }, function (error) {

                                // A full list of error codes is available at
                                // https://firebase.google.com/docs/storage/web/handle-errors
                                switch (error.code) {
                                    case 'storage/unauthorized':
                                        // User doesn't have permission to access the object
                                        break;

                                    case 'storage/canceled':
                                        // User canceled the upload
                                        break;

                                    case 'storage/unknown':
                                        // Unknown error occurred, inspect error.serverResponse
                                        break;
                                }
                            }, function () {
                                // Upload completed successfully, now we can get the download URL
                                // save this link somewhere, e.g. put it in an input field
                                var downloadURL = uploadTask.snapshot.downloadURL;
                                image = downloadURL;
                                console.log('downloadURL ===>', downloadURL);
                                let divLocation = document.getElementById("imgDiv");
                                let imgElement = document.createElement("img");
                                imgElement.src = downloadURL;
                                imgElement.width = 100;
                                imgElement.height = 100;
                                console.log('pic ==', downloadURL);
                                divLocation.append(imgElement);
                                document.getElementById('avatar').value = downloadURL;
                            });

                        });

                        function resultImage() {
                            console.log('image resulte -->', image);
                            return image;
                        }
                        $(document).ready(function () {
                            //Lấy tỉnh thành
                            $.getJSON('https://esgoo.net/api-tinhthanh/1/0.htm', function (data_tinh) {
                                if (data_tinh.error == 0) {
                                    $.each(data_tinh.data, function (key_tinh, val_tinh) {
                                        $("#tinh").append('<option value="' + val_tinh.id + '">' + val_tinh.full_name + '</option>');
                                    });
                                    $("#tinh").change(function (e) {
                                        var idtinh = $(this).val();
                                        //Lấy quận huyện
                                        $.getJSON('https://esgoo.net/api-tinhthanh/2/' + idtinh + '.htm', function (data_quan) {
                                            if (data_quan.error == 0) {
                                                $("#quan").html('<option value="0">Quận Huyện</option>');
                                                $("#phuong").html('<option value="0">Phường Xã</option>');
                                                $.each(data_quan.data, function (key_quan, val_quan) {
                                                    $("#quan").append('<option value="' + val_quan.id + '">' + val_quan.full_name + '</option>');
                                                });
                                                //Lấy phường xã  
                                                $("#quan").change(function (e) {
                                                    var idquan = $(this).val();
                                                    $.getJSON('https://esgoo.net/api-tinhthanh/3/' + idquan + '.htm', function (data_phuong) {
                                                        if (data_phuong.error == 0) {
                                                            $("#phuong").html('<option value="0">Phường Xã</option>');
                                                            $.each(data_phuong.data, function (key_phuong, val_phuong) {
                                                                $("#phuong").append('<option value="' + val_phuong.id + '">' + val_phuong.full_name + '</option>');
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


                    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
                    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> 

                    </body>

                    </html>
