package com.vi.inventory.controllers;

import com.vi.inventory.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.vi.inventory.models.Product;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {

	@Autowired
	private ProductRepo productRepo;

	@PostMapping("/product")
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Object> postProduct(@RequestBody Product product) throws URISyntaxException {
//		this.productRepo.save(product);

		return ResponseEntity.status(201)
				.location(URI.create("http://localhost/api/auth/seller/product/3"))
				.build();


	}

	@GetMapping("/product")
	@PreAuthorize("hasAuthority('SELLER')")
	public List<Product> getAllProducts() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		List<Product> p=this.productRepo.findAll().stream().filter(i->i.getSeller().getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
		return p;
	}

	@GetMapping("/product/{productId}")
	@PreAuthorize("hasAuthority('SELLER')")
	public Product getProduct(@PathVariable("productId") int id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		List<Product> p=this.productRepo.findAll().stream().filter(i->i.getSeller().getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
		return p.stream().filter(i->i.getProductId()==id).findAny().get();
	}

	@PutMapping("/product")
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Object> putProduct() {
		return ResponseEntity.status(404).build();
	}

	@DeleteMapping("/product/{productId}")
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Product> deleteProduct() {
		return null;
	}
}