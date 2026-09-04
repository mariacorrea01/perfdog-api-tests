package com.perfdog.automation.test;

import com.perfdog.automation.config.TestRunner;
import com.perfdog.automation.model.Order;
import com.perfdog.automation.model.Pet;
import com.perfdog.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Funcionalidad 5: Crear una orden (compra) para una mascota.
 * Test independiente: obtiene un petId valido consultando el catalogo
 * disponible en el propio test, nunca usa un id hardcodeado.
 */
public class CreateOrderTests extends TestRunner {
    /**
     * Setup: consulta GET /pet/findByStatus?status=available y toma
     * el id de la primera mascota de la lista.
     * Test real: envia POST /store/order para esa mascota y valida:
     * - que la API responda 200 OK.
     * - que la orden creada tenga un id asignado.
     * - que el petId de la orden coincida con el solicitado.
     * - que el status de la orden sea "placed".
     */
    @Test(testName = "Validate order creation for an avaliable pet")
    public void CreateOrderTests() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("status", "avaliable");

        Response listResponse = RequestBuilder.getRequestWithParams(getBaseUrl(), "/pet/findByStatus", queryParams);
        assertEquals(listResponse.getStatusCode(), 200, "Setup failed: could not list avaliable pets.");

        List<Pet> avaliablePets = listResponse.jsonPath().getList(".", Pet.class);
        assertFalse(avaliablePets.isEmpty(),"Setup failed: no avaliable pets found to run the test");

        Long petId= avaliablePets.get(0).getId();

        Order newOrder= Order.builder()
                .petId(petId)
                .quantity(1)
                .shipDate(Instant.now().toString())
                .status("placed")
                .complete(false)
                .build();

        Response orderResponse= RequestBuilder.postRequest(getBaseUrl(),"/store/order",newOrder);
        Order createdOrder= orderResponse.as(Order.class);

        assertEquals(orderResponse.getStatusCode(),200,"The status code doesn´t match");
        assertNotNull(createdOrder.getId(),"The created order should have an id.");
        assertEquals(createdOrder.getPetId(),petId,"The order´s petId should match the requested pet.");
        assertEquals(createdOrder.getStatus(),"placed","The order status should be placed");


    }
    }

