package com.hotelmanagement.reservationservice.Buissnesslayer;

import com.hotelmanagement.reservationservice.DataLayer.*;
import com.hotelmanagement.reservationservice.DataLayer.Payment;
import com.hotelmanagement.reservationservice.DataMapper.ReservationResponseMapper;
import com.hotelmanagement.reservationservice.DomainClientLayer.*;
import com.hotelmanagement.reservationservice.presenation.ReservationRequestModel;
import com.hotelmanagement.reservationservice.presenation.ReservationResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration")

 class ReservationServiceImplUnitTest {

    @Autowired
    ReservationService reservationService;

    @MockBean
    StaffServiceClient staffServiceClient;

    @MockBean
    RoomServiceClient roomServiceClient;

    @MockBean
    GuestServiceClient guestServiceClient;

    @MockBean
    ReservationRepository reservationRepository;

    @SpyBean
    ReservationResponseMapper reservationResponseMapper;

    @Test
    public void whenValidGuestId_Staff_Id_Room_Id_thenProcessReservationOrderRequest() {


        //arrange
        ReservationRequestModel reservationRequestModel = ReservationRequestModel.builder()
                .roomNumber(256)
                .staffId("c3540a89-cb47-4c96-888e-ff96708db4d8")
                .roomStatus(Status.AVAILABLE)
                .price(150)
                .dateReservationmade(LocalDate.of(2023, 03, 10))
                .build();
        String guestId = "dd1ab8b0-ab17-4e03-b70a-84caa3871606";

        GuestResponseModel guestResponseModel = new GuestResponseModel(guestId, "Alick", "Ucceli",
                "aucceli0@dot.gov", "73 Shoshone Road", "Barraute", "Québec", "Canada", "P0M 2T6", Payment.Cash);

        //required for get request in employeeServiceClient
        StaffResponseModel staffResponseModel = new StaffResponseModel("c3540a89-cb47-4c96-888e-ff96708db4d8", "Dorry",
                "Gepp", "dgepp1@stanford.edu", "964-472-9806", "Cook", "2023-03-01",
                "Toronto", "J4x 13x");


        RoomsResponseModel roomsResponseModel = new RoomsResponseModel(
                "Single",
                450,
                256,
                100);


        Reservation reservation = buildReservation1();
        Reservation saved = buildReservation1();
        saved.setId("0001");


        // define Mock behaviors
        when(guestServiceClient.getGuestByGuestId(guestId)).thenReturn(guestResponseModel);
        when( staffServiceClient.getStaffById(staffResponseModel.getStaffId())).thenReturn(staffResponseModel);
        when(roomServiceClient.getRoomByRoomNumber(roomsResponseModel.getRoomNumber())).thenReturn(roomsResponseModel);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(saved);

        // act

        ReservationResponseModel reservationResponseModel= reservationService.createReservationForGuest(reservationRequestModel, guestId);

        // assert
        assertNotNull(reservationResponseModel);
        assertNotNull(reservationResponseModel.getReservationId());
       assertEquals(reservationRequestModel.getRoomNumber(), reservationResponseModel.getRoomNumber());
        assertEquals(reservationRequestModel.getStaffId(), reservationResponseModel.getStaffId());
        assertEquals(guestId, reservationResponseModel.getGuestId());
        assertEquals(staffResponseModel.getEmployeeFirstName(), reservationResponseModel.getEmployeeFirstName());
        assertEquals(staffResponseModel.getEmployeeLastName(), reservationResponseModel.getEmployeeLastName());
        assertEquals(guestResponseModel.getFirstName(), reservationResponseModel.getGuestFirstName());
        assertEquals(guestResponseModel.getLastName(), reservationResponseModel.getGuestLastName());
       // assertEquals(guestResponseModel.getPayment(), reservationResponseModel.getPayment());
        assertEquals(reservationRequestModel.getRoomStatus(), reservationResponseModel.getRoomStatus());
        assertEquals(reservationRequestModel.getPrice(), reservationResponseModel.getPrice());
        assertEquals(reservationRequestModel.getDateReservationmade(), reservationResponseModel.getReservationDateMade());




        // for the spy

        verify(reservationResponseMapper, times(1))
                .entityToResponseModel(saved);


    }

    private Reservation buildReservation1() {
        var reservationIdentifier1 = new ReservationIdentifier();
        var guestIdentifier1 = new GuestIdentifier("dd1ab8b0-ab17-4e03-b70a-84caa3871606");
        var staffPersonalIdentifier1 = new StaffPersonalIdentifier("c3540a89-cb47-4c96-888e-ff96708db4d8");
        var roomIdentifier1 = new RoomIdentifier(256);

        var reservation1 = Reservation.builder()
                .reservationIdentifier(reservationIdentifier1)
                .guestIdentifier(guestIdentifier1)
                .staffPersonalIdentifier(staffPersonalIdentifier1)
                .roomIdentifier(roomIdentifier1)
                .employeeFirstName("Dorry")
                .employeeLastName("Gepp")
                .guestFirstName("Alick")
                .guestLastName("Ucceli")
                .roomStatus(Status.AVAILABLE) // Fix: Change roomstatus to roomStatus
                .price(150)
                .reservationDateMade(LocalDate.of(2023, 03, 10))
                .build();
        return reservation1;
    }

    @Test
    public void whenValidGuestId_Staff_Id_Room_Id_then_update() {


        //arrange
        ReservationRequestModel reservationRequestModel2 = ReservationRequestModel.builder()
                .roomNumber(256)
                .staffId("c3540a89-cb47-4c96-888e-ff96708db4d8")
                .roomStatus(Status.AVAILABLE)
                .price(150)
                .dateReservationmade(LocalDate.of(2023, 03, 10))
                .build();
        String guestId = "dd1ab8b0-ab17-4e03-b70a-84caa3871606";

        GuestResponseModel guestResponseModel = new GuestResponseModel(guestId, "Alick", "Ucceli",
                "aucceli0@dot.gov", "73 Shoshone Road", "Barraute", "Québec", "Canada", "P0M 2T6", Payment.Cash);

        //required for get request in employeeServiceClient
        StaffResponseModel staffResponseModel = new StaffResponseModel("c3540a89-cb47-4c96-888e-ff96708db4d8", "Dorry",
                "Gepp", "dgepp1@stanford.edu", "964-472-9806", "Cook", "2023-03-01",
                "Toronto", "J4x 13x");


        RoomsResponseModel roomsResponseModel = new RoomsResponseModel(
                "Single",
                450,
                256,
                100);


        Reservation reservation = buildReservation1();
        Reservation saved = buildReservation1();
        saved.setId("0001");


        ReservationRequestModel reservationRequestModel = ReservationRequestModel.builder()
                .roomNumber(256)
                .staffId("c3540a89-cb47-4c96-888e-ff96708db4d8")
                .roomStatus(Status.AVAILABLE)
                .price(150)
                .dateReservationmade(LocalDate.of(2023, 03, 10))
                .build();


        // define Mock behaviors
        when(guestServiceClient.getGuestByGuestId(guestId)).thenReturn(guestResponseModel);
        when( staffServiceClient.getStaffById(reservationRequestModel2.getStaffId())).thenReturn(staffResponseModel);
        when(roomServiceClient.getRoomByRoomNumber(reservationRequestModel2.getRoomNumber())).thenReturn(roomsResponseModel);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(saved);

        when(reservationRepository.findByReservationIdentifier_ReservationId(saved.getReservationIdentifier().getReservationId())).thenReturn(saved);

        // act

        ReservationResponseModel reservationResponseModel= reservationService.updateReservationForGuest(reservationRequestModel, guestId, saved.getReservationIdentifier().getReservationId());

        // assert
        assertNotNull(reservationResponseModel);
        assertNotNull(reservationResponseModel.getReservationId());
        assertEquals(reservationRequestModel.getRoomNumber(), reservationResponseModel.getRoomNumber());
        assertEquals(reservationRequestModel.getStaffId(), reservationResponseModel.getStaffId());
        assertEquals(guestId, reservationResponseModel.getGuestId());
        assertEquals(staffResponseModel.getEmployeeFirstName(), reservationResponseModel.getEmployeeFirstName());
        assertEquals(staffResponseModel.getEmployeeLastName(), reservationResponseModel.getEmployeeLastName());
        assertEquals(guestResponseModel.getFirstName(), reservationResponseModel.getGuestFirstName());
        assertEquals(guestResponseModel.getLastName(), reservationResponseModel.getGuestLastName());
        // assertEquals(guestResponseModel.getPayment(), reservationResponseModel.getPayment());
        assertEquals(reservationRequestModel.getRoomStatus(), reservationResponseModel.getRoomStatus());
        assertEquals(reservationRequestModel.getPrice(), reservationResponseModel.getPrice());
        assertEquals(reservationRequestModel.getDateReservationmade(), reservationResponseModel.getReservationDateMade());




        // for the spy

        verify(reservationResponseMapper, times(1))
                .entityToResponseModel(saved);


    }







}
