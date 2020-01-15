package com.orange.spring.jpabatis.domain.repository;

import com.orange.spring.jpabatis.domain.dto.EmployeeDTO;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public interface CustomizeEmployeeRepository {

    /**
     * 获取employee dto对象
     * @param employeeId
     * @return
     */
    EmployeeDTO getEmployeeDTO(Integer employeeId);
}
