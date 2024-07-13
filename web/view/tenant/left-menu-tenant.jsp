<%-- 
    Document   : left-menu-tenant
    Created on : May 20, 2024, 3:50:09 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user">
        <img class="avatarHeader app-sidebar__user-avatar" src="${userInfor.getAvatar()}" width="50px"
             alt="User Image">
        <div>
            <p class="app-sidebar__user-name"><b>${userInfor.fullName}</b></p>
            <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
        </div>
    </div>
    <hr>
    <ul class="app-menu">
        <li><a class="app-menu__item ${tagMenu=="showRoom"?"active":""}" href="home-tenant?service=showLodgingInfor"><i class='app-menu__icon  bx bxs-home'></i><span
                    class="app-menu__label">Trang Chủ</span></a></li>
        <li><a class="app-menu__item ${tagMenu=="showProfile"?"active":""}" href="account?service=showProfile"><i class='app-menu__icon bx bx-id-card'></i><span
                    class="app-menu__label">Hồ Sơ</span></a></li>
        <c:if test="${account.getTypeOfLogin()=='username'}"><li><a class="app-menu__item ${tagMenu=="changePassword"?"active":""}" href="account?service=showChangePassword"><i class='app-menu__icon bx bxs-lock '></i> <span
                        class="app-menu__label">Mật Khẩu</span></a></li></c:if>
        <li><a class="app-menu__item ${tagMenu=="showCostIncurred"?"active":""}" href="cost-incurred?service=showListCost&index=1&pay=all"><i class='app-menu__icon bx bx-task'></i><span
                    class="app-menu__label">Chi Phí Phát Sinh</span></a></li>
        <li><a class="app-menu__item" href="${pageContext.request.contextPath}/bill-of-tenant"><i class='app-menu__icon bx bx-task'></i><span
                    class="app-menu__label">Hóa Đơn</span></a></li>
        <li><a class="app-menu__item ${tagMenu=="feedback"?"active":""}" href="feedback?service=showFeedback"><i class='app-menu__icon fas fa-star'></i><span
                    class="app-menu__label">Phản Hồi</span></a></li>
        <li><a class="app-menu__item ${tagMenu=="chat"?"active":""}" href="${pageContext.request.contextPath}/chat"><i class='app-menu__icon bx bx-mail-send'></i>
                <span class="app-menu__label">Tin nhắn</span></a></li>
        
        <li><a class="app-menu__item" href="account?service=logout"><i class='app-menu__icon bx bx-log-out'></i><span
                    class="app-menu__label">Đăng Xuất</span></a></li>
</aside>
