package com.Qlog.backend.controller.form.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginForm {
    private String loginId;
    private String password;
}
