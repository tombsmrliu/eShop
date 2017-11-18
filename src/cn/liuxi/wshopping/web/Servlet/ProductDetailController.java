package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ProductDetailController extends HttpServlet {

    private ProductServiceImpl productServiceImpl = new ProductServiceImpl();


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取pid
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");

        Product product = null;

        try {
            //获取产品详情
             product = productServiceImpl.queryProductByPid(pid);


        } catch (SQLException e) {
            e.printStackTrace();
        }


        //获得客户端携带的cookie-name=pids的cookie
        String pids = pid;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {

            if ("pids".equals(cookie.getName())) {
                pids = cookie.getValue();
                //1-3-2 本次访问的pid是0 0-1-3-2
                //1-3-2 本次访问的pid是3 3-1-2
                //1-3-2 本次访问的pid是2 2-1-3

                //将pids拆解成一个字符数组
                String[] split = pids.split("-");

                List<String> stringList = Arrays.asList(split);
                //将字符数组转化成一个集合
                LinkedList<String> list = new LinkedList<String>(stringList);

                //判断当前集合中是否存在当前的pid
                if (list.contains(pid)) {

                    //当前集合包含该pid
                    list.remove(pid);
                }

                //将当前pid放在集合头部
                list.addFirst(pid);

                //将当前集合转化为string
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < list.size() ; i++) {
                    sb.append(list.get(i));
                    sb.append("-");
                }

                //去掉sb尾部的-
                pids = sb.substring(0,sb.length()-1);


            }
        }


        Cookie cookie_pids = new Cookie("pids",pids);
        response.addCookie(cookie_pids);


            //在请求域对象中传递数据
            request.setAttribute("product", product);
            request.setAttribute("cid",cid);
            request.setAttribute("currentPage",currentPage);

            request.getRequestDispatcher("jsp/product_info.jsp").forward(request,response);


    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }


}
