<%--
  Created by IntelliJ IDEA.
  User: Tombsliu
  Date: 2017/11/7
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>eShop</title>
</head>
<body>

    <%
        response.sendRedirect(request.getContextPath()+"/productController?method=index");
    %>


</body>
</html>
