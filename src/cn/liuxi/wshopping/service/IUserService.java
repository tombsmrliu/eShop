package cn.liuxi.wshopping.service;

import cn.liuxi.wshopping.entity.User;

import java.sql.SQLException;

public interface IUserService {


    //根据用户名查找用户
    abstract public User queryUserByUsername(String username) throws SQLException;

    abstract public boolean register(User user) throws SQLException;

    //用户激活
    abstract public void active(String activeCode);

}
