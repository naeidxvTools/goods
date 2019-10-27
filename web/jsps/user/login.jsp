<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/login.css'/>">
    <script type="text/javascript" src="<c:url value='/jquery/jquery-3.4.0.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/jsps/js/user/login.js'/> "></script>
    <script src="<c:url value='/js/common.js'/>"></script>
</head>
<body>
<div class="main">
    <div><img src="<c:url value='/images/zan.png'/>" width="100px"/></div>
    <div>
        <div class="imageDiv"><img class="img" src="<c:url value='/images/zj.png'/>"/></div>
        <div class="login1">
            <div class="login2">
                <div class="loginTopDiv">
                    <span class="loginTop">船舶会员登录</span>
                    <span>
                <a href="<c:url value='/jsps/user/regist.jsp'/>" class="registBtn"></a>
              </span>
                </div>
                <div>
                    <form target="_top" action="<c:url value='/index.jsp'/>" method="post" id="loginForm">
                        <input type="hidden" name="method" value="" />
                        <table>
                            <tr>
                                <td width="50"></td>
                                <td><label class="error" id="msg"></label></td>
                            </tr>
                            <tr>
                                <td width="50">用户名</td>
                                <td><input class="input" type="text" name="loginname" id="loginname"/></td>
                            </tr>
                            <tr>
                                <td height="20">&nbsp;</td>
                                <td><label id="loginnameError" class="error"></label></td>
                            </tr>
                            <tr>
                                <td>密　码</td>
                                <td><input class="input" type="password" name="loginpass" id="loginpass"/></td>
                            </tr>
                            <tr>
                                <td height="20">&nbsp;</td>
                                <td><label id="loginpassError" class="error"></label></td>
                            </tr>
                            <tr>
                                <td>验证码</td>
                                <td>
                                    <input class="input yzm" type="text" name="verifyCode" id="verifyCode" value=""/>
                                    <img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/> "/>
                                    <a id="aVerifyCode">换张图</a>
                                </td>
                            </tr>
                            <tr>
                                <td height="20px">&nbsp;</td>
                                <td><label id="verifyCodeError" class="error"></label></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td align="left">
                                    <input type="image" id="submit" src="<c:url value='/images/login1.jpg'/>" class="loginBtn"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
