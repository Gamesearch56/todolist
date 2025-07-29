package com.bastianweigand.demo.controller;

import com.bastianweigand.demo.dto.TodoListDto;
import com.bastianweigand.demo.model.TodoList;
import com.bastianweigand.demo.model.User;
import com.bastianweigand.demo.service.TodoListService;
import com.bastianweigand.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todolists")
public class TodoListController {

    private final TodoListService todoListService;

    private final UserService userService;

    public TodoListController(TodoListService todoListService, UserService userService) {
        this.todoListService = todoListService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<TodoList> createTodoList(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TodoListDto todoListDto) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(todoListService.createTodoList(user, todoListDto.getTitle()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoList> updateTodoList(@PathVariable Long id, @RequestBody TodoListDto todoListDto) {
        return ResponseEntity.ok(todoListService.updateTodoList(id, todoListDto.getTitle()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoList(@PathVariable Long id) {
        todoListService.deleteTodoList(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<TodoListDto>> getMyTodoLists(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);

        List<TodoListDto> todoLists = todoListService.getTodoListsByUser(user);
        return ResponseEntity.ok(todoLists);
    }
}
