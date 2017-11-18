package cn.liuxi.wshopping.dao.impl;

import cn.liuxi.wshopping.dao.ICategoryDao;
import cn.liuxi.wshopping.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CategoryDaoImpl implements ICategoryDao {

    public List<Map<String,Object>> queryCategoryAll() throws SQLException {

            //1.创建QueryRunner对象
            QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
            //2.构造sql语句
            String sql = "SELECT * FROM category";

            //查询结果
            List<Map<String, Object>> categoryList = queryRunner.query(sql, new MapListHandler());

            return categoryList;

    }
}
