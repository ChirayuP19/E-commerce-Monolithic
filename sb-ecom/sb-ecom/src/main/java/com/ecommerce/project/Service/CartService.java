package com.ecommerce.project.Service;

import com.ecommerce.project.payload.CartDTO;


public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
