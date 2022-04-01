package com.maveric.likeservice.repo;

import com.maveric.likeservice.model.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRepo extends MongoRepository<Like,String> {
    List<Like> findBypostorcommentId(String postOrCommentId);
    List<Like> findBypostorcommentId(String postOrCommentId, Pageable pageable);
    Like findBylikeId(String likeId);
}
