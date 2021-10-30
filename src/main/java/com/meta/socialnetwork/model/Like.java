package com.meta.socialnetwork.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_chat", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Post> posts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
