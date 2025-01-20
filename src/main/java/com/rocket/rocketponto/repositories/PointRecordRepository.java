package com.rocket.rocketponto.repositories;

import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRecordRepository extends JpaRepository<PointRecord, Long> {
    PointRecord findTopByUserOrderByEntryDateHourDesc(User user);
}
