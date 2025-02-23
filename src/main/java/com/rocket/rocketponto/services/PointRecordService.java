package com.rocket.rocketponto.services;

import com.rocket.rocketponto.dto.ListPointRecordDTO;
import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.entity.User;
import com.rocket.rocketponto.enums.PointRecordStatus;
import com.rocket.rocketponto.repositories.PointRecordRepository;
import com.rocket.rocketponto.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointRecordService {

    private final PointRecordRepository pointRecordRepository;
    private final UserRepository userRepository;

    @Autowired
    public PointRecordService(PointRecordRepository pointRecordRepository, UserRepository userRepository) {
        this.pointRecordRepository = pointRecordRepository;
        this.userRepository = userRepository;
    }

    public PointRecord savePointRecord(User user, String description) {
        User userExists = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PointRecord lastPointRecord = pointRecordRepository.findTopByUserOrderByEntryDateHourDesc(userExists);

        ZonedDateTime entryDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        if (lastPointRecord == null || lastPointRecord.getExitDateHour() != null) {
            PointRecord pointRecord = new PointRecord();
            pointRecord.setUser(userExists);
            pointRecord.setEntryDateHour(entryDateTime.toLocalDateTime());
            pointRecord.setExitDateHour(null);
            pointRecord.setPointRecordStatus(PointRecordStatus.IN_PROGRESS);
            return pointRecordRepository.save(pointRecord);
        } else {
            if (description == null || description.isEmpty()) {
                throw new IllegalArgumentException("Descrição é obrigatŕoria");
            }
            ZonedDateTime exitDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            lastPointRecord.setExitDateHour(exitDateTime.toLocalDateTime());
            lastPointRecord.setPointRecordStatus(PointRecordStatus.COMPLETED);
            lastPointRecord.setDescription(description);
            return pointRecordRepository.save(lastPointRecord);
        }
    }

    public List<ListPointRecordDTO> listRecordPointsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<PointRecord> pointsRecords = pointRecordRepository.findByUser(user);
        pointsRecords.sort((pr1, pr2) -> pr2.getEntryDateHour().compareTo(pr1.getEntryDateHour()));
        return getListPointRecordDTOS(pointsRecords);
    }

    public List<ListPointRecordDTO> getAllPointRecords() {
        List<PointRecord> pointRecords = pointRecordRepository.findAll();
        pointRecords.sort((pr1, pr2) -> pr2.getEntryDateHour().compareTo(pr1.getEntryDateHour()));
        return getListPointRecordDTOS(pointRecords);
    }

    private List<ListPointRecordDTO> getListPointRecordDTOS(List<PointRecord> pointRecords) {
        return pointRecords.stream()
                .map(pointRecord -> {
                    ListPointRecordDTO dto = new ListPointRecordDTO();
                    dto.setId(pointRecord.getId());
                    dto.setNameUser(pointRecord.getUser().getName());
                    dto.setEntryDateHour(pointRecord.getEntryDateHour());
                    dto.setExitDateHour(pointRecord.getExitDateHour());
                    dto.setPointRecordStatus(pointRecord.getPointRecordStatus());
                    dto.setDescription(pointRecord.getDescription());
                    dto.setJustification(pointRecord.getJustification() != null ? pointRecord.getJustification().getDescription() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
