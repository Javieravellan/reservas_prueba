package io.github.javieravellan.reservabutacas.infra.web.rest;

import io.github.javieravellan.reservabutacas.domain.BookingRecord;
import io.github.javieravellan.reservabutacas.infra.web.response.BookingCancelResponse;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("default")
class BookingRestControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        // Configuración inicial si es necesario

    }
    // Prueba para cancelar todas las reservas y habilitar las butacas reservadas.
    @Test
    void cancelBillboardMovieAndActivateSeats() {
        long billboardMovieId = 1; // Cambia esto por un ID válido de tu base de datos

        var headers = new HttpHeaders();
        var httpEntity = new HttpEntity<>(headers);

        var typeRef = new ParameterizedTypeReference<Map<String, String>>() {};
        var response = restTemplate.exchange("/api/v1/bookings/billboard_movies/" + billboardMovieId, HttpMethod.PUT,
                httpEntity, typeRef);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("message"));
        assertEquals("Todas las reservas de la función han sido canceladas y los asientos han sido activados", response.getBody().get("message"));
    }

    // Prueba una reserva y activar las butacas.
    @Test
    void cancelBookingAndActivateSeats() {
        long bookingId = 2; // Cambia esto por un ID válido de tu base de datos

        var headers = new HttpHeaders();
        var httpEntity = new HttpEntity<>(headers);

        var response = restTemplate.exchange("/api/v1/bookings/" + bookingId + "/cancel", HttpMethod.POST,
                httpEntity, BookingCancelResponse.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().result());
        assertEquals("Reserva cancelada con éxito. Todas las butacas han sido liberadas.",
                response.getBody().message());
    }

    // Prueba para obtener todas las reservas de terror por rango de fecha.
    @Test
    void getAllBookingsHorrorByPeriod() {
        String startDate = "2025-05-14";
        String endDate = "2025-05-14";

        var headers = new HttpHeaders();
        var httpEntity = new HttpEntity<>(headers);

        var typeRef = new ParameterizedTypeReference<List<BookingRecord>>() {};
        var response = restTemplate.exchange("/api/v1/bookings/genre/horror?startDate=" + startDate + "&endDate=" + endDate,
                HttpMethod.GET, httpEntity, typeRef);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
}