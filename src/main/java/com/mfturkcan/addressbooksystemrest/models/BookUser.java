package com.mfturkcan.addressbooksystemrest.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUser {
    @Id @GeneratedValue
    private int id;
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String department;

    private String position;
    private String phoneNumber;

    @Column(nullable = false)
    private String email;
    private String officeNo;

    @JsonManagedReference
    @OneToMany(mappedBy = "bookUser",
            cascade = { CascadeType.ALL},
            orphanRemoval = true) // removes child when it is not referenced from parent
    private List<TimeTablePart> timeTable;
}