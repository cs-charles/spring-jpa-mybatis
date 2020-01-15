package com.orange.spring.jpabatis.domain.repository;

import com.orange.spring.jpabatis.domain.dto.EmployeeDTO;
import com.orange.spring.jpabatis.domain.model.employee.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>,CustomizeEmployeeRepository {
}
