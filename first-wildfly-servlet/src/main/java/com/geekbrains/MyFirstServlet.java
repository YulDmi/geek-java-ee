package com.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class MyFirstServlet implements Servlet {

    private ServletConfig servletConfig;
    private static final Logger logger = LoggerFactory.getLogger(MyFirstServlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        logger.info("from init");
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        logger.info("new request");
        servletResponse.getWriter().println("<h1>Hello from my FirstServlet!<h1>");
   
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
