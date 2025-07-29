package com.bastianweigand.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private boolean done;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList todoList;

    public Todo() {
    }

    public Todo(Long id, String content, boolean done, Date dueDate) {
        this.id = id;
        this.content = content;
        this.done = done;
        this.dueDate = dueDate;
    }
}
