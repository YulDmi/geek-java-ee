package com.geekbrains.listener;

import com.geekbrains.persist.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class StartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductRepository productRepository = new ProductRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        CustomersRepository customersRepository = new CustomersRepository();

        productRepository.save(new Product(null, "product1", new BigDecimal(1000), "description1" ));
        productRepository.save(new Product(null, "product2", new BigDecimal(1010), "description1" ));
        productRepository.save(new Product(null, "product3", new BigDecimal(1500), "description1" ));
        productRepository.save(new Product(null, "product4", new BigDecimal(1570), "description1" ));

        sce.getServletContext().setAttribute("productRepository", productRepository);

        categoryRepository.save(new Category(null, "goods"));
        categoryRepository.save(new Category(null, "food"));

        sce.getServletContext().setAttribute("categoryRepository", categoryRepository);

//        customersRepository.save(new Customer(null, "Bob", "123456789"));
//        customersRepository.save(new Customer(null, "Ivan", "223456789"));
//        customersRepository.save(new Customer(null, "Tom", "55555555"));

        sce.getServletContext().setAttribute("customersRepository", customersRepository);

    }
}
