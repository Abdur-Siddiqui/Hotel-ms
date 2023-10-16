package com.hotelmanagement.guestsservice.businesslayer;

import com.hotelmanagement.guestsservice.datalayer.Address;
import com.hotelmanagement.guestsservice.datalayer.Guest;
import com.hotelmanagement.guestsservice.datalayer.GuestRepository;
import com.hotelmanagement.guestsservice.datamapperlayer.GuestRequestMapper;
import com.hotelmanagement.guestsservice.datamapperlayer.GuestResponseMapper;
import com.hotelmanagement.guestsservice.presentationlayer.GuestRequestModel;
import com.hotelmanagement.guestsservice.presentationlayer.GuestResponseModel;
import com.hotelmanagement.guestsservice.utils.exceptions.DuplicateEmailAddressException;
import com.hotelmanagement.guestsservice.utils.exceptions.NotFoundException;
import com.hotelmanagement.guestsservice.utils.exceptions.InvalidInputException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestResponseMapper guestResponseMapper;
    private final GuestRequestMapper guestRequestMapper;

    public GuestServiceImpl(GuestRepository guestRepository, GuestResponseMapper guestResponseMapper, GuestRequestMapper guestRequestMapper) {
        this.guestRepository = guestRepository;
        this.guestResponseMapper = guestResponseMapper;
        this.guestRequestMapper = guestRequestMapper;
    }

        @Override
        public List<GuestResponseModel> getGuests() {
            //List<Client> clients = clientRepository.findAll();
            //return clientResponseMapper.entityListToResponseModelList(clients);
            return guestResponseMapper.entityListToResponseModelList(guestRepository.findAll());
        }

   

    @Override
    public GuestResponseModel getGuestByGuestId(String guestId) {
        Guest existingGuest = guestRepository.findByGuestIdentifier_GuestId((guestId));
        if(existingGuest == null){
            throw new NotFoundException("Unknown guest ID Provided: " + guestId);
        }
        return guestResponseMapper.entityToResponseModel(guestRepository.findByGuestIdentifier_GuestId(guestId));
    }

    @Override
    public GuestResponseModel updateGuest(GuestRequestModel guestRequestModel, String guestId) {

        Guest existingGuest = guestRepository.findByGuestIdentifier_GuestId((guestId));
        if(existingGuest == null){
            throw new NotFoundException("Unknown guest ID Provided: " + guestId);
        }

        Address address = new Address(guestRequestModel.getStreetAddress(), guestRequestModel.getCity(),
                guestRequestModel.getProvince(), guestRequestModel.getCountry(), guestRequestModel.getPostalCode());

        Guest guest = guestRequestMapper.requestModelToEntity(guestRequestModel);

        guest.setAddress(address);
        guest.setId(existingGuest.getId());
        guest.setGuestIdentifier(existingGuest.getGuestIdentifier());


        return guestResponseMapper.entityToResponseModel(guestRepository.save(guest));      // UPDATE SQL - Guest
    }


    @Override
    public GuestResponseModel addGuest(GuestRequestModel guestRequestModel) {
        Guest guest = guestRequestMapper.requestModelToEntity(guestRequestModel);

        String existingEmail = guestRequestModel.getEmailAddress();
        List<Guest> foundGuests = guestRepository.findGuestByEmailAddress(existingEmail);

        if (foundGuests.size() > 0) {
            throw new DuplicateEmailAddressException("Email is assigened to more than one guest: " + guestRequestModel.getEmailAddress());
        } else if (existingEmail.length() > 17) {
            throw new InvalidInputException("Email address is too long: " + existingEmail);
        } else {
            Guest saved = guestRepository.save(guest);
            return guestResponseMapper.entityToResponseModel(saved);
        }
    }


    @Override
    public void removeGuest(String guestId) {
        Guest existingGuest = guestRepository.findByGuestIdentifier_GuestId(guestId);

        if(existingGuest == null){
            throw new NotFoundException("Unknown guest ID Provided: " + guestId);
        }

        guestRepository.delete(existingGuest);

    }
}




