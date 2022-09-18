package com.example.springsecuritylivecodingpractice.helper;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.example.springsecuritylivecodingpractice.security.login.LoginAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {

	private static final int ACCESS_TOKEN_VALIDITY = 10 * 60 * 1000; // 10 Minutes
	private static final int REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 1 Days
	private static final String secretKey = "ch4njun";

	public static String generateAccessToken(Long id, Collection<? extends GrantedAuthority> authorities) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));

		return Jwts.builder()
			.setClaims(claims)
			.setSubject(id.toString())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
			.signWith(SignatureAlgorithm.HS512, secretKey)
			.compact();
	}

	public static String generateRefreshToken(Long id) {
		return Jwts.builder()
			.setSubject(id.toString())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
			.signWith(SignatureAlgorithm.HS512, secretKey)
			.compact();
	}

	public static boolean validateJwt(String jwtToken) throws RuntimeException {
		return Jwts.parser().setSigningKey(secretKey).isSigned(jwtToken);
	}

	public static <T> T getClaim(String jwtToken, Function<Claims, T> func) throws RuntimeException {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
		return func.apply(claims);
	}

}
