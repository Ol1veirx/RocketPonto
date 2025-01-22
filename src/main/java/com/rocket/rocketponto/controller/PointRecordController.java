package com.rocket.rocketponto.controller;

import com.rocket.rocketponto.entity.User;
import com.rocket.rocketponto.repositories.UserRepository;
import com.rocket.rocketponto.services.PointRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point-record")
public class PointRecordController {

    private final PointRecordService pointRecordService;
    private final UserRepository userRepository;

    public PointRecordController(PointRecordService pointRecordService, UserRepository userRepository) {
        this.pointRecordService = pointRecordService;
        this.userRepository = userRepository;
    }

    @PostMapping("/save")
    public void savePointRecord(@RequestBody Long userId) {
        User user = userRepository.findById(userId).get();
        pointRecordService.savePointRecord(user);
    }
}
