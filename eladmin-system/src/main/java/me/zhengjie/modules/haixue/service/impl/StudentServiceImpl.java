package me.zhengjie.modules.haixue.service.impl;

import cn.hutool.core.date.DateTime;
import com.google.common.collect.Lists;
import me.zhengjie.modules.haixue.domain.ProcessRecord;
import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.domain.StudentGroupby;
import me.zhengjie.modules.haixue.domain.vo.StudentChartTableVo;
import me.zhengjie.modules.haixue.domain.vo.StudentChartVo;
import me.zhengjie.modules.haixue.repository.ProcessRecordRepository;
import me.zhengjie.modules.haixue.repository.StudentRepository;
import me.zhengjie.modules.haixue.service.StudentService;
import me.zhengjie.modules.haixue.service.constant.StatusEnum;
import me.zhengjie.modules.haixue.service.dto.StudentDto;
import me.zhengjie.modules.haixue.service.dto.StudentQueryCriteriaDto;
import me.zhengjie.modules.haixue.service.mapper.StudentMapper;
import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.domain.DictDetail;
import me.zhengjie.modules.system.service.DictService;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private ProcessRecordRepository processRecordRepository;

    @Autowired
    private  DictService dictService;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper,ProcessRecordRepository processRecordRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.processRecordRepository = processRecordRepository;
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
        Page<StudentDto> pageDtos =  page.map(studentMapper::toDto);
        //判断是否启动了流程，如果启动，设置为true
        for(StudentDto studentDto : pageDtos.getContent()){
            ProcessRecord processRecord =   processRecordRepository.selectByStudentId(studentDto.getId());
            if(processRecord!=null){
                studentDto.setProcessFlag(true);
            }

        }
        return PageUtil.toPage(pageDtos);
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
        Dict dict =  dictService.queryByName(stu.getSchoolId());
        if(dict!=null){
            stu.setSchoolName(dict.getRemark());
        }
        return studentMapper.toDto(studentRepository.save(stu));
    }

    /**
     * 编辑
     *
     * @param resources /
     */
    @Override
    public void update(Student resources) {
        Dict dict =  dictService.queryByName(resources.getSchoolId());
        if(dict!=null){
            resources.setSchoolName(dict.getRemark());
            String departMentId =  resources.getDepartmentId();
            DictDetail dictDetail =   dict.getDictDetails().stream().filter(e->e.getValue().equalsIgnoreCase(departMentId)).findFirst().get();
            resources.setDepartmentName(dictDetail.getLabel());
        }
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

    @Override
    public StudentChartVo chart() {
        DateTime dateTime = DateTime.now();
        //2019年至今的统计
        int[]  years = IntStream.rangeClosed(2019,dateTime.year()).toArray();
        StudentChartVo studentChartVo = new StudentChartVo();
        List<StudentGroupby> groups =  studentRepository.getTotalAmount();
        studentChartVo.setTotal(groups.stream().map(student -> student.getTotal()).collect(Collectors.toList()));
        studentChartVo.setAmount(groups.stream().map(student -> student.getAmount().divide(new BigDecimal(10000))).collect(Collectors.toList()));
        studentChartVo.setXAxis(years);
        Collections.reverse(groups);
        studentChartVo.setGroups(groups);
        return studentChartVo;
    }

    @Override
    public List<StudentChartTableVo> charttable() {
        List<StudentChartTableVo> list = Lists.newArrayList();
        List<StudentGroupby> groups =  studentRepository.group();
        Map<Integer,List<StudentGroupby>> map =  groups.stream().collect(Collectors.groupingBy(StudentGroupby::getPyear));
        map.forEach((k,v)->{
            List<StudentGroupby> groupByYear =  map.get(k);
            Map<String,List<StudentGroupby>> groupBySchoolName =  groupByYear.stream().collect(Collectors.groupingBy(StudentGroupby::getSchoolName));

            groupBySchoolName.forEach((k1,v1)->{
                StudentChartTableVo studentChartTableVo = new StudentChartTableVo();
                studentChartTableVo.setYear(k);
                studentChartTableVo.setSchoolName(k1);
                BigDecimal amout = new BigDecimal(0);
                for(StudentGroupby stu:v1){
                    amout =  amout.add(stu.getAmount());
                    if(StatusEnum.INIT.getStatus()==stu.getStatus()){
                        studentChartTableVo.setInitNums(stu.getNums());
                    }else if(StatusEnum.INTENTION.getStatus()==stu.getStatus()){
                        studentChartTableVo.setIntentionNums(stu.getNums());
                    }else if(StatusEnum.PRESIGNUP.getStatus()==stu.getStatus()){
                        studentChartTableVo.setPresignupNums(stu.getNums());
                    }else if(StatusEnum.SUBSCRIPTION.getStatus()==stu.getStatus()){
                        studentChartTableVo.setSubscriptionNums(stu.getNums());
                    }else if(StatusEnum.FULLPAYMENT.getStatus()==stu.getStatus()){
                        studentChartTableVo.setFullpaymentNums(stu.getNums());
                    }
                }
                studentChartTableVo.setTotalAmount(amout.doubleValue());
                list.add(studentChartTableVo);
            });
        });
        return list;
    }
}
