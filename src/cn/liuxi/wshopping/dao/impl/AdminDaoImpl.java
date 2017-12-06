package cn.liuxi.wshopping.dao.impl;

import cn.liuxi.wshopping.dao.IAdminDao;
import cn.liuxi.wshopping.entity.Category;
import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminDaoImpl implements IAdminDao {

    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

    //保存商品
    @Override
    public void saveProduct(Product product) throws SQLException {



        String sql = "INSERT INTO product VALUES (?,?,?,?,?,?,?,?,?,?)";

        queryRunner.update(sql,product.getPid(),
                product.getPname(),product.getMarket_price(),
                product.getShop_price(),product.getPimage(),
                product.getPdate(),product.getIs_hot(),
                product.getPdesc(),product.getPflag(),
                product.getCategory().getCid());

    }


    //查询所有分类
    @Override
    public List<Category> queryAllCategory() throws SQLException {

        String sql ="SELECT * FROM category";

        List<Category> categoryList = queryRunner.query(sql, new BeanListHandler<Category>(Category.class));

        return categoryList;
    }

    //查询所有订单
    @Override
    public List<Order> queryAllOrders() throws SQLException {

        String sql = "SELECT * FROM orders";

        List<Order> orderList = queryRunner.query(sql, new BeanListHandler<Order>(Order.class));

        return orderList;

    }

    //根据id订单查询订单项
    @Override
    public List<Map<String, Object>> queryOrderInfoById(String oid) throws SQLException {

        String sql = "SELECT p.pimage,p.pname,p.shop_price,i.count,i.subtotal " +
                     " FROM product p,orderitem i "+
                     " WHERE i.pid = p.pid  and i.oid = ?";

        List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(), oid);

        return mapList;
    }

    //查询所有商品
    @Override
    public List<Product> queryAllProducts() throws SQLException {

        String sql = "SELECT * FROM product";

        List<Product> productList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));

        return productList;

    }

    //根据pid删除商品
    @Override
    public void delProduct(String pid) throws SQLException {

        String sql = "DELETE FROM product WHERE pid = ?";

        queryRunner.update(sql,pid);

    }

    //根据pid查询商品信息
    @Override
    public Product queryProductByPid(String pid) throws SQLException {

        String sql = "SELECT * FROM product WHERE pid = ?";

        Product product = queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);

        return product;
    }

    //保存分类
    @Override
    public void saveCategory(Category category) throws SQLException {

        String sql = "INSERT INTO category VALUES (?,?)";

        queryRunner.update(sql,category.getCid(),category.getCname());

    }

    //删除分类
    @Override
    public void delCategory(String cid) throws SQLException {

        String sql = "DELETE FROM category WHERE cid = ?";

        queryRunner.update(sql,cid);

    }

    //根据cid查询分类
    @Override
    public Category queryCategoryByCid(String cid) throws SQLException {

        String sql = "SELECT * FROM category WHERE cid = ?";

        Category category = queryRunner.query(sql, new BeanHandler<Category>(Category.class), cid);

        return category;

    }

    //更新分类
    @Override
    public void updateCategory(Category category) throws SQLException {

        String sql = "UPDATE category SET cname = ? WHERE cid = ?";

        queryRunner.update(sql,category.getCname(),category.getCid());

    }


}
