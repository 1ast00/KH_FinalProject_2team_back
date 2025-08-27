package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.TodoListDTO;
import com.vitalog.spring_diet.service.TodoListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService service;

    /**
     * 목록 조회
     * @param authenticatedUsermno
     * @param date
     * @return
     */
    @GetMapping("/list")
    public Map<String, Object> list(@RequestAttribute int authenticatedUsermno, @RequestParam String date) {
        System.out.println("TodoList list");

        // 목록 받음
        List<TodoListDTO> list = service.selectTodoList(authenticatedUsermno, date);
        Map<String, Object> map = new HashMap<>();
        map.put("todoList", list);
        return map;
    }

    /**
     * 추가
     * @param authenticatedUsermno
     * @param todo
     * @return
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestAttribute int authenticatedUsermno, @Valid @RequestBody TodoListDTO todo) {
        Map<String, Object> map = new HashMap<>();

        todo.setMno(authenticatedUsermno);
        System.out.println("TodoList add: " + todo.getMno());

        int count = service.insertTodo(todo);
        int tno = service.selectByTno(authenticatedUsermno);

        // tno, tdate, tcheck, tcontent
        if (count != 0) {
            map.put("code", 0);
            map.put("tno", tno);
            map.put("tcontent", todo.getTcontent());
            map.put("tcheck", todo.getTcheck());
            map.put("tdate", todo.getTdate());
            map.put("msg", "오늘 할 일 작성이 완료되었습니다.");
        } else {
            map.put("code", 1);
            map.put("msg", "작성에 실패하였습니다.");
        }
        return map;
    }


    @PatchMapping("/updateCheck")
    public Map<String, Object> updateCheck(@RequestBody Map<String, List<TodoListDTO>> request) {
        List<TodoListDTO> todos = request.get("todos");
        System.out.println("updateCheck: " + todos);

        Map<String, Object> map = new HashMap<>();

        int count = service.updateTodoCheck(todos);

        if (count != 0) {
            map.put("code", 0);
            map.put("msg", "변경 사항이 저장되었습니다.");
        } else {
            map.put("code", 1);
            map.put("msg", "변경 사항 반영을 실패하였습니다.");
        }
        return map;
    }
}
