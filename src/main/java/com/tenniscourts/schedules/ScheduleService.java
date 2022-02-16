package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService implements ScheduleMapper {

    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
        return null;
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        //TODO: implement
        return null;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return null;
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }

    @Override
    public Schedule map(ScheduleDTO source) {

        return null;
    }

    @Override
    public ScheduleDTO map(Schedule source) {
        if ( source == null ) {
            return null;
        }
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setEndDateTime(source.getEndDateTime());
        scheduleDTO.setStartDateTime(source.getStartDateTime());
        scheduleDTO.setId(source.getId());
        scheduleDTO.setId(source.getId());
        return scheduleDTO;
    }

    @Override
    public List<ScheduleDTO> map(List<Schedule> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
