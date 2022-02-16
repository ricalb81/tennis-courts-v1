package com.tenniscourts.reservations;

import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import com.tenniscourts.tenniscourts.TennisCourt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ReservationDTO {

    private Long id;

    private ScheduleDTO schedule;

    private ReservationStatus reservationStatus;

    private ReservationDTO previousReservation;

    private BigDecimal refundValue;

    private BigDecimal value;

    @NotNull
    private Long scheduledId;

    @NotNull
    private Long guestId;


    public Reservation update(Long reservationtId, ReservationRepository reservationRepository, ScheduleDTO dto) {
        Reservation reservation = reservationRepository.getOne(reservationtId);
        Schedule newSchedule = new Schedule();
        TennisCourt court = new TennisCourt();
        court.setId(dto.getTennisCourtId());
        newSchedule.setStartDateTime(dto.getStartDateTime());
        newSchedule.setTennisCourt(court);
        newSchedule.setEndDateTime(dto.getStartDateTime().plusHours(1));
        newSchedule.setStartDateTime(dto.getStartDateTime());
        reservation.setSchedule(newSchedule);
        reservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        return reservation;
    }

}
