package com.meta.socialnetwork.repository;

import com.meta.socialnetwork.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILikeRepo extends JpaRepository<Like, Long> {

}
