package com.rocket.rocketponto.services;

import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.entity.User;
import com.rocket.rocketponto.repositories.PointRecordRepository;
import com.rocket.rocketponto.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
