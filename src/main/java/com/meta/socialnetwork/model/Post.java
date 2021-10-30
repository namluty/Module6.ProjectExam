package com.meta.socialnetwork.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Long user_id;
    private LocalDate created_date;
    private LocalDate modified_date;

}
