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
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductController extends HttpServlet {

    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {

            req.setAttribute("products", productRepository.findAll());
            getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);

        } else {
            Product product;
            Matcher matcher = pathParam.matcher(req.getPathInfo());
            if (req.getPathInfo().equals("/new")) {
                product = new Product();
            } else if (matcher.matches()) {
                long id;
                try {
                    id = (Long.parseLong(matcher.group(1)));
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                product = productRepository.findById(id);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("product", product);
            getServletContext().getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            Long id = null;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            Product product = new Product(
                    id,
                    req.getParameter("name"),
                    new BigDecimal(req.getParameter("price")),
                    req.getParameter("description"));
            productRepository.save(product);
            resp.sendRedirect(getServletContext().getContextPath() + "/product");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}