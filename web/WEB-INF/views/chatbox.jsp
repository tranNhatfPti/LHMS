<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link
            href="//maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
            rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/static/css/style.css" />">

        <link rel="icon" type="image/png"
              href="<c:url value="/static/images/icon.jpg" />">
        <title>Chat</title>
    </head>
    <body>
        <div class="container">
            <div class="conversation-container">
                <div class="left-side active">
                    <p id="username" style="display: none">${sessionScope.account.accountID}</p>
                    <p id="userAvatar" style="display: none">
                        ${pageContext.request.contextPath}/Resource/images/avatar-default.jpg
                    </p>
                    <div class="list-user-search">
                        <input type="text" class="txt-input" data-type="user" placeholder="Search..." onkeyup="searchUser(this)">
                    </div>
                    <div class="list-user">
                        <ul>
                            <c:forEach var="friend" items="${friends}">
                                <li id=${friend.accountID} onclick="setReceiver(this)">
                                    <div class="user-contain">
                                        <div class="user-img">
                                            <img id="img-${friend.accountID}"
                                                 src="${pageContext.request.contextPath}/Resource/images/avatar-default.jpg"
                                                 alt="Image of user">
                                            <div id="status-${friend.accountID}" class="user-img-dot"></div>
                                        </div>
                                        <div class="user-info">
                                            <span class="user-name">${friend.fullName}</span>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="right-side" id="receiver">
                    <!-- dùng JS để thêm nội dùng vào right side -->
                </div>
            </div>
        </div>

        <script type="text/javascript"
        src="<c:url value="/static/js/chatbox.js" />" charset="utf-8"></script>
    </body>
</html>