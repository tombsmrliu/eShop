package cn.liuxi.wshopping.dao;

import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IProductDao {

    //根据cid查询产品列表
    List<Product> queryProductListByCidForPage(String cid  , int index , int currentCount) throws SQLException;


    //根据产品id查询产品信息

    Product queryProductByPid(String pid) throws SQLException;


    //查询所有热门产品
    List<Product> queryHotProductAll() throws SQLException;

    //查询所有最新产品
    List<Product> queryNewProductAll() throws SQLException;


    //获取总条数
    int getCount(String cid) throws SQLException;

    //添加订单
    void addOrder(Order order) throws SQLException;

    //添加订单项
    void addOrderItems(Order order) throws SQLException;

    void updateOrderAddr(Order order) throws SQLException;

    List<Order> queryAllOrdersByUid(String uid) throws SQLException;

    List<Map<String, Object>> queryAllOrderItemByOid(String oid) throws SQLException;
}
