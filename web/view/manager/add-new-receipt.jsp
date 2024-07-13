<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<head>
    <title>Phiếu thu | Quản trị Admin</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resource/doc/css/main.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
    <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">

    <style>
        .container {
            padding-top: 20px; /* Khoảng cách từ top xuống */
            margin-bottom: 20px; /* Khoảng cách dưới cùng */
        }
        .form-group {
            margin-bottom: 20px; /* Khoảng cách giữa các thành phần form */
        }
    </style>
</head>
<body onload="time()" class="app sidebar-mini rtl">
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
            <img class="app-sidebar__user-avatar" src="" width="100px" alt="User Image">
            <div>
                <p class="app-sidebar__user-name"><b></b></p>
                <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
            </div>
        </div>
        <hr>
        <ul class="app-menu">
            
              <li>
                    <a id="notificationLink" href="list-notification-for-manager" class="app-menu__item">
                        <i class='app-menu__icon bx bx-id-card'></i>
                        <span class="app-menu__label">Thông Báo Mới</span>
                        <span class="badge">${sessionScope.NumberOfNotification}</span> <!-- Example: Replace with dynamic content -->
                    </a>
                </li>
            <li><a class="app-menu__item active" href="/ManageLodgingHouse/home-manager"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Quản lí trọ</span></a></li>
            <li><a class="app-menu__item" href="management-lodging-houses"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lí thu chi</span></a></li>             
            <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Chỉ số điện</span></a></li>
            <li><a class="app-menu__item" href="table-data-product.html"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
            <li><a class="app-menu__item" href="table-data-oder.html"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Chỉ số nước</span></a></li>
            <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-cog'></i><span class="app-menu__label">Cài đặt hệ thống</span></a></li>
            <li><a class="app-menu__item" href="${pageContext.request.contextPath}/investment-costs-servlet"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lí khoản phí đầu tư</span></a></li> 
            <li><a class="app-menu__item" href="${pageContext.request.contextPath}/list-staff"><i class='app-menu__icon bx bx-id-card'></i><span class="app-menu__label">Quản lý nhân viên</span></a></li>
        </ul>
    </aside>
    <main class="app-content">
        <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card">
                        <h5 class="card-header">Phiếu thu</h5>
                        <div class="card-body">
                            <form id="receiptForm" action="add-new-receipt" method="post">
                               
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="date"><i class="far fa-calendar-alt mr-1"></i> Ngày phát sinh:</label>
                                        <input type="text" class="form-control" id="date" autocomplete="off" readonly="" name="date">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="creator"><i class="fas fa-user-tag mr-1"></i> Người tạo:</label>
                                        <input type="text" class="form-control" id="creator" value="${account.getEmail()}" name="emailSender">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="customerEmail"><i class="fas fa-file-signature mr-1"></i> Email khách hàng:</label>
                                    <input list="email-list" id="customerEmail" name="tenantEmail" class="form-control" type="email" required placeholder="Enter email addresses separated by commas" multiple>
                                    <datalist id="email-list">
                                        <c:forEach items="${listAccount}" var="acc">
                                            <option value="${acc.getEmail()}"></option>
                                        </c:forEach>
                                    </datalist>
                                    <div id="email-error" style="color:red; display:none;">Vui lòng nhập địa chỉ email hợp lệ.</div>
                                </div>
                                <div class="form-group">
                                    <label for="price"><i class="fas fa-file-signature mr-1"></i> Giá tiền:</label>
                                    <input type="number" class="form-control" id="price" autocomplete="off"  name="price" required>
                                    <div class="invalid-feedback">Giá tiền phải là số nguyên lớn hơn không .</div>
                                </div>
                                <div class="form-group">
                                    <label for="content"><i class="fas fa-file-alt mr-1"></i> Nội dung:</label>
                                    <input type="text" class="form-control" id="content" autocomplete="off" name="description">
                                </div>
                                <button type="submit" class="btn btn-primary">Gửi</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
  <script>
    document.getElementById('receiptForm').addEventListener('submit', function(event) {
    var priceInput = document.getElementById('price');
    var priceValue = priceInput.value;

    // Validate if the input is an integer
    if (!Number.isInteger(Number(priceValue))) {
        priceInput.classList.add('is-invalid');
        event.preventDefault(); // Prevent form submission
    } else {
        // Validate if the input is non-negative
        if (Number(priceValue) < 0) {
            priceInput.classList.add('is-invalid');
            event.preventDefault(); // Prevent form submission
        } else {
            priceInput.classList.remove('is-invalid');
        }
    }

    // Validate email input
    var emailInput = document.getElementById('customerEmail');
    var emailError = document.getElementById('email-error');
    var emails = emailInput.value.split(',');
    var isValid = true;
    for (var i = 0; i < emails.length; i++) {
        var email = emails[i].trim();
        if (!validateEmail(email)) {
            isValid = false;
            break;
        }
    }

    if (!isValid) {
        emailError.style.display = 'block';
        event.preventDefault(); // Prevent form submission
    } else {
        emailError.style.display = 'none';
    }
});

function validateEmail(email) {
    var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

</script>

    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- jQuery -->
    <script src="js/jquery-3.2.1.min.js"></script>
    <!-- Popper.js -->
    <script src="js/popper.min.js"></script>
    <!-- Boxicons -->
    <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>

    <!-- JavaScript để lấy ngày hiện tại và điền vào trường "Ngày phát sinh" -->
    <script>
     // Function để lấy ngày hiện tại và đưa vào trường "Ngày phát sinh"
     function getCurrentDate() {
         var today = new Date();
         var dd = String(today.getDate()).padStart(2, '0');
         var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
         var yyyy = today.getFullYear();

         today = dd + '/' + mm + '/' + yyyy;
         document.getElementById('date').value = today;
     }

     // Gọi hàm để tự động điền ngày khi trang được load
     window.onload = getCurrentDate;
    </script>
</body>
</html>