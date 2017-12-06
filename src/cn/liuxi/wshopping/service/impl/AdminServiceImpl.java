package cn.liuxi.wshopping.service.impl;

import cn.liuxi.wshopping.dao.IAdminDao;
import cn.liuxi.wshopping.dao.impl.AdminDaoImpl;
import cn.liuxi.wshopping.entity.Category;
import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.service.IAdminService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminServiceImpl implements IAdminService {

   private IAdminDao adminDao = new AdminDaoImpl();

    //保存商品
    @Override
    public void savaProduct(Product product) {


        try {

            adminDao.saveProduct(product);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取所有分类
    @Override
    public List<Category> findAllCategory() {

        List<Category> categoryList = null;

        try {
             categoryList = adminDao.queryAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    //获取所有订单
    @Override
    public List<Order> findAllOrders() {

        List<Order> orderList = null;
        try {

            orderList = adminDao.queryAllOrders();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    //根据oid查询订单
    @Override
    public List<Map<String, Object>> findOrderInfoById(String oid) {

        List< Map<String,Object> > mapList = null;

        try {

            mapList = adminDao.queryOrderInfoById(oid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mapList;
    }

    //获取所有商品
    @Override
    public List<Product> findAllProducts() {

        List<Product> productList = null;

        try {

            productList = adminDao.queryAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;

    }

    //根据pid删除产品
    @Override
    public void delProduct(String pid) {

        try {

            adminDao.delProduct(pid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //查询编辑信息
    @Override
    public Product findProductByPid(String pid) {

        Product product = null;

        try {

            product = adminDao.queryProductByPid(pid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    //保存分类
    @Override
    public void savaCategory(Category category) {

        try {

            adminDao.saveCategory(category);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //删除分类
    @Override
    public void delCategory(String cid) {

        try {

            adminDao.delCategory(cid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //查找分类
    @Override
    public Category findCategroyByCid(String cid) {

        Category category = null;

        try {

            category = adminDao.queryCategoryByCid(cid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;

    }

    //更新分类
    @Override
    public void updateCategory(Category category) {

        try {

            adminDao.updateCategory(category);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
