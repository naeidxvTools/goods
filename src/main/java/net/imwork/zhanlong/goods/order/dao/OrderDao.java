package net.imwork.zhanlong.goods.order.dao;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.goods.book.domain.Book;
import net.imwork.zhanlong.goods.order.domain.Order;
import net.imwork.zhanlong.goods.order.domain.OrderItem;
import net.imwork.zhanlong.goods.pager.Expression;
import net.imwork.zhanlong.goods.pager.PageBean;
import net.imwork.zhanlong.goods.pager.PageConstants;
import net.imwork.zhanlong.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao
{
    private QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 按用户查询订单
     * @param uid
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Order> findByUser(String uid, int pc) throws SQLException
    {
        List<Expression> expressions = new ArrayList<>();
        expressions.add(new Expression("uid", "=", uid));
        return findByCriteria(expressions, pc);
    }

    /**
     * 通用的查询方法
     * @param expressions
     * @param pc
     * @return
     */
    private PageBean<Order> findByCriteria(List<Expression> expressions, int pc) throws SQLException
    {
        /*
        1.得到ps
        2.得到tr
        3.得到beanList
        4.创建PageBean，返回
         */

        /*
        1.得到ps
         */
        int ps = PageConstants.ORDER_PAGE_SIZE; //每页记录数
        /*
        2.通过expressions来生成where子句
         */
        StringBuilder whereSql = new StringBuilder(" where 1=1");
        List<Object> params = new ArrayList<>(); //sql中问号，它是对应问号的值
        for (Expression expression : expressions)
        {
            whereSql.append(" and " + expression.getName() + " " + expression.getOperator() + " ");

            if (!expression.getOperator().equals("is null"))
            {
                whereSql.append("?");
                params.add(expression.getValue());
            }
        }
        /*
        3.得到tr总记录数
         */
        String sql = "select count(*) from t_order" + whereSql;
        Number number = (Number)queryRunner.query(sql, new ScalarHandler(), params.toArray());
        int tr = number.intValue();

        /*
        4.得到beanList，即当前页记录
         */
        sql = "select * from t_order" + whereSql + " order by ordertime desc limit ?, ?";
        params.add((pc - 1) * ps);
        params.add(ps);
        List<Order> beanList = queryRunner.query(sql, new BeanListHandler<>(Order.class), params.toArray());
        //虽然已经获得所有的订单，但是每个订单中并没有订单条目
        //遍历每个订单，为其添加所有订单
        for (Order order : beanList)
        {
            loadOrderItem(order);
        }

        /*
        5.创建PageBean，设置参数
         */
        PageBean<Order> pb = new PageBean<>();
        pb.setBeanList(beanList);
        pb.setPc(pc);
        pb.setPs(ps);
        pb.setTr(tr);

        return pb;
    }

    /*
     * 为指定的order加载他的所有OrderItem
     */
    private void loadOrderItem(Order order) throws SQLException
    {
        //1.给出sql语句select * from t_orderitem where oid=?
        //2.执行之，得到List<OrderItem>
        //3.设置给Order对象
        String sql = "select * from t_orderitem where oid=?";
        List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), order.getOid());

        List<OrderItem> orderItemList = toOrderItemList(mapList);

        order.setOrderItemList(orderItemList);
    }

    /*
     * 把多个map转换成多个OrderItem
     */
    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList)
    {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (Map<String, Object> map : mapList)
        {
            OrderItem orderItem = toOrderItem(map);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    /*
     * 把一个map转换成一个OrderItem
     */
    private OrderItem toOrderItem(Map<String, Object> map)
    {
        OrderItem orderItem = CommonUtils.mapToBean(map, OrderItem.class);
        Book book = CommonUtils.mapToBean(map, Book.class);
        orderItem.setBook(book);
        return orderItem;
    }

}
