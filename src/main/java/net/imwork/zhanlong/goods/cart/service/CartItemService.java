package net.imwork.zhanlong.goods.cart.service;

import net.imwork.zhanlong.goods.cart.dao.CartItemDao;
import net.imwork.zhanlong.goods.cart.domain.CartItem;

import java.sql.SQLException;
import java.util.List;

public class CartItemService
{
    private CartItemDao cartItemDao = new CartItemDao();

    /**
     * 我的购物车
     * @param uid
     * @return
     */
    public List<CartItem> myCart(String uid)
    {
        try
        {
            return cartItemDao.findByUser(uid);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


}
