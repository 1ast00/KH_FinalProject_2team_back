package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.TodoListDTO;
import com.vitalog.spring_diet.mapper.TodoListMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TodoListService {
    private final TodoListMapper mapper;

    public TodoListService(TodoListMapper mapper) {
        this.mapper = mapper;
    }

    public List<TodoListDTO> selectTodoList(int mno, String date) {
        Map<String, Object> map = Map.of("mno", mno, "date", date);
        return mapper.selectTodoList(map);
    }

    public int insertTodo(TodoListDTO todo) {
        return mapper.insertTodo(todo);
    }
}