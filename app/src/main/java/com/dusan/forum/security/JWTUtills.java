package com.dusan.forum.security;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtills {

	private static String SECRET = "Xn2r5u8x/A?D(G+KbPeSgVkYp3s6v9y$B&E)H@McQfTjWmZq4t7w!z%C*F-JaNdR";
	private static long EXPIRATION = 86400000L; // 24 hours

	public static String createToken(UserDetails userDetails) {

		String roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("roles", roles)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}

	public static boolean isValid(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static String getUsername(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();

	}
	
	public static Set<SimpleGrantedAuthority> getRoles(String token){
		return Arrays.stream(Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody()
				.get("roles").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());	
	}
}
