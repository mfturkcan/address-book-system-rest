package com.mfturkcan.addressbooksystemrest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTablePart {
    @Id @GeneratedValue
    private int id;
    // todo: make dayOfWeek integer
    private String dayOfWeek;
    private String hour;
    private String className;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "book_user_id", nullable = false)
    private BookUser bookUser;
}