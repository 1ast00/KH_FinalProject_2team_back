package com.vitalog.spring_diet.etc;

import com.vitalog.spring_diet.service.ManufacturerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(scanBasePackages = "com.vitalog.spring_diet",
exclude = { SecurityAutoConfiguration .class })
@Profile("cli")
public class ManufacturerInsert {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ManufacturerInsert.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 웹 서버 실행 안 함
        app.setAdditionalProfiles("cli"); // CLI 프로파일 활성화
        app.run(args);
    }

    @Bean
    public CommandLineRunner run(ManufacturerService manufacturerService) {
        return args -> {
            manufacturerService.storeManufacturer();
        };
    }
}
