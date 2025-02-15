package com.rocket.rocketponto.controller;

import com.rocket.rocketponto.dto.ListPointRecordDTO;
import com.rocket.rocketponto.entity.PointRecord;
import com.rocket.rocketponto.entity.User;
import com.rocket.rocketponto.repositories.UserRepository;
import com.rocket.rocketponto.security.UserDetailsServiceImpl;
import com.rocket.rocketponto.services.PointRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @PostMapping("/save")
    public void savePointRecord(@RequestBody Map<String, String> request) {
        String description = request.get("description");
        User user = userDetailsService.getAuthenticatedUser();
        pointRecordService.savePointRecord(user, description);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @GetMapping("/list-by-user")
    public ResponseEntity<List<ListPointRecordDTO>> listRecordPointsByUser() {
        User user = userDetailsService.getAuthenticatedUser();
        List<ListPointRecordDTO> pointRecords = pointRecordService.listRecordPointsByUser(user.getId());
        return ResponseEntity.ok(pointRecords);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all-point-records")
    public ResponseEntity<List<ListPointRecordDTO>> getAllPointRecords() {
        List<ListPointRecordDTO> pointRecords = pointRecordService.getAllPointRecords();
        return ResponseEntity.ok(pointRecords);
    }
}
