package com.perfdog.automation.test;

import com.perfdog.automation.config.TestRunner;
import com.perfdog.automation.model.ApiResponse;
import com.perfdog.automation.model.User;
import com.perfdog.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;
/**
 * Funcionalidad 1: Crear un usuario.
 * Test independiente: genera sus propios datos (username unico via UUID)
 * en cada ejecucion, sin depender de otro test ni de datos hardcodeados.
 */
public class CreateUserTests extends TestRunner {
    /**
     * Envia POST /user con un usuario generado aleatoriamente y valida:
     * - que la API responda 200 OK.
     * - que el body de respuesta traiga un id de usuario valido (mayor a 0).
     */
    @Test(testName = "Validate user creation")
    public void createUserTest(){
        String uniqueSuffix = UUID.randomUUID().toString().substring(0,8);

        User newUser = User.builder()
                .username("perfdog_"+uniqueSuffix)
                .firstName("Perf")
                .lastName("Dog")
                .email("perfdog_"+uniqueSuffix+"@example.com")
                .password("P@ssw0rd_"+uniqueSuffix)
                .phone("+1-555-0100")
                .userStatus(1)
                .build();
        Response response = RequestBuilder.postRequest(getBaseUrl(),"/user",newUser);
        ApiResponse apiResponse = response.as(ApiResponse.class);

        assertEquals(response.getStatusCode(),200,"he status code doesn't match.");
        assertNotNull(apiResponse.getMessage(), "The response message (new user id) should not be null.");
        assertTrue(Long.parseLong(apiResponse.getMessage())>0,"The returned id should be greater than 0.");
    }
}
