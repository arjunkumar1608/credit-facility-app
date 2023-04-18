package com.credit.facilty.loan.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {

	@Value("${creditfacility.jwt.security.app.key}")
	private String appKey;

	@Value("${creditfacility.jwt.security.expire.time}")
	private Long expireTime;

	public String generateJwtToken(Authentication authentication) {

		JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
		Date expireDate = new Date(new Date().getTime() + expireTime);

		String token = Jwts.builder().setSubject(Long.toString(jwtUserDetails.getId())).setIssuedAt(new Date())
				.setExpiration(expireDate).signWith(SignatureAlgorithm.HS512, appKey).compact();

		return token;
	}

	public Long findUserIdByToken(String token) {

		Jws<Claims> claimsJws = parseToken(token);

		String userId = claimsJws.getBody().getSubject();

		return Long.parseLong(userId);
	}

	private Jws<Claims> parseToken(String token) {
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(appKey).parseClaimsJws(token);
		return claimsJws;
	}

	public boolean validateToken(String token) {

		boolean isValid;

		try {
			Jws<Claims> claimsJws = parseToken(token);

			isValid = !isTokenExpired(claimsJws);
		} catch (Exception e) {
			isValid = false;
		}

		return isValid;
	}

	private boolean isTokenExpired(Jws<Claims> claimsJws) {

		Date expirationDate = claimsJws.getBody().getExpiration();

		return expirationDate.before(new Date());
	}
}
