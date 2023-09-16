package com.vi.inventory.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

	public String getUser(final String token) {
		return getClaim(token,Claims::getSubject);
	}

	public String generateToken(String username) {
		Map<String,Object> map=new HashMap<>();
		return Jwts.builder()
				.setClaims(map)
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis()*90000))
				.signWith(SignatureAlgorithm.HS256,"secretkey")
				.compact();
	}

	public void validateToken(final String token) {
	}

	public Claims getAllClaims(String token){
		return Jwts.parser()
				.setSigningKey("secretkey")
				.parseClaimsJws(token)
				.getBody();
	}
	public <T> T getClaim(String token, Function<Claims,T> tokenResolver){
		Claims claims=getAllClaims(token);
		return tokenResolver.apply(claims);

	}
}
