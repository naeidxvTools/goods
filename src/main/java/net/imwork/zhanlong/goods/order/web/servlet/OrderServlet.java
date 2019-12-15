package net.imwork.zhanlong.goods.order.web.servlet;

import net.imwork.zhanlong.goods.order.domain.Order;
import net.imwork.zhanlong.goods.order.service.OrderService;
import net.imwork.zhanlong.goods.pager.PageBean;
import net.imwork.zhanlong.goods.user.domain.User;
import net.imwork.zhanlong.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OrderServlet",urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet
{
    private OrderService orderService = new OrderService();

    /**
     * 获得当前页码
     * @param request
     * @return
     */
    private int getPc(HttpServletRequest request)
    {
        int pc = 1;
        String param = request.getParameter("pc");

        if (param != null && !param.trim().isEmpty())
        {
            try
            {
                pc = Integer.parseInt(param);
            } catch (RuntimeException e)
            {}
        }
        return pc;
    }

    /**
     * 截取url，页面中的分页导航中需要使用它作为超链接的目标
     * @param request
     * @return
     */
    private String getUrl(HttpServletRequest request)
    {
        String url = request.getRequestURI() + "?" + request.getQueryString();

        //如果url中存在pc参数，截取掉，如果不存在那就不用截取
        int index = url.lastIndexOf("&pc=");
        if (index != -1)
        {
            url = url.substring(0, index);
        }
        return url;
    }

    /**
     * 我的订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        //1.得到pc:如果页面传，使用页面的，如果没传，pc=1
        int pc = getPc(request);

        //2.得到url
        String url = getUrl(request);

        //3.从当前session中获取User
        User user = (User) request.getSession().getAttribute("sessionUser");

        //4.使用pc和cid调用service#findByCategory得到PageBean
        PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);

        //5.给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
        pb.setUrl(url);
        request.setAttribute("pb", pb);
        return "/jsps/order/list.jsp";
    }

}
