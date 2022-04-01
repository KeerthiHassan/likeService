package com.maveric.likeservice.controller;

import com.maveric.likeservice.dto.LikeResponse;
import com.maveric.likeservice.dto.Likedto;
import com.maveric.likeservice.model.Like;
import com.maveric.likeservice.services.LikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LikeController {
    private static Logger log = LoggerFactory.getLogger(LikeController.class);

    @Autowired
    LikeService likeService;

    @GetMapping("/postOrCommentId/{postOrCommentId}/likes")
    public ResponseEntity<List<LikeResponse>> getLikes(@PathVariable("postOrCommentId") String postOrCommentId)
    {
        log.info("getting likes");
        return new ResponseEntity<List<LikeResponse>>(likeService.getLikes(postOrCommentId), HttpStatus.OK);

    }
@PostMapping("/postOrCommentId/{postOrCommentId}/likes")
    public ResponseEntity<LikeResponse> createLike(@PathVariable("postOrCommentId") String postOrCommentId, @Valid @RequestBody Likedto likedto)
    {
        log.info("creating like");
        return new ResponseEntity<LikeResponse>(likeService.createLike(postOrCommentId,likedto), HttpStatus.OK);
    }
   
    @GetMapping("/postOrCommentId/{postOrCommentId}/likes/count")
    public ResponseEntity<Integer> getLikesCount(@PathVariable("postOrCommentId") String postOrCommentId)
    {
        log.info("getting like count with respect to postorcommentId: "+postOrCommentId);
        return new ResponseEntity<Integer>(likeService.getLikesCount(postOrCommentId), HttpStatus.OK);
    }

    @GetMapping("/postOrCommentId/{postOrCommentId}/likes/{likeId}")
    public ResponseEntity<LikeResponse> getLikeDetails(@PathVariable("postOrCommentId") String postOrCommentId,@PathVariable("likeId") String likeId)
    {
        log.info("getting like details");
        return new ResponseEntity<LikeResponse>(likeService.getLikeDetails(postOrCommentId,likeId), HttpStatus.OK);
    }

    
    }


