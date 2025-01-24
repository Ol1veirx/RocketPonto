package com.rocket.rocketponto.dto;

import com.rocket.rocketponto.entity.Justification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ListPointRecordDTO {
    private Long id;
    private LocalDateTime entryDateHour;
    private LocalDateTime exitDateHour;
    private String justification;

    public ListPointRecordDTO(Long id, LocalDateTime entryDateHour, LocalDateTime exitDateHour, String justification) {
        this.id = id;
        this.entryDateHour = entryDateHour;
        this.exitDateHour = exitDateHour;
        this.justification = justification;
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
}
