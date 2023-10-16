package com.hotelmanagement.roomsservice.businesslayer;


import com.hotelmanagement.roomsservice.datalayer.Room;
import com.hotelmanagement.roomsservice.datalayer.RoomRepository;
import com.hotelmanagement.roomsservice.datamapperlayer.RoomRequestMapper;
import com.hotelmanagement.roomsservice.datamapperlayer.RoomResponseMapper;
import com.hotelmanagement.roomsservice.presentationlayer.RoomsRequestModel;
import com.hotelmanagement.roomsservice.presentationlayer.RoomsResponseModel;
import com.hotelmanagement.roomsservice.utils.exceptions.DuplicateRoomNumberException;
import com.hotelmanagement.roomsservice.utils.exceptions.InvalidInputException;
import com.hotelmanagement.roomsservice.utils.exceptions.NotFoundException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomResponseMapper roomResponseMapper;
    private final RoomRequestMapper roomRequestMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomResponseMapper roomResponseMapper, RoomRequestMapper roomRequestMapper) {
        this.roomRepository = roomRepository;
        this.roomResponseMapper = roomResponseMapper;
        this.roomRequestMapper = roomRequestMapper;
    }

        @Override
        public List<RoomsResponseModel> getRooms() {
            //List<Client> clients = clientRepository.findAll();
            //return clientResponseMapper.entityListToResponseModelList(clients);
            return roomResponseMapper.entityListToResponseModelList(roomRepository.findAll());
        }

   

    @Override
    public RoomsResponseModel getRoomByRoomNumber(Integer roomNumber) {
        Room existingRoom = roomRepository.findRoomByRoomNumber(roomNumber);
        if(existingRoom == null){
            throw new NotFoundException("Unknown room number : " + roomNumber);
        }

        return roomResponseMapper.entityToResponseModel(roomRepository.findRoomByRoomNumber(roomNumber));
    }



    public RoomsResponseModel updateRoom(RoomsRequestModel roomRequestModel, Integer roomNumber) {
        Room existingRoom = roomRepository.findRoomByRoomNumber(roomNumber);
        if (existingRoom == null) {
            throw new NotFoundException("Unknown room number: " + roomNumber);
        }

        Room updatedRoom = roomRequestMapper.requestModelToEntity(roomRequestModel);
        updatedRoom.setId(existingRoom.getId());
        //updatedRoom.setRoomNumber(existingRoom.getRoomNumber());
        updatedRoom.setRoomIdentifier(existingRoom.getRoomIdentifier());

        try {
            Room savedRoom = roomRepository.save(updatedRoom);
            return roomResponseMapper.entityToResponseModel(savedRoom);
        }  catch (DataAccessException ex) {
            //log.debug("ex.message is: " +  ex.getMessage());
            //log.debug("ex.cause is: " + ex.getCause().toString());
            if (ex.getMessage().contains("constraint [roomNumber]") ||
                    ex.getCause().toString().contains("ConstraintViolationException")) {
                throw new DuplicateRoomNumberException("Room already contains a room with this room number: " +
                        roomRequestModel.getRoomNumber());
            }
            throw new InvalidInputException("RoomNumberNotfound");
        }

    }

    @Override
    public RoomsResponseModel addRoom(RoomsRequestModel roomsRequestModel) {
        Room room = roomRequestMapper.requestModelToEntity(roomsRequestModel);
        try {
            return roomResponseMapper.entityToResponseModel(roomRepository.save(room));
        } catch (DataAccessException ex) {
            //log.debug("ex.message is: " +  ex.getMessage());
            //log.debug("ex.cause is: " + ex.getCause().toString());
            if (ex.getMessage().contains("constraint [roomNumber]") ||
                    ex.getCause().toString().contains("ConstraintViolationException")) {
                throw new DuplicateRoomNumberException("Room already contains a room with this room number: " +
                        roomsRequestModel.getRoomNumber());
            }
            throw new NotFoundException("RoomNumberNotfound");
        }
    }




    @Override
    public void removeRooms(Integer roomNumber) {
        Room existingRoom = roomRepository.findRoomByRoomNumber(roomNumber);

        if(existingRoom == null){
            throw  new NotFoundException("Unknown room number provided:" + roomNumber);
        }

        roomRepository.delete(existingRoom);

    }
}




