package com.hotelmanagement.staffservice.presentationlayer;

import com.hotelmanagement.staffservice.datalayer.StaffPersonal;
import com.hotelmanagement.staffservice.datalayer.StaffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment= RANDOM_PORT)
@Sql({"/data-mysql.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

class StaffControllerIntegreationTest {


    private final String BASE_URI_STAFF = "/api/v1/staff";
    private final String VALID_STAFF_ID = "c3540a89-cb47-4c96-888e-ff96708db4d8";

    private final String VALID_PHONE_NUMBER ="438-776-8989";

    private final String DUPLICATE_PHONE_NUMBER = VALID_PHONE_NUMBER;
    private final String INVALID_STAFF_ID = VALID_STAFF_ID + 1;
    private final String VALID_JOB_POSITION = "Receptionist";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    StaffRepository staffRepository;


    // getting all the staffs
    @Test
    public void whenStaffExist_thenReturnAllStaff() {
        // arrange
        Integer expectedNumsStaff = 1;

        // act
        webTestClient.get()
                .uri(BASE_URI_STAFF)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumsStaff);

    }

    // getting staffs by id
    @Test
    public void whenGetStaffWithValidIdExists_thenReturnStaff() {
        webTestClient.get().uri(BASE_URI_STAFF + "/" + VALID_STAFF_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.staffId").isEqualTo(VALID_STAFF_ID)
                .jsonPath("$.employeeJobPosition").isEqualTo(VALID_JOB_POSITION);
    }

/*
    // creating a post
    @Test
    public void whenCreateStaffWithValidValues_thenReturnNewStaff() {

        //arrange
        String employee_first_name = "John";
        String employee_last_name = "Adams";
        String employee_email_address = "1234@gmail.com";
        String employeePhoneNumber = "123-456-990";
        String employeeJobPosition = "Cook";
        String reservationDateMade = "2022-09-6";
        String employee_city = "Toronto";
        String employeePostalCode = "98xs";



        StaffRequestModel staffRequestModel = new StaffRequestModel(employee_first_name, employee_last_name, employee_email_address, employeePhoneNumber, employeeJobPosition, reservationDateMade, employee_city, employeePostalCode);

        //act and assert
        webTestClient.post()
                .uri(BASE_URI_STAFF)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(staffRequestModel)
                .accept(MediaType.APPLICATION_JSON)
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
*/

    @Test
    public void whenUpdateStaffWithValidValues_thenReturnUpdatedStaff() {

        //arrange
        //arrange
        String employee_first_name = "John";
        String employee_last_name = "Adams";
        String employee_email_address = "1234@gmail.com";
        String employeePhoneNumber = "123-456-990";
        String employeeJobPosition = "Cook";
        String reservationDateMade = "2022-09-6";
        String employee_city = "Toronto";
        String employeePostalCode = "98xs";



        StaffRequestModel staffRequestModel = new StaffRequestModel(employee_first_name, employee_last_name, employee_email_address, employeePhoneNumber, employeeJobPosition,reservationDateMade, employee_city,  employeePostalCode);

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_STAFF + "/" + VALID_STAFF_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(staffRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
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
    public void whenUpdateStaffWithUnknownStaffId_thenThrowNotFoundException() {

        //arrange
        //arrange
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

        webTestClient.put()
                .uri(BASE_URI_STAFF + "/" + INVALID_STAFF_ID).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(staffRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(404))
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_STAFF + "/" + INVALID_STAFF_ID)
                .jsonPath("$.message").isEqualTo("Unknown staff ID Provided: " + INVALID_STAFF_ID);


    }

/*
    @Test
    public void whenUpdateStaffAndDuplicatePhoneNumber_throw_StaffNumberException() {

        //arrange
        //arrange
        String employee_first_name = "John";
        String employee_last_name = "Adam";
        String employee_email_address = "124@gmail.com";
        String employee_city = "Boronto";
        String employeePostalCode = "98xs";
        String reservationDateMade = "2022-09-6";
        String employeePhoneNumber = DUPLICATE_PHONE_NUMBER;
        String employeeJobPosition = "Manager";


        StaffRequestModel staffRequestModel = new StaffRequestModel(employee_first_name, employee_last_name, employee_email_address, employeePhoneNumber, employeeJobPosition , reservationDateMade, employee_city, employeePostalCode);

        webTestClient.put()
                .uri(BASE_URI_STAFF + "/" +  VALID_STAFF_ID).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(staffRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(422))
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_STAFF + "/" + VALID_STAFF_ID)
                .jsonPath("$.message").isEqualTo("Staff already contains a staff with this staff number: " + employeePhoneNumber );
    }


 */










@Test
    public void whenCreateStaffWithDuplicateValues_thenReturnStaffNumberException() {
        // arrange
        //arrange
        //arrange
        String employee_first_name = "John";
        String employee_last_name = "Adam";
        String employee_email_address = "124@gmail.com";

        String employee_city = "Boronto";

        String employeePostalCode = "98xs";
        String reservationDateMade = "2022-09-6";
        String employeePhoneNumber = DUPLICATE_PHONE_NUMBER;
        String employeeJobPosition = "Manager";


        StaffRequestModel staffRequestModel = new StaffRequestModel(employee_first_name, employee_last_name, employee_email_address, employeePhoneNumber, employeeJobPosition , reservationDateMade, employee_city, employeePostalCode);


// act and assert
        webTestClient.post()
                .uri(BASE_URI_STAFF)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(staffRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(422))
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_STAFF)
                .jsonPath("$.message").isEqualTo("Staff already contains a staff with this staff number: " + employeePhoneNumber);


    }


    @Test
    public void whenGetStaffWithUnknownStaffId_thenThrowNotFoundException() {
        webTestClient.get()
                .uri(BASE_URI_STAFF + "/" + INVALID_STAFF_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_STAFF + "/" + INVALID_STAFF_ID)
                .jsonPath("$.message").isEqualTo("Unknown Staff ID Provided: " + INVALID_STAFF_ID);
    }


    @Test

    public void whenDeleteStaff_thenDeleteStaff() {

        webTestClient.delete()
                .uri(BASE_URI_STAFF + "/" + VALID_STAFF_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

    }


    @Test
    public void whenDeleteStaffWithUnknownStaffId_thenThrowNotFoundException() {



        // Act and Assert
        webTestClient.delete()
                .uri(BASE_URI_STAFF + "/" + INVALID_STAFF_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_STAFF + "/" + INVALID_STAFF_ID)
                .jsonPath("$.message").isEqualTo("Unknown Staff ID Provided: " + INVALID_STAFF_ID);

    }

}


