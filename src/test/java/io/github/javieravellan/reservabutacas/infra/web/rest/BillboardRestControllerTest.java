package io.github.javieravellan.reservabutacas.infra.web.rest;

import io.github.javieravellan.reservabutacas.domain.SeatsByRoomAtBillboard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("default")
class BillboardRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void getAllSeatsPeerRoomByBillboardId() {
        long billboardId = 1;

        var httpEntity = new HttpEntity<>(new HttpHeaders());

        var typeRef = new ParameterizedTypeReference<List<SeatsByRoomAtBillboard>>() {};
        var response = testRestTemplate.exchange("/api/v1/billboards/" + billboardId + "/seats",
                HttpMethod.GET, httpEntity, typeRef);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
}