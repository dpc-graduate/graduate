package cn.sjxy.graduate.core.results;

import lombok.Getter;


@Getter
public enum AuthErrorEnum {
    ACCESS_DENIED(4031,"权限不足"),
    LOGIN_PARAM_ERROR(4302,"用户名密码错误"),
    AUTH_HEADER_ERROR(4304,"不合法的token验证"),
    ;

    private Integer code;
    private String message;

    AuthErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
