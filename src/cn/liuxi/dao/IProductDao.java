package cn.liuxi.dao;

import cn.liuxi.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDao {

    //根据cid查询产品列表
    abstract public List<Product> queryProductListByCid(String cid) throws SQLException;


    //根据产品id查询产品信息

    abstract public Product queryProductByPid(String pid) throws SQLException;


    //查询所有热门产品
    abstract public List<Product> queryHotProductAll() throws SQLException;

    //查询所有最新产品
    abstract public List<Product> queryNewProductAll() throws SQLException;

}
