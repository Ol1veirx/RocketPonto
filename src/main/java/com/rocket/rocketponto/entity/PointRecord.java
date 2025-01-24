package com.rocket.rocketponto.entity;

import com.rocket.rocketponto.enums.PointRecordStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "point_records")
public class PointRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime entryDateHour;

    @Column
    private LocalDateTime exitDateHour;

    @Column
    @Enumerated(EnumType.STRING)
    private PointRecordStatus pointRecordStatus;

    @OneToOne(mappedBy = "pointRecord", cascade = CascadeType.ALL)
    private Justification justification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getEntryDateHour() {
        return entryDateHour;
    }

    public void setEntryDateHour(LocalDateTime entryDateHour) {
        this.entryDateHour = entryDateHour;
    }

    public LocalDateTime getExitDateHour() {
        return exitDateHour;
    }

    public void setExitDateHour(LocalDateTime exitDateHour) {
        this.exitDateHour = exitDateHour;
    }

    public Justification getJustification() {
        return justification;
    }

    public void setJustification(Justification justification) {
        this.justification = justification;
    }

    public PointRecordStatus getPointRecordStatus() {
        return pointRecordStatus;
    }

    public void setPointRecordStatus(PointRecordStatus pointRecordStatus) {
        this.pointRecordStatus = pointRecordStatus;
    }
}
