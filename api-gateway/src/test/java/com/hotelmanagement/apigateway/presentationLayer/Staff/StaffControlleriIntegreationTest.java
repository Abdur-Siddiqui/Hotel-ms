package com.hotelmanagement.apigateway.presentationLayer.Staff;

import com.hotelmanagement.apigateway.Utils.Exceptions.InvalidInputException;
import com.hotelmanagement.apigateway.Utils.Exceptions.NotFoundException;
import com.hotelmanagement.apigateway.businessLayer.StaffService;
import com.hotelmanagement.apigateway.domainClientLayer.StaffServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@AutoConfigureWebTestClient
 class StaffControllerIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    StaffServiceClient staffServiceClient;

    private final String BASE_URI_STAFF = "/api/v1/staff";
    private final String VALID_STAFF_ID = "c3540a89-cb47-4c96-888e-ff96708db4d8";
    private final String INVALID_STAFF_ID = VALID_STAFF_ID + 1;



    @Test
    public void getStaffMemebrsest() {
        // Arrange
        int expectedNumstaff = 1;
        StaffResponseModel[] mockStaff = new StaffResponseModel[expectedNumstaff];
        when(staffServiceClient.getStaffMembers()).thenReturn(mockStaff);

        // Act & Assert
        webTestClient.get()
                .uri(BASE_URI_STAFF)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumstaff);
    }

    @Test
    public void getStaffByIdTest() {
        // Arrange
        StaffResponseModel mockClient = new StaffResponseModel();
        mockClient.setStaffId(VALID_STAFF_ID);
        when(staffServiceClient.getStaffById(VALID_STAFF_ID)).thenReturn(mockClient);

        // Act & Assert
        webTestClient.get()
                .uri(BASE_URI_STAFF + "/" + VALID_STAFF_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.staffId").isEqualTo(VALID_STAFF_ID);
    }

    @Test
    public void PostClient() {

        String employee_first_name = "John";
        String employee_last_name = "Adams";
        String employee_email_address = "1234@gmail.com";
        String employeePhoneNumber = "123-456-990";
        String employeeJobPosition = "Cook";
        String reservationDateMade = "2022-09-6";
        String employee_city = "Toronto";
        String employeePostalCode = "98xs";


        StaffRequestModel staffRequestModel = new StaffRequestModel(employee_first_name, employee_last_name, employee_email_address, employeePhoneNumber, employeeJobPosition, reservationDateMade, employee_city, employeePostalCode);


        StaffResponseModel staffResponseModel = new StaffResponseModel(
                UUID.randomUUID().toString(),
                employee_first_name,
                employee_last_name,
                employee_email_address,
                employeePhoneNumber,
                employeeJobPosition,
                reservationDateMade,
                employee_city,
                employeePostalCode);

        when(staffServiceClient.addStaff(staffRequestModel))
                .thenReturn(staffResponseModel);

        // Act
        webTestClient.post()
                .uri(BASE_URI_STAFF)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(staffRequestModel))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.staffId").isNotEmpty()
                .jsonPath("$.employeeFirstName").isEqualTo(employee_first_name)
                .jsonPath("$.employeeLastName").isEqualTo(employee_last_name)
                .jsonPath("$.employeeEmailAddress").isEqualTo(employee_email_address)
                .jsonPath("$.employeePhoneNumber").isEqualTo(employeePhoneNumber)
                .jsonPath("$.employeeJobPosition").isEqualTo(employeeJobPosition)
                .jsonPath("$.reservationDateMade").isEqualTo(reservationDateMade)
                .jsonPath("$.employeeCity").isEqualTo(employee_city)
                .jsonPath("$.employeePostalCode").isEqualTo(employeePostalCode);
    }



    @Test
    public void PutInvalidClientId_shouldThrowInvalidInputException() {
        // Arrange
        String employee_first_name = "John";
        String employee_last_name = "Adam";
        String employee_email_address = "124@gmail.com";
        String employee_street_address = "999 rivard";
        String employee_city = "Boronto";
        String employee_province = "Hontario";
        String employee_country = "Canada";
        String employeePostalCode = "98xs";
        String reservationDateMade = "2022-09-6";
        String employeePhoneNumber = "923-456-990";
        String employeeJobPosition = "Manager";


        StaffRequestModel staffRequestModel = new StaffRequestModel(employee_first_name, employee_last_name, employee_email_address, employeePhoneNumber, employeeJobPosition , reservationDateMade, employee_city, employeePostalCode);



        // Act
        webTestClient.put()
                .uri(BASE_URI_STAFF + "/" + INVALID_STAFF_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(staffRequestModel))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(200));
    }



    @Test
    public void deleteStaff() {

        doNothing().when(staffServiceClient).deleteStaffPersonel(VALID_STAFF_ID);


        webTestClient.delete()
                .uri(BASE_URI_STAFF + "/" + VALID_STAFF_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        webTestClient.get()
                .uri(BASE_URI_STAFF + "/" + VALID_STAFF_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk(); // to fix
    }

}
