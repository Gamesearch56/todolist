package com.bastianweigand.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoListDto {
    private Long id;
    private String title;
    private List<TodoDto> todos;

    public TodoListDto(Long id, String title) {

    }
}
