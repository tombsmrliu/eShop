<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="jsp/css/bootstrap.min.css" type="text/css"/>
    <script src="jsp/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="jsp/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="jsp/css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
            width: 100%;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }
    </style>
</head>

<body>


<!-- 引入header.jsp -->
<jsp:include page="header.jsp"></jsp:include>

<div class="container">

    <div class="row">
        <div class="col-md-12">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/productController?method=index">首页</a></li>
            </ol>
        </div>

    </div>


    <div class="row">

        <c:forEach items="${requestScope.pageBean.list}" var="product">

            <div class="col-md-2" style="height: 250px">
                <a href="${pageContext.request.contextPath}/productController?method=productDetail&pid=${product.pid}&cid=${cid}&currentPage=${pageBean.currentPage}">
                    <img src="jsp/${product.pimage}" width="170"
                         height="170" style="display: inline-block;">
                </a>
                <p>
                    <a href="${pageContext.request.contextPath}/productController?method=productDetail&pid=${product.pid}&cid=${cid}&currentPage=${pageBean.currentPage}"
                       style='color: green'>
                            ${product.pname}
                    </a>
                </p>
                <p>
                    <font color="#FF0000">商城价：&yen;${product.shop_price}</font>
                </p>
            </div>

        </c:forEach>

    </div>


    <!--分页 -->
    <div class="row">

            <ul class="pagination" style="text-align: center; margin-top: 10px;">
                <li class="disabled"><a href="#" aria-label="Previous"><span
                        aria-hidden="true">&laquo;</span></a></li>
                <c:forEach begin="1" end="${pageBean.totalPage}" var="num">
                    <c:if test="${num == pageBean.currentPage}">
                        <li class="active"><a href="javascript:void(0);">${num}</a></li>
                    </c:if>
                    <c:if test="${num != pageBean.currentPage}">
                        <li>
                            <a href="${pageContext.request.contextPath}/productController?method=productDetail&cid=${cid}&currentPage=${num}">${num}</a>
                        </li>
                    </c:if>
                </c:forEach>
                <li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                </a></li>
            </ul>

    </div>
    <!-- 分页结束 -->

    <!--商品浏览记录-->
    <div class="row">


            <h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
            <div style="width: 50%; float: right; text-align: right;">
                <a href="">more</a>
            </div>
            <div style="clear: both;"></div>

            <div style="overflow: hidden;">

                <ul style="list-style: none;">
                    <li
                            style="width: 150px; height: 216; float: left; margin: 0 8px 0 0; padding: 0 18px 15px; text-align: center;">
                        <img
                                src="jsp/products/1/cs10001.jpg" width="130px" height="130px"/></li>
                </ul>

            </div>

    </div>

</div>
<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>


</body>

</html>