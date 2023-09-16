package com.vi.inventory.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vi.inventory.models.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByUserUsername(String username);
}