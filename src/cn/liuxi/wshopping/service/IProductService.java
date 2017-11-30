package cn.liuxi.wshopping.service;

import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.PageBean;
import cn.liuxi.wshopping.entity.Product;

import java.sql.SQLException;

public interface IProductService {

    //根据cid获取所有产品
    abstract public PageBean queryProductByCId(String cid ,int currentPage ,int currentCount) throws SQLException;


    //根据pid获取产品详情
    abstract public Product queryProductByPid(String pid) throws SQLException;

    abstract void submitOrder(Order order);

    abstract void updateOrderAddr(Order order);
}
