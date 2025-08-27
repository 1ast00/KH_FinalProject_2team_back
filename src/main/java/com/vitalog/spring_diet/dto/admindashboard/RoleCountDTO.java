// src/main/java/com/vitalog/spring_diet/dto/admindashboard/RoleCountDTO.java
package com.vitalog.spring_diet.dto.admindashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class RoleCountDTO {
    private String role;  // 예: "ROLE_USER", "ROLE_ADMIN"
    private int count;
}
