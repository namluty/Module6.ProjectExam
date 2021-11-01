package com.meta.socialnetwork.repository;

import com.meta.socialnetwork.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepo extends JpaRepository<Post,Long> {
 List<Post> findPostByStatusIsContaining( boolean status);
}
