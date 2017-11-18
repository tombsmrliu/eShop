package cn.liuxi.wshopping.service.impl;

import cn.liuxi.wshopping.dao.impl.UserDaoImpl;
import cn.liuxi.wshopping.entity.User;
import cn.liuxi.wshopping.service.IUserService;

import java.sql.SQLException;

public class UserServiceImpl implements IUserService {

    private UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public User queryUserByUsername(String username) throws SQLException {


        User user = userDao.queryUserByUsername(username);

        return user;

    }


}
