package com.orange.spring.jpabatis.domain.model.employee;
import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author xieyong
 * @date 2020/1/14
 * @Description:
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id","employeeName","employeeNo","address"})
@Table(name = "`employee`")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String employeeName;

    private String employeeNo;

    private String address;

    @JoinColumn(name = "companyId")
    @ManyToOne(cascade = {CascadeType.ALL})
    private CompanyEntity companyEntity;
}
