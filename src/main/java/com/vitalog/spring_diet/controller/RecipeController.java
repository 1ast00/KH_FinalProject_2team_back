package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.RecipeDTO;
import com.vitalog.spring_diet.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    @GetMapping("/naverSearch")
    public String naverSearch(@RequestParam String search) throws Exception {
        System.out.println("naverSearch");
        String clientId = "YKM2X10KyN7nJd3_WXCz";
        String clientSecret = "t32wkSPRTf";
        String text = URLEncoder.encode(search, "UTF-8");
        String apiURL = "https://openapi.naver.com/v1/search/webkr.json?query=" + text;

        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", clientId);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

        int responseCode = con.getResponseCode();
        BufferedReader br = responseCode == 200
                ? new BufferedReader(new InputStreamReader(con.getInputStream()))
                : new BufferedReader(new InputStreamReader(con.getErrorStream()));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        return response.toString();
    }
}
