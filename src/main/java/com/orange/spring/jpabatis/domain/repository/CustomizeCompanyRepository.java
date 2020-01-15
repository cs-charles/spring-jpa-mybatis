package com.orange.spring.jpabatis.domain.repository;

import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;

import java.util.List;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public interface CustomizeCompanyRepository {

    /**
     *  获取所有公司
     * @return
     */
    List<CompanyEntity> getAllCompanyEntity();
}
