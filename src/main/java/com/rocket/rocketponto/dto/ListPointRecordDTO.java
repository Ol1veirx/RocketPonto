package com.rocket.rocketponto.dto;

import com.rocket.rocketponto.entity.Justification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ListPointRecordDTO {
    private Long id;
    private String entryDateHour;
    private String exitDateHour;
    private String justification;

    public ListPointRecordDTO(Long id, String entryDateHour, String exitDateHour, String justification) {
        this.id = id;
        this.entryDateHour = entryDateHour;
        this.exitDateHour = exitDateHour;
        this.justification = justification;
    }

    public ListPointRecordDTO(Long id, String string, String exitDateHour, Justification justification) {
    }


    public Long getId() {
        return id;
    }

    public String getEntryDateHour() {
        return entryDateHour;
    }

    public String getExitDateHour() {
        return exitDateHour;
    }

    public String getJustification() {
        return justification;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEntryDateHour(String entryDateHour) {
        this.entryDateHour = entryDateHour;
    }

    public void setExitDateHour(String exitDateHour) {
        this.exitDateHour = exitDateHour;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
