package net.imwork.zhanlong.goods.admin.admin.dao;

import net.imwork.zhanlong.goods.admin.admin.domain.Admin;
import net.imwork.zhanlong.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class AdminDao
{
    QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 通过管理员登录名和登录密码查询
     * @param adminname
     * @param adminpwd
     * @return
     * @throws SQLException
     */
    public Admin find(String adminname, String adminpwd) throws SQLException
    {
        String sql = "select * from t_admin where adminname=? and adminpwd=?";
        return queryRunner.query(sql, new BeanHandler<>(Admin.class), adminname, adminpwd);
    }


}
