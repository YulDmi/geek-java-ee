package ru.geekbrains.persist;
import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "deleteProductById", query = "delete from Product p where p.id = :id"),
        @NamedQuery(name = "findAllProduct", query = "select p from Product p"),
        @NamedQuery(name = "countProduct", query = "select count(p) from Product p")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private String description;

    @ManyToOne
    private Category category;

    public Product() {
        }

        public Product(Long id, String name, BigDecimal price, String description, Category category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.category = category;
        }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


