package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.Cart;
import cn.liuxi.wshopping.entity.CartItem;
import cn.liuxi.wshopping.entity.PageBean;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.service.ICategoryService;
import cn.liuxi.wshopping.service.impl.CategoryServiceImpl;
import cn.liuxi.wshopping.service.impl.IndexServiceImpl;
import cn.liuxi.wshopping.service.impl.ProductServiceImpl;
import cn.liuxi.wshopping.utils.JedisPoolUtils;
import cn.liuxi.wshopping.web.base.BaseServlet;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ProductController extends BaseServlet{

    private ICategoryService categoryService = new CategoryServiceImpl();

    private IndexServiceImpl indexServiceImpl = new IndexServiceImpl();

    private ProductServiceImpl productServiceImpl = new ProductServiceImpl();



    //商品分类显示功能
    public String categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        //TODO 3响应给前端
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(categoryMapListJson);

         return null;
    }



    //主页的逻辑
    public String index(HttpServletRequest request,HttpServletResponse response) {

        //TODO 1获取主页数据
        try {

            //TODO 1.1①获取所有热门商品
            List<Product> hotProductAll = indexServiceImpl.queryHotProductAll();

            //TODO 1.1②获取所有最新商品
            List<Product> newProductAll = indexServiceImpl.queryNewProductAll();

            //TODO 1.2向前端传数据
            request.setAttribute("hotProductList",hotProductAll);
            request.setAttribute("newProductList",newProductAll);



        } catch (Exception e) {
            e.printStackTrace();
        }


        //TODO 1.3请求转发到商城主页
        return "jsp/index.jsp";

    }



    //显示商品详细功能
    public String productDetail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {


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

       return "jsp/product_info.jsp";



    }



    //显示商品列表功能
    public String productList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {


        String cid = request.getParameter("cid");
        String currentPages = request.getParameter("currentPage");

        currentPages = currentPages != null ? currentPages:"1";

        int currentPage = Integer.parseInt(currentPages);
        int currentCount = 12;

        PageBean pageBean = productServiceImpl.queryProductByCId(cid,currentPage,currentCount);


        request.setAttribute("pageBean",pageBean);
        request.setAttribute("cid",cid);

        return "jsp/product_list.jsp";

    }


    //将商品添加到购物车
    public String addProductToCart(HttpServletRequest request , HttpServletResponse response)throws ServletException,IOException {


        HttpSession session = request.getSession();

        //获取pid
        String pid = request.getParameter("pid");

        //System.out.println("cart");

        //获取购买数量
        int buyNum = Integer.parseInt(request.getParameter("buyNum"));

        Product product = null;

        try {

            product = productServiceImpl.queryProductByPid(pid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        CartItem item = new CartItem();
        item.setBuyNum(buyNum);
        item.setProduct(product);

        //设置小计
        item.setSubtotal();

        //获取购物车，判断是否在session中存在购物车
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        Map<String, CartItem> cartItems = cart.getCartItems();

        double newsubtotal = 0.0;

        if (cartItems.containsKey(pid)) {

            //获取原商品数量
            CartItem cartItem = cartItems.get(pid);
            int oldBuyNum = cartItem.getBuyNum();
            oldBuyNum += buyNum;
            cartItem.setBuyNum(oldBuyNum);
            cart.setCartItems(cartItems);

            //修改小计
            double oldsubtotal = cartItem.getSubtotal();
            //新买商品的小计
            newsubtotal = buyNum * product.getShop_price();
            cartItem.setSubtotal();
        } else {

            //如果购物车中没有该商品
            cart.getCartItems().put(product.getPid(),item);
            cart.getCartItems().get(product.getPid()).setSubtotal();
            newsubtotal = buyNum * product.getShop_price();
        }

        //合计
        double total = cart.getTotal() +  newsubtotal;
        cart.setTotal(total);

        //覆盖session中cart
        session.setAttribute("cart",cart);


        response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");

        return null;
    }



    //清空购物车
    public String clearCart(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {

        HttpSession session = request.getSession();

        //从session中移除
        session.removeAttribute("cart");

        response.sendRedirect(request.getContextPath() +"/jsp/cart.jsp");

        return null;
    }



    //从购物车中移除商品
    public String deleteProductFromCart(HttpServletRequest request , HttpServletResponse response)throws ServletException,IOException {

        HttpSession session = request.getSession();

        //获取pid
        String pid = request.getParameter("pid");

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {

            Map<String, CartItem> cartItems = cart.getCartItems();

            //修改合计
            cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());

            cartItems.remove(pid);
            cart.setCartItems(cartItems);
        }

        session.setAttribute("cart",cart);

        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");

        return null;
    }
}
