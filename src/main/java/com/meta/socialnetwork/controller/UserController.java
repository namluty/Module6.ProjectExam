package com.meta.socialnetwork.controller;

import com.meta.socialnetwork.model.Comment;
import com.meta.socialnetwork.model.Like;
import com.meta.socialnetwork.model.Post;
import com.meta.socialnetwork.model.User;
import com.meta.socialnetwork.service.comment.ICommentService;
import com.meta.socialnetwork.service.like.ILikeService;
import com.meta.socialnetwork.service.post.IPostService;
import com.meta.socialnetwork.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    IPostService postService;
    @Autowired
    IUserService userService;
    @Autowired
    ILikeService likeService;
    @Autowired
    ICommentService commentService;


    @GetMapping("/showPost")
    public ResponseEntity<?> getListPost(){
//        String[] sortById = new String[2];
//        Pageable pageable = PageRequest.of(Integer.parseInt(page), 5, Sort.by("id").descending());
//        Page<Post> postPage = postService.findPostByPrivacyContaining("public", pageable);
        Iterable<Post> postPage =  postService.findAll();
        return new ResponseEntity<>(postPage, HttpStatus.OK);
    }
    @PostMapping("/createPost/{id}")
    public ResponseEntity<?> createPost(@RequestBody Post post, @PathVariable Long id) {
        User user  = userService.findById(id).get();
        post.setUser(user);
        LocalDate localDate = LocalDate.now();
        post.setCreated_date(localDate);
        postService.save(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/deletepost/{idPost}")
    public ResponseEntity<String> deletePost(@PathVariable Long idPost) {
        postService.remove(idPost);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/findPost/{idPost}")
    public ResponseEntity<?> findPostById(@PathVariable("idPost") Long idPost) {
        Optional<Post> post = postService.findById(idPost);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/likeshow/{idUser}/{idPost}")
    public ResponseEntity<?> createlike(@PathVariable("idUser") Long idUser, @PathVariable("idPost") Long idPost) {
            User user = userService.findById(idUser).get();
            Post post = postService.findById(idPost).get();
            Like like = new Like();
            like.setUser(user);
            like.setPosts(post);
            likeService.save(like);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/{idUser}/{idPost}")
    public ResponseEntity<String> createComment(@RequestBody Comment comment, @PathVariable("idUser") Long idUser, @PathVariable("idPost") Long idPost) {
        User user = userService.findById(idUser).get();
        Post post = postService.findById(idPost).get();
        comment.setUser(user);
        comment.setPost(post);
        LocalDate localDate = LocalDate.now();
        comment.setCreated_date(localDate);
        commentService.save(comment);

        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

// dang bug
    @PutMapping("/updatecomment/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Comment comment1 = new Comment();
        comment1.setId(id);
        comment1.setContent(comment.getContent());
        LocalDate localDate = LocalDate.now();
        comment1.setModified_date(localDate);
        commentService.save(comment1);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @DeleteMapping("/deletecomment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
        commentService.remove(id);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }


}
