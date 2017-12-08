package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.*;
import cn.liuxi.wshopping.service.ICategoryService;
import cn.liuxi.wshopping.service.IIndexService;
import cn.liuxi.wshopping.service.IProductService;
import cn.liuxi.wshopping.service.impl.CategoryServiceImpl;
import cn.liuxi.wshopping.service.impl.IndexServiceImpl;
import cn.liuxi.wshopping.service.impl.ProductServiceImpl;
import cn.liuxi.wshopping.utils.JedisPoolUtils;
import cn.liuxi.wshopping.utils.PaymentUtil;
import cn.liuxi.wshopping.utils.UUIDUtils;
import cn.liuxi.wshopping.web.base.BaseServlet;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;


public class ProductController extends BaseServlet {

    private ICategoryService categoryService = new CategoryServiceImpl();

    private IIndexService indexService = new IndexServiceImpl();

    private IProductService productService = new ProductServiceImpl();


    public String orderUI(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/jsp/order_info.jsp";
    }


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
            jedis.set("categoryMapListJson", categoryMapListJson);
        }
        //TODO 3响应给前端
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(categoryMapListJson);

        return null;
    }


    //主页的逻辑
    public String index(HttpServletRequest request, HttpServletResponse response) {


        //TODO 1获取主页数据
        try {

            //TODO 1.1①获取所有热门商品
            List<Product> hotProductAll = indexService.queryHotProductAll();

            //TODO 1.1②获取所有最新商品
            List<Product> newProductAll = indexService.queryNewProductAll();

            //TODO 1.2向前端传数据
            request.setAttribute("hotProductList", hotProductAll);
            request.setAttribute("newProductList", newProductAll);


        } catch (Exception e) {
            e.printStackTrace();
        }


        //TODO 1.3请求转发到商城主页
        return "jsp/index.jsp";

    }


    //站内搜索商品
    public String productDetailForWord(HttpServletRequest request, HttpServletResponse response) throws Exception {


        //获取pname
        String pname = request.getParameter("pname");

        //获取产品详情
        Product product = productService.queryProductByPname(pname);

        if (product == null) {

            request.setAttribute("notPro","没有找到您要的商品");

            return "/jsp/error.jsp";
        }

        request.setAttribute("product",product);

        return "/jsp/product_info.jsp";

    }

    //显示商品详细功能
    public String productDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //获取pid
        String pid = request.getParameter("pid");
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");

        Product product = null;

        try {
            //获取产品详情
            product = productService.queryProductByPid(pid);


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
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i));
                    sb.append("-");
                }

                //去掉sb尾部的-
                pids = sb.substring(0, sb.length() - 1);


            }
        }


        Cookie cookie_pids = new Cookie("pids", pids);
        response.addCookie(cookie_pids);


        //在请求域对象中传递数据
        request.setAttribute("product", product);
        request.setAttribute("cid", cid);
        request.setAttribute("currentPage", currentPage);

        return "jsp/product_info.jsp";


    }


    //显示商品列表功能
    public String productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {


        String cid = request.getParameter("cid");
        String currentPages = request.getParameter("currentPage");

        currentPages = currentPages != null ? currentPages : "1";

        int currentPage = Integer.parseInt(currentPages);
        int currentCount = 12;

        PageBean pageBean = productService.queryProductByCId(cid, currentPage, currentCount);


        request.setAttribute("pageBean", pageBean);
        request.setAttribute("cid", cid);

        return "jsp/product_list.jsp";

    }


    //将商品添加到购物车
    public String addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();

        //获取pid
        String pid = request.getParameter("pid");

        //System.out.println("cart");

        //获取购买数量
        int buyNum = Integer.parseInt(request.getParameter("buyNum"));

        Product product = null;

        try {

            product = productService.queryProductByPid(pid);

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
            cart.getCartItems().put(product.getPid(), item);
            cart.getCartItems().get(product.getPid()).setSubtotal();
            newsubtotal = buyNum * product.getShop_price();
        }

        //合计
        double total = cart.getTotal() + newsubtotal;
        cart.setTotal(total);

        //覆盖session中cart
        session.setAttribute("cart", cart);


        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");

        return null;
    }


    //清空购物车
    public String clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        //从session中移除
        session.removeAttribute("cart");

        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");

        return null;
    }


    //从购物车中移除商品
    public String deleteProductFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();

        //获取pid
        String pid = request.getParameter("pid");

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {

            Map<String, CartItem> cartItems = cart.getCartItems();

            //修改合计
            cart.setTotal(cart.getTotal() - cartItems.get(pid).getSubtotal());

            cartItems.remove(pid);
            cart.setCartItems(cartItems);
        }

        session.setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");

        return null;
    }


    //提交订单
    public String submitOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        //判断用户是否登录，如果未登录则跳转登录页面
        User user = (User) session.getAttribute("user");
        if (user == null) {

            //没有登录
            response.sendRedirect(request.getContextPath() + "/userController?method=loginUI");

            return null;

        }

        //封装一个Order对象传递给service层
        Order order = new Order();

        //1订单id
        String oid = UUIDUtils.getUUID();
        order.setOid(oid);

        //2下单时间
        order.setOrdertime(new Date());

        //获得购物车
        Cart cart = (Cart) session.getAttribute("cart");

        //3该订单的总金额
        double total = cart.getTotal();
        order.setTotal(total);

        //4订单状态
        order.setStatus(0);

        //5收获地址
        order.setAddr(null);

        //6收货人
        order.setFullname(null);

        //7收货人电话
        order.setTelephone(null);

        //8订单属于哪个用户
        order.setUser(user);

        //9封装订单中的订单项
        Map<String, CartItem> cartItems = cart.getCartItems();
        for (Map.Entry<String, CartItem> entry : cartItems.entrySet()) {

            //获取当前购物车项
            CartItem cartItem = entry.getValue();
            //创建新的订单项
            OrderItem orderItem = new OrderItem();

            //1订单项的id
            orderItem.setItemid(UUIDUtils.getUUID());
            //2订单项内商品的数量
            orderItem.setCount(cartItem.getBuyNum());
            //3订单项的小计
            orderItem.setSubtotal(cartItem.getSubtotal());
            //4订单内的商品
            orderItem.setProduct(cartItem.getProduct());
            //5该订单项属于哪个订单
            orderItem.setOrder(order);

            //将该订单项添加到订单项集合中
            order.getOrderItems().add(orderItem);
        }

        //order对象封装完毕 传递到service层
        productService.submitOrder(order);

        //将订单信息存储到session域对象中
        session.setAttribute("order", order);

        //页面跳转至订单页
        response.sendRedirect(request.getContextPath() + "/productController?method=orderUI");

        return null;

    }


    //确认订单 更新收货人信息  在线支付
    public String confirmOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //1更新收货人信息
        Map<String, String[]> parameterMap = request.getParameterMap();
        Order order = new Order();

        try {
            BeanUtils.populate(order, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        productService.updateOrderAddr(order);

        //2在线支付
        //只接入一个接口，这个接口已经集成所有的银行接口了  ，这个接口是第三方支付平台提供的
        //接入的是易宝支付
        // 获得 支付必须基本数据
        String orderid = request.getParameter("oid");
        //String money = order.getTotal()+"";//支付金额
        String money = "0.01";//支付金额
        // 银行
        String pd_FrpId = request.getParameter("pd_FrpId");

        // 发给支付公司需要哪些数据
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = orderid;
        String p3_Amt = money;
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
        // 第三方支付可以访问网址
        String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        // 加密hmac 需要密钥
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                "keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId=" + pd_FrpId +
                "&p0_Cmd=" + p0_Cmd +
                "&p1_MerId=" + p1_MerId +
                "&p2_Order=" + p2_Order +
                "&p3_Amt=" + p3_Amt +
                "&p4_Cur=" + p4_Cur +
                "&p5_Pid=" + p5_Pid +
                "&p6_Pcat=" + p6_Pcat +
                "&p7_Pdesc=" + p7_Pdesc +
                "&p8_Url=" + p8_Url +
                "&p9_SAF=" + p9_SAF +
                "&pa_MP=" + pa_MP +
                "&pr_NeedResponse=" + pr_NeedResponse +
                "&hmac=" + hmac;

        //重定向到第三方支付平台
        response.sendRedirect(url);


        return null;

    }


    //我的订单
    public String myOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        //判断用户是否登录，如果未登录则跳转登录页面
        User user = (User) session.getAttribute("user");
        if (user == null) {

            //没有登录
            response.sendRedirect(request.getContextPath() + "/userController?method=loginUI");

            return null;

        }

        //查询该用户的所有订单信息(单表查询orders表）
        //集合中order对象的数据不完整缺少订单项集合
        List<Order> orderList = productService.queryAllOrders(user.getUid());

        //循环所有订单信息 为每个订单填充订单项集合信息
        if (orderList != null) {

            for (Order order : orderList) {

                //获得没一个订单的oid
                String oid = order.getOid();
                //查询该订单的所有订单项
                List<Map<String, Object>> mapList = productService.queryAllOrderItemByOid(oid);
                //System.out.println(mapList);
                //将mapList转换成List<OrderItem> orderItems
                for (Map<String, Object> map : mapList) {

                    try {
                        //从map中取出count subtotal 封装到orderitem中
                        OrderItem item = new OrderItem();

                        BeanUtils.populate(item, map);
                        //从map中取出pimage，pname，shop_price封装到product中
                        Product product = new Product();

                        BeanUtils.populate(product, map);
                        //将product封装到orderitem
                        item.setProduct(product);
                        //System.out.println(item);
                        //将orderitem封装到order中的orderItemList中
                        order.getOrderItems().add(item);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

        //System.out.println(orderList);
        //将封装好的orderList放入request域对象
        request.setAttribute("orderList", orderList);

        return "/jsp/order_list.jsp";

    }


    //站内搜索商品
    public String searchWord(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //获得关键字
        String word = request.getParameter("word");

        //查询该关键字的所有商品
        List<Object> productList = productService.findProductByWord(word);

        //["xiaomi","huawei",""...]

        //使用json的转换工具将对象或集合转成json格式的字符串
		/*JSONArray fromObject = JSONArray.fromObject(productList);
		String string = fromObject.toString();
		System.out.println(string);*/

        Gson gson = new Gson();
        String json = gson.toJson(productList);
        System.out.println(json);

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(json);

        return null;

    }


}
