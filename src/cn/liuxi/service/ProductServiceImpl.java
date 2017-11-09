package cn.liuxi.service;


import cn.liuxi.dao.ProductDaoImpl;
import cn.liuxi.entity.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements IProductService{

    private ProductDaoImpl productDaoImpl = new ProductDaoImpl();

    //根据分类id查询所有产品
    @Override
    public List<Product> queryProductByCId(String cid) throws SQLException {

        List<Product> productList = productDaoImpl.queryProductListByCid(cid);

        return productList;
    }

    @Override
    public Product queryProductByPid(String pid) throws SQLException {

        Product product = productDaoImpl.queryProductByPid(pid);

        return product;
    }

}
