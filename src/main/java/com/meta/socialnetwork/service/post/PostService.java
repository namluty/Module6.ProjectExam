package com.meta.socialnetwork.service.post;

import com.meta.socialnetwork.model.Like;
import com.meta.socialnetwork.model.Post;
import com.meta.socialnetwork.repository.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostService implements IPostService{

    @Autowired
    IPostRepo postRepo;
    @Override
    public Iterable<Post> findAll() {
        return postRepo.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepo.findById(id);
    }

    @Override
    public void save(Post post) {
        postRepo.save(post);
    }

    @Override
    public void remove(Long id) {
        postRepo.deleteById(id);
    }

    @Override
    public List<Post> findPostByStatusIsContaining( boolean status) {
        return postRepo.findPostByStatusIsContaining(status);
    }


}
