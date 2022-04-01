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
    public List<LikeResponse> getLikes(String postOrCommentId,Integer page,Integer size) {
    Pageable pageable= PageRequest.of(page,size);
    List<Like> likeList=likeRepo.findBypostorcommentId(postOrCommentId,pageable);
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
    public LikeResponse createLike(String postOrCommentId, Likedto likedto) {
        List<Like> likeList=likeRepo.findBypostorcommentId(postOrCommentId);
        Like likes=new Like();
        likes.setPostorcommentId(postOrCommentId);
        likes.setCreatedAt(LocalDate.now());
        likes.setLikedBy(likedto.getLikedBy());
        if(likeList!=null && !(likeList.stream().filter(like->like.getLikedBy().equals(likedto.getLikedBy())).collect(Collectors.toList()).isEmpty()))
        {
                log.info("user have already liked it---error");
                throw new AlreadyLiked("You have already liked it");
        }
            Like like = likeRepo.save(likes);
            log.info("like saved successfully");
            return new LikeResponse(like.getLikeId(), like.getPostorcommentId(),
                    userFeign.getUsersById(likedto.getLikedBy()).getBody(), like.getCreatedAt());
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

    @Override
    public String removeLike(String postOrCommentId,String likeId) {
    if(likeRepo.findBylikeId(likeId)==null) {
        log.info("cant remove, like doesn't exist");
        throw new LikeDetailsNotFound("Like Does not exist");
    }
         likeRepo.deleteById(likeId);
            log.info("like deleted successfully");
        return "Like has been successfully removed";
    }
}
