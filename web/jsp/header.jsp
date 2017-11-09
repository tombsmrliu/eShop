<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid" style="height: 80px">
	<div class="col-md-4" style="position:relative">
		<img src="jsp/img/logo2.png" style="position: absolute;left: 0;top: -55px" />
	</div>
	<div class="col-md-5">
		<img src="jsp/img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<li><a href="login.jsp">登录</a></li>
			<li><a href="register.jsp">注册</a></li>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<%--TODO 1.1主页显示分类信息--%>
					<c:forEach items="${sessionScope.categoryMapList}" var="category">

						<%--可能会有个active激活状态--%>
						<li
						  <c:if test="${!empty pageContext.request.queryString and pageContext.request.queryString.substring(4) eq category.cid}">
							  class="active"
						  </c:if>
						>
							<a href="${pageContext.request.contextPath}/productListController?cid=${category.cid}">
								${category.cname}<span class="sr-only">(current)</span>
							</a>
						</li>

					</c:forEach>




				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>

