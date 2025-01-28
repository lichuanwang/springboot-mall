package com.oscar.springbootmall.dao.impl;

import com.oscar.springbootmall.dao.ProductDao;
import com.oscar.springbootmall.dto.ProductRequest;
import com.oscar.springbootmall.model.Product;
import com.oscar.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Product> getProducts() {
        String sql = "select * from product";
        return namedParameterJdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT * FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (!productList.isEmpty()) {
            return productList.getFirst();
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        String sql =
                """
                INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date)
                VALUES(:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate);
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("productName", productRequest.getProductName());
        params.put("category", productRequest.getCategory().toString());
        params.put("imageUrl", productRequest.getImageUrl());
        params.put("price", productRequest.getPrice());
        params.put("stock", productRequest.getStock());
        params.put("description", productRequest.getDescription());

        Date now = new Date();
        params.put("createdDate", now);
        params.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();

    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql =
                """
                UPDATE product
                SET product_name = :productName,
                    category = :category,
                    image_url = :imageUrl,
                    price = :price,
                    stock = :stock,
                    description = :description,
                    last_modified_date = :lastModifiedDate
                WHERE product_id = :productId
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("productName", productRequest.getProductName());
        params.put("category", productRequest.getCategory().toString());
        params.put("imageUrl", productRequest.getImageUrl());
        params.put("price", productRequest.getPrice());
        params.put("stock", productRequest.getStock());
        params.put("description", productRequest.getDescription());
        Date now = new Date();
        params.put("lastModifiedDate", now);

        namedParameterJdbcTemplate.update(sql, params);


    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource("productId", productId));
    }
}
