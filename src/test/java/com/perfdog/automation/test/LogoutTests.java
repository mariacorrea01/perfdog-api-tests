package com.perfdog.automation.test;

import com.perfdog.automation.config.TestRunner;
import com.perfdog.automation.model.ApiResponse;
import com.perfdog.automation.model.User;
import com.perfdog.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
/**
 * Funcionalidad 6: Hacer el logout a la aplicacion.
 * Test independiente: crea su propio usuario y hace login antes de
 * probar el logout, sin depender de otros tests.
 */
public class LogoutTests extends TestRunner {
    /**
     * Setup: crea un usuario y hace login (GET /user/login).
     * Test real: hace logout via GET /user/logout y valida:
     * - que la API responda 200 OK.
     * - que el mensaje de respuesta confirme el cierre de sesion ("ok").
     */
    @Test(testName = "Validate logout after a valid login")
    public void logoutTest(){
        String id = UUID.randomUUID().toString().substring(0,8);
        String username = "perfdog_"+id;
        String password="P@ssword_"+id;

        User newUser = User.builder()
                .username(username)
                .firstName("Perf")
                .lastName("Dog")
                .email(username+"@example.com")
                .password(password)
                .phone("1-555-0100")
                .userStatus(1)
                .build();
        Response createUserResponse = RequestBuilder.postRequest(getBaseUrl(),"/user",newUser);
        assertEquals(createUserResponse.getStatusCode(),200,"Setup failed:could not create the test user");

        Map<String,String> loginParams = new HashMap<>();
        loginParams.put("username",username);
        loginParams.put("password",password);

        Response loginResponse= RequestBuilder.getRequestWithParams(getBaseUrl(),"/user/login",loginParams);
        assertEquals(loginResponse.getStatusCode(),200,"Setup failed: could not log in the test user");

        Response logoutResponse = RequestBuilder.getRequest(getBaseUrl(),"/user/logout");
        ApiResponse apiResponse = logoutResponse.as(ApiResponse.class);

        assertEquals(logoutResponse.getStatusCode(),200,"The status code doesn´t match");
        assertTrue(apiResponse.getMessage().equalsIgnoreCase("ok"),
                "The response message should confirm a successful logout");
    }
}
