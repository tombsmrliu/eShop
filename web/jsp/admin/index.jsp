<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>网上商城管理中心</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <style type="text/css">
        body {
            color: white;
        }
    </style>
</head>
<body style="background: #278296">
<center></center>
<form method="post" action="${pageContext.request.contextPath}/adminController?method=login" id="adminForm">

    <table cellspacing="0" cellpadding="0" style="margin-top: 100px" align="center">
        <tr>
            <td style="padding-left: 50px">
                <table>
                    <tr>
                        <td>管理员姓名：</td>
                        <td><input type="text" name="username" id="username"/>
                        </td>
                    </tr>
                    <tr>
                        <td>管理员密码：</td>
                        <td><input type="password" name="password" id="password"/>
                            </td>
                    </tr>

                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="进入管理中心" class="button"/></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/js/jquery.validate.min.js"></script>

<script language="JavaScript">

    <!--


    /**
     * 检查表单输入的内容
     */
    $(function () {

        $("#adminForm").validate({

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
            },
            messages: {
                username: {
                    required: "该项为必填项",
                    minlength: $.validator.format("请输入至少{0}个字符"),
                },


                password: {
                    required: "该项为必填项",
                    minlength: $.validator.format("请输入至少{0}个字符"),
                }
            }
        });
    });


    //-->
</script>
</body>