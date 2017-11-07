package cn.liuxi.web;

import cn.liuxi.dao.IndexService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "IndexController")
public class IndexController extends HttpServlet {

    private IndexService indexService = new IndexService();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO 1获取主页数据
        try {
            //TODO 1.1获取所有分类
            List<Map<String, Object>> categoryList = indexService.queryCategroy();

            for(Map categoryName : categoryList){
                System.out.println(categoryName.get("cname"));
            }

            //TODO 1.2向前端传数据
            request.setAttribute("categoryMapList",categoryList);

            //TODO 1.3请求转发到商城主页
            request.getRequestDispatcher("/jsp/index.jsp").forward(request,response);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);
    }


}
