package cn.liuxi.wshopping.service.impl;


import cn.liuxi.wshopping.dao.IProductDao;
import cn.liuxi.wshopping.dao.impl.ProductDaoImpl;
import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.PageBean;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.service.IProductService;
import cn.liuxi.wshopping.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements IProductService {

    private IProductDao productDao = new ProductDaoImpl();

    //根据分类id查询所有产品
    @Override
    public PageBean queryProductByCId(String cid ,int currentPage ,int currentCount){

        //TODO 1创建pagebean对象
        PageBean<Product> pageBean = new PageBean();

        currentPage = currentPage > 0 ? currentPage : 1;
        currentCount = currentCount > 0 ? currentCount : 12;

        //TODO 2.1封装当前页
        pageBean.setCurrentPage(currentPage);
        //TODO 2.2封装每页显示条数
        pageBean.setCurrentCount(currentCount);
        //TODO 2.3封装总条数
        int totalCount = 0;

        try {
             totalCount = productDao.getCount(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pageBean.setTotalCount(totalCount);

        //TODO 2.4封装总页数
        int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
        pageBean.setTotalPage(totalPage);

        //TODO 2.5当前页显示的数据
        int index = (currentPage -1 ) * currentCount;
        List<Product> productList = null;

        try {

            productList= productDao.queryProductListByCidForPage(cid, index, currentCount);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        pageBean.setList(productList);


        return pageBean;
    }

    @Override
    public Product queryProductByPid(String pid) throws SQLException {

        Product product = productDao.queryProductByPid(pid);

        return product;
    }

    @Override
    public void submitOrder(Order order) {

        try {

            //1开启事务
            DataSourceUtils.startTransaction();

            //2调用dao存储order表数据的方法
            productDao.addOrder(order);

            //3调用dao存储orderitem表数据的方法
            productDao.addOrderItems(order);

        } catch (SQLException e) {
            try {
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                DataSourceUtils.commitAndRelease();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateOrderAddr(Order order) {

        try {

            productDao.updateOrderAddr(order);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
