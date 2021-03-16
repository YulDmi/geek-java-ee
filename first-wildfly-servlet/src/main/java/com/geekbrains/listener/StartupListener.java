package com.geekbrains.listener;

import com.geekbrains.persist.Product;
import com.geekbrains.persist.ProductRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class StartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductRepository productRepository = new ProductRepository();
        productRepository.save(new Product(null, "product1", new BigDecimal(1000), "description1" ));
        productRepository.save(new Product(null, "product2", new BigDecimal(1010), "description1" ));
        productRepository.save(new Product(null, "product3", new BigDecimal(1500), "description1" ));
        productRepository.save(new Product(null, "product4", new BigDecimal(1570), "description1" ));

        sce.getServletContext().setAttribute("productRepository", productRepository);
    }
}
