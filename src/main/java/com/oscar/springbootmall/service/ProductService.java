package com.oscar.springbootmall.service;

import com.oscar.springbootmall.dto.ProductRequest;
import com.oscar.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts();

}
