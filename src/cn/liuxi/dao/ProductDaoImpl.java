package cn.liuxi.dao;

import cn.liuxi.entity.Product;
import cn.liuxi.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements IProductDao{

    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());


    //根据cid获取所有产品
    @Override
    public List<Product> queryProductListByCid(String cid) throws SQLException {


        //查询sql语句
        String sql = "SELECT * FROM product WHERE cid=?";

        //
        List<Product> productList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid);

        return productList;

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

        String sql = "SELECT * FROM product WHERE is_hot = 1";

        List<Product> productList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));

        return productList;

    }


    //查询所有最新产品
    @Override
    public List<Product> queryNewProductAll() throws SQLException {

        String sql = "SELECT * FROM product ORDER BY pdate DESC LIMIT 0,10;";

        List<Product> newProductList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));

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
