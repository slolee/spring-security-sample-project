package com.example.springsecuritylivecodingpractice.helper;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.tomcat.util.codec.binary.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {

	private static final int ACCESS_TOKEN_VALIDITY = 10 * 60 * 1000; // 10 Minutes
	private static final int REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 1 Days
	private static final String secretKey = "ch4njun";

	public static String generateJwt(Long id, JwtType type) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("jwt_type", type.name());

		return Jwts.builder()
			.setClaims(claims)
			.setSubject(id.toString())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(generateValidity(type))
			.signWith(SignatureAlgorithm.HS512, secretKey)
			.compact();
	}

	public static Date generateValidity(JwtType type) {
		switch (type) {
			case ACCESS_TOKEN:
				return new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY);
			case REFRESH_TOKEN:
				return new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY);
			default:
				return null;
		}
	}

	public static boolean validateJwt(String jwtToken) {
		return Jwts.parser().setSigningKey(secretKey).isSigned(jwtToken);
	}

	public static <T> T getClaim(String jwtToken, Function<Claims, T> func) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
		return func.apply(claims);
	}

}
