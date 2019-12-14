package net.imwork.zhanlong.goods.cart.dao;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.goods.book.domain.Book;
import net.imwork.zhanlong.goods.cart.domain.CartItem;
import net.imwork.zhanlong.goods.user.domain.User;
import net.imwork.zhanlong.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartItemDao
{
    private QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 把一个map映射成一个CartItem
     * @param map
     * @return
     */
    private CartItem toCartItem(Map<String, Object> map)
    {
        if(map == null) return null;
        CartItem cartItem = CommonUtils.mapToBean(map,CartItem.class);
        Book book = CommonUtils.mapToBean(map, Book.class);
        User user = CommonUtils.mapToBean(map, User.class);

        cartItem.setBook(book);
        cartItem.setUser(user);
        return cartItem;
    }

    /**
     * 把多个map(List<Map>)映射成多个CartItem(List<CartItem>)
     * @param mapList
     * @return
     */
    private List<CartItem> toCartItemList(List<Map<String, Object>> mapList)
    {
        List<CartItem> list = new ArrayList<>();

        for (Map<String, Object> map : mapList)
        {
            CartItem cartItem = toCartItem(map);
            list.add(cartItem);
        }
        return list;
    }

    /**
     * 通过用户查询购物车条目
     * @param uid
     * @return
     */
    public List<CartItem> findByUser(String uid) throws SQLException
    {
        String sql = "select * from t_cartitem c, t_book b where c.bid = b.bid and uid=? order by c.orderBy";
        List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), uid);

        List<CartItem> cartItems = toCartItemList(mapList);

        return cartItems;
    }



}
