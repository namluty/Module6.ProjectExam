package com.meta.socialnetwork.repository;

import com.meta.socialnetwork.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepo extends JpaRepository<Comment, Long> {
}
