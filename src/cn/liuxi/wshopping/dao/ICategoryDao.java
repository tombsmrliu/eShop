package cn.liuxi.wshopping.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ICategoryDao {

    abstract public List<Map<String,Object>> queryCategoryAll() throws SQLException;
}
