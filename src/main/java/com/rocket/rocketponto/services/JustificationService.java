package com.rocket.rocketponto.services;

import com.rocket.rocketponto.entity.Justification;
import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.enums.JustificationStatus;
import com.rocket.rocketponto.repositories.JustificationRepository;
import com.rocket.rocketponto.repositories.PointRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class JustificationService {

    private final JustificationRepository justificationRepository;
    private final PointRecordRepository pointRecordRepository;

    public JustificationService(JustificationRepository justificationRepository, PointRecordRepository pointRecordRepository) {
        this.justificationRepository = justificationRepository;
        this.pointRecordRepository = pointRecordRepository;
    }

    public Justification saveJustification(Justification justification, Long pointRecordId) {
        PointRecord pointRecord = pointRecordRepository.findById(pointRecordId).get();
        if(pointRecord == null) {
            throw new RuntimeException("Point Record not found");
        }

        if(pointRecord.getJustification() != null) {
            throw new RuntimeException("Justification already exists");
        }
        Justification justifcation = new Justification();
        justifcation.setPointRecord(pointRecord);
        justifcation.setReason(justification.getReason());
        justification.setStatus(JustificationStatus.PENDING);
        justifcation.setSendDate(LocalDateTime.now());
        justification.setDescription(justification.getDescription());

        return justificationRepository.save(justification);
    }

}
