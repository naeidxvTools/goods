package net.imwork.zhanlong.goods.cart.web.servlet;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.goods.book.domain.Book;
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
import java.util.Map;

@WebServlet(name = "CartItemServlet", urlPatterns = "/CartItemServlet")
public class CartItemServlet extends BaseServlet
{
    private CartItemService cartItemService = new CartItemService();

    /**
     * 添加购物车条目
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Map map = request.getParameterMap();
        CartItem cartItem = CommonUtils.mapToBean(map, CartItem.class);
        Book book = CommonUtils.mapToBean(map, Book.class);
        User user = (User) request.getSession().getAttribute("sessionUser");
        cartItem.setBook(book);
        cartItem.setUser(user);

        cartItemService.add(cartItem);

        return myCart(request,response);
    }

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
        User user = (User) request.getSession().getAttribute("sessionUser");

        List<CartItem> cartItemList = cartItemService.myCart(user.getUid());

        request.setAttribute("cartItemList", cartItemList);

        System.out.println(cartItemList);
        return "f:/jsps/cart/list.jsp";
    }

}
