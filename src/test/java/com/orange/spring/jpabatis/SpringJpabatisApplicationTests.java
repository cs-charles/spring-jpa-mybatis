package com.orange.spring.jpabatis;

import com.orange.spring.jpabatis.domain.dao.CompanyDO;
import com.orange.spring.jpabatis.domain.dao.CustomerDO;
import com.orange.spring.jpabatis.domain.dto.EmployeeDTO;
import com.orange.spring.jpabatis.domain.model.company.CompanyEntity;
import com.orange.spring.jpabatis.domain.model.customer.CustomerEntity;
import com.orange.spring.jpabatis.domain.model.employee.EmployeeEntity;
import com.orange.spring.jpabatis.domain.repository.CompanyRepository;
import com.orange.spring.jpabatis.domain.repository.CustomerRepository;
import com.orange.spring.jpabatis.domain.repository.EmployeeRepository;
import com.orange.spring.jpabatis.mapper.CompanyMapper;
import com.orange.spring.jpabatis.mapper.CustomerMapper;
import com.orange.spring.jpabatis.mapper.EmployeeMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@RunWith(SpringRunner.class)
@Transactional
@Rollback
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

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void test1() {
        //jpa 查询
        Optional<CompanyEntity> optionalCompanyEntity = companyRepository.findFirstByCompanyName("云集网络");
        Assert.assertTrue(optionalCompanyEntity.isPresent());
        //jpa保存
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName("云影网络");
        Set<EmployeeEntity> set = new HashSet<>();
        EmployeeEntity employeeEntity1 = new EmployeeEntity();
        employeeEntity1.setEmployeeNo("23425");
        employeeEntity1.setEmployeeName("刘备");
        EmployeeEntity employeeEntity2 = new EmployeeEntity();
        employeeEntity2.setEmployeeNo("234256");
        employeeEntity2.setEmployeeName("刘备");
        set.add(employeeEntity1);
        set.add(employeeEntity2);
        companyEntity.setEmployeeEntitySet(set);
        companyRepository.save(companyEntity);
        //jpa查询所有
        List<CompanyEntity> companyEntityList2 = companyRepository.findAll();

        //tkMybatis查询
        List<CompanyEntity> list1 = companyMapper.getCompanyList();
        Assert.assertTrue(list1.size() > 0);
        CompanyDO companyDO = companyMapper.selectByPrimaryKey(1L);
        Assert.assertTrue(companyDO != null && companyDO.getCompanyName() != null);
        //tKMybatis保存
        CompanyDO company = new CompanyDO();
        company.setCompanyName("云荣网络");
        companyMapper.insert(company);

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
                .like("customerName",null);
        //Specification查询
        Optional<CustomerEntity>  optional =customerRepository.findOne(specification);
        Assert.assertTrue(optional.isPresent());
    }

    @Test
    public void test4(){
        CustomerDO customerDO = customerMapper.selectByPrimaryKey(1L);
        Assert.assertTrue(customerDO != null && customerDO.getId() != null);
    }

}
