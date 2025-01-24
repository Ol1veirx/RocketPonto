package com.rocket.rocketponto.services;

import com.rocket.rocketponto.dto.ListPointRecordDTO;
import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.entity.User;
import com.rocket.rocketponto.repositories.PointRecordRepository;
import com.rocket.rocketponto.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public PointRecord savePointRecord(User user) {
        User userExists = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PointRecord lastPointRecord = pointRecordRepository.findTopByUserOrderByEntryDateHourDesc(userExists);



        if (lastPointRecord == null || lastPointRecord.getExitDateHour() != null) {
            PointRecord pointRecord = new PointRecord();
            pointRecord.setUser(userExists);
            pointRecord.setEntryDateHour(LocalDateTime.now());
            pointRecord.setExitDateHour(null);
            return pointRecordRepository.save(pointRecord);
        } else {
            lastPointRecord.setExitDateHour(LocalDateTime.now());
            return pointRecordRepository.save(lastPointRecord);
        }
    }

    public List<ListPointRecordDTO> listRecordPointsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<PointRecord> pointsRecords = pointRecordRepository.findByUser(user);

        return pointsRecords.stream()
                .map(pointRecord -> new ListPointRecordDTO(
                        pointRecord.getId(),
                        pointRecord.getEntryDateHour().toString(),
                        pointRecord.getExitDateHour() != null ? pointRecord.getExitDateHour().toString() : null,
                        pointRecord.getJustification()
                ))
                .collect(Collectors.toList());
    }
}
