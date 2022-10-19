package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("book")
@Data
public class Book {
    @TableId(type = IdType.AUTO)
    public Integer id;
    public String name;
    public String price;
    public String author;
    //@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public String createTime;
    public String cover;
    public String tp;
    public String tn;
    public String fp;
    public String fn;
    // TableField注解表示数据库不存在的字段，而Java中需要使用，加上这个注解就不会报错
    @TableField(exist = false)
    private String username;
}
