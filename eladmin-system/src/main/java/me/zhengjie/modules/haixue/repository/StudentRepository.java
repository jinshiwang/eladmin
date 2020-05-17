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

    @Query(value="select count(1) as total,postgraduate_year as pyear,IFNULL(sum(p.amount),0) as amount from student s left join process_record p " +
            " on s.id=p.student_id where p.status>=3 and if(?1>0,p.user_id=?1,1=1) and if(?2!='',s.school_id=?2,1=1)  group by postgraduate_year order by postgraduate_year",nativeQuery=true)
     List<StudentGroupby> getTotalAmount(Long userId,String schoolId);

    @Query(value="select count(1) as nums,postgraduate_year as pyear,school_name as schoolName,s.status,IFNULL(sum(p.amount),0) as amount   from student s left join process_record p " +
            "on s.id=p.student_id  where p.status>=3 and if(?1>0,p.user_id=?1,1=1) and if(?2!='',s.school_id=?2,1=1) group by postgraduate_year,school_name,s.status order by postgraduate_year",nativeQuery=true)
    List<StudentGroupby> group(Long userId,String schoolId);
}
