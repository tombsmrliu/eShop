package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.Category;
import cn.liuxi.wshopping.entity.Order;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.service.IAdminService;
import cn.liuxi.wshopping.service.impl.AdminServiceImpl;
import cn.liuxi.wshopping.utils.AdminUtils;
import cn.liuxi.wshopping.utils.MD5Utils;
import cn.liuxi.wshopping.utils.UUIDUtils;
import cn.liuxi.wshopping.web.base.BaseServlet;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AdminController extends BaseServlet {

    private IAdminService adminService = new AdminServiceImpl();

    //异步查询所有分类
    public String findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //提供一个List<Category> 转成json字符串
        List<Category> categoryList = adminService.findAllCategory();

        Gson gson = new Gson();
        String json = gson.toJson(categoryList);

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().write(json);

        return null;
    }


    //查询所有订单
    public String findAllOrders(HttpServletRequest request , HttpServletResponse response)throws Exception {

        //获得所有的订单信息 List<Order>
        List<Order> orderList = adminService.findAllOrders();

        request.setAttribute("orderList",orderList);

        return "/jsp/admin/order/list.jsp";

    }


    //查询所有商品
    public String findAllProduct(HttpServletRequest request , HttpServletResponse response)throws Exception {

        List<Product> productList = adminService.findAllProducts();

        //把所有产品放入request域对象
        request.setAttribute("productList",productList);

        return "/jsp/admin/product/list.jsp";

    }


    //根据订单id查询订单项
    public String findOrderInfoById(HttpServletRequest request , HttpServletResponse response)throws Exception {

        //获取oid
        String oid = request.getParameter("oid");

        List< Map<String,Object> > mapList = adminService.findOrderInfoById(oid);

        Gson gson = new Gson();
        String json = gson.toJson(mapList);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);

        return null;

    }


    //删除商品
    public String delProduct(HttpServletRequest request , HttpServletResponse response)throws Exception {

        //获取pid
        String pid = request.getParameter("pid");

        adminService.delProduct(pid);


        response.sendRedirect(request.getContextPath()+"/adminController?method=findAllProduct");

        return null;

    }


    //显示编辑商品信息
    public String editProduct(HttpServletRequest request , HttpServletResponse response) {

        //获取pid
        String pid = request.getParameter("pid");

        Product product = adminService.findProductByPid(pid);

        //将获取的product信息放入request域对象
        request.setAttribute("product",product);

        return "/jsp/admin/product/edit.jsp";
    }


    //同步查询所有分类信息
    public String findAllCategorys(HttpServletRequest request , HttpServletResponse response)throws Exception {


        List<Category> categoryList = adminService.findAllCategory();

        request.setAttribute("categoryList",categoryList);

        return "/jsp/admin/category/list.jsp";

    }


    //添加分类
    public String addCategory(HttpServletRequest request , HttpServletResponse response)throws Exception {

        String cname = request.getParameter("cname");

        Category category = new Category();
        category.setCid(UUIDUtils.getUUID());
        category.setCname(cname);

        adminService.saveCategory(category);

        response.sendRedirect(request.getContextPath()+"/adminController?method=findAllCategorys");

        return null;

    }


    //删除分类
    public String delCategory(HttpServletRequest request , HttpServletResponse response)throws Exception {

        //获取pid
        String cid = request.getParameter("cid");

        adminService.delCategory(cid);


        response.sendRedirect(request.getContextPath()+"/adminController?method=findAllCategorys");

        return null;

    }


    //显示编辑分类
    public String editCategory(HttpServletRequest request , HttpServletResponse response)throws Exception {


        //获取cid
        String cid = request.getParameter("pid");

        Category category = adminService.findCategroyByCid(cid);

        //将获取的product信息放入request域对象
        request.setAttribute("category",category);

        return "/jsp/admin/category/edit.jsp";

    }


    //修改分类
    public String updateCategory(HttpServletRequest request , HttpServletResponse response)throws Exception {


        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");

        Category category = new Category();
        category.setCid(cid);
        category.setCname(cname);

        adminService.updateCategory(category);

        response.sendRedirect(request.getContextPath()+"/adminController?method=findAllCategorys");


        return null;
    }


    //后台登录
    public String login(HttpServletRequest request , HttpServletResponse response) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!AdminUtils.getUsername().equals(username)) {

            response.sendRedirect(request.getContextPath() + "/jsp/admin/index.jsp");

            return null;

        }

        if (!AdminUtils.getPassword().equals(MD5Utils.md5(AdminUtils.getPrefix() + password))) {

            response.sendRedirect(request.getContextPath() + "/jsp/admin/index.jsp");

            return null;

        }

        request.getSession().setAttribute("username",username);

        return "/jsp/admin/home.jsp";

    }



}
