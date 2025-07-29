package com.bastianweigand.demo.controller;

import com.bastianweigand.demo.model.Todo;
import com.bastianweigand.demo.service.TodoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<?> createTodo(
            @RequestParam Long listId,
            @RequestParam String content,
            @RequestParam(defaultValue = "false") boolean done,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dueDate
    ) {
        try {
            Todo todo = todoService.createTodo(listId, content, done, dueDate);
            return ResponseEntity.ok(todo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(
            @PathVariable Long id,
            @RequestParam String content,
            @RequestParam(defaultValue = "false") boolean done,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dueDate
    ) {
        try {
            Todo todo = todoService.updateTodo(id, content, done, dueDate);
            return ResponseEntity.ok(todo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list/{listId}")
    public ResponseEntity<List<Todo>> getTodosByList(@PathVariable Long listId) {
        return ResponseEntity.ok(todoService.getTodosByList(listId));
    }
}

