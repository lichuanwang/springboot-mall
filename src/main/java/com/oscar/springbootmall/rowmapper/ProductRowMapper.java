package com.oscar.springbootmall.rowmapper;

import com.oscar.springbootmall.constant.ProductCategory;
import com.oscar.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        String category = rs.getString("category");
        ProductCategory productCategory = ProductCategory.valueOf(category);
        product.setCategory(productCategory);
        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return product;
    }
}
