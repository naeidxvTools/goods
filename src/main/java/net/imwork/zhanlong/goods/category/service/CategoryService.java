package net.imwork.zhanlong.goods.category.service;

import net.imwork.zhanlong.goods.category.dao.CategoryDao;
import net.imwork.zhanlong.goods.category.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * 分类模块业务层
 */
public class CategoryService
{
    private CategoryDao categoryDao = new CategoryDao();

    /**
     * //查询所有分类
     * @return
     */
    public List<Category> findAll()
    {
        try
        {
            return categoryDao.findAll();
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

}
