package com.orange.spring.jpabatis.mapper;

import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
@Repository
public interface CompanyMapper {

    /**
     * 查询公司集合
     * @return
     */
    List<CompanyEntity> getCompanyList();
}
