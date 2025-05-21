package com.pn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 存储用户登录信息的User类：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrentUser {

    private int userId;

    private String userCode;//用户名

    private String userName;//用户状态

}
