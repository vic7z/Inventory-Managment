package com.vi.inventory.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vi.inventory.models.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
	List<Product> findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(String productName,
			String categoryName);

//	List<Product> findBySellerUserName(Integer sellerId);

	List<Product> findBySellerUsername(String Username);

	Optional<Product> findBySellerUsernameAndProductId(String Username, Integer productId);
}