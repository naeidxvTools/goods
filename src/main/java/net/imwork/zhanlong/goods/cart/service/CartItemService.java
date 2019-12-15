package net.imwork.zhanlong.goods.cart.service;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.goods.cart.dao.CartItemDao;
import net.imwork.zhanlong.goods.cart.domain.CartItem;

import java.sql.SQLException;
import java.util.List;

public class CartItemService
{
    private CartItemDao cartItemDao = new CartItemDao();

    /**
     * 添加条目
     * @param cartItem
     */
    public void add(CartItem cartItem)
    {
        try
        {
            CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(),
                    cartItem.getBook().getBid());

            if (_cartItem == null)
            {
                cartItem.setCartItemId(CommonUtils.uuid());
                cartItemDao.addCartItem(cartItem);
            }else
            {
                int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
                //_cartItem.setQuantity(quantity);
                cartItemDao.updateQuantity(_cartItem.getCartItemId(),quantity);
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

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
