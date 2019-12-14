package net.imwork.zhanlong.goods.cart.web.servlet;

import net.imwork.zhanlong.goods.cart.domain.CartItem;
import net.imwork.zhanlong.goods.cart.service.CartItemService;
import net.imwork.zhanlong.goods.user.domain.User;
import net.imwork.zhanlong.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CartItemServlet",urlPatterns = "/CartItemServlet")
public class CartItemServlet extends BaseServlet
{
    private CartItemService cartItemService = new CartItemService();

    /**
     * 我的购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String myCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        User user = (User)request.getSession().getAttribute("sessionUser");

        List<CartItem> cartItemList = cartItemService.myCart(user.getUid());

        request.setAttribute("cartItemList", cartItemList);

        System.out.println(cartItemList);
        return "f:/jsps/cart/list.jsp";
    }

}
