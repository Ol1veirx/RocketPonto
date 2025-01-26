package com.rocket.rocketponto.controller;

import com.rocket.rocketponto.dto.ListPointRecordDTO;
import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.entity.User;
import com.rocket.rocketponto.repositories.UserRepository;
import com.rocket.rocketponto.security.UserDetailsServiceImpl;
import com.rocket.rocketponto.services.PointRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point-record")
public class PointRecordController {

    private final PointRecordService pointRecordService;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public PointRecordController(PointRecordService pointRecordService, UserRepository userRepository, UserDetailsServiceImpl userDetailsService) {
        this.pointRecordService = pointRecordService;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/save")
    public void savePointRecord() {
        User user = userDetailsService.getAuthenticatedUser();
        pointRecordService.savePointRecord(user);
    }

    @GetMapping("/list-by-user")
    public ResponseEntity<List<ListPointRecordDTO>> listRecordPointsByUser() {
        User user = userDetailsService.getAuthenticatedUser();
        List<ListPointRecordDTO> pointRecords = pointRecordService.listRecordPointsByUser(user.getId());
        return ResponseEntity.ok(pointRecords);
    }
}
