package cn.liuxi.service;

import cn.liuxi.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductService {

    //根据cid获取所有产品
    abstract public List<Product> queryProductByCId(String cid) throws SQLException;


    //根据pid获取产品详情
    abstract public Product queryProductByPid(String pid) throws SQLException;
}
