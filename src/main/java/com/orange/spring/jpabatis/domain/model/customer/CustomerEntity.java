package com.orange.spring.jpabatis.domain.model.customer;

import lombok.Data;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author xieyong
 * @date 2020/1/15
 * @Description:
 */
@Data
@Entity
@Table(name = "`customer`")
@EntityListeners(AuditingEntityListener.class)
public class CustomerEntity extends AbstractAggregateRoot<CustomerEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerNo;

    private String customerName;

    private Integer age;

    private Integer sex;

    private String address;
}
