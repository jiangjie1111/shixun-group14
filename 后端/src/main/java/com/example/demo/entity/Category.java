package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("category")
@Data
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer pid;

    @TableField(exist = false)
    private List<Category> children;
}
