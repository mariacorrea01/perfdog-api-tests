package com.perfdog.automation.test;

import com.perfdog.automation.config.TestRunner;
import com.perfdog.automation.model.Pet;
import com.perfdog.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;
/**
 * Funcionalidad 3: Listar todas las mascotas con status "disponible".
 * Test independiente: no depende de datos creados por otros tests,
 * consulta directamente el catalogo publico de mascotas.
 */
public class ListAvailablePetsTests extends TestRunner {
    /**
     * Envia GET /pet/findByStatus?status=available y valida:
     * - que la API responda 200 OK.
     * - que la lista devuelta no este vacia.
     * - que TODAS las mascotas de la lista tengan status "available"
     *   (no solo la primera).
     */
    @Test(testName = "Validate listing of avaliable pets")
    public void listAvaliablePestTests(){
        Map<String,String> queryParams = new HashMap<>();
        queryParams.put("status","avaliable");

        Response response = RequestBuilder.getRequestWithParams(getBaseUrl(),"/pet/findByStatus",queryParams);
        List<Pet> pets = response.jsonPath().getList(".", Pet.class);

        assertEquals(response.getStatusCode(),200,"The status code doesn´t match.");
        assertFalse(pets.isEmpty(),"The list of avaliable pets should not be empty.");

        boolean allAvaliable = true;
        for (Pet pet :pets){
            if(!"avaliable".equalsIgnoreCase(pet.getStatus())){
                allAvaliable=false;
                break;
            }
        }
        assertTrue(allAvaliable,"All returned pets should have status avaliable");
    }

}
