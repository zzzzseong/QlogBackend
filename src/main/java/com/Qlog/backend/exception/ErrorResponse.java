package com.Qlog.backend.exception;

import com.Qlog.backend.exception.errorcodes.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private CustomErrorCode status;
    private String statusMessage;
}
