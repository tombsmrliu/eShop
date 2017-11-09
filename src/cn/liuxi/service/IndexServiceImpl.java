package cn.liuxi.service;

import cn.liuxi.dao.CategoryDaoImpl;
import cn.liuxi.dao.ProductDaoImpl;
import cn.liuxi.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class IndexServiceImpl implements IIndexService{

    private CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();

    private ProductDaoImpl productDao = new ProductDaoImpl();

    //获取所有分类
    public List<Map<String,Object>> queryCategroyAll() throws SQLException {

        List<Map<String, Object>> categoryList = categoryDaoImpl.queryCategoryAll();

        return categoryList;
    }

    //获取所有热门商品
    @Override
    public List<Product> queryHotProductAll() throws SQLException {

        List<Product> hotProductAll = productDao.queryHotProductAll();

        return hotProductAll;

    }

    //获取最新产品
    @Override
    public List<Product> queryNewProductAll() throws SQLException {

        List<Product> newProductAll = productDao.queryNewProductAll();

        return newProductAll;

    }


}
