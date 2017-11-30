package cn.liuxi.wshopping.web.filter;

import cn.liuxi.wshopping.entity.User;
import cn.liuxi.wshopping.service.IUserService;
import cn.liuxi.wshopping.service.impl.UserServiceImpl;
import cn.liuxi.wshopping.utils.MD5Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //获取cookie的用户名密码进行登录操作

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        //定义字符串cookie_username
        String cookie_username = null;

        //定义字符串cookie_password
        String cookie_password = null;

        //获取cookie
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {

                if ("cookie_username".equals(cookie.getName())) {
                    cookie_username = cookie.getValue();
                }

                if ("cookie_password".equals(cookie.getName())) {
                    cookie_password = cookie.getValue();
                }

            }
        }

        //判断username和password是否为空
        if (cookie_username != null && cookie_password != null) {


            IUserService userService = new UserServiceImpl();

            User user = null;
            //调用业务层查询
            try {

                user = userService.queryUserByUsername(cookie_username);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (user != null) {
                if (user.getPassword().equals(MD5Utils.md5(cookie_password))) {
                    //将用户信息放入session域对象
                    session.setAttribute("user", user);
                }
            }
        }
        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
