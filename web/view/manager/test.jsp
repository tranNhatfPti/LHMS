<%-- 
    Document   : test
    Created on : Jun 14, 2024, 11:36:14 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <c:forEach items="${requestScope.listAccount}" var="l">
        <h1>${l.email}</h1>
        
    </c:forEach>
        
        <h1>${requestScope.manager.email}</h1>
</body>
</html>
