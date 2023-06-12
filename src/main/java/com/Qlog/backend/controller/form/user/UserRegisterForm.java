package com.Qlog.backend.controller.form.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterForm {
    private String loginId;
    private String password;
    private String name;
}