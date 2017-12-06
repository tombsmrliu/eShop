package cn.liuxi.wshopping.web.Servlet;

import cn.liuxi.wshopping.entity.Category;
import cn.liuxi.wshopping.entity.Product;
import cn.liuxi.wshopping.service.IAdminService;
import cn.liuxi.wshopping.service.impl.AdminServiceImpl;
import cn.liuxi.wshopping.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddProduct extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //目的：收集表单数据 封装成一个Product实体 将上传的文件存到服务器的磁盘上
        Product product = new Product();

        Map<String,Object> map = new HashMap<>();

        // 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = this.getServletContext().getRealPath("jsp/upload");
        // 上传时生成的临时文件保存目录
        String tempPath = this.getServletContext().getRealPath("jsp/temp");

        //上传文件目录对象
        File upfile = new File(savePath);
        if (!upfile.exists()) {
            upfile.mkdir();
        }

        //临时目录对象
        File tmpFile = new File(tempPath);
        if (!tmpFile.exists()) {
            // 创建临时目录
            tmpFile.mkdir();
        }

        try {

            //创建磁盘文件项工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024*1024);
            factory.setRepository(tmpFile);
            //创建文件上传核心对象
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解析request获得文件项对象集合
            List<FileItem> parseRequest = upload.parseRequest(request);

            for (FileItem item : parseRequest) {

                //判断是否是普通表单项
                boolean formField = item.isFormField();
                if (formField) {

                    //普通表单项获得表单数据 封装到Product实体中
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");

                    map.put(fieldName,fieldValue);

                } else {

                    //文件上传项 获得文件名称 获得文件内容
                    String fileName = item.getName();

                    InputStream in = item.getInputStream();
                    OutputStream out = new FileOutputStream(upfile+"/"+fileName);
                    IOUtils.copy(in , out);
                    //关闭io流资源
                    in.close();
                    out.close();
                    //移除临时文件
                    item.delete();

                    map.put("pimage","upload/"+fileName);

                }
            }

            BeanUtils.populate(product,map);
            //pid
            product.setPid(UUIDUtils.getUUID());
            //pdate
            product.setPdate(new Date());
            //pflag
            product.setPflag(0);
            //category
            Category category = new Category();
            category.setCid(map.get("cid").toString());
            product.setCategory(category);

            //讲product传递给service层
            IAdminService adminService = new AdminServiceImpl();
            adminService.savaProduct(product);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath()+"/adminController?method=findAllProduct");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);

    }


}
