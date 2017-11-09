package cn.liuxi.service;

import cn.liuxi.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IIndexService {

    //获取所有分类
    abstract public List<Map<String,Object>> queryCategroyAll() throws SQLException;

    //获取热门产品
    abstract public List<Product> queryHotProductAll() throws SQLException;

    //获取最新产品
    abstract public List<Product> queryNewProductAll() throws SQLException;

}
