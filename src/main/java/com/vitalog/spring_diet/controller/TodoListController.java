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
        } else {
            map.put("code", 1);
        }
        return map;
    }

    /**
     * 체크 토글 변경 사항 저장
     * @param request
     * @return
     */
    @PatchMapping("/updateCheck")
    public Map<String, Object> updateCheck(@RequestAttribute int authenticatedUsermno, @RequestBody Map<String, List<TodoListDTO>> request) {
        List<TodoListDTO> todos = request.get("todos");
        System.out.println("updateCheck: " + todos);

        Map<String, Object> map = new HashMap<>();

        int count = service.updateTodoCheck(authenticatedUsermno, todos);
        System.out.println(count);

        if (count != 0) {
            map.put("code", 0);
            map.put("msg", "변경 사항이 저장되었습니다.");
        } else {
            map.put("code", 1);
        }
        return map;
    }

    /**
     * 삭제
     * @param authenticatedUsermno
     * @param tno
     * @return
     */
    @DeleteMapping("/{tno}")
    public Map<String, Object> deleteTodo(@RequestAttribute int authenticatedUsermno, @PathVariable int tno) {
        System.out.println("deleteTodo: " + tno);
        Map<String, Object> map = new HashMap<>();

        int count = service.deleteTodo(tno, authenticatedUsermno);

        if (count != 0) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }
        return map;
    }

    /**
     * 완료 항목 일괄 삭제
     * @param doneTodos
     * @return
     */
    @DeleteMapping("/done")
    public Map<String, Object> deleteDoneTodo(@RequestAttribute int authenticatedUsermno, @RequestBody List<TodoListDTO> doneTodos) {
        System.out.println("deleteDoneTodo: " + doneTodos);
        Map<String, Object> map = new HashMap<>();

        int count = service.deleteDoneTodo(authenticatedUsermno, doneTodos);

        if (count != 0) {
            map.put("code", 0);
            map.put("msg", "일괄 삭제가 완료되었습니다.");
        } else {
            map.put("code", 1);
            map.put("msg", "일괄 삭제를 실패했습니다.");
        }
        return map;
    }

    /**
     * 수정
     * @param authenticatedUsermno
     * @param tno
     * @param body
     * @return
     */
    @PatchMapping("/updateTodo/{tno}")
    public Map<String, Object> updateTodo(@RequestAttribute int authenticatedUsermno, @PathVariable int tno, @RequestBody Map<String, String> body) {
        System.out.println("updateTodo: " + body);

        String tcontent = body.get("tcontent");
        Map<String, Object> map = new HashMap<>();

        int count = service.updateTodo(authenticatedUsermno, tno, tcontent);

        if (count != 0) {
            map.put("code", 0);
        } else {
            map.put("code", 1);
        }
        return map;
    }
}
