package cn.liuxi.web;

import cn.liuxi.entity.Product;
import cn.liuxi.service.IndexServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class IndexController extends HttpServlet {

    private IndexServiceImpl indexServiceImpl = new IndexServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO 1获取主页数据
        try {

            //TODO 1.1①获取所有分类
            List<Map<String, Object>> categoryList = indexServiceImpl.queryCategroyAll();

            //TODO 1.1②获取所有热门商品
            List<Product> hotProductAll = indexServiceImpl.queryHotProductAll();

            //TODO 1.1③获取所有最新商品
            List<Product> newProductAll = indexServiceImpl.queryNewProductAll();

            //TODO 1.2向前端传数据
            request.getSession().setAttribute("categoryMapList",categoryList);
            request.setAttribute("hotProductList",hotProductAll);
            request.setAttribute("newProductList",newProductAll);

            //TODO 1.3请求转发到商城主页
            request.getRequestDispatcher("jsp/index.jsp").forward(request,response);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);
    }


}
