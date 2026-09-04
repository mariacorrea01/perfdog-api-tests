package com.perfdog.automation.request;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * Punto unico de construccion y ejecucion de requests HTTP con Rest Assured.
 * Todos los metodos son estaticos y devuelven la Response cruda: las
 * aserciones se dejan siempre a cargo de los tests, nunca de esta clase.
 */
public class RequestBuilder {

    /**
     * Ejecuta un GET simple, sin body ni query params.
     * Usado por endpoints como /user/logout.
     *
     * @param baseUrl URL base de la API (ej. https://petstore.swagger.io/v2)
     * @param path    path relativo del endpoint (ej. /user/logout)
     */
    public static Response getRequest(String baseUrl, String path) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        return requestSpecification.get(path);
    }
    /**
     * Ejecuta un POST enviando un objeto Java como body JSON.
     * El objeto se serializa automaticamente via Jackson (POJO -> JSON).
     * Usado por endpoints como /user (crear usuario) y /store/order (crear orden).
     *
     * @param baseUrl URL base de la API
     * @param path    path relativo del endpoint
     * @param body    objeto Java a enviar como body (ej. User, Order)
     */
    public static Response postRequest(String baseUrl, String path, Object body) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        return requestSpecification.post(path);
    }

    /**
     * Ejecuta un GET con query params en la URL.
     * Usado por endpoints como /user/login (username, password)
     * y /pet/findByStatus (status).
     *
     * @param baseUrl     URL base de la API
     * @param path        path relativo del endpoint
     * @param queryParams parametros a enviar como ?clave=valor
     */
    public static Response getRequestWithParams(String baseUrl, String path, Map<String,String> queryParams){
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .queryParams(queryParams)
                .filter(new ResponseLoggingFilter())
                .filter(new ResponseLoggingFilter());

        return requestSpecification.get(path);
    }
}