package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 太好听了吧
 * @version 1.0
 * @Description 角色枚举
 * @Date 2021/12/3 21:40
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN(1, "管理员", "admin"),

    /**
     * 普通用户
     */
    USER(2, "普通用户", "user");


    /**
     * 角色id
     */
    private final Integer roleId;

    /**
     * 描述
     */
    private final String name;

    /**
     * 权限标签
     */
    private final String label;
}
