package com.mfturkcan.addressbooksystemrest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTablePart {
    @Id @GeneratedValue
    private int id;
    // todo: make dayOfWeek integer
    private int dayOfWeek;
    private LocalTime hour;
    private String className;
    private String label;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "book_user_id", nullable = false)
    private BookUser bookUser;
}