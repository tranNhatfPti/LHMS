<%-- 
    Document   : index-demo
    Created on : May 15, 2024, 4:17:03 PM
    Author     : ASUS ZenBook
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Thêm mới nhà trọ | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <script src="https://esgoo.net/scripts/jquery.js"></script>
        <script src="https://www.gstatic.com/firebasejs/4.2.0/firebase.js"></script>
        <style>
            .house-icon {
                font-size: 50px;
                color: #007bff;
            }
            .card-house {
                text-align: center;
            }
            .card-house .card-body {
                padding: 1rem;
            }
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
            .form-message {
                color: #dc3545; /* Màu đỏ */
                font-size: 0.875rem; /* Kích thước chữ nhỏ */
                margin-top: 0.25rem; /* Khoảng cách từ input */
            }
        </style>
    </head>

    <body class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">


                <!-- User Menu-->
                <li><a class="app-nav__item" href="/LHMS/LoginServlet?service=logout"><i class='bx bx-log-out bx-rotate-180'></i> </a>

                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
           
            <hr>
            <ul class="app-menu">

                <li><a class="app-menu__item active" href="home-controller"><i class='app-menu__icon bx bx-tachometer'></i><span
                            class="app-menu__label">Bảng điều khiển</span></a></li>
                <li><a class="app-menu__item " href="management-lodging-houses?index=1"><i class='app-menu__icon bx bx-id-card'></i> <span
                            class="app-menu__label">Quản lí các khu trọ</span></a></li>
                <li><a class="app-menu__item " href="table-data-table.html"><i class='app-menu__icon bx bx-id-card'></i> <span
                            class="app-menu__label">Dịch vụ</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Chỉ số điện</span></a></li>
                <li><a class="app-menu__item" href="table-data-product.html"><i
                            class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a>
                </li>
                <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span
                            class="app-menu__label">Chỉ số nước</span></a></li>
                <li><a class="app-menu__item" href="table-data-banned.html"><i class='app-menu__icon bx bx-run'></i><span
                            class="app-menu__label">Quản lý nội bộ
                        </span></a></li>
                <li><a class="app-menu__item" href="table-data-money.html"><i class='app-menu__icon bx bx-dollar'></i><span
                            class="app-menu__label">Phát sinh</span></a></li>
                <li><a class="app-menu__item" href="quan-ly-bao-cao.html"><i
                            class='app-menu__icon bx bx-pie-chart-alt-2'></i><span class="app-menu__label">Báo cáo doanh thu</span></a>
                </li>
                <li><a class="app-menu__item" href="page-calendar.html"><i class='app-menu__icon bx bx-calendar-check'></i><span
                            class="app-menu__label">Tính tiền </span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài
                            đặt hệ thống</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div class="container mt-5">
                <h1 class="modal-title" id="addModalLabel"style="text-align:center;">Thêm nhà trọ mới</h1>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addLodgingForm" action="add-lodging-house" method="post">
                    <div class="form-row">

                        <div class="form-group col-md-6">
                            <label class="control-label">Tên nhà trọ </label>
                            <input class="form-control" type="text" id="tenNhaTro" name="nameLodging" required>
                            <span id="form-message" class="form-message"></span>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="tinh">Thành phố/Tỉnh<span style="color:red;">(*)</span></label>
                            <select class="form-control" id="tinh" name="tinh" title="Chọn Tỉnh Thành" required>
                                <option value="0">Tỉnh Thành</option>
                                <!-- Populate with options dynamically if applicable -->
                            </select>
                            <span id="form-message-tinh" class="form-message"></span>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="quan">Quận/Huyện<span style="color:red;">(*)</span></label>
                            <select class="form-control" id="quan" name="quan" title="Chọn Quận Huyện" required>
                                <option value="0">Quận Huyện</option>
                                <!-- Populate with options dynamically if applicable -->
                            </select>
                            <span id="form-message-quan" class="form-message"></span>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="phuong">Phường/Xã</label>
                            <select class="form-control" id="phuong" name="phuong" title="Chọn Phường Xã" required>
                                <option value="0">Phường Xã</option>
                                <!-- Populate with options dynamically if applicable -->
                            </select>
                            <span id="form-message-phuong" class="form-message"></span>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="addressDetail">Địa chỉ cụ thể<span style="color:red;">(*)</span></label>
                            <input type="text" name="addressDetail" class="form-control" id="addressDetail" placeholder="Nhập địa chỉ cụ thể">

                            <span id="form-message-tinh" class="form-message"></span>                        
                        </div>
                        <div class="form-group col-md-6">
                            <label for="numberOfRoom">Số lượng phòng<span style="color:red;">(*)</span></label>
                            <input type="number" name="numberOfRoom" class="form-control" id="numberOfRoom" placeholder="Nhập số phòng" min="1" step="1" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="status">Trạng thái<span style="color:red;">(*)</span></label>
                            <select class="form-control" id="status" name="status" title="Chọn Trạng thái" required>
                                <option value="1">Còn phòng</option>
                                <option value="0">Hết phòng</option>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="imageUpload">Tải ảnh lên</label>
                            <progress value="0" max="100" id="uploader">0%</progress>
                            <input type="file" value="upload" accept=".jpg" id="fileButton">
                            <input name="avatar" type="text" id="avatar" style="display: none">
                            <div id="imgDiv"></div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Thêm nhà trọ</button>
                </form>

                <p style="color:red">${requestScope.error}</p>
            </div>



        </main>  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
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
<script type="text/javascript">
    document.getElementById('tenNhaTro').addEventListener('blur', function () {
        const input = this.value.trim();
        const formMessage = document.getElementById('form-message');
        
        // Kiểm tra nếu ô input trống
        if (input === '') {
            formMessage.textContent = 'Vui lòng nhập tên trọ.';
        } 
        // Kiểm tra khoảng trắng ở đầu hoặc cuối
        else if (this.value !== input) {
            formMessage.textContent = 'Tên trọ không được chứa khoảng trắng ở đầu hoặc cuối.';
        } 
        // Kiểm tra ký tự không phải chữ cái
        else if (!/^[\p{L}\s]+$/u.test(input)) {
            formMessage.textContent = 'Tên trọ chỉ được chứa chữ cái và khoảng trắng.';
        } 
        else {
            formMessage.textContent = '';
        }
    });
</script>

        <script>
            document.getElementById('addLodgingForm').addEventListener('submit', function (event) {
                // Function to validate dropdown selection
                function validateDropdown(dropdownId, errorMessageId) {
                    const selectedValue = document.getElementById(dropdownId).value;
                    if (selectedValue === '0') {
                        document.getElementById(errorMessageId).textContent = 'Vui lòng chọn một giá trị.';
                        return false; // Validation failed
                    } else {
                        document.getElementById(errorMessageId).textContent = ''; // Clear error message
                        return true; // Validation passed
                    }
                }

                // Function to check valid name
                function checkValidName(errorMessageId) {
                    const input = document.getElementById("tenNhaTro").value.trim();
                    if (input === '') {
                        document.getElementById(errorMessageId).textContent = 'Vui lòng nhập tên trọ.';
                        return false;
                    } else {
                        document.getElementById(errorMessageId).textContent = '';
                        return true;
                    }
                }

                // Validate each dropdown
                const isTinhValid = validateDropdown('tinh', 'form-message-tinh');
                const isQuanValid = validateDropdown('quan', 'form-message-quan');
                const isPhuongValid = validateDropdown('phuong', 'form-message-phuong');
                const isValidName = checkValidName('form-message');

                // Prevent form submission if any validation fails
                if (!isTinhValid || !isQuanValid || !isPhuongValid || !isValidName) {
                    console.log('Form submission prevented due to validation errors.');
                    event.preventDefault();
                } else {
                    console.log('Form submitted successfully.');
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
