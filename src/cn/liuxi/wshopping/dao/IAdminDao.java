package cn.liuxi.wshopping.dao;

import cn.liuxi.wshopping.entity.Category;
import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IAdminDao {

    //保存商品
    void saveProduct(Product product) throws SQLException;

    List<Category> queryAllCategory() throws SQLException;

    List<Order> queryAllOrders() throws SQLException;


    //根据id订单查询订单项
    List<Map<String, Object>> queryOrderInfoById(String oid) throws SQLException;

    List<Product> queryAllProducts() throws SQLException;

    void delProduct(String pid) throws SQLException;


    Product queryProductByPid(String pid) throws SQLException;

    void saveCategory(Category category) throws SQLException;

    void delCategory(String cid) throws SQLException;

    Category queryCategoryByCid(String cid) throws SQLException;

    void updateCategory(Category category) throws SQLException;

}
