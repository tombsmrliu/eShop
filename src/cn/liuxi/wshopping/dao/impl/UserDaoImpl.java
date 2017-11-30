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

    @Override
    public int save(User user) throws SQLException {

        //保存用户sql语句
        String sql = "INSERT INTO user(uid, username, password, fullname, email, birthday, gender, state, code) VALUES (?,?,?,?,?,?,?,?,?)";

        int update = queryRunner.update(sql, user.getUid(),user.getUsername(),
                user.getPassword(),user.getFullname(),user.getEmail(),
                user.getBirthday(),user.getGender(), user.getState(), user.getCode()
               );


        System.out.println(update);


        return update;
    }

    @Override
    public void active(String activeCode) throws SQLException {
        String sql = "update user set state=? where code=?";
        queryRunner.update(sql, 1,activeCode);
    }
}
