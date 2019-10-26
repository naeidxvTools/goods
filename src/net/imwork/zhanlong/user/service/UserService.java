package net.imwork.zhanlong.user.service;

import net.imwork.zhanlong.commons.CommonUtils;
import net.imwork.zhanlong.mail.Mail;
import net.imwork.zhanlong.mail.MailUtils;
import net.imwork.zhanlong.user.dao.UserDao;
import net.imwork.zhanlong.user.domain.User;

import javax.mail.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * 用户模块的业务层
 */
public class UserService
{
    private UserDao userDao = new UserDao();

    /**
     * 校验用户名是否注册
     *
     * @param loginname
     * @return
     */
    public boolean ajaxValidateLoginname(String loginname)
    {
        try
        {
            return userDao.ajaxValidateLoginname(loginname);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验Email是否注册
     *
     * @param email
     * @return
     */
    public boolean ajaxValidateEmail(String email)
    {
        try
        {
            return userDao.ajaxValidateEmail(email);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册用户
     *
     * @param user
     */
    public void regist(User user)
    {
        //1.补齐User对象
        user.setUid(CommonUtils.uuid());
        user.setStatus(0);
        user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
        try
        {
            //2.向数据库插入
            userDao.add(user);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        //3.发邮件

        Properties prop = new Properties();
        try
        {
            prop.load(this.getClass().getResourceAsStream("/email_template.properties"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        //登录邮件服务器，得到session
        String host = prop.getProperty("host");//服务器主机名
        String name = prop.getProperty("username");//登录名
        String pass = prop.getProperty("password");//登录密码
        Session session = MailUtils.createSession(host, name, pass);

        //创建Mail对象
        String from = prop.getProperty("from");
        String to = user.getEmail();
        String subject = prop.getProperty("subject");
        String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
        System.out.println("content = " + content);
        Mail mail = new Mail(from, to, subject, content);

        //发送邮件
        try
        {
            MailUtils.send(session, mail);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

}
