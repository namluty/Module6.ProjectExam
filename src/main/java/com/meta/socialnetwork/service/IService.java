package com.meta.socialnetwork.service;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<M>{
    Iterable<M> findAll();
    Optional<M> findById(Long id);
    void save(M m);
    void remove(Long id);
}
