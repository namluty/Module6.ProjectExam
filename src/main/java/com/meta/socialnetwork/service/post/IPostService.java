package com.meta.socialnetwork.service.post;

import com.meta.socialnetwork.model.Like;
import com.meta.socialnetwork.model.Post;
import com.meta.socialnetwork.service.IService;

import java.security.PolicySpi;
import java.util.List;

public interface IPostService extends IService<Post> {
    List<Post> findPostByStatusIsContaining( boolean status);
}
