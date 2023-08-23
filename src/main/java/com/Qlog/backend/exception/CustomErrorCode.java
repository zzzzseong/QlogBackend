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

    PAGE_NOT_FOUND_EXCEPTION("존재하지 않는 페이지입니다."),

    NON_START_BEARER_EXCEPTION("[잘못된 형식의 토큰] 클라이언트 토큰 요청 형식이 잘못되었습니다."),

    //Jwt Token Exception
    JWT_NOT_FOUND_EXCEPTION("[토큰을 찾을 수 없음] 접근 권한이 없습니다. 로그인 해주세요."), //토큰 없이 접근할때 발생

    //Auth Exception
    AUTHENTICATION_EXCEPTION("로그인 정보가 일치하지 않습니다."),
    ALREADY_LOGOUT_EXCEPTION("이미 로그아웃 되었습니다.");

    private final String statusMessage;
}
