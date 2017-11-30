package cn.liuxi.wshopping.dao.impl;

import cn.liuxi.wshopping.dao.IProductDao;
import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.OrderItem;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements IProductDao {

    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());


    //根据cid获取所有产品
    @Override
    public List<Product> queryProductListByCidForPage(String cid , int index , int currentCount) throws SQLException {



        //查询sql语句
        String sql = "SELECT * FROM product WHERE cid=? LIMIT ?,?";

        //查询当前页数据
        List<Product> productList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid,index,currentCount);

        return productList;

    }

    //根据id查询总条数
    @Override
    public int getCount(String id) throws SQLException {

        //初始化sql语句
        String sql = "SELECT count(*) FROM product WHERE cid=?";

        Long query =(Long) queryRunner.query(sql,new ScalarHandler(),id);

        return query.intValue();
    }

    //添加订单
    @Override
    public void addOrder(Order order) throws SQLException {

        QueryRunner runner  = new QueryRunner();

        String sql = "INSERT INTO orders VALUES (?,?,?,?,?,?,?,?)";

        Connection conn = DataSourceUtils.getConnection();

        runner.update(conn,sql,order.getOid(),order.getOrdertime(),
                order.getTotal(),order.getStatus(), order.getAddr(),
                order.getFullname(),order.getTelephone(),order.getUser().getUid());
    }

    //添加订单项
    @Override
    public void addOrderItems(Order order) throws SQLException {

        QueryRunner runner  = new QueryRunner();

        String sql = "INSERT INTO orderitem VALUES (?,?,?,?,?)";

        Connection conn = DataSourceUtils.getConnection();

        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem item : orderItems) {

            runner.update(conn,sql,item.getItemid(),
                    item.getCount(),item.getSubtotal(),
                    item.getProduct().getPid(),
                    item.getOrder().getOid());

        }

    }

    //更新收收货人信息
    @Override
    public void updateOrderAddr(Order order) throws SQLException {

        String sql = "UPDATE orders SET address=?,fullname=?,telephone=? WHERE oid=?";

        queryRunner.update(sql,order.getAddr(),order.getFullname(),order.getTelephone(),order.getOid());

    }

    //根据pid查询产品详情
    @Override
    public Product queryProductByPid(String pid) throws SQLException {


        //查询的sql语句
        String sql = "SELECT * FROM product WHERE pid=?";

        Product product = queryRunner.query(sql, new BeanHandler<Product>(Product.class),pid);

        return product;
    }


    //查询所有热门商品
    @Override
    public List<Product> queryHotProductAll() throws SQLException {

        String sql = "SELECT * FROM product WHERE is_hot = ? LIMIT ?,?";

        List<Product> productList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class),1,0,9);

        return productList;

    }


    //查询所有最新产品
    @Override
    public List<Product> queryNewProductAll() throws SQLException {

        String sql = "SELECT * FROM product ORDER BY pdate DESC LIMIT ?,?";

        List<Product> newProductList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class),0,9);

        return newProductList;

    }

    //测试方法
    @Test
    public void test(){
        try {
            Product product = queryProductByPid("1");

            System.out.println("product"+product.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
