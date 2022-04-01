package com.maveric.likeservice.UserFeign;

import com.maveric.likeservice.dto.UserResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service",fallbackFactory = HystrixFallBackFactory.class)
public interface UserFeign {
    @LoadBalanced
    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<UserResponse> getUsersById(@PathVariable("userId") String userId);
}
