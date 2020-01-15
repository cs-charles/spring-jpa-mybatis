package com.orange.spring.jpabatis.mapper;

import com.orange.spring.jpabatis.domain.dto.EmployeeDTO;
import org.springframework.data.repository.query.Param;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public interface EmployeeMapper {

    /**
     *  获取雇员对象
     * @param employeeId
     * @return
     */
    EmployeeDTO getEmployeeDTO(@Param("employeeId") Integer employeeId);
}
