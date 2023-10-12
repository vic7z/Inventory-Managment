package com.vi.inventory.controllers;

import com.vi.inventory.repo.ProductRepo;
import com.vi.inventory.service.SellerService;
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

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private SellerService sellerService;

	@PostMapping("/product")
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Object> postProduct(@RequestBody Product product) throws URISyntaxException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();

		int id=this.sellerService.addProduct(product,username);

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
//		List<Product> p=this.productRepo.findAll().stream().filter(i->i.getSeller().getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
//		return p;
		return this.sellerService.getAllProducts(username);
	}

	@GetMapping("/product/{productId}")
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Product> getProduct(@PathVariable("productId") int id) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
//		List<Product> p=this.productRepo.findAll().stream().filter(i->i.getSeller().getUsername().equalsIgnoreCase(username)).collect(Collectors.toList());
//		return p.stream().filter(i->i.getProductId()==id).findAny().get();
		if(this.sellerService.getProduct(username,id).isPresent()){

			 return ResponseEntity.ok( this.sellerService.getProduct(username,id).get());
		}else {
			return ResponseEntity.status(404).build();
		}
	}

	@PutMapping("/product")
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Object> putProduct(@RequestBody Product product) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		if(this.productRepo.findBySellerUsernameAndProductId(username,product.getProductId()).isPresent()){
			this.sellerService.update(product,username);
			return ResponseEntity.status(200).build();


		}else {
					return ResponseEntity.status(404).build();

		}
	}

	@DeleteMapping("/product/{productId}")
	@PreAuthorize("hasAuthority('SELLER')")
	public ResponseEntity<Product> deleteProduct(@RequestBody Product product) {
		System.out.println("9999999999999999999999999999");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		if (this.sellerService.delete(product,username)){

			return ResponseEntity.status(200).build();
		}else {
			return ResponseEntity.status(404).build();
		}
	}
}