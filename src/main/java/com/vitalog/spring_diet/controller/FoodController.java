package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.FoodNutritionDTO;
import com.vitalog.spring_diet.dto.FoodNutritionResponseDTO;
import com.vitalog.spring_diet.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/search")
    //return type
    // 1. Map: 만들기 쉬움
    // 2. ResponseEntity: 범용성 높음. 오랜기간 장기 유지보수에 유리
    public ResponseEntity<FoodNutritionResponseDTO<List<FoodNutritionDTO>>> getFoodApiResult(@RequestParam String searchTxt){

        Map<String,Object> map = new HashMap<>();

        System.out.println(searchTxt);

        //데이터를 Api와 DB에서 받는 메소드
        //DB의 데이터가 api의 데이터에 대해 항상 우선순위를 가짐

        //1. 우선 DB에 데이터가 있는지 확인(미구현)
        //2. DB의 데이터를 출력(미구현)

        //3. 없으면 api 호출
        List<FoodNutritionDTO> result = foodService.foodApiSearch(searchTxt);
        System.out.println("result in getFoodApiResult: "+ result);

        // 4. 호출한 데이터를 앞단으로 보냄
        FoodNutritionResponseDTO<List<FoodNutritionDTO>> responseDTO;

        if(result.isEmpty()){
            responseDTO = new FoodNutritionResponseDTO<>(204, "검색결과 없음", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.NO_CONTENT);
        } else {
            responseDTO = new FoodNutritionResponseDTO<>(200,"검색결과 존재",result);
            return ResponseEntity.ok(responseDTO);
        }
    }
}
