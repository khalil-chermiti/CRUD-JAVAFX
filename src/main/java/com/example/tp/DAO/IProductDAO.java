package com.example.tp.DAO;

import com.example.tp.models.Product;

import java.util.List;

public interface IProductDAO {
    public boolean addProduct(Product product);

    public boolean removeProduct(Product product);

    public List<Product> getProducts();
}
