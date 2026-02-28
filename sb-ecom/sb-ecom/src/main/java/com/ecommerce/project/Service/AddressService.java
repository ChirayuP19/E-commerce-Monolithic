package com.ecommerce.project.Service;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);
}
