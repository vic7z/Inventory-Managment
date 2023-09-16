package com.vi.inventory.service;

import com.vi.inventory.models.Cart;
import com.vi.inventory.models.Product;
import com.vi.inventory.repo.CartRepo;
import com.vi.inventory.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;

    public Cart getCart(String name){
      return this.cartRepo.findByUserUsername(name).get();
    }

    public boolean addProduct(Product product){
        return  false;
    }



}
