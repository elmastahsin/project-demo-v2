package com.company.projectdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {
    private Long id;
    private String tableName;
    private Map<String,Object> changedColumn;
    private String operation;
    private UserDTO changedBy;
    private String changedAt;
}