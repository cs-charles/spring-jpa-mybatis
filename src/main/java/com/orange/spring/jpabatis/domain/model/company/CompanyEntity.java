package com.orange.spring.jpabatis.domain.model.company;
import com.orange.spring.jpabatis.domain.model.employee.EmployeeEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * @author xieyong
 * @date 2020/1/14
 * @Description:
 */
@Data
@Table(name = "`company`")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;

    private String companyAddress;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "companyEntity")
    private Set<EmployeeEntity> employeeEntitySet;
}
