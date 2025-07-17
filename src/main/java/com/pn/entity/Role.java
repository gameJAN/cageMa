package com.pn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    private Integer roleId;//角色id

    private String roleName;//角色名称

    private String roleDesc;//角色描述

    private String roleCode;//角色标识

    private String roleState;//角色状态

    private Integer createBy;//创建角色的用户id

    //json转换的日期格式
    private Date createTime;//创建时间

    private Integer updateBy;//修改角色的用户id

    private Date updateTime;//修改时间

    private String getCode; //角色创建人

}
