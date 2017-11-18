package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.service.impl.CategoryServiceImpl;
import cn.liuxi.wshopping.service.ICategoryService;
import cn.liuxi.wshopping.utils.JedisPoolUtils;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class CategoryController extends HttpServlet {

    private ICategoryService categoryService = new CategoryServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO 1获得jredis对象链接redis数据库
        Jedis jedis = JedisPoolUtils.getJedis();

        String categoryMapListJson = jedis.get("categoryMapListJson");

        //TODO 2判断categoryMapListJson是否为空
        if (categoryMapListJson == null) {

            //获取分类数据
            List<Map<String, Object>> categoryList = categoryService.queryCategoryAll();

            //将获取的分类数据编码成json格式
            categoryMapListJson = new Gson().toJson(categoryList);

            //将结果json存入redis
            jedis.set("categoryMapListJson",categoryMapListJson );
        }

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(categoryMapListJson);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);

    }

}
