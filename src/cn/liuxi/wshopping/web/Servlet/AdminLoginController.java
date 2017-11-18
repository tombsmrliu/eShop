package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.User;
import cn.liuxi.wshopping.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class AdminLoginController extends HttpServlet {

   private UserServiceImpl userService = new UserServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取管理员用户名和密码

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = null;

        try {
             user = userService.queryUserByUsername(username);

             if (user==null){


                  //临时组装数据
                 Map<String,String> loginInfoMap  = new HashMap<String,String>();
                 loginInfoMap.put("username",username);
                 loginInfoMap.put("password",password);
                 loginInfoMap.put("error","用户名不存在");
                 request.setAttribute("logininfo",loginInfoMap);
             }else {

             }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
