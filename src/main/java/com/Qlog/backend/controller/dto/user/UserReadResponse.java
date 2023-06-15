package com.Qlog.backend.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserReadResponse {
    private String name;
    private int point;
}
