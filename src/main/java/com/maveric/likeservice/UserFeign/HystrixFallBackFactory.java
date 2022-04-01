package com.maveric.likeservice.UserFeign;

import org.springframework.cloud.openfeign.FallbackFactory;

public class HystrixFallBackFactory implements FallbackFactory<UserFeign> {
    @Override
    public UserFeign create(Throwable cause) {

        return null;

    }
}
