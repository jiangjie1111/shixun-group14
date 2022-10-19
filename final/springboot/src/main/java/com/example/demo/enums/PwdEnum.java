package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 太好听了吧
 * @version 1.0
 * @Description 默认密码枚举
 * @Date 2021/12/3 22:25
 */
@Getter
@AllArgsConstructor
public enum PwdEnum {

    /**
     * 默认密码
     */
    PASSWORD("123");

    private final String password;
}
