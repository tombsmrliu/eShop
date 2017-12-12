package cn.liuxi.wshopping.service;

import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.PageBean;
import cn.liuxi.wshopping.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IProductService {

    //根据cid获取所有产品
    PageBean queryProductByCId(String cid ,int currentPage ,int currentCount) throws SQLException;


    //根据pid获取产品详情
    Product queryProductByPid(String pid) throws SQLException;

    void submitOrder(Order order);

    void updateOrderAddr(Order order);

    List<Order> queryAllOrders(String uid) throws SQLException;

    List<Map<String, Object>> queryAllOrderItemByOid(String oid);

    List<Object> findProductByWord(String word);


    Product queryProductByPname(String pname);


}
