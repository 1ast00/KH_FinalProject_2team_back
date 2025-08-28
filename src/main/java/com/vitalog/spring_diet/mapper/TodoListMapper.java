package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.TodoListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TodoListMapper {
    List<TodoListDTO> selectTodoList(Map<String, Object> map);

    int insertTodo(TodoListDTO todo);

    int selectByTno(int mno);
  
    int updateTodoCheck(int mno, List<TodoListDTO> todos);

    int deleteTodo(int tno, int mno);

    int deleteDoneTodo(int mno, List<TodoListDTO> doneTodos);

    int updateTodo(int mno, int tno, String tcontent);
}
