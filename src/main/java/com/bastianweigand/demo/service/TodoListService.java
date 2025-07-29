package com.bastianweigand.demo.service;

import com.bastianweigand.demo.dto.TodoDto;
import com.bastianweigand.demo.dto.TodoListDto;
import com.bastianweigand.demo.model.TodoList;
import com.bastianweigand.demo.model.User;
import com.bastianweigand.demo.repository.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {

    private final TodoListRepository todoListRepo;

    public TodoListService(TodoListRepository todoListRepo) {
        this.todoListRepo = todoListRepo;
    }

    public TodoList getTodoListById(Long id) {
        return todoListRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("TodoList not found"));
    }

    public TodoList createTodoList(User user, String title) {
        TodoList list = new TodoList();
        list.setTitle(title);
        list.setUser(user);

        return todoListRepo.save(list);
    }

    public TodoList updateTodoList(Long listId, String newTitle) {
        TodoList list = todoListRepo.findById(listId)
                .orElseThrow(() -> new RuntimeException("TodoList not found"));

        list.setTitle(newTitle);
        return todoListRepo.save(list);
    }

    public void deleteTodoList(Long listId) {
        if (!todoListRepo.existsById(listId)) {
            throw new RuntimeException("TodoList not found");
        }
        todoListRepo.deleteById(listId);
    }

    public List<TodoListDto> getTodoListsByUser(User user) {
        return user.getTodoLists().stream()
                .map(todoList -> new TodoListDto(todoList.getId(), todoList.getTitle()))
                .toList();
    }

    public List<TodoDto> getTodoByTodoList(TodoList todoList) {
        return todoList.getTodos().stream()
                .map(todo -> new TodoDto(
                        todo.getId(),
                        todo.getContent(),
                        todo.isDone(),
                        todo.getDueDate()
                ))
                .toList();
    }
}
