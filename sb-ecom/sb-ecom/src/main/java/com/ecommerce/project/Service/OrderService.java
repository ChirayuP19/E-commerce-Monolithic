package com.ecommerce.project.Service;


import com.ecommerce.project.payload.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgPaymentId, String pgStatus, String pgName, String pgResponseMessage);
}
