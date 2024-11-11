package com.example.closestv2.api.exception;

/**
 * @Valid에 직접 삽입하기 위해 문자열 상수로 선언
 */
public final class ExceptionMessageConstants {
    private ExceptionMessageConstants(){
        throw new IllegalStateException();
    }
    /* 이메일 */
    public static final String EMAIL_IS_REQUIRED = "이메일은 필수값입니다.";
    public static final String NOT_VALID_EMAIL= "올바른 이메일 형식이 아닙니다.";

    /* 비밀번호 */
    public static final String PASSWORD_IS_REQUIRED = "비밀번호는 필수값입니다.";
    public static final String CONFIRM_PASSWORD_IS_REQUIRED = "확인 비밀번호는 필수값입니다.";
    public static final String NOT_VALID_PASSWORD = "비밀번호는 대소문자와 특수문자가 하나 이상 씩 포함되어야합니다.";
    public static final String NOT_VALID_CONFIRM_PASSWORD_FORM = "확인 비밀번호는 대소문자와 특수문자가 하나 이상 씩 포함되어야합니다.";
    public static final String NOT_VALID_PASSWORD_SIZE = "비밀번호는 최소 8자 이상 최대 64자 이하입니다";
    public static final String NOT_VALID_CONFIRM_PASSWORD_SIZE = "확인 비밀번호는 최소 8자 이상 최대 64자 이하입니다";
    public static final String NOT_EQUAL_PASSWORDS = "비밀번호와 확인 비밀번호가 다릅니다.";
    public static final String WRONG_PASSWORD = "비밀번호가 일치하지 않습니다.";

    /* URL */
    public static final String URL_IS_REQUIRED = "URL은 필수값입니다.";

    /* 아이디 */
    public static final String DUPLICATED_ID = "이미 사용 중인 아이디입니다.";

    /* 회원 */
    public static final String MEMBER_NOT_FOUND = "존재하지 않는 회원입니다.";

    /* 토큰 */
    public static final String EXPIRED_TOKEN = "토큰이 만료되었습니다.";
}
