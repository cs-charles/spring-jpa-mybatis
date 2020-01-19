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
@Table(name = "`customer`")
public class CustomerDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerNo;

    private String customerName;

    private Integer age;

    private Integer sex;

    private String address;
}
