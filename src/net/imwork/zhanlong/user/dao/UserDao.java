package net.imwork.zhanlong.user.dao;

import net.imwork.zhanlong.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;

/**
 * 用户模块的持久层
 */
public class UserDao
{
    QueryRunner queryRunner = new TxQueryRunner();


}
