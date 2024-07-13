<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">

    <hr>
    <ul class="app-menu">
        
        <li><a class="app-menu__item ${tagMenu=="showRoom"?"active":""}" href="room-detail-for-tenant?id=${sessionScope.roomId}"><i class='app-menu__icon  bx bxs-home'></i><span
                    class="app-menu__label">Trang chủ</span></a></li>
        <li>
        <li><a class="app-menu__item ${tagMenu=="showRoom"?"active":""}" href="room?service=showRoomInfor"><i class='app-menu__icon  bx bxs-home'></i><span
                    class="app-menu__label">Thông Tin Cá nhân</span></a></li>
        <li>
            <a id="notificationLink " href="list-notification-for-tenant" class="app-menu__item ">
                <i class='app-menu__icon bx bx-id-card'></i>
                <span class="app-menu__label">Thông Báo Mới</span>
                <span class="badge">${sessionScope.NumberOfNotification}</span> <!-- Example: Replace with dynamic content -->
            </a>
        </li>
        <li><a class="app-menu__item ${tagMenu=="showProfile"?"active":""}" href="account?service=showProfile"><i class='app-menu__icon bx bx-id-card'></i><span
                    class="app-menu__label">Hồ Sơ</span></a></li>
        <li><a class="app-menu__item ${tagMenu=="changePassword"?"active":""}" href="account?service=showChangePassword"><i class='app-menu__icon bx bxs-lock '></i> <span
                    class="app-menu__label">Mật Khẩu</span></a></li>
        <li><a class="app-menu__item" href="account?service=logout"><i class='app-menu__icon bx bx-log-out'></i><span
                    class="app-menu__label">Đăng Xuất</span></a></li>
</aside>
