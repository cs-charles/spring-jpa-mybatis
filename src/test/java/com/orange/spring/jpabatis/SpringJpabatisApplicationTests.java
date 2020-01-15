package com.orange.spring.jpabatis;

import com.orange.spring.jpabatis.domain.dto.EmployeeDTO;
import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;
import com.orange.spring.jpabatis.domain.model.customer.CustomerEntity;
import com.orange.spring.jpabatis.domain.repository.CompanyRepository;
import com.orange.spring.jpabatis.domain.repository.CustomerRepository;
import com.orange.spring.jpabatis.domain.repository.EmployeeRepository;
import com.orange.spring.jpabatis.mapper.CompanyMapper;
import com.orange.spring.jpabatis.mapper.EmployeeMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJpabatisApplicationTests {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void test1() {
        //jpa 查询
        Optional<CompanyEntity> optionalCompanyEntity = companyRepository.findFirstByCompanyName("云集网络");
        Assert.assertTrue(optionalCompanyEntity.isPresent());

        //mybatis 查询
        List<CompanyEntity> list1 = companyMapper.getCompanyList();
        Assert.assertTrue(list1.size() > 0);

        //jpa + mybatis查询
        List<CompanyEntity> list2 = companyRepository.getAllCompanyEntity();
        Assert.assertTrue(list2.size() > 0);
    }

    @Test
    public void test2(){
        //jpa + mybatis 查询dto对象
        EmployeeDTO employeeDTO = employeeRepository.getEmployeeDTO(1);
        Assert.assertTrue(employeeDTO != null && employeeDTO.getEmployeeNo() != null);
    }

    @Test
    public void test3(){
        Specification specification = customerRepository.support().eq("id",1)
                .and(
                        customerRepository.support().like("customerName",null)
                );
        //Specification查询
        Optional<CustomerEntity>  optional =customerRepository.findOne(specification);
        Assert.assertTrue(optional.isPresent());
    }

}
