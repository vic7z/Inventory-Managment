package com.vi.inventory.service;

import java.util.Optional;

import com.vi.inventory.config.JwtUtil;
import com.vi.inventory.models.JwtRequest;
import com.vi.inventory.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vi.inventory.models.User;

@Service
public class UserAuthService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;



	public User loadUserByUserID(Integer id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent())
			return user.get();
		else
			throw new UsernameNotFoundException("User ID not found");
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isPresent())
			return user.get();
		else
			throw new UsernameNotFoundException("User ID not found");
	}
	public String createJwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUsername();
		String userPassword = jwtRequest.getPassword();
		authenticate(userName, userPassword);

		String newGeneratedToken = jwtUtil.generateToken(userName);

		return newGeneratedToken;
	}
	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}



}
