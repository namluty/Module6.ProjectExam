package com.meta.socialnetwork.repository;

import com.meta.socialnetwork.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepo extends JpaRepository<Post,Long> {

}
