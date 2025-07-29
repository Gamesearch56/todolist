package com.bastianweigand.demo.service;

import com.bastianweigand.demo.model.Todo;
import com.bastianweigand.demo.model.TodoList;
import com.bastianweigand.demo.repository.TodoListRepository;
import com.bastianweigand.demo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepo;

    private final TodoListRepository listRepo;

    public TodoService(TodoRepository todoRepo, TodoListRepository listRepo) {
        this.todoRepo = todoRepo;
        this.listRepo = listRepo;
    }

    public Todo createTodo(Long listId, String content, boolean done, Date dueDate) {
        TodoList list = listRepo.findById(listId)
                .orElseThrow(() -> new RuntimeException("TodoList not found"));

        // Enforce max 5 due-date todos
        long dueDateCount = list.getTodos().stream()
                .filter(t -> t.getDueDate() != null)
                .count();

        if (dueDate != null && dueDateCount >= 5) {
            throw new RuntimeException("Maximum of 5 todos with due dates allowed in this list.");
        }

        Todo todo = new Todo();
        todo.setContent(content);
        todo.setDone(done);
        todo.setDueDate(dueDate);
        todo.setTodoList(list);

        return todoRepo.save(todo);
    }

    public Todo updateTodo(Long id, String content, boolean done, Date dueDate) {
        Todo todo = todoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (dueDate != null && (todo.getDueDate() == null || !todo.getDueDate().equals(dueDate))) {
            long count = todo.getTodoList().getTodos().stream()
                    .filter(t -> t.getDueDate() != null && !t.getId().equals(todo.getId()))
                    .count();
            if (count >= 5) {
                throw new RuntimeException("Cannot assign due date — list already has 5 due-dated todos.");
            }
        }

        todo.setContent(content);
        todo.setDone(done);
        todo.setDueDate(dueDate);
        return todoRepo.save(todo);
    }

    public void deleteTodo(Long id) {
        if (!todoRepo.existsById(id)) {
            throw new RuntimeException("Todo not found");
        }
        todoRepo.deleteById(id);
    }

    public List<Todo> getTodosByList(Long listId) {
        return todoRepo.findAllByTodoListId(listId);
    }
}