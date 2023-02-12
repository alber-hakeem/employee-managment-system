package com.workmotion.employeemanagementsystem.base.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCredentials {
    private String username;
    private String password;
}
