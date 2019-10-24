package net.imwork.zhanlong.user.web.servlet;

import net.imwork.zhanlong.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户模块WEB层
 */
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet
{
    /**
     * 注册功能
     * @param request
     * @param response
     * @return
     */
    public String regist(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("regist....");
        return null;
    }

}
