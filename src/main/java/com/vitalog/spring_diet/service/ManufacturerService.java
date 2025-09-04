package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.ManufacturerDTO;
import com.vitalog.spring_diet.mapper.ManufacturerMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManufacturerService {
    private ManufacturerMapper manufacturerMapper;

    public ManufacturerService (ManufacturerMapper manufacturerMapper){
        this.manufacturerMapper = manufacturerMapper;
    }

    //1. 데이터 수동 삽입(DBEAVER 써도 됨)
    public void storeManufacturer() throws IOException {

        // 덜 단단한 코딩
        String basePath= "C:\\Users\\user1\\Desktop\\Manufacturer Image\\";

        String [][] manufacturers = {
                {"㈜동원F&B", "dongone.jpg"},
                {"(유)푸드원", "FoodOne.PNG"},
                {"행복담기㈜", "HangBokDamgi.png"},
                {"한성기업㈜", "Hansung.png"},
                {"㈜프로엠", "prom.jpg"},
                {"풀무원건강생활㈜", "pulmuone.png"},
        };

        List<ManufacturerDTO> mList = new ArrayList<>();

        for (int i = 0; i <manufacturers.length; i++){
            String[] data = manufacturers[i];
            ManufacturerDTO dto = new ManufacturerDTO();
            dto.setManufacture(data[0]);
            dto.setImage(Files.readAllBytes(Path.of(basePath + data[1])));
            mList.add(dto);
        }

        for (ManufacturerDTO manufacturer: mList){
            manufacturerMapper.insertManufacturer(manufacturer);
        }

//        // 단단한 코딩
//        List<ManufacturerDTO> mList = new ArrayList<>();
//
//        ManufacturerDTO mDTO1 = new ManufacturerDTO();
//        mDTO1.setManufacture("㈜동원F&B");
//        mDTO1.setImage(Files.readAllBytes(Path.of("C:\\Users\\user1\\Desktop\\Manufacturer Image\\dongone.jpg")));
//
//        ManufacturerDTO mDTO2 = new ManufacturerDTO();
//        mDTO2.setManufacture("(유)푸드원");
//        mDTO2.setImage(Files.readAllBytes(Path.of("C:\\Users\\user1\\Desktop\\Manufacturer Image\\FoodOne.PNG")));
//
//        ManufacturerDTO mDTO3 = new ManufacturerDTO();
//        mDTO3.setManufacture("행복담기㈜");
//        mDTO3.setImage(Files.readAllBytes(Path.of("C:\\Users\\user1\\Desktop\\Manufacturer Image\\HangBokDamgi.png")));
//
//        ManufacturerDTO mDTO4 = new ManufacturerDTO();
//        mDTO4.setManufacture("한성기업㈜");
//        mDTO4.setImage(Files.readAllBytes(Path.of("C:\\Users\\user1\\Desktop\\Manufacturer Image\\Hansung.png")));
//
//        ManufacturerDTO mDTO5 = new ManufacturerDTO();
//        mDTO5.setManufacture("㈜프로엠");
//        mDTO5.setImage(Files.readAllBytes(Path.of("C:\\Users\\user1\\Desktop\\Manufacturer Image\\prom.jpg")));
//
//        ManufacturerDTO mDTO6 = new ManufacturerDTO();
//        mDTO6.setManufacture("풀무원건강생활㈜");
//        mDTO6.setImage(Files.readAllBytes(Path.of("C:\\Users\\user1\\Desktop\\Manufacturer Image\\pulmuone.png")));
//
//        mList.add(mDTO1);
//        mList.add(mDTO2);
//        mList.add(mDTO3);
//        mList.add(mDTO4);
//        mList.add(mDTO5);
//        mList.add(mDTO6);
//
//        for (ManufacturerDTO manufacturer : mList){
//            manufacturerMapper.insertManufacturer(manufacturer);
//        }

    }
}