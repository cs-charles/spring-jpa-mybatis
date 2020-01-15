package com.orange.spring.jpabatis.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable {

    private Integer id;

    private String employeeName;

    private String employeeNo;

    private String employeeAddress;

    private String companyName;

    private String companyAddress;
}
