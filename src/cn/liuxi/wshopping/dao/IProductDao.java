package cn.liuxi.wshopping.dao;

import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDao {

    //根据cid查询产品列表
    abstract public List<Product> queryProductListByCidForPage(String cid  , int index , int currentCount) throws SQLException;


    //根据产品id查询产品信息

    abstract public Product queryProductByPid(String pid) throws SQLException;


    //查询所有热门产品
    abstract public List<Product> queryHotProductAll() throws SQLException;

    //查询所有最新产品
    abstract public List<Product> queryNewProductAll() throws SQLException;


    //获取总条数
    abstract public int getCount(String cid) throws SQLException;

    //添加订单
    abstract void addOrder(Order order) throws SQLException;

    //添加订单项
    abstract void addOrderItems(Order order) throws SQLException;

    abstract void updateOrderAddr(Order order) throws SQLException;
}
