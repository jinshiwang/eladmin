package me.zhengjie.modules.haixue.service;

import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.domain.vo.StudentChartTableVo;
import me.zhengjie.modules.haixue.domain.vo.StudentChartVo;
import me.zhengjie.modules.haixue.service.dto.StudentDto;
import me.zhengjie.modules.haixue.service.dto.StudentQueryCriteriaDto;
import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.modules.mnt.service.dto.AppDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 16:42
 */

public interface StudentService {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(StudentQueryCriteriaDto criteria, Pageable pageable);


    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    StudentDto findById(Long id);


    /**
     * 创建
     * @param stu /
     * @return /
     */
    StudentDto create(Student stu);

    /**
     * 编辑
     * @param stu /
     */
    void update(Student stu);

    void delete(Set<Long> ids);

    StudentChartVo chart();

    List<StudentChartTableVo> charttable();
}
