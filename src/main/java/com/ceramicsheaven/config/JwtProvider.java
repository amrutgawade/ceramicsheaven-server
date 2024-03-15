package com.ceramicsheaven.config;

import com.ceramicsheaven.services.CustomUserServiceImplementation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtProvider {
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	@Autowired
	private CustomUserServiceImplementation customUserService;

	public JwtProvider(CustomUserServiceImplementation customUserService){

	}
	public String generateToken(Authentication auth) {
		UserDetails userDetails = customUserService.loadUserByUsername(auth.getName());

		String joinedAuthorities = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		Claims claims = Jwts.claims().setSubject(auth.getName());
		claims.put("authorities",joinedAuthorities);
		claims.put("email", userDetails.getUsername());

		String jwt = Jwts.builder().setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+846000000))
				.signWith(key)
				.compact();

		return jwt;
	}

	public String getEmailFromToken(String jwt) {

		jwt=jwt.substring(7);

		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

		String email = String.valueOf(claims.get("email"));

		return email;
	}
}
