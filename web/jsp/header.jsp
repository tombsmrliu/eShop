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
			
			<c:if test="${empty user}">
				<li><a href="${pageContext.request.contextPath}/userController?method=loginUI">登录</a></li>
				<li><a href="${pageContext.request.contextPath}/userController?method=registerUI">注册</a></li>
			</c:if>

			<c:if test="${!empty user}">
				<li>欢迎您,${user.username}</li>
				<li><a href="${pageContext.request.contextPath}/userController?method=logout">退出</a></li>
			</c:if>

			<li><a href="jsp/cart.jsp">购物车</a></li>
			<li><a href="${pageContext.request.contextPath }/productController?method=myOrders">我的订单</a></li>
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
				<a class="navbar-brand" href="${pageContext.request.contextPath}/productController?method=index">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">

						  <c:if test="${!empty pageContext.request.queryString and pageContext.request.queryString.substring(4) eq category.cid}">
							  class="active"
						  </c:if>

				</ul>
				<form class="navbar-form navbar-right" method="post" action="${pageContext.request.contextPath}/productController?method=productDetailForWord">
					<div class="form-group">
						<input type="text" id="search" name="pname" class="form-control" placeholder="Search" onkeyup="searchWord(this)">

						<div id="showDiv" style="display:none; position:absolute;z-index:1000;background:#fff; width:179px;border:1px solid #ccc;">

						</div>

					</div>
					<button type="submit" class="btn btn-default">搜索</button>
				</form>

				<%--导入jquery库--%>
				<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/jquery-3.2.1.min.js"></script>

				<!-- 完成异步搜索 -->
				<script type="text/javascript">

                    function overFn(obj){
                        $(obj).css("background","#DBEAF9");
                    }
                    function outFn(obj){
                        $(obj).css("background","#fff");
                    }

                    function clickFn(obj){
                        $("#search").val($(obj).html());
                        $("#showDiv").css("display","none");
                    }


                    function searchWord(obj){
                        //1、获得输入框的输入的内容
                        var word = $(obj).val();
                        //2、根据输入框的内容去数据库中模糊查询---List<Product>
                        var content = "";
                        $.post(
                            "${pageContext.request.contextPath}/productController?method=searchWord",
                            {"word":word},
                            function(data){
                                //3、将返回的商品的名称 现在showDiv中
                                //[{"pid":"1","pname":"小米 4c 官方版","market_price":8999.0,"shop_price":8999.0,"pimage":"products/1/c_0033.jpg","pdate":"2016-08-14","is_hot":1,"pdesc":"小米 4c 标准版 全网通 白色 移动联通电信4G手机 双卡双待 官方好好","pflag":0,"cid":"1"}]

                                if(data.length>0){
                                    for(var i=0;i<data.length;i++){
                                        content+="<div style='padding:5px;cursor:pointer' onclick='clickFn(this)' onmouseover='overFn(this)' onmouseout='outFn(this)'>"+data[i]+"</div>";
                                    }
                                    $("#showDiv").html(content);
                                    $("#showDiv").css("display","block");
                                }

                            },
                            "json"
                        );

                    }
				</script>

			</div>
		</div>
	</nav>
</div>



<script type="text/javascript">

	//header.jsp加载完成后去服务端获取所有category数据
	$(function () {
		var content = "";

		$.post(
            "${pageContext.request.contextPath}/productController?method=categoryList",
			function (res) {
                 for (var i = 0 ; i < res.length ; i++){
					 content += "<li><a href='${pageContext.request.contextPath}/productController?method=productList&cid="+res[i].cid+"'>"+res[i].cname+"</a></li>";
				 }

                $("#categoryUl").html(content);
            },
			"json"
		);

    })


</script>

