package com.orange.spring.jpabatis.domain.dao;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xieyong
 * @date 2020/1/19
 * @Description:
 */
@Data
@Table(name = "`company`")
public class CompanyDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;

    private String companyAddress;
}
