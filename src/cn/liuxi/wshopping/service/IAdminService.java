package cn.liuxi.wshopping.service;

import cn.liuxi.wshopping.entity.Category;
import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.Product;

import java.util.List;
import java.util.Map;

public interface IAdminService {

    //保存商品
    void saveProduct(Product product);

    List<Category> findAllCategory();

    List<Order> findAllOrders();

    List<Map<String,Object>> findOrderInfoById(String oid);

    List<Product> findAllProducts();

    void delProduct(String pid);

    Product findProductByPid(String pid);

    //保存分类
    void saveCategory(Category category);


    void delCategory(String cid);

    Category findCategroyByCid(String cid);

    void updateCategory(Category category);

    void updateProduct(Product product);
}
