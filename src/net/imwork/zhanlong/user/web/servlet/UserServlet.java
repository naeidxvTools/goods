package net.imwork.zhanlong.user.web.servlet;

import net.imwork.zhanlong.servlet.BaseServlet;
import net.imwork.zhanlong.user.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户模块WEB层
 */
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet
{
    private UserService userService = new UserService();

    /**
     * 校验用户名是否注册
     * @param request
     * @param response
     * @return
     */
    public String ajaxValidateLoginname(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //1.得到用户名
        String loginname = request.getParameter("loginname");

        //2.通过service得到校验结果
        boolean b = userService.ajaxValidateLoginname(loginname);

        //3.发给客户端
        response.getWriter().print(b);

        return null;
    }

    /**
     * 校验邮箱是否注册
     * @param request
     * @param response
     * @return
     */
    public String ajaxValidateEmail(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //1.获取Email
        String email = request.getParameter("email");

        //2.通过service得到校验结果
        boolean b = userService.ajaxValidateEmail(email);

        //3.发给客户端
        response.getWriter().print(b);
        return null;
    }

    /**
     * 校验验证码是否正确
     * @param request
     * @param response
     * @return
     */
    public String ajaxValidateVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //1.获取输入框的验证码
        String verifyCode = request.getParameter("verifyCode");

        //2.获取图片上真实的验证码
        String sessionVerifyCode = (String) request.getSession().getAttribute("verifyCode");

        //3.进行比较（忽略大小写）
        boolean b = sessionVerifyCode.equalsIgnoreCase(verifyCode);

        //4.发送给客户端
        response.getWriter().print(b);

        return null;
    }
    /**
     * 注册功能
     * @param request
     * @param response
     * @return
     */
    public String regist(HttpServletRequest request, HttpServletResponse response)
    {
        //1.封装表单数据到User对象

        //2.校验之，如果校验失败，保存错误信息，返回到regist.jsp显示

        //3.使用service完成业务

        //4.保存成功信息，转发到msg.jsp显示

        return null;
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @return
     */
    public String activation(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("activation....");
        return null;
    }

}
