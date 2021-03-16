package com.geekbrains;

import com.geekbrains.persist.Product;
import com.geekbrains.persist.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServlet.class);

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            resp.getWriter().println("<table>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<th>Id</th>");
            resp.getWriter().println("<th>Price</th>");
            resp.getWriter().println("<th>Description</th>");

            resp.getWriter().println("</tr>");

            for (Product product : productRepository.findAll()) {

                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td><a href='" + getServletContext().getContextPath() + "/product/" + product.getId() + "'>" + product.getId() + "</a></td>");
                resp.getWriter().println("<td>" + product.getName() + "</td>");
                resp.getWriter().println("<td>" + product.getPrice() + "</td>");
                resp.getWriter().println("<td>" + product.getDescription() + "</td>");

                resp.getWriter().println("</tr>");
            }
            resp.getWriter().println("</table>");
        } else {
            String info = req.getPathInfo().substring(1);
            Product product = productRepository.findById(Long.parseLong(info));
            // обработать ошибку парсинга не числа
            logger.info("product id: " + product.getId());
            resp.getWriter().println("<h2>Product info</h2>");
            resp.getWriter().println("<p>Name: " + product.getName() + "</p>");
            resp.getWriter().println("<p>Description: " + product.getDescription() + "</p>");
            resp.getWriter().println("<p>Price: " + product.getPrice() + "</p>");
        }
    }
}