package com.bastianweigand.demo.controller;

import com.bastianweigand.demo.dto.TodoDto;
import com.bastianweigand.demo.model.Todo;
import com.bastianweigand.demo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/{listId}")
    public ResponseEntity<?> createTodo(
            @PathVariable Long listId,
            @RequestBody TodoDto todoDto
    ) {
        try {
            Todo todo = todoService.createTodo(listId, todoDto.getContent(), todoDto.isDone(), todoDto.getDueDate());
            return ResponseEntity.ok(todo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoDto todoDto
    ) {
        try {
            Todo todo = todoService.updateTodo(id, todoDto.getContent(), todoDto.isDone(), todoDto.getDueDate());
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

