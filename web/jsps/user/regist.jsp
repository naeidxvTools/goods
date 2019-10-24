<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>注册页面</title>
    <link rel="stylesheet" href="<c:url value='/jsps/css/user/regist.css'/>" type="text/css">
</head>
<body>
    <div id="divMain">

        <div id="divTitle">
            <span id="spanTitle">新用户注册</span>
        </div>

        <div id="divBody">
            <table id="tableForm">
                <tr>
                    <td class="tdText">用户名：</td>
                    <td class="tdInput"><input class="inputClass" type="text" name="loginname" id="loginname"></td>
                    <td class="tdError"><label class="errorClass" id="loginnameError">用户名不能为空！</label></td>
                </tr>
                <tr>
                    <td class="tdText">登录密码：</td>
                    <td><input class="inputClass" type="password" name="loginpass" id="loginpass"></td>
                    <td><label class="errorClass" id="loginpassError"></label></td>
                </tr>
                <tr>
                    <td class="tdText">确认密码：</td>
                    <td><input class="inputClass" type="password" name="reloginpass" id="reloginpass"></td>
                    <td><label class="errorClass" id="reloginpassError"></label></td>
                </tr>
                <tr>
                    <td class="tdText">Email：</td>
                    <td><input class="inputClass" type="text" name="email" id="email"></td>
                    <td><label class="errorClass" id="emailError"></label></td>
                </tr>
                <tr>
                    <td class="tdText">验证码：</td>
                    <td><input class="inputClass" type="text" name="verifyCode" id="verifyCode"></td>
                    <td><label class="errorClass" id="verifyCodeError"></label></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div id="divVerifyCode"><img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/> "></div>
                    </td>
                    <td><label><a href="">换一张</a></label></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="image" src="<c:url value='/images/regist1.jpg'/> " id="submitBtn"></td>
                    <td><label></label></td>
                </tr>
            </table>
        </div>

    </div>
</body>
</html>
