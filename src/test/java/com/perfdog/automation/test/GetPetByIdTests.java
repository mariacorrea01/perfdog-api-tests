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
 * Funcionalidad 4: Consultar los datos de una mascota en especifico.
 * Test independiente: obtiene un id valido consultando el catalogo
 * disponible en el propio test, nunca usa un id hardcodeado.
 */
public class GetPetByIdTests extends TestRunner {

    /**
     * Setup: consulta GET /pet/findByStatus?status=available y toma
     * el id de la primera mascota de la lista.
     * Test real: consulta GET /pet/{petId} con ese id y valida:
     * - que la API responda 200 OK.
     * - que el id devuelto coincida con el solicitado.
     * - que la mascota tenga un nombre asignado.
     */
    @Test(testName = "Validate retrieval of a specific pet by id")
    public void getPetByIdTest(){
        Map<String,String> queryParams= new HashMap<>();
        queryParams.put("status","avaliable");

        Response listResponse = RequestBuilder.getRequestWithParams(getBaseUrl(),"/pet/findByStatus",queryParams);
        assertEquals(listResponse.getStatusCode(),200,"Setup failed: could not list avaliable pets");

        List<Pet> avaliablePets = listResponse.jsonPath().getList(".", Pet.class);
        assertFalse(avaliablePets.isEmpty(),"Setup failed: no avaliable pets found to run the test");

        Long petId= avaliablePets.get(0).getId();
        Response pertResponse = RequestBuilder.getRequest(getBaseUrl(),"/pet/"+petId);
        Pet pet= pertResponse.as(Pet.class);

        assertEquals(pertResponse.getStatusCode(),200,"The status code  doesn´t match");
        assertEquals(pet.getId(),petId,"The returned pet id should match the requested id");
        assertNotNull(pet.getName(),"The pet name should not be null");
    }
}
