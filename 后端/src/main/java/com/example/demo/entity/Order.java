package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@TableName("t_order")
@Data
public class Order {

    @TableId(type = IdType.AUTO)
    public Integer id;
    public String name;
    public String totalPrice;
    public String payPrice;
    public String discount;
    public String transportPrice;
    public Integer tp;
    public Integer tn;
    public Integer fp;
    public Integer fn;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    //private Date payTime;
    //private Integer state;

}
