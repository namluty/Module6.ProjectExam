package com.meta.socialnetwork.repository;

import com.meta.socialnetwork.model.Friend;
import com.meta.socialnetwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFriendRepo extends JpaRepository<Friend,Long> {
    @Query("select f from Friend f where f.user = ?1 and  f.friend = ?2")
    Friend findByUser_idAndFriend_id(User user, User friend);

    @Query("select f from Friend f where f.user = ?1 and f.status = ?2 or f.friend = ?3 and f.status = ?4")
    List<Friend> findAllByIdAcc(User account, Boolean status1, User friend, Boolean status2);
}
