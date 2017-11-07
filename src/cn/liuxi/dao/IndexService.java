package cn.liuxi.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class IndexService {

    private CategoryDao categoryDao = new CategoryDao();

    public List<Map<String,Object>> queryCategroy() throws SQLException {

        List<Map<String, Object>> categoryList = categoryDao.queryCategory();

        return categoryList;
    }

}
