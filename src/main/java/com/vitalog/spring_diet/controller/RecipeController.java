package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.RecipeDTO;
import com.vitalog.spring_diet.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService service;

    @GetMapping("/list")
    public Map<String, Object> list(@RequestAttribute(required = false) Integer authenticatedUsermno) {
        System.out.println("Recipe list");
        Map<String, Object> map = new HashMap<>();

        if(authenticatedUsermno == null) {
            map.put("msg", "로그인 후 이용해 주세요.");
            return map;
        }

        // 목록 받음
        List<RecipeDTO> list = service.selectRecipe();

        if(!list.isEmpty())
            map.put("code", 0);
        else
            map.put("code", 1);

        map.put("recipeList", list);
        return map;
    }
}
