package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.PageBean;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
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
        String currentPages = request.getParameter("currentPage");

        currentPages = currentPages != null ? currentPages:"1";

        int currentPage = Integer.parseInt(currentPages);
        int currentCount = 12;

        PageBean pageBean = productServiceImpl.queryProductByCId(cid,currentPage,currentCount);



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
          request.setAttribute("pageBean",pageBean);
          request.setAttribute("cid",cid);

          request.getRequestDispatcher("jsp/product_list.jsp").forward(request,response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }


}
