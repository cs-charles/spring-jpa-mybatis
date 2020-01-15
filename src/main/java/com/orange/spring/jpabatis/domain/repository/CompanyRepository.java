package com.orange.spring.jpabatis.domain.repository;

import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long>, CustomizeCompanyRepository,JpaSpecificationExecutor<CompanyEntity> {
    /**
     *  自定义jpa查询
     * @param companyName
     * @return
     */
    Optional<CompanyEntity> findFirstByCompanyName(String companyName);


}
