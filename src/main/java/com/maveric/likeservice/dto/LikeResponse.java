package com.maveric.likeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponse {
    private String likeId;
    private String postorcommentId;
    private UserResponse likedBy;
    private LocalDate createdAt;

}
