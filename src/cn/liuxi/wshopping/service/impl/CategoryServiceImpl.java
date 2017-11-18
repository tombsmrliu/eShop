package cn.liuxi.wshopping.service.impl;

import cn.liuxi.wshopping.dao.ICategoryDao;
import cn.liuxi.wshopping.dao.impl.CategoryDaoImpl;
import cn.liuxi.wshopping.service.ICategoryService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CategoryServiceImpl implements ICategoryService {

    private ICategoryDao categoryDao = new CategoryDaoImpl();

    //获取所有分类信息业务逻辑
    @Override
    public List<Map<String, Object>> queryCategoryAll() {

        List< Map<String,Object> > categoryMapList = null;

        try {

            categoryMapList = categoryDao.queryCategoryAll();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

        return categoryMapList;

    }


}
