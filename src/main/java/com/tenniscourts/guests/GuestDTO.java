package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GuestDTO {

    private Long id;
    @NotNull
    private String name;


    public Guest converterDTO(GuestRepository guestRepository) {
        return new Guest(name);
    }

    public Guest update(Long guestId, GuestRepository guestRepository, GuestDTO dto) {
        Guest guest = guestRepository.getOne(guestId);
        guest.setName(dto.getName());
        guest.setDateUpdate(LocalDateTime.now());
        return guest;
    }
}
