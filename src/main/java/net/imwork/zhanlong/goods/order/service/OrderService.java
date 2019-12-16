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
     * 查找订单状态
     * @param oid
     * @return
     */
    public int findStatus(String oid)
    {
        try
        {
            return orderDao.findStatus(oid);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改订单状态
     * @param oid
     * @param status
     */
    public void updateStatus(String oid,int status)
    {
        try
        {
            orderDao.updateStatus(oid,status);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载订单
     * @param oid
     * @return
     */
    public Order load(String oid)
    {
        try
        {
            JdbcUtils.beginTransaction();
            Order order = orderDao.load(oid);
            JdbcUtils.commitTransaction();
            return order;
        } catch (SQLException e)
        {
            try
            {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException ex){}
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成订单
     * @param order
     */
    public void createOrder(Order order)
    {
        try
        {
            JdbcUtils.beginTransaction();
            orderDao.add(order);
            JdbcUtils.commitTransaction();
        } catch (SQLException e)
        {
            try
            {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException ex) {}
            throw new RuntimeException(e);
        }
    }

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
