package com.maveric.likeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likedto {
    private String postorcommentId;
    private String likedBy;
}
