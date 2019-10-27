package net.imwork.zhanlong.user.dao;

import net.imwork.zhanlong.jdbc.TxQueryRunner;
import net.imwork.zhanlong.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * 用户模块的持久层
 */
public class UserDao
{
    QueryRunner queryRunner = new TxQueryRunner();

    /**
     * 通过激活码查找
     * @param activationCode
     * @return
     * @throws SQLException
     */
    public User findByActivationCode(String activationCode) throws SQLException
    {
        String sql = "select * from t_user where activationCode=?";
        User user = queryRunner.query(sql, new BeanHandler<>(User.class), activationCode);
        return user;
    }

    /**
     * 更新状态
     * @param uid
     * @param status
     * @throws SQLException
     */
    public void updateStatus(String uid, int status) throws SQLException
    {
        String sql = "update t_user set status=? where uid=?";
        queryRunner.update(sql, status,uid);
    }

    /**
     * 校验用户名是否注册
     * @param loginname
     * @return
     */
    public boolean ajaxValidateLoginname(String loginname) throws SQLException
    {
        String sql = "select count(*) from t_user where loginname=?";
        Number number = (Number) queryRunner.query(sql, new ScalarHandler(), loginname);
        return number.intValue() == 0;
    }

    /**
     * 校验Email是否注册
     * @param email
     * @return
     */
    public boolean ajaxValidateEmail(String email) throws SQLException
    {
        String sql = "select count(*) from t_user where email=?";
        Number number = (Number) queryRunner.query(sql, new ScalarHandler(), email);
        return number.intValue() == 0;
    }

    /**
     * 添加用户
     * @param user
     * @throws SQLException
     */
    public void add(User user) throws SQLException
    {
        String sql = "insert into t_user values (?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(), user.getEmail(),
                user.getStatus(), user.getActivationCode()};
        queryRunner.update(sql,params);
    }
}
