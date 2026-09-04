package com.perfdog.automation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Envelope generico que devuelve la Petstore API en operaciones de
 * escritura (POST /user, GET /user/login, GET /user/logout):
 * {"code": 200, "type": "unknown", "message": "..."}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ApiResponse {
    private Integer code;
    private String type;
    private String message;
}
