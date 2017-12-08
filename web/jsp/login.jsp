<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/bootstrap.min.css" type="text/css"/>
    <script src="${pageContext.request.contextPath}/jsp/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jsp/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jsp/js/jquery.form.min.js" type="text/javascript"></script>
    <%--引入表单校验插件--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/jquery.validate.min.js"></script>

    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }

        .container .row div {
            /* position:relative;
                         float:left; */

        }

        font {
            color: #666;
            font-size: 22px;
            font-weight: normal;
            padding-right: 17px;
        }

        .error {
            color: red;
        }
    </style>


    <script type="text/javascript">

        function updateImg(obj) {
            obj.src = "${pageContext.request.contextPath}/checkImg?time="+new Date().getTime();
        }

    </script>


</head>
<body>

<!-- 引入header.jsp -->
<jsp:include page="header.jsp"></jsp:include>


<div class="container"
     style="width: 100%; height: 460px; background: #FF2C4C url('jsp/images/loginbg.jpg') no-repeat;">
    <div class="row">
        <div class="col-md-7">

        </div>

        <div class="col-md-5">
            <div
                    style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
                <font>会员登录</font>USER LOGIN
                <div>&nbsp;</div>
                <form class="form-horizontal" id="loginform"
                      action="${pageContext.request.contextPath}/userController?method=login" method="post">
                    <div class="form-group">
                        <label for="username" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="username" id="username"
                                   placeholder="请输入用户名">
                            <span class="error">${usernamemsg}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" name="password" id="password"
                                   placeholder="请输入密码">
                            <span class="error">${pwdmsg}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="valcode" class="col-sm-2 control-label">验证码</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control" name="valcode" id="valcode"
                                   placeholder="请输入验证码">
                            <span class="error">${valcodemsg}</span>
                        </div>
                        <div class="col-sm-3">
                            <img src="${pageContext.request.contextPath}/checkImg" onclick="updateImg(this)"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> 自动登录
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <input type="submit" width="100" value="登录"
                                   style="background: url('jsp/images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>


<script type="text/javascript">

    $(function () {

        $("#loginform").validate({

            submitHandler: function (form) {

                form.submit();

            },

            rules: {

                username: {
                    required: true,
                },


                password: {
                    required: true,
                },

                valcode: {required: true}

            },


            messages: {

                    required: "该项为必填项",

            }

        });

    });


</script>


</body>
</html>