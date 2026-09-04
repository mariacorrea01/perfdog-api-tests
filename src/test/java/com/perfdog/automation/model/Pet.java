package com.perfdog.automation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * POJO que representa una mascota, segun el schema /pet de la
 * Swagger Petstore API. Se usa para deserializar tanto un unico
 * pet (GET /pet/{id}) como listas de pets (GET /pet/findByStatus).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private  String status;
}
