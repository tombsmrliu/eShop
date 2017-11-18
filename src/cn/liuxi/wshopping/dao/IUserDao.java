package cn.liuxi.wshopping.dao;

import cn.liuxi.wshopping.entity.User;

import java.sql.SQLException;

public interface IUserDao {

    //验证管理员登录
    abstract public User queryUserByUsername(String username) throws SQLException;

}
