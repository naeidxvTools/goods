package net.imwork.zhanlong.goods.book.dao;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.goods.book.domain.Book;
import net.imwork.zhanlong.goods.category.domain.Category;
import net.imwork.zhanlong.goods.pager.Expression;
import net.imwork.zhanlong.goods.pager.PageBean;
import net.imwork.zhanlong.goods.pager.PageConstants;
import net.imwork.zhanlong.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图书模块的持久层
 */
public class BookDao
{
    private QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 按bid查询
     * @param bid
     * @return
     */
    public Book findByBid(String bid) throws SQLException
    {
        String sql = "select * from t_book where bid=?";
        Map<String, Object> map = queryRunner.query(sql, new MapHandler(), bid);
        Book book = CommonUtils.mapToBean(map, Book.class);
        Category category = CommonUtils.mapToBean(map, Category.class);
        book.setCategory(category);
        return book;
    }

    /**
     * 按分类查询
     * @param cid
     * @param pc
     * @return
     */
    public PageBean<Book> findByCategory(String cid, int pc) throws SQLException
    {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new Expression("cid", "=", cid));

        return findByCriteria(expressionList,pc);
    }

    /**
     * 按书名模糊查询
     * @param bname
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByBname(String bname, int pc) throws SQLException
    {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new Expression("bname", "like", "%" + bname + "%"));

        return findByCriteria(expressionList,pc);
    }

    /**
     * 按作者模糊查询
     * @param author
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByAuthor(String author, int pc) throws SQLException
    {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new Expression("author", "like", "%" + author + "%"));

        return findByCriteria(expressionList,pc);
    }

    /**
     * 按出版社查询
     * @param press
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByPress(String press, int pc) throws SQLException
    {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new Expression("press", "like", "%" + press + "%"));

        return findByCriteria(expressionList,pc);
    }

    /**
     * 多条件组合查询
     * @param criteria
     * @param pc
     * @return
     * @throws SQLException
     */
    public PageBean<Book> findByCombination(Book criteria, int pc) throws SQLException
    {
        List<Expression> expressionList = new ArrayList<>();
        expressionList.add(new Expression("bname", "like", "%" + criteria.getBname() + "%"));
        expressionList.add(new Expression("author", "like", "%" + criteria.getAuthor() + "%"));
        expressionList.add(new Expression("press", "like", "%" + criteria.getPress() + "%"));

        return findByCriteria(expressionList,pc);
    }
    /**
     * 通用的查询方法
     *
     * @param expressionList
     * @param pc
     * @return
     */
    private PageBean<Book> findByCriteria(List<Expression> expressionList, int pc) throws SQLException
    {
        //1.得到ps
        int ps = PageConstants.BOOK_PAGE_SIZE;
        //2.得到tr
        //通过expressionList生成where子句
        StringBuilder whereSql = new StringBuilder(" where 1=1");
        List<Object> params = new ArrayList<>();
        for (Expression expression : expressionList)
        {
            whereSql.append(" and ").append(expression.getName()).append(" " + expression.getOperator());
            if (!expression.getOperator().equals("is null"))
            {
                whereSql.append(" ?");
                params.add(expression.getValue());
            }
        }
        //得到总记录数
        String sql = "select count(*) from t_book" + whereSql;
        Number number = queryRunner.query(sql, new ScalarHandler<>(), params.toArray());
        int tr = number.intValue();
        //3.得到beanList
        sql = "select * from t_book" + whereSql + " order by orderBy limit ?, ?";
        params.add((pc - 1) * ps);
        params.add(ps);
        List<Book> beanList = queryRunner.query(sql, new BeanListHandler<>(Book.class), params.toArray());

        //4.创建PageBean，返回
        PageBean<Book> pageBean = new PageBean<>();
        pageBean.setPc(pc);
        pageBean.setPs(ps);
        pageBean.setBeanList(beanList);
        pageBean.setTr(tr);
        return pageBean;
    }
}