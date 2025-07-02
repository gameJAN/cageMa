package com.pn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//接收分配角色 请求json数据的DTO类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleData {
    private Integer userId;

    private List<String> roleCheckList;
}
