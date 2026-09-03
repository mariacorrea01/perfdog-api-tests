package com.perfdog.automation.request;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RequestBuilder {

    public static Response getRequest(String baseUrl, String path) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        return requestSpecification.get(path);
    }

    public static Response postRequest(String baseUrl, String path, Object body) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        return requestSpecification.post(path);
    }

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