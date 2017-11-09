package cn.liuxi.web;

import cn.liuxi.entity.Product;
import cn.liuxi.service.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ProductDetailController")
public class ProductDetailController extends HttpServlet {

    private ProductServiceImpl productServiceImpl = new ProductServiceImpl();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取pid
        String pid = request.getParameter("pid");

        Product product = null;

        try {
            //获取产品详情
             product = productServiceImpl.queryProductByPid(pid);


        } catch (SQLException e) {
            e.printStackTrace();
        }


            //在请求域对象中传递数据
            request.setAttribute("product", product);

            request.getRequestDispatcher("jsp/product_info.jsp").forward(request,response);


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }


}
