package cn.liuxi.wshopping.service;

import cn.liuxi.wshopping.entity.User;

import java.sql.SQLException;

public interface IUserService {


    //管理员登录业务逻辑
    abstract public User queryUserByUsername(String username) throws SQLException;

}
