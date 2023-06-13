package com.Qlog.backend.controller.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterForm {
    private String loginId;
    private String password;
    private String name;
}