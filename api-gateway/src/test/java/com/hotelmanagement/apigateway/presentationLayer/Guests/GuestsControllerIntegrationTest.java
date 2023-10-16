package com.hotelmanagement.apigateway.presentationLayer.Guests;

import com.hotelmanagement.apigateway.domainClientLayer.GuestServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@AutoConfigureWebTestClient
public class GuestsControllerIntegrationTest {





        @Autowired
        WebTestClient webTestClient;

        @MockBean
        GuestServiceClient guestServiceClient; // Replace @Autowired with @MockBean

    private final String BASE_URI_GUESTS = "/api/v1/guests";
    private final String VALID_GUEST_ID = "c3540a89-cb47-4c96-888e-ff96708db4d8";




        @Test
        public void whenGuestExist_thenReturnAllStaff() {
            // Arrange
            int expectedNumClients = 9;
            GuestResponseModel[] mockClients = new GuestResponseModel[expectedNumClients];
            when(guestServiceClient.getGuests()).thenReturn(mockClients);

            // Act & Assert
            webTestClient.get()
                    .uri(BASE_URI_GUESTS)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.length()").isEqualTo(expectedNumClients);
        }

        @Test
        public void whenGetGuestWithValidIdExists_thenReturnGuest() {
            // Arrange
            GuestResponseModel mockClient = new GuestResponseModel();
            mockClient.setGuestId(VALID_GUEST_ID);
            when(guestServiceClient.getGuestByGuestId(VALID_GUEST_ID)).thenReturn(mockClient);
            // Act & Assert
            webTestClient.get()
                    .uri(BASE_URI_GUESTS + "/" + VALID_GUEST_ID)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody()
                    .jsonPath("$.guestId").isEqualTo(VALID_GUEST_ID);
        }




}
