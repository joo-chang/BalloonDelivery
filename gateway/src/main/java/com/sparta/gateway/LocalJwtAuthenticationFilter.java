package com.sparta.gateway;

import com.sparta.gateway.client.AuthClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    private final AuthClient authClient;

    public LocalJwtAuthenticationFilter(@Lazy AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.equals("/auth/signIn") || path.equals("/auth/signUp")) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);
        if (token == null || !validateToken(token, exchange)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }


        return chain.filter(exchange);
    }

    private boolean validateToken(String token, ServerWebExchange exchange) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            String userId = claims.get("user_id").toString();
            String username = claims.get("username").toString();
            exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .header("X-User-Name",username)
                    .header("X-User-Role", authClient.getPermission(Long.valueOf(userId)))
                    .build();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
