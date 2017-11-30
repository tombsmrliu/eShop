package cn.liuxi.wshopping.dao;

import cn.liuxi.wshopping.entity.User;

import java.sql.SQLException;

public interface IUserDao {

    //验证管理员登录
    abstract public User queryUserByUsername(String username) throws SQLException;

    //用户存储数据库操作
    abstract public int save(User user)throws SQLException;


    //激活数据库操作
    abstract void active(String activeCode)throws SQLException;

}
