package cn.liuxi.web;

import cn.liuxi.entity.Product;
import cn.liuxi.entity.Result;
import cn.liuxi.service.ProductServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ProductListController extends HttpServlet {

    private ProductServiceImpl productServiceImpl = new ProductServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cid = request.getParameter("cid");

        List<Product> productList = null;

        try {

            productList = productServiceImpl.queryProductByCId(cid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        response.setContentType("application/json;charset=UTF-8");
//
//        Result<List<Product>> productListResult = new Result<>();
//        productListResult.code=1;
//        productListResult.messgage ="ok";
//        productListResult.data = productList;
//
//        String resultJson = new Gson().toJson(productListResult,
//                new TypeToken<Result<List<Product>>>() {}.getType()
//        );
//
//        System.out.println(resultJson);
//
//        response.getWriter().write(resultJson);
          request.setAttribute("productList",productList);

          request.getRequestDispatcher("jsp/product_list.jsp").forward(request,response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }


}
