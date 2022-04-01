package com.maveric.likeservice.model;

import com.maveric.likeservice.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Document(collection = "like")
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    private String likeId;
    private String postorcommentId;
    private String likedBy;
    private LocalDate createdAt;
}
