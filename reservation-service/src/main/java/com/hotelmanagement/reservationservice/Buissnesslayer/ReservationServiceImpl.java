package com.hotelmanagement.reservationservice.Buissnesslayer;

import com.hotelmanagement.reservationservice.DataLayer.*;
import com.hotelmanagement.reservationservice.DataMapper.ReservationResponseMapper;
import com.hotelmanagement.reservationservice.DomainClientLayer.*;
import com.hotelmanagement.reservationservice.presenation.ReservationRequestModel;
import com.hotelmanagement.reservationservice.presenation.ReservationResponseModel;
import com.hotelmanagement.reservationservice.utils.exeption.DuplicateStaff;
import com.hotelmanagement.reservationservice.utils.exeption.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements  ReservationService{
    private final ReservationRepository reservationRepository ;
    private final ReservationResponseMapper reservationResponseMapper;
    private final GuestServiceClient guestServiceClient;
    private final StaffServiceClient staffServiceClient;
    private final RoomServiceClient roomServiceClient;
    @Override
    public List<ReservationResponseModel> getAllReservations() {
        return reservationResponseMapper.entityListToResponseModelList(reservationRepository.findAll());
    }


    @Override
    public ReservationResponseModel getReservationByGuestId(String guestId, String  reservationId) {
        return reservationResponseMapper.entityToResponseModel(reservationRepository.findByGuestIdentifier_GuestIdAndAndReservationIdentifier_ReservationId(guestId, reservationId));
    }

    @Override
    public ReservationResponseModel createReservationForGuest( ReservationRequestModel reservationRequestModel, String guestId) {
        GuestResponseModel guestResponseModel = guestServiceClient.getGuestByGuestId(guestId);
        if (guestResponseModel == null){
            throw new NotFoundException("Invalid GuestId provided: " + guestId);
        }
        StaffResponseModel staffResponseModel = staffServiceClient.getStaffById(reservationRequestModel.getStaffId());
        if (staffResponseModel == null){
            throw new NotFoundException("Invalid StaffId provided: " + reservationRequestModel.getStaffId());
        }
        RoomsResponseModel roomsResponseModel = roomServiceClient.getRoomByRoomNumber(reservationRequestModel.getRoomNumber());
        if (roomsResponseModel == null){
            throw new NotFoundException("Invalid RoomNumber provided: " + reservationRequestModel.getRoomNumber());
        }


        Reservation reservation = Reservation.builder()
                .reservationIdentifier(new ReservationIdentifier())
                .guestIdentifier(new GuestIdentifier(guestResponseModel.getGuestId()))
                .roomIdentifier(new RoomIdentifier(roomsResponseModel.getRoomNumber()))
                .staffPersonalIdentifier(new StaffPersonalIdentifier(staffResponseModel.getStaffId()))
                .guestFirstName(guestResponseModel.getFirstName())
                .guestLastName(guestResponseModel.getLastName())
                .employeeLastName(staffResponseModel.getEmployeeLastName())
                .employeeFirstName(staffResponseModel.getEmployeeFirstName())
                .reservationDateMade(reservationRequestModel.getDateReservationmade())
                .roomStatus(reservationRequestModel.getRoomStatus())
                .price(roomsResponseModel.getPrice())
                .build();
                
log.debug(" **** Reservation" + reservation);
        return reservationResponseMapper.entityToResponseModel(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseModel updateReservationForGuest(ReservationRequestModel reservationRequestModel, String guestId, String reservationId) {
        Reservation existingReservation = reservationRepository.findByReservationIdentifier_ReservationId(reservationId);
      if(existingReservation==null){
          throw new NotFoundException("Invalid reservationId provided: " + reservationId);
      }
        GuestResponseModel guestResponseModel = guestServiceClient.getGuestByGuestId(guestId);
        if (guestResponseModel == null){
            throw new NotFoundException("Invalid GuestId provided: " + guestId);
        }
        StaffResponseModel staffResponseModel = staffServiceClient.getStaffById(reservationRequestModel.getStaffId());
        if (staffResponseModel == null){
            throw new NotFoundException("Invalid StaffId provided: " + reservationRequestModel.getStaffId());
        }
        RoomsResponseModel roomsResponseModel = roomServiceClient.getRoomByRoomNumber(reservationRequestModel.getRoomNumber());
        if (roomsResponseModel == null){
            throw new NotFoundException("Invalid RoomId provided: " + reservationRequestModel.getRoomNumber());
        }



        Reservation reservation = Reservation.builder()
                .id(existingReservation.getId())
                .reservationIdentifier(existingReservation.getReservationIdentifier())
                .guestIdentifier(existingReservation.getGuestIdentifier())
                .roomIdentifier(new RoomIdentifier(reservationRequestModel.getRoomNumber()))
                .staffPersonalIdentifier(new StaffPersonalIdentifier(reservationRequestModel.getStaffId()))
                .guestFirstName(guestResponseModel.getFirstName())
                .guestLastName(guestResponseModel.getLastName())
                .employeeLastName(staffResponseModel.getEmployeeLastName())
                .employeeFirstName(staffResponseModel.getEmployeeFirstName())
                .reservationDateMade(reservationRequestModel.getDateReservationmade())
                .roomStatus(reservationRequestModel.getRoomStatus())
                .price(roomsResponseModel.getPrice())
                .build();

         try{
             return reservationResponseMapper.entityToResponseModel(reservationRepository.save(reservation));
         }
     catch (DataAccessException ex) {
        //log.debug("ex.message is: " +  ex.getMessage());
        //log.debug("ex.cause is: " + ex.getCause().toString());
        if (ex.getMessage().contains("constraint [staffId]") ||
                ex.getCause().toString().contains("ConstraintViolationException")) {
            throw new DuplicateStaff("Staff already contains this staff id: " +
                    reservation.getStaffPersonalIdentifier().getStaffId());
        }
        throw new NotFoundException("RoomNumberNotfound");
    }

    }

    @Override
    public void deleteReservationForGuest(String guestId, String reservationId) {
        GuestResponseModel guestResponseModel = guestServiceClient.getGuestByGuestId(guestId);
        if (guestResponseModel == null){
            throw new NotFoundException("Invalid GuestId provided: " + guestId);
        }

        Reservation reservation = reservationRepository.findByReservationIdentifier_ReservationId(reservationId);
        if (reservation == null){
            throw new NotFoundException("Invalid PurchaseId provided: " + reservationId);
        }

        reservationRepository.delete(reservation);

    }

   


}
