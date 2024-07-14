package com.example.mockpro.dto;

import lombok.Data;

@Data
public class EditEmployeeDTO {
    private Long id;

    private String fullName;

    private String username;

    private int outOfOfficeBalance;

    private Long position;

    private Long status;

    private Long subdivision;

    private Long peoplePartner;
}
