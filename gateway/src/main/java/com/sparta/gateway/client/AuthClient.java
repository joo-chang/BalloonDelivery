package com.sparta.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "BalloonDelivery")

public interface AuthClient {
    @GetMapping("/auth/getPermission/{userId}")
    String getPermission(@PathVariable Long userId);
}
