package com.geekbrains;

import com.geekbrains.persist.Category;
import com.geekbrains.persist.CategoryRepository;
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

@WebServlet(urlPatterns = "/category/*")
public class CategoryController extends HttpServlet {

    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");
    private CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Override
    public void init() throws ServletException {
        categoryRepository = (CategoryRepository) getServletContext().getAttribute("categoryRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {

            req.setAttribute("categories", categoryRepository.findAll());
            getServletContext().getRequestDispatcher("/WEB-INF/views/category.jsp").forward(req, resp);
        } else {
            Category category;
            Matcher matcher = pathParam.matcher(req.getPathInfo());

            if (req.getPathInfo().equals("/new")) {
                category = new Category();
            } else if (matcher.matches()) {
                long id;
                try {
                    id = (Long.parseLong(matcher.group(1)));
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                category = categoryRepository.findById(id);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("category", category);
            getServletContext().getRequestDispatcher("/WEB-INF/views/category_form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("save into CRepository");
        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {
            Long id = null;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            categoryRepository.save(new Category(id, req.getParameter("name")));
            resp.sendRedirect(getServletContext().getContextPath() + "/category");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}