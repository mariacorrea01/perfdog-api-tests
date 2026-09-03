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

public class LoginTests extends TestRunner {
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
