package net.imwork.zhanlong.goods.category.dao;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.goods.category.domain.Category;
import net.imwork.zhanlong.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分类模块持久层
 */
public class CategoryDao
{
    private QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 把一个map的数据映射到Category中
     * @param map
     * @return
     */
    private Category toCategory(Map<String, Object> map)
    {
        Category category = CommonUtils.mapToBean(map, Category.class);

        String pid = (String) map.get("pid");

        if (pid != null)
        {
            Category parent = new Category();
            parent.setCid(pid);
            category.setParent(parent);
        }

        return category;
    }

    /**
     * 把多个map映射成多个Category
     * @param mapList
     * @return
     */
    private List<Category> toCategoryList(List<Map<String,Object>> mapList)
    {
        List<Category> categoryList = new ArrayList<>();

        for (Map<String, Object> map : mapList)
        {
            Category category = toCategory(map);
            categoryList.add(category);
        }

        return categoryList;
    }

    /**
     * 返回所有分类
     * @return
     */
    public List<Category> findAll() throws SQLException
    {
        //1.查询出所有一级分类
        String sql = "select * from t_category where pid is null";
        List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler());

        List<Category> parents = toCategoryList(mapList);

        //2.循环遍历所有的一级分类，为每个一级分类加载他的二级分类
        for (Category parent : parents)
        {
            parent.setChildren(findByParent(parent.getCid()));
        }

        return parents;
    }

    public List<Category> findByParent(String pid) throws SQLException
    {
        String sql = "select * from t_category where pid=?";
        List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), pid);
        return toCategoryList(mapList);
    }


}
