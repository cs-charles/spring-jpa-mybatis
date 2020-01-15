package com.orange.spring.jpabatis.domain.repository.impl;

import com.orange.spring.jpabatis.domain.dto.EmployeeDTO;
import com.orange.spring.jpabatis.domain.repository.CustomizeEmployeeRepository;
import com.orange.spring.jpabatis.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public class CustomizeEmployeeRepositoryImpl implements CustomizeEmployeeRepository {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO getEmployeeDTO(Integer employeeId) {
        return employeeMapper.getEmployeeDTO(employeeId);
    }
}
