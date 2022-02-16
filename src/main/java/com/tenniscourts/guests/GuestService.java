package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestService implements GuestMapper {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;


    public GuestDTO findGuest(Long guestId, String guestName) {

        if(guestId != null) {

            return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
                throw new EntityNotFoundException("Guest not found.");
            });
        } else if(guestName != null) {

            return Optional.ofNullable(guestRepository.findByName(guestName)).map(guestMapper::map).orElseThrow(() -> {
                throw new EntityNotFoundException("Guest not found.");

            });

        } else
            throw new EntityNotFoundException("Enter the GuestID or GuestName.");

    }

    public List<GuestDTO> findAllGuest() {
        try {
            return guestRepository.findAll().stream().map(guestMapper::map).collect(Collectors.toList());
        }catch (Exception e) {
            throw new EntityNotFoundException("Guest not found.");
        }
    }

    @Transactional
    public Guest create(GuestDTO guestDTO) {
        return guestRepository.save(guestDTO.converterDTO(guestRepository));
    }

    @Transactional
    public GuestDTO deleteGuest(Long guestId) {
        try {
            Optional<Guest> opGuest = guestRepository.findById(guestId);
            if(opGuest.isPresent()) {
                guestRepository.deleteById(guestId);
                return map(opGuest.get());
            }
        } catch (Exception e) {
            throw new EntityNotFoundException("Guest not found.");
        }
        throw new EntityNotFoundException("Guest not found.");
    }

    @Transactional
    public GuestDTO updateGuest(Long guestId, GuestDTO guestDTO) {
        try {
            return map(guestDTO.update(guestId, guestRepository, guestDTO));
        } catch (Exception e) {
            throw new EntityNotFoundException("Guest not found.");
        }
    }

    @Override
    public Guest map(GuestDTO source) {
        if ( source == null ) {
            return null;
        }
        Guest guest = new Guest();
        guest.setId(source.getId());
        guest.setName(source.getName());
        return guest;
    }


    @Override
    public GuestDTO map(Guest source) {
        if ( source == null ) {
            return null;
        }
        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setId(source.getId());
        guestDTO.setName(source.getName());
        return guestDTO;
    }


}
