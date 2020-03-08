package me.zhengjie.modules.haixue.repository;

import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.domain.StudentGroupby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 17:31
 */
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    @Query(value="select count(1) as total,postgraduate_year as pyear,sum(amount) as amount from student group by postgraduate_year order by postgraduate_year",nativeQuery=true)
     List<StudentGroupby> getTotalAmount();

}
