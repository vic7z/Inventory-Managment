package com.vi.inventory.service;

import com.vi.inventory.models.Category;
import com.vi.inventory.models.Product;
import com.vi.inventory.repo.CartRepo;
import com.vi.inventory.repo.ProductRepo;
import com.vi.inventory.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;

    public List<Product> getAllProducts(String Username){
        return this.productRepo.findBySellerUsername(Username);
    }

    public Optional<Product> getProduct(String username,int id){
        return this.productRepo.findBySellerUsernameAndProductId(username,id);
    }

    public int addProduct(Product product,String username){
        product.setSeller(this.userRepo.findByUsername(username).get());

        System.out.println(product.toString());
        this.productRepo.save(product);
        return product.getProductId();

    }
    public void update(Product product,String username){
        product.setSeller(this.userRepo.findByUsername(username).get());
        this.productRepo.save(product);
    }
    public boolean delete(Product product,String username){
        System.out.println("yes");
        if (this.productRepo.findBySellerUsernameAndProductId(username,product.getProductId()).isPresent()){
            this.productRepo.delete(product);
            return true;
        }else {
            return false;
        }
    }
}
