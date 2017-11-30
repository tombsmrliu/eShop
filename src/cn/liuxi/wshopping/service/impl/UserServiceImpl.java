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

    @Override
    public boolean register(User user){
        int row = 0;

        try {

            row = userDao.save(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row > 0 ? true:false;
    }


    //用户激活逻辑
    @Override
    public void active(String activeCode){

        try {
            userDao.active(activeCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}
