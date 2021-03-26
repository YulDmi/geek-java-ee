package com.geekbrains;

import com.geekbrains.persist.Customer;
import com.geekbrains.persist.CustomersRepository;
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

@WebServlet(urlPatterns = "/customer/*")
public class CustomerController extends HttpServlet {

    private static final Pattern pathParam = Pattern.compile("\\/(\\d*)$");
    private CustomersRepository customersRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Override
    public void init() throws ServletException {
        customersRepository = (CustomersRepository) getServletContext().getAttribute("customersRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("") || req.getPathInfo().equals("/")) {

            req.setAttribute("customers", customersRepository.findAll());
            getServletContext().getRequestDispatcher("/WEB-INF/views/customer.jsp").forward(req, resp);
        } else {
            Customer customer;
            Matcher matcher = pathParam.matcher(req.getPathInfo());
            if (req.getPathInfo().equals("/new")) {
                customer = new Customer();
            } else if (matcher.matches()) {
                long id;
                try {
                    id = (Long.parseLong(matcher.group(1)));
                } catch (NumberFormatException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                customer = customersRepository.findById(id);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("customer", customer);
            getServletContext().getRequestDispatcher("/WEB-INF/views/customer_form.jsp").forward(req, resp);
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
            Customer customer = new Customer(id, req.getParameter("name"), req.getParameter("phone"));
            customersRepository.save(customer);
            resp.sendRedirect(getServletContext().getContextPath() + "/customer");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}