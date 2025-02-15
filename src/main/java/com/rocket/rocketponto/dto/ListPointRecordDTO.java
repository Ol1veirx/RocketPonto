package com.rocket.rocketponto.dto;

import com.rocket.rocketponto.entity.Justification;
import com.rocket.rocketponto.enums.PointRecordStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ListPointRecordDTO {
    private Long id;
    private LocalDateTime entryDateHour;
    private String nameUser;
    private LocalDateTime exitDateHour;
    private String justification;
    private String description;
    private PointRecordStatus pointRecordStatus;

    public ListPointRecordDTO(Long id,
                              LocalDateTime entryDateHour,
                              LocalDateTime exitDateHour,
                              String justification,
                              String nameUser,
                              String description,
                              PointRecordStatus pointRecordStatus) {
        this.id = id;
        this.entryDateHour = entryDateHour;
        this.exitDateHour = exitDateHour;
        this.justification = justification;
        this.nameUser = nameUser;
        this.description = description;
    }

    public ListPointRecordDTO() {}

    public ListPointRecordDTO(Long id, String string, String exitDateHour, Justification justification) {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getEntryDateHour() {
        return entryDateHour;
    }

    public LocalDateTime getExitDateHour() {
        return exitDateHour;
    }

    public String getJustification() {
        return justification;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEntryDateHour(LocalDateTime entryDateHour) {
        this.entryDateHour = entryDateHour;
    }

    public void setExitDateHour(LocalDateTime exitDateHour) {
        this.exitDateHour = exitDateHour;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public PointRecordStatus getPointRecordStatus() {
        return pointRecordStatus;
    }

    public void setPointRecordStatus(PointRecordStatus pointRecordStatus) {
        this.pointRecordStatus = pointRecordStatus;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
