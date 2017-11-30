package cn.liuxi.wshopping.service;

import cn.liuxi.wshopping.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IIndexService {

    //获取热门产品
    abstract public List<Product> queryHotProductAll() throws SQLException;

    //获取最新产品
    abstract public List<Product> queryNewProductAll() throws SQLException;

}
