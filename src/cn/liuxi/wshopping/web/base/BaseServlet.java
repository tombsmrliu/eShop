package cn.liuxi.wshopping.web.base;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException,IOException{


        try {

            //TODO 1获取请求参数method
            String methodName = request.getParameter("method");

            //TODO 2默认方法名
            if (methodName == null) {
                methodName = "execute";
            }

            //TODO 3获取当前实例对象，需要指定方法
            Method method = this.getClass().getMethod(methodName,
                HttpServletRequest.class,HttpServletResponse.class);

            //TODO 4执行指定方法
            String jspPath = (String) method.invoke(this,request,response);

            //TODO 如果子类有返回值，将请求到指定jsp页面
            if (jspPath != null) {
                request.getRequestDispatcher(jspPath).forward(request,response);
            }


        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }


    /**
     * 默认方法，用于子类重写
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String execute(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{

        //NOOP (no operate,没操作)
        return null;

    }
}
