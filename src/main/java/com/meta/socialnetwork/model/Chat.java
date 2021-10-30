package com.meta.socialnetwork.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@Entity
@Data
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String name_send;
    private String name_received;
    private Boolean status;
    private TimeZone time_send;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_chat", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<User> users = new HashSet<>();
}
