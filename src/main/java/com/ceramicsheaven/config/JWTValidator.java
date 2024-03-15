package com.ceramicsheaven.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JWTValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		System.out.println("Received JWT: " + jwt);

		if (jwt != null) {
			jwt = jwt.substring(7);

			try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				System.out.println("Key:"+ key);

				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

				String email = String.valueOf(claims.get("email"));
				String authority = String.valueOf(claims.get("authorities"));

				List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);

//				System.out.println("Email: " + email);
//				System.out.println("Authorities: "+authorities);

				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (Exception e) {
				System.out.println("Error during JWT validation: " + e.getMessage());
				throw new BadCredentialsException("Invalid token... from jwt validator");
			}
		}
		filterChain.doFilter(request, response);

	}


}
