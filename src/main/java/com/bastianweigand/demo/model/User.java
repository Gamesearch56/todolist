package com.bastianweigand.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TodoList> todoLists = new HashSet<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {
    }

    public User(Long id, String email, String username, String password, Set<TodoList> todoLists) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.todoLists = todoLists;
    }
}
