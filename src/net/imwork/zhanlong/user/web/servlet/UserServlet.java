package net.imwork.zhanlong.user.web.servlet;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.servlet.BaseServlet;
import net.imwork.zhanlong.user.domain.User;
import net.imwork.zhanlong.user.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        User user = CommonUtils.mapToBean(request.getParameterMap(), User.class);

        //2.校验之，如果校验失败，保存错误信息，返回到regist.jsp显示
        Map<String, String> errors = validateRegist(user, request);
        if (!errors.isEmpty())
        {
            request.setAttribute("errors", errors);
            return "f:/jsps/user/regist.jsp";
        }

        //3.使用service完成业务
        userService.regist(user);

        //4.保存成功信息，转发到msg.jsp显示
        request.setAttribute("user", user);
        request.setAttribute("code","success");
        request.setAttribute("msg","注册成功，请马上到邮箱中激活!");
        return "f:/jsps/msg.jsp";
    }

    /**
     * 注册校验
     * @return
     */
    private Map<String, String> validateRegist(User user, HttpServletRequest request)
    {
        Map<String, String> errors = new HashMap<>();

        //1.校验用户名
        String loginname = user.getLoginname();
        if (loginname == null || loginname.trim().isEmpty())
        {
            errors.put("loginname", "（服务端）用户名不能为空!");
        } else if (loginname.length() < 3 || loginname.length() > 20)
        {
            errors.put("loginname", "（服务端）用户名长度必须在3-20之间!");
        } else if (!userService.ajaxValidateLoginname(loginname))
        {
            errors.put("loginname", "（服务端）用户名已经注册!");
        }

        //2.校验登录密码
        String loginpass = user.getLoginpass();
        if (loginpass == null || loginpass.trim().isEmpty())
        {
            errors.put("loginpass", "（服务端）密码不能为空!");
        } else if (loginpass.length() < 3 || loginpass.length() > 20)
        {
            errors.put("loginpass", "（服务端）密码长度必须在3-20之间!");
        }

        //3.校验确认密码
        String reloginpass = user.getReloginpass();
        if (reloginpass == null || reloginpass.trim().isEmpty())
        {
            errors.put("reloginpass", "（服务端）确认密码不能为空!");
        } else if (!reloginpass.equals(loginpass))
        {
            errors.put("reloginpass", "（服务端）两次输入不一致!");
        }

        //4.校验Email
        String email = user.getEmail();
        if (email == null || email.trim().isEmpty())
        {
            errors.put("email", "（服务端）email不能为空!");
        } else if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$"))
        {
            errors.put("email", "（服务端）Email格式错误!");
        } else if (!userService.ajaxValidateEmail(email))
        {
            errors.put("email", "（服务端）email已经注册!");
        }

        //5.校验验证码
        String verifyCode = user.getVerifyCode();
        if (verifyCode == null || verifyCode.trim().isEmpty())
        {
            errors.put("verifyCode", "（服务端）验证码不能为空!");
        } else if (!((String)request.getSession().getAttribute("verifyCode")).equalsIgnoreCase(verifyCode))
        {
            errors.put("reloginpass", "（服务端）验证码错误!");
        }

        return errors;
    }

    /**
     * 激活功能
     * @param request
     * @param response
     * @return
     */
    public String activation(HttpServletRequest request, HttpServletResponse response)
    {

        String activationCode = (String) request.getParameter("activationCode");
        System.out.println("activation...." + activationCode);

        return null;
    }

}
