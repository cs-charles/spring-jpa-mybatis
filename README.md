# spring-jpa-mybatis
spring boot整合jpa+mybatis

配置见application.properties

* 首先定义一个jpa接口，继承jpa提供的抽象接口，然后自定义一个接口，jpa接口继承自定义接口，自定义接口实现可以采用mybatis或者jdbc任意一种方式
  例如CompanyRepository
```
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long>, CustomizeCompanyRepository,JpaSpecificationExecutor<CompanyEntity>
```
* 如果项目使用CQRS架构，可以分别写一个jpa接口用作增删改，一个Mybatis接口负责查询，可以继承tkMybatis的接口，这样简单的查询就不用自己写了
  
  总之，使用方式很灵活，具体看项目需求。如果不存在多数据源的情况下，是不会存在事物问题。多数据源的情况还没遇到过，具体遇到可以再解决。

## 遇到的几个问题
* Mybatis报错，找不到SqlProvider对象
错误原因是由于@MapperScan的包导的不是tkMybatis的包

* 在OneToMany关系中，保存一个对象(CompanyEntity)，出现了很多查询sql
```
 [http-nio-8000-exec-1] WARN  o.h.e.loading.internal.LoadContexts - HHH000100: Fail-safe cleanup (collections) : org.hibernate.engine.loading.internal.CollectionLoadContext@2203bd6e<rs=com.alibaba.druid.pool.DruidPooledResultSet@668512ae>
```
错误原因是由于Set集合会去比较对象是否相等，由于采用了Lombok，没有自己重写equal和hashcode方法,所以导致无限的循环。
解决方案就是在EmployeeEntity对象中排除CompanyEntity对象
另外，如果EmployeeEntity对象的除id外的其他属性允许相同，就不能采用Set集合，改用List集合。或者自定义equal和hashcode方法
    
* 对象在OneToMany关系中，对象在ToString时，出现stackOverFlow
错误原因是由于存在循环引用导致的，重写ToString即可解决。


