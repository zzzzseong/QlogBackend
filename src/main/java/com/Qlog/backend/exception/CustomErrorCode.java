package com.Qlog.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {
    //Jwt Filter Exception
    MALFORMED_JWT_EXCEPTION("[손상된 토큰] 잘못된 요청입니다."),
    EXPIRED_JWT_EXCEPTION("[만료된 토큰] 잘못된 요청입니다. 다시 로그인해주세요."),
    UNSUPPORTED_JWT_EXCEPTION("[지원하지 않는 토큰] 잘못된 요청입니다."),
    SIGNATURE_EXCEPTION("[지원하지 않는 토큰] 잘못된 요청입니다."),
    ILLEGAL_ARGUMENT_EXCEPTION("[지원하지 않는 토큰] 잘못된 요청입니다."),

    PAGE_NOT_FOUND_EXCEPTION("존재하지 않는 페이지입니다.");

    private final String statusMessage;
}
