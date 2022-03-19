package com.mfturkcan.addressbooksystemrest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUserDto {
    private int id;
    private String name;
    private String username;
    private String role;
    private String department;
    private String position;
    private long phoneNumber;
    private String email;
    private String officeNo;
}