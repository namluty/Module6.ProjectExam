package com.meta.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Boolean status;
    private String imageUrl;
    @ManyToOne
    private User user;
    private LocalDate created_date;
    private LocalDate modified_date;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", orphanRemoval = true)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @JsonManagedReference
    @OneToMany
    private List<Comment> commentList;
    @OneToMany
    private List<Like> likeList;

}
