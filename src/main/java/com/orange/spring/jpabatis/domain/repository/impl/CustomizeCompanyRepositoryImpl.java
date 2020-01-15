package com.orange.spring.jpabatis.domain.repository.impl;

import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;
import com.orange.spring.jpabatis.domain.repository.CustomizeCompanyRepository;
import com.orange.spring.jpabatis.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public class CustomizeCompanyRepositoryImpl implements CustomizeCompanyRepository {
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public List<CompanyEntity> getAllCompanyEntity() {
        return companyMapper.getCompanyList();
    }
}
