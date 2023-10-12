package com.vi.inventory.service;

import com.vi.inventory.models.Cart;
import com.vi.inventory.models.CartProduct;
import com.vi.inventory.models.Product;
import com.vi.inventory.repo.CartRepo;
import com.vi.inventory.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductRepo productRepo;

    public Cart getCart(String name){
      return this.cartRepo.findByUserUsername(name).get();
    }

    public boolean addProduct(Product product,String name){
        Cart cart=this.getCart(name);
        List<CartProduct> cartProduct=cart.getCartProducts();
        if(cartProduct.stream().anyMatch(i->i.getProduct().getProductName().equalsIgnoreCase(product.getProductName()))){
            for (CartProduct i:cartProduct){
                if(i.getProduct().getProductName().equalsIgnoreCase(product.getProductName())){
                    i.setProduct(product);
                    i.setQuantity(i.getQuantity()+1);
                }
            }
            cart.setCartProducts(cartProduct);
            this.cartRepo.save(cart);
            return false;
        }else {
            cartProduct.add(new CartProduct(cart,product,1));
            cart.setCartProducts(cartProduct);
            this.cartRepo.save(cart);
            return true;
        }

    }



}
