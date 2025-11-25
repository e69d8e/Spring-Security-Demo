package com.li.securitydemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String authority;
}
