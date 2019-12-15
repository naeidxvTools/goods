package net.imwork.zhanlong.goods.order.service;

import net.imwork.zhanlong.goods.order.dao.OrderDao;
import net.imwork.zhanlong.goods.order.domain.Order;
import net.imwork.zhanlong.goods.pager.PageBean;
import net.imwork.zhanlong.jdbc.JdbcUtils;

import java.sql.SQLException;

public class OrderService
{
    private OrderDao orderDao = new OrderDao();

    /**
     * 我的订单
     * @param uid
     * @param pc
     * @return
     */
    public PageBean<Order> myOrders(String uid, int pc)
    {
        try
        {
            JdbcUtils.beginTransaction();
            PageBean<Order> pb = orderDao.findByUser(uid, pc);
            JdbcUtils.commitTransaction();
            return pb;
        } catch (SQLException e)
        {
            try
            {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException ex) {}
            throw new RuntimeException(e);
        }
    }

}
