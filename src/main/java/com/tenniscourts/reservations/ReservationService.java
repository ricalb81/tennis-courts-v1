package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.schedules.ScheduleDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService implements ReservationMapper {

    @Autowired
    private final ReservationRepository reservationRepository;

    @Autowired
    private final ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        throw new UnsupportedOperationException();
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setAmount(reservation.getAmount().subtract(refundValue));
        reservation.setRefundValue(refundValue);
        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates."+reservation.getSchedule().getStartDateTime());
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getAmount();
        }

        return BigDecimal.ZERO;
    }

    @Transactional
    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId , ScheduleDTO scheduleDTO ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        Long tennisCourtId = scheduleDTO.getTennisCourtId();
        Reservation previousReservation = reservationRepository.findById(previousReservationId).get();

        if(tennisCourtId == previousReservation.getSchedule().getTennisCourt().getId()
                && scheduleDTO.getStartDateTime().format(formatter).equals(previousReservation.getSchedule().getStartDateTime().format(formatter))) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation.getSchedule().setStartDateTime(scheduleDTO.getStartDateTime());
        previousReservation = cancel(previousReservationId);

        return map(new ReservationDTO().update(previousReservationId, reservationRepository, scheduleDTO));
    }



    @Override
    public Reservation map(ReservationDTO source) {
        if ( source == null ) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(source.getReservationStatus());
        reservation.setId(source.getId());
        reservation.setRefundValue(source.getRefundValue());
        reservation.setAmount(source.getValue());
        return reservation;
    }

    @Override
    public ReservationDTO map(Reservation source) {
        if ( source == null ) {
            return null;
        }
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setGuestId(source.getGuest().getId());
        reservationDTO.setRefundValue(source.getRefundValue());
        reservationDTO.setValue(source.getAmount());
        reservationDTO.setReservationStatus(source.getReservationStatus());
        reservationDTO.setId(source.getId());
        reservationDTO.setScheduledId(source.getSchedule().getId());
        return reservationDTO;
    }

    @Override
    public Reservation map(CreateReservationRequestDTO source) {
        if ( source == null ) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setId(source.getGuestId());
        return reservation;
    }
}
