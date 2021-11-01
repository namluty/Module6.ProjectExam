package com.meta.socialnetwork.controller;

import com.meta.socialnetwork.model.*;
import com.meta.socialnetwork.service.comment.ICommentService;
import com.meta.socialnetwork.service.friend.IFriendService;
import com.meta.socialnetwork.service.like.ILikeService;
import com.meta.socialnetwork.service.post.IPostService;
import com.meta.socialnetwork.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    IFriendService friendService;


    @GetMapping("/showPost")
    public ResponseEntity<?> getListPost(){
//        String[] sortById = new String[2];
//        Pageable pageable = PageRequest.of(Integer.parseInt(page), 5, Sort.by("id").descending());
//        Page<Post> postPage = postService.findPostByPrivacyContaining("public", pageable);
        Iterable<Post> postPage =  postService.findAll();
        return new ResponseEntity<>(postPage, HttpStatus.OK);
    }
//
//    @GetMapping("/showPostPublic")
//    public ResponseEntity<?> getPostPublic(@RequestParam Boolean status){
//        List<Post> list = postService.findPostByStatusIsContaining(status);
//        return new ResponseEntity<>(list,HttpStatus.OK);
//    }
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
        Iterable<Comment> listComment = commentService.findAllByPost_Id(idPost);
        commentService.deleteAll(listComment);
        Iterable<Like> likes = likeService.findAllLikeByPosts_Id(idPost);
        likeService.deleteAll(likes);
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

    // sửa comment
    @PutMapping("/updatecomment/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Comment comment1 = commentService.findById(id).get();
        comment1.setContent(comment.getContent());
        LocalDate localDate = LocalDate.now();
        comment1.setModified_date(localDate);
        commentService.save(comment1);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    // xóa comment
    @DeleteMapping("/deletecomment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
        commentService.remove(id);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    // tìm kiếm bạn theo tên
    @GetMapping("findFriend")
    public ResponseEntity<?> findFriend(@RequestBody User user){
        Iterable<User> user1 = userService.findAllByUsernameIsContaining(user.getUsername());
        if (user1 != null) {
            return new ResponseEntity<>(user1, HttpStatus.OK);
        }else
            return new ResponseEntity<>("no user", HttpStatus.OK);
    }

    // gửi yêu cầu kết bạn
    @GetMapping("/sendaddfriend/{idAcc}/{idFriend}")
    public ResponseEntity<String> sendAddFriend(@PathVariable("idAcc") Long idUser, @PathVariable("idFriend") Long idFriend) {
        User user = userService.findById(idUser).get();
        User friend = userService.findById(idFriend).get();
        Friend friend1 = friendService.findByUser_idAndFriend_id(user, friend);
        Friend friend2 = friendService.findByUser_idAndFriend_id(friend, user);
        if (friend1 == null && friend2 == null) {
            Friend newFriend = new Friend();
            newFriend.setUser(user);
            newFriend.setFriend(friend);
            newFriend.setStatus(false);
            friendService.save(newFriend);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }
        return new ResponseEntity<>("exits", HttpStatus.OK);
    }

    // chấp nhận kết bạn
    @GetMapping("/confirmfriend/{idAcc}/{idFriend}")
    public ResponseEntity<String> confirmFriend(@PathVariable("idAcc") Long idUser, @PathVariable("idFriend") Long idFriend) {
        User account = userService.findById(idUser).get();
        User friend = userService.findById(idFriend).get();
        Friend friend1 = friendService.findByUser_idAndFriend_id(account, friend);
        Friend friend2 = friendService.findByUser_idAndFriend_id(friend, account);
        if (friend1 != null) {
            friend1.setStatus(true);
            friendService.save(friend1);
        } else {
            friend2.setStatus(true);
            friendService.save(friend2);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // từ chối kết bạn
    @DeleteMapping("/refuse/{idAcc}/{idFriend}")
    public ResponseEntity<String> refuseFriend(@PathVariable("idAcc") Long idUser, @PathVariable("idFriend") Long idFriend) {
        User user = userService.findById(idUser).get();
        User friend = userService.findById(idFriend).get();
        Friend f = friendService.findByUser_idAndFriend_id(friend, user);
        friendService.remove(f.getId());
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    // xem danh sách bạn
    @GetMapping("/showfriend/{idAcc}")
    public ResponseEntity<List<User>> showListFriend(@PathVariable("idAcc") Long idUser) {
        User account = userService.findById(idUser).get();
        List<Friend> list = friendService.findAllByIdAcc(account, true, account, true);
        List<User> accountList = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getUser().getId() == idUser) {
                    accountList.add(list.get(i).getFriend());
                } else {
                    accountList.add(list.get(i).getUser());
                }
            }
        }
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }


}
