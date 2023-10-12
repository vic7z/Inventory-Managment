package com.vi.inventory.service;

import com.vi.inventory.models.Product;
import com.vi.inventory.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getProductByKeyword(String keyword){
//        List<Product> products=productRepo.findAll();
//        return products.stream()
//                .filter(i->i.getProductName().toLowerCase().contains(keyword.toLowerCase())
//                        || i.getCategory().getCategoryName().toLowerCase().contains(keyword.toLowerCase()))
//                .collect(Collectors.toList());

        return productRepo.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword,keyword);
    }



}
