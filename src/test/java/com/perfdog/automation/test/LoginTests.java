package com.perfdog.automation.test;

import com.perfdog.automation.config.TestRunner;
import com.perfdog.automation.model.ApiResponse;
import com.perfdog.automation.model.User;
import com.perfdog.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Funcionalidad 2: Hacer login con un usuario recien creado.
 * Test independiente: crea su propio usuario dentro del mismo metodo,
 * no depende de que CreateUserTests haya corrido antes.
 */
public class LoginTests extends TestRunner {
    /**
     * Setup: crea un usuario nuevo via POST /user.
     * Test real: hace login con esas mismas credenciales via
     * GET /user/login y valida:
     * - que la API responda 200 OK.
     * - que el mensaje de respuesta confirme una sesion iniciada.
     */
    @Test(testName = "Validate login with a newly created user")
    public void loginTest(){
        String id = UUID.randomUUID().toString().substring(0,8);
        String username ="perfdog_"+id;
        String password = "P@ssw0rd_"+id;

        User newUser = User.builder()
                .username(username)
                .firstName("Perf")
                .lastName("Dog")
                .email(username+"@examaple.com")
                .password(password)
                .phone("+1-555-0100")
                .userStatus(1)
                .build();

        Response createUserResponse= RequestBuilder.postRequest(getBaseUrl(),"/user",newUser);
        assertEquals(createUserResponse.getStatusCode(),200,"Setup failed: could not create the test user.");

        Map<String,String> loginParams = new HashMap<>();
        loginParams.put("username",username);
        loginParams.put("password",password);

        Response loginResponse = RequestBuilder.getRequestWithParams(getBaseUrl(),"/user/login",loginParams);
        ApiResponse apiResponse = loginResponse.as(ApiResponse.class);

        assertEquals(loginResponse.getStatusCode(),200,"The status code doesn´t match ");
        assertTrue(apiResponse.getMessage().contains("logged in user session"),"The response message should confirm a logged in session.");


    }

}
