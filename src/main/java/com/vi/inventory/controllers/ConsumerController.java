package com.vi.inventory.controllers;

import com.vi.inventory.models.Cart;
import com.vi.inventory.models.Product;
import com.vi.inventory.repo.ProductRepo;
import com.vi.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/consumer")
public class ConsumerController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductRepo productRepo;


	@GetMapping("/cart")
	@PreAuthorize("hasAuthority('CONSUMER')")
	public ResponseEntity<Cart> getCart() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		System.out.println(username);
		return ResponseEntity.ok(this.userService.getCart(username));
	}

	@PostMapping("/cart")
	@PreAuthorize("hasAnyRole('CONSUMER')")
	public ResponseEntity<Object> postCart(@RequestBody Product product) {
		  productRepo.save(product);
		 return ResponseEntity.ok().build();
	}

	@PutMapping("/cart")
	@PreAuthorize("hasAnyRole('CONSUMER')")
	public ResponseEntity<Object> putCart(@RequestBody Product product) {
		Product product1=this.productRepo.findById(product.getProductId()).orElse(null);
		if (product1==null){
			this.productRepo.save(product1);
		}
		else {
			this.productRepo.deleteById(product.getProductId());
			this.productRepo.save(product1);
		}
		return ResponseEntity.status(404).build();
	}

	@DeleteMapping("/cart")
	@PreAuthorize("hasAnyRole('CONSUMER')")
	public ResponseEntity<Object> deleteCart(@RequestBody Product product) {
		this.productRepo.deleteById(product.getProductId());
		return ResponseEntity.ok().build();
	}

}