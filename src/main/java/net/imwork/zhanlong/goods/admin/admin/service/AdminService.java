package net.imwork.zhanlong.goods.admin.admin.service;

import net.imwork.zhanlong.goods.admin.admin.dao.AdminDao;
import net.imwork.zhanlong.goods.admin.admin.domain.Admin;

import java.sql.SQLException;

public class AdminService
{
    private AdminDao adminDao = new AdminDao();

    /**
     * 登录功能
     * @param admin
     * @return
     */
    public Admin login(Admin admin)
    {
        try
        {
            return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

}