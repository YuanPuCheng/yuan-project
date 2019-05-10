package com.zihui.cwoa.system.config;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterConfig implements Filter {


    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //添加过滤器，禁止浏览器缓存数据
        response.setDateHeader("Expires", 0);
        // 注意这里是两个，很多博客少了no-store是不成功的
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        //System.out.println("进入过滤器");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
