package com.mfturkcan.addressbooksystemrest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerResponse {
    private String status;
    private Object message;
}