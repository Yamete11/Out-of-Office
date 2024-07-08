package com.example.mockpro.dto;

import lombok.Data;

@Data
public class AddEmployeeDTO {
    private String fullName;

    private String username;

    private String password;

    private int outOfOfficeBalance;

    private Long position;

    private Long status;

    private Long subdivision;

    private Long peoplePartner;

    //private byte[] photo;
}
