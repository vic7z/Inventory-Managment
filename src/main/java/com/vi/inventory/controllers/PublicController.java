package com.vi.inventory.controllers;

import java.util.List;

import com.vi.inventory.models.JwtRequest;
import com.vi.inventory.service.ProductService;
import com.vi.inventory.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vi.inventory.models.Product;

@RestController
@RequestMapping("/api/public")
public class PublicController {
	@Autowired
	private ProductService productService;

	@Autowired
	private UserAuthService userAuthService;

	@GetMapping("/product/search")
	public List<Product> getProducts(@RequestParam("keyword") String keyword) {
		return productService.getProductByKeyword(keyword);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody JwtRequest jwtRequest) throws Exception {
		return ResponseEntity.ok(userAuthService.createJwtToken(jwtRequest));
	}

}