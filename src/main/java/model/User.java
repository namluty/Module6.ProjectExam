package model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String re_password;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String gender;
    private Boolean is_active;
    private LocalDate created_date;
    private LocalDate modified_date;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String re_password, String email, String phone, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.re_password = re_password;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

}
