package com.rocket.rocketponto.repositories;

import com.rocket.rocketponto.entity.Justification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JustificationRepository extends JpaRepository<Justification, Long> {
    Optional<Justification> findByDescription(String description);
}
