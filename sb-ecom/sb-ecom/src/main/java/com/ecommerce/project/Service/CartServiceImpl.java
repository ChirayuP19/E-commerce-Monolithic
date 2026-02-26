package com.ecommerce.project.Service;

import com.ecommerce.project.payload.CartDTO;
import com.ecommerce.project.repositories.CartRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@RequiredArgsConstructor

public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        cartRepository.findByEmail()
    }
}
