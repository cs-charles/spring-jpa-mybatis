package com.orange.spring.jpabatis.domain.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

/**
 * @author hexm
 * @date 2019/8/20 10:54
 */
@NoRepositoryBean
public interface BaseJpaRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
 
    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByIdIn(Collection<ID> ids);

    /**
     * 提供默认复杂查询支持
     * @return
     */
    default SpecificationSupport<T> support(){
        return SpecificationSupport.of();
    }
}