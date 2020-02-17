package me.zhengjie.modules.haixue.service.impl;

import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.repository.StudentRepository;
import me.zhengjie.modules.haixue.service.StudentService;
import me.zhengjie.modules.haixue.service.dto.StudentDto;
import me.zhengjie.modules.haixue.service.dto.StudentQueryCriteriaDto;
import me.zhengjie.modules.haixue.service.mapper.StudentMapper;
import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 17:00
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    @Override
    public Object queryAll(StudentQueryCriteriaDto criteria, Pageable pageable) {
        Page<Student> page = studentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(studentMapper::toDto));
    }

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    @Override
    public StudentDto findById(Long id) {
        Student app = studentRepository.findById(id).orElseGet(Student::new);
      //  ValidationUtil.isNull(app.getId(),"App","id",id);
        return studentMapper.toDto(app);
    }

    /**
     * 创建
     *
     * @param stu /
     * @return /
     */
    @Override
    public StudentDto create(Student stu) {
        return studentMapper.toDto(studentRepository.save(stu));
    }

    /**
     * 编辑
     *
     * @param resources /
     */
    @Override
    public void update(Student resources) {
        Student app = studentRepository.findById(resources.getId()).orElseGet(Student::new);ValidationUtil.isNull(app.getId(),"App","id",resources.getId());
        app.copy(resources);
        studentRepository.save(app);
    }

    @Override
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            studentRepository.deleteById(id);
        }
    }
}
