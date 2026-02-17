package com.ecommerce.project.Service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import org.springframework.stereotype.Service;


public interface ProductService {

    ProductDTO addProduct(Long categoryId,Product product);
}
