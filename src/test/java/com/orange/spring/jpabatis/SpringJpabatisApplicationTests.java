package com.orange.spring.jpabatis;

import com.orange.spring.jpabatis.domain.dto.EmployeeDTO;
import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;
import com.orange.spring.jpabatis.domain.repository.CompanyRepository;
import com.orange.spring.jpabatis.domain.repository.EmployeeRepository;
import com.orange.spring.jpabatis.mapper.CompanyMapper;
import com.orange.spring.jpabatis.mapper.EmployeeMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private EmployeeMapper employeeMapper;

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
        EmployeeDTO employeeDTO = employeeMapper.getEmployeeDTO(1);
        Assert.assertTrue(employeeDTO != null && employeeDTO.getEmployeeNo() != null);
    }

}
