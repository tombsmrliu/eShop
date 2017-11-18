package cn.liuxi.wshopping.dao.impl;

import cn.liuxi.wshopping.dao.IUserDao;
import cn.liuxi.wshopping.entity.User;
import cn.liuxi.wshopping.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements IUserDao {

    QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

    //根据用户名获取用户
    @Override
    public User queryUserByUsername(String username) throws SQLException {

        //验证sql用户语句
        String sql ="SELECT * FROM user WHERE username = ?";

        User user = queryRunner.query(sql, new BeanHandler<User>(User.class), username);

        return user;
    }
}
