package com.example.closestv2.util.validation;

/**
 * @Valid에 직접 삽입하기 위해 문자열 상수로 선언
 */
public final class Pattern {
    // 대소문자, 숫자, 특수문자를 하나 이상씩 포함하고 8자 이상
    public static final String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,}$";

    private Pattern() {
        throw new IllegalStateException("Pattern은 생성할 수 없는 클래스입니다.");
    }
}
