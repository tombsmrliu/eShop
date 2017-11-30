package cn.liuxi.wshopping.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class EncodingFilter implements Filter {

    private String charset = "UTF-8";

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //在传递request之前对request的getParameter方法进行增强
		/*
		 * 装饰者模式(包装)
		 *
		 * 1、增强类与被增强的类要实现统一接口
		 * 2、在增强类中传入被增强的类
		 * 3、需要增强的方法重写 不需要增强的方法调用被增强对象的
		 *
		 */
        //System.out.println("filter before");


        //解决post中文乱码问题
        req.setCharacterEncoding(charset);



        //解决get中文乱码问题
        //被增强的对象
        HttpServletRequest request = (HttpServletRequest) req;
        //增强对象
        EncodingRequestWrapper encodingRequestWrapper = new EncodingRequestWrapper(request,charset);

        //System.out.println("filter after");


        chain.doFilter(encodingRequestWrapper, resp);
    }


    public void destroy() {
    }



    public void init(FilterConfig config) throws ServletException {

    }

}


