package com.Qlog.backend.controller.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProfileUpdateForm {
    private String username;
    private String introduction;
}
