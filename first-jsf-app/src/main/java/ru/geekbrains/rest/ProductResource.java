package ru.geekbrains.rest;

import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/v1/product")
public interface ProductResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductRepr> findAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductRepr findById(@PathParam("id")Long id);

    @GET
    @Path("/list/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductRepr> findByName(@PathParam("name")String name);

    @GET
    @Path("/listByCategory/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    List<ProductRepr> findProductByCategoryId(@PathParam("id")Long id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(ProductRepr product);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(ProductRepr product);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Long id);
}
