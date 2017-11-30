package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.Result;
import cn.liuxi.wshopping.entity.User;
import cn.liuxi.wshopping.service.IUserService;
import cn.liuxi.wshopping.service.impl.UserServiceImpl;
import cn.liuxi.wshopping.utils.MD5Utils;
import cn.liuxi.wshopping.utils.MailUtils;
import cn.liuxi.wshopping.utils.MyBeanUtils;
import cn.liuxi.wshopping.utils.UUIDUtils;
import cn.liuxi.wshopping.web.base.BaseServlet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class UserController extends BaseServlet{

   IUserService userService =  new UserServiceImpl();

   //路由到登录页面
   public String loginUI(HttpServletRequest request , HttpServletResponse response)throws Exception {

       return "/jsp/login.jsp";
   }

   //路由到注册页面
   public String registerUI(HttpServletRequest request , HttpServletResponse response)throws Exception {

       return "/jsp/register.jsp";
   }


   //ajax检查用户名是否存在
    public String checkUsername(HttpServletRequest request , HttpServletResponse response)throws Exception  {

       //获取用户名
        String username = request.getParameter("username");

        User user = null;
        //调用业务层查询
        try {
             user = userService.queryUserByUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //判断用户是否存在
        if (user == null) {

            //用户名没有使用可以注册
            response.getWriter().println(1);
        } else {

            //用户名已存在，不可以注册
            response.getWriter().println(2);
        }


        return  null;

    }


    public String login(HttpServletRequest request , HttpServletResponse response)throws Exception {

        HttpSession session = request.getSession();

        //获取用户名
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = null;
        //调用业务层查询
        try {

            user = userService.queryUserByUsername(username);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (user == null) {
            //用户名不存在
            request.setAttribute("msg","用户名不存在");

            return "/jsp/login.jsp";

        } else {

            if (user.getPassword().equals(MD5Utils.md5(password))) {

                //验证通过登录成功

                //判断用户是否勾选自动登录
                String autoLogin = request.getParameter("autoLogin");

                if (autoLogin != null){
                    Cookie cookie_username = new Cookie("cookie_username",user.getUsername());
                    Cookie cookie_password = new Cookie("cookie_password",user.getPassword());
                    //设置cookie持久化时间为10天
                    cookie_username.setMaxAge(60*60*24*10);
                    cookie_password.setMaxAge(60*60*24*10);

                    //设置cookie携带路径
                    cookie_username.setPath(request.getContextPath());
                    cookie_password.setPath(request.getContextPath());

                    response.addCookie(cookie_username);
                    response.addCookie(cookie_password);


                }

                //将用户信息放入session域对象
                session.setAttribute("user",user);

                //重定向到主页
                response.sendRedirect(request.getContextPath());

            } else {

                //密码错误
                request.setAttribute("msg","密码错误");

                return "/jsp/login.jsp";

            }


        }



       return null;
    }


    public String logout(HttpServletRequest request , HttpServletResponse response)throws Exception {

       HttpSession session = request.getSession();

       //从session中移除user
        session.removeAttribute("user");

        //删除客户端cookie
        Cookie cookie_username = new Cookie("cookie_username","");
        cookie_username.setMaxAge(0);

        Cookie cookie_password = new Cookie("cookie_password","");
        cookie_password.setMaxAge(0);

        //将结果写入客户端
        response.addCookie(cookie_username);
        response.addCookie(cookie_password);

        response.sendRedirect(request.getContextPath()+"/userController?method=loginUI");

       return null;
    }

    public String register(HttpServletRequest request , HttpServletResponse response)throws Exception {


       //获取数据并且封装
        User user = MyBeanUtils.populate(User.class, request.getParameterMap());

        //初始化用户Id
        user.setUid(UUIDUtils.getUUID());
        //初始化激活码
        String activeCode = UUIDUtils.getUUID64();
        user.setCode(activeCode);
        //密码使用MD5加密
        user.setPassword( MD5Utils.md5( user.getPassword() ) );
        user.setState(0);




        //将user传递给service处理业务逻辑
        boolean isRegisterSuccess = userService.register(user);

        if (isRegisterSuccess) {


              //发送激活邮件
            String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
                    + "<a href='http://localhost:8080/eShop/userController?method=active&activeCode="+activeCode+"'>"
                    + "http://localhost:8080/eShop/userController?method=active&activeCode="+activeCode+"</a>";
            try {
                MailUtils.sendMail(user.getEmail(), emailMsg);
            } catch (MessagingException e) {
                e.printStackTrace();
            }


            //跳转到注册成功页面
            response.sendRedirect(request.getContextPath()+"/jsp/registerSuccess.jsp");

        }else{

            //跳转到失败的提示页面
            response.sendRedirect(request.getContextPath()+"/jsp/registerFail.jsp");
        }


        return null;
    }



    public String active(HttpServletRequest request , HttpServletResponse response) throws Exception {

        //获得激活码
        String activeCode = request.getParameter("activeCode");

        userService.active(activeCode);

        //跳转到登录页面
        return "/jsp/login.jsp";

    }

}
