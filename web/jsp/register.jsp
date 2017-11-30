<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="jsp/css/bootstrap.min.css" type="text/css"/>
<script src="jsp/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="jsp/js/bootstrap.min.js" type="text/javascript"></script>
<script src="jsp/js/jquery.form.min.js" type="text/javascript"></script>

<%--引入表单校验插件--%>
<script type="text/javascript" src="jsp/js/jquery.validate.min.js"></script>

<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="jsp/css/style.css" type="text/css"/>

<style>
    body {
        margin-top: 20px;
        margin: 0 auto;
    }

    .carousel-inner .item img {
        width: 100%;
        height: 300px;
    }

    font {
        color: #3164af;
        font-size: 18px;
        font-weight: normal;
        padding: 0 10px;
    }
</style>

<script type="text/javascript">

    $(function () {

        $("#username").on("blur", function () {

            //获得文本框的值
            var username = $(this).val();

            //异步发送数据
            if (username != "") {

                var url = "${pageContext.request.contextPath}/userController";
                var param = {method: "checkUsername", "username": username};

                $.get(
                    url,
                    param,
                    function (data) {
                        if (data == 1) {
                            $("#s1").html("用户名可以使用").css("color", "#0f0");
                            $("#regBut").attr("disabled", false);
                        } else if (data == 2) {
                            $("#s1").html("用户名已经被注册").css("color", "#f00");
                            $("#regBut").attr("disabled", true);
                        }
                    }
                )

            }
        });

    });

</script>


</head>
<body>

<!-- 引入header.jsp -->
<jsp:include page="header.jsp"></jsp:include>

<div class="container"
     style="width: 100%; background: url('image/regist_bg.jpg');">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"
             style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
            <font>会员注册</font>USER REGISTER
            <form class="form-horizontal" id="regform"
                  action="${pageContext.request.contextPath}/userController?method=register" method="post"
                  style="margin-top: 5px;">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="username" id="username"
                               placeholder="请输入用户名">
                        <span id="s1"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" name="password" id="password"
                               placeholder="请输入密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirm_password" class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" name="confirm_password" id="confirm_password"
                               placeholder="请输入确认密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-6">
                        <input type="email" class="form-control" name="email" id="email"
                               placeholder="Email">
                    </div>
                </div>
                <div class="form-group">
                    <label for="fullname" class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" name="fullname" id="fullname"
                               placeholder="请输入姓名">
                    </div>
                </div>
                <div class="form-group opt">
                    <label for="gender" class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-6">
                        <label class="radio-inline"> <input type="radio"
                                                            name="gender" id="gender" value="m">
                            男
                        </label> <label class="radio-inline"> <input type="radio"
                                                                     name="gender" id="gender"
                                                                     value="f">
                        女
                    </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="birthday" class="col-sm-2 control-label">出生日期</label>
                    <div class="col-sm-6">
                        <input type="date" name="birthday" id="birthday" class="form-control">
                    </div>
                </div>

                <div class="form-group">
                    <label for="valcode" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-3">
                        <input type="text" name="valcode" id="valcode" class="form-control">

                    </div>
                    <div class="col-sm-2">
                        <img src="jsp/image/captcha.jhtml"/>
                    </div>

                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" width="100" value="注册" name="submit"
                               style="background: url('jsp/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
                    </div>
                </div>
            </form>
        </div>

        <div class="col-md-2"></div>

    </div>
</div>

<!-- 引入footer.jsp -->
<jsp:include page="footer.jsp"></jsp:include>


<script type="text/javascript">

    $(function () {

        $("#regform").validate({

            submitHandler: function (form) {
                form.submit();
            },

            rules: {

                username: {
                    required: true,
                    minlength: 2,
                },

                password: {
                    required: true,
                    minlength: 8
                },
                confirm_password: {
                    required: true,
                    minlength: 8,
                    equalTo: "#password"
                },
                email: {
                    required: true,
                    email: true,
                },
                fullname: {
                    required: true,
                    minlength: 3
                },
                gender: {required: true},
                birthday: {
                    required: true,
                    date: true
                },
                valcode: {required: true}

            },

            messages: {
                required: "This field is required.",
                email: "Please enter a valid email address.",
                date: "Please enter a valid date.",
                equalTo: "Please enter the same value again.",
                minlength: $.validator.format("Please enter at least {0} characters."),

            }

        });

    });

</script>


</body>
</html>




