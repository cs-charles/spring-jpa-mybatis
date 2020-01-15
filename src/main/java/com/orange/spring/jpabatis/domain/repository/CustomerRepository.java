package com.orange.spring.jpabatis.domain.repository;

import com.orange.spring.jpabatis.domain.model.customer.CustomerEntity;
import com.orange.spring.jpabatis.domain.repository.base.BaseJpaRepository;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
public interface CustomerRepository extends BaseJpaRepository<CustomerEntity,Long> {
}
