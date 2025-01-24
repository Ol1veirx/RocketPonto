package com.rocket.rocketponto.repositories;

import com.rocket.rocketponto.dto.ListPointRecordDTO;
import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRecordRepository extends JpaRepository<PointRecord, Long> {
    PointRecord findTopByUserOrderByEntryDateHourDesc(User user);
    List<PointRecord> findByUser(User user);
}
