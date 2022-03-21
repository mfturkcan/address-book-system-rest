package com.mfturkcan.addressbooksystemrest.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUser {
    @Id @GeneratedValue
    private int id;
    private String name;

    @Column(unique = true)
    private String username;
    private String password;
    private String role;
    private String department;
    private String position;
    private String phoneNumber;
    private String email;
    private String officeNo;

    @JsonManagedReference
    @OneToMany(mappedBy = "bookUser", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.REMOVE})
    private List<TimeTablePart> timeTable;
}