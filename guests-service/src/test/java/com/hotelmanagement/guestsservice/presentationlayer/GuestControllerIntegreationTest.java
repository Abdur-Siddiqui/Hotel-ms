package com.hotelmanagement.guestsservice.presentationlayer;

import com.hotelmanagement.guestsservice.datalayer.GuestRepository;
import com.hotelmanagement.guestsservice.datalayer.Payment;
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
public class GuestControllerIntegreationTest {

    private final String BASE_URI_GUEST = "/api/v1/guests";
    private final String VALID_GUEST_ID = "c3540a89-cb47-4c96-888e-ff96708db4d8";

    private final String VALID_EMAIL_ADDRESS = "aucceli0@dot.gov";

    private final String DUPLICATE_EMAIL_ADDRESS = VALID_EMAIL_ADDRESS;
    private final String INVALID_GUEST_ID = VALID_GUEST_ID + 1;
    private final Payment VALID_PAYMENT = Payment.CreditCard;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    GuestRepository guestRepository;


    // getting all the staffs
    @Test
    public void whenGuestExist_thenReturnAllStaff() {
        // arrange
        Integer expectedNumsGuest = 9;

        // act
        webTestClient.get()
                .uri(BASE_URI_GUEST)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedNumsGuest);

    }


    // getting staffs by id
    @Test
    public void whenGetGuestWithValidIdExists_thenReturnGuest() {
        webTestClient.get().uri(BASE_URI_GUEST + "/" + VALID_GUEST_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.guestId").isEqualTo(VALID_GUEST_ID);

    }

    /*
        // creating a post
        @Test
        public void whenCreateGuestWithValidValues_thenReturnNewGuest() {

            //arrange
            String first_name = "John";
            String last_name = "Adams";
            String email_address = "1234@gmail.com";
            String streetAddress = "223 rue Mcguil";
            String city = "Toronto";
            String province = "Ontario";
            String Country="Canada";
            String expecetedPostalCode = "98xs";
            Payment exepectedpayment= Payment.CreditCard;



            GuestRequestModel guestRequestModel = new GuestRequestModel(first_name, last_name, email_address, streetAddress, city, province, Country, expecetedPostalCode, exepectedpayment);

            //act and assert
            webTestClient.post()
                    .uri(BASE_URI_GUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(guestRequestModel)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(GuestResponseModel.class);



        }

    */
    @Test
    public void whenUpdateGuestWithValidValues_thenReturnUpdatedGuest() {

        String first_name = "John";
        String last_name = "Adams";
        String email_address = "1234@gmail.com";
        String streetAddress = "123 rue Mcguil";
        String city = "Toronto";
        String province = "Ontario";
        String Country = "Canada";
        String expecetedPostalCode = "98xs";
        Payment exepectedpayment = Payment.CreditCard;


        GuestRequestModel guestRequestModel = new GuestRequestModel(first_name, last_name, email_address, streetAddress, city, province, Country, expecetedPostalCode, exepectedpayment);

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_GUEST + "/" + VALID_GUEST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(guestRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.guestId").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo(first_name)
                .jsonPath("$.lastName").isEqualTo(last_name)
                .jsonPath("$.emailAddress").isEqualTo(email_address)
                .jsonPath("$.streetAddress").isEqualTo(streetAddress)
                .jsonPath("$.city").isEqualTo(city)
                .jsonPath("$.province").isEqualTo(province)
                .jsonPath("$.country").isEqualTo(Country)
                .jsonPath("$.postalCode").isEqualTo(expecetedPostalCode);


    }


    @Test
    public void whenUpdateStaffWithUnknownStaffId_thenThrowNotFoundException() {


        String first_name = "John";
        String last_name = "Adams";
        String email_address = "1234@gmail.com";
        String streetAddress = "123 rue Mcguil";
        String city = "Toronto";
        String province = "Ontario";
        String Country = "Canada";
        String expecetedPostalCode = "98xs";
        Payment exepectedpayment = Payment.CreditCard;


        GuestRequestModel guestRequestModel = new GuestRequestModel(first_name, last_name, email_address, streetAddress, city, province, Country, expecetedPostalCode, exepectedpayment);

        //act and assert
        webTestClient.put()
                .uri(BASE_URI_GUEST + "/" + INVALID_GUEST_ID).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(guestRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isEqualTo(HttpStatusCode.valueOf(404))
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_GUEST + "/" + INVALID_GUEST_ID)
                .jsonPath("$.message").isEqualTo("Unknown guest ID Provided: " + INVALID_GUEST_ID);


    }




    @Test
    public void whenCreateGuestWithDuplicateValues_thenReturnEmailAddressException() {
        // arrange


        String first_name = "John";
        String last_name = "Adams";
        String email_address = DUPLICATE_EMAIL_ADDRESS;
        String streetAddress = "120 rue Robert";
        String city = "Toronto";
        String province = "Ontario";
        String Country="Canada";
        String expecetedPostalCode = "98xs";
        Payment exepectedpayment= Payment.CreditCard;


        GuestRequestModel guestRequestModel = new GuestRequestModel(first_name, last_name, email_address, streetAddress, city, province, Country, expecetedPostalCode, exepectedpayment);


// act and assert
        webTestClient.post()
                .uri(BASE_URI_GUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(guestRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(422))
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.path")
                .isEqualTo("uri=" + BASE_URI_GUEST)
                .jsonPath("$.message").isEqualTo("Email is assigened to more than one guest: " + email_address);


    }


    @Test
    public void whenGetGuestWithUnknownGuestId_thenThrowNotFoundException() {
        webTestClient.get()
                .uri(BASE_URI_GUEST + "/" + INVALID_GUEST_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_GUEST + "/" + INVALID_GUEST_ID)
                .jsonPath("$.message").isEqualTo("Unknown guest ID Provided: " + INVALID_GUEST_ID);
    }


    @Test

    public void whenDeleteGuest_thenDeleteGuest() {

        webTestClient.delete()
                .uri(BASE_URI_GUEST + "/" + VALID_GUEST_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();

    }


    @Test
    public void whenDeleteGuestWithUnknownGuestId_thenThrowNotFoundException() {



        // Act and Assert
        webTestClient.delete()
                .uri(BASE_URI_GUEST + "/" + INVALID_GUEST_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI_GUEST + "/" + INVALID_GUEST_ID)
                .jsonPath("$.message").isEqualTo("Unknown guest ID Provided: " + INVALID_GUEST_ID);

    }




}




