package com.maveric.likeservice.services;


import com.maveric.likeservice.UserFeign.UserFeign;
import com.maveric.likeservice.controller.LikeController;
import com.maveric.likeservice.dto.LikeResponse;
import com.maveric.likeservice.dto.Likedto;
import com.maveric.likeservice.dto.UserResponse;
import com.maveric.likeservice.exception.AlreadyLiked;
import com.maveric.likeservice.exception.LikeDetailsNotFound;
import com.maveric.likeservice.model.Like;
import com.maveric.likeservice.repo.LikeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImplementation implements LikeService{
    private static Logger log = LoggerFactory.getLogger(LikeServiceImplementation.class);
    @Autowired
    LikeRepo likeRepo;
    @Autowired
   UserFeign userFeign;
@LoadBalanced
    @Override
    public List<LikeResponse> getLikes(String postOrCommentId) {
    
    List<Like> likeList=likeRepo.findBypostorcommentId(postOrCommentId);
        if(likeList==null){
            log.info("No like for the post---error");
            throw new LikeDetailsNotFound("No like for this post");
        }
        List<LikeResponse> likeResponses=new ArrayList<>();
        for(Like like:likeList)
        {
            likeResponses.add(new LikeResponse(like.getLikeId(),like.getPostorcommentId(),
                    userFeign.getUsersById(like.getLikedBy()).getBody(),like.getCreatedAt()));
        }
        log.info("list of like successfully retrieved");
        return likeResponses;
    }

   

    @Override
    public Integer getLikesCount(String postOrCommentId) {
    log.info("getting like count from repo");
        return likeRepo.findBypostorcommentId(postOrCommentId).size();
    }

    @Override
    public LikeResponse getLikeDetails(String postOrCommentId,String likeId) {
        Like like=likeRepo.findBylikeId(likeId);
        if(like==null) {
            log.info("not able to find details for this like---error");
            throw new LikeDetailsNotFound("Like Details Does't exist with id :" + likeId);
        }
        log.info("like details fetched successfully");
        return new LikeResponse(like.getLikeId(),like.getPostorcommentId(),userFeign.getUsersById(like.getLikedBy()).getBody(),like.getCreatedAt());
    }

    
}