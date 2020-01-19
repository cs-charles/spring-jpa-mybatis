package com.orange.spring.jpabatis.domain.model.company;
import com.orange.spring.jpabatis.domain.model.employee.EmployeeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

/**
 * @author xieyong
 * @date 2020/1/14
 * @Description:
 */
@Data
//需要重写toString,否则存在循环引用问题
@ToString(of = {"id","companyName","companyAddress","employeeEntitySet"})
@Table(name = "`company`")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;

    private String companyAddress;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "companyEntity",fetch = FetchType.EAGER)
    private Set<EmployeeEntity> employeeEntitySet;
}
