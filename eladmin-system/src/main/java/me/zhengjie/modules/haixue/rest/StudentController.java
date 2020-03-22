package me.zhengjie.modules.haixue.rest;

import cn.hutool.core.date.DateTime;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.domain.vo.StudentChartTableVo;
import me.zhengjie.modules.haixue.domain.vo.StudentChartVo;
import me.zhengjie.modules.haixue.service.StudentService;
import me.zhengjie.modules.haixue.service.dto.StudentQueryCriteriaDto;
import me.zhengjie.modules.system.domain.Dict;
import me.zhengjie.modules.system.service.DictService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 16:30
 */
@Api(tags = "学生管理")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DictService dictService;

    public StudentController(StudentService service){
        this.studentService = service;
    }
    @Log("查询应用")
    @ApiOperation(value = "查询应用")
    @GetMapping
    public ResponseEntity<Object> getStudents(StudentQueryCriteriaDto criteria, Pageable pageable){
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        RoleSmallDto roleSmallDto =  roleService.getHighestRole(user.getId());
        String dataScope =  roleSmallDto.getDataScope();
        if("本级".equalsIgnoreCase(dataScope)){
            criteria.setUserId(user.getId());
        }else if("本校".equalsIgnoreCase(dataScope)){
           String name = user.getDept().getName();
           Dict dict =  dictService.queryByRemark(name);
           criteria.setSchoolId(dict.getName());
        }
        return new ResponseEntity<>(studentService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @Log("新增学生")
    @ApiOperation(value = "新增应用")
    @PostMapping
    @PreAuthorize("@el.check('app:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Student resources){
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        resources.setUserId(user.getId());
        return new ResponseEntity<>(studentService.create(resources),HttpStatus.CREATED);
    }


    @Log("修改学生")
    @ApiOperation(value = "修改学生")
    @PutMapping
    @PreAuthorize("@el.check('app:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Student resources){
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        resources.setUserId(user.getId());
        resources.setModifiedTime(new Date());
        studentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除应用")
    @ApiOperation(value = "删除应用")
    @DeleteMapping
    @PreAuthorize("@el.check('app:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        studentService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("柱状图报表数据")
    @GetMapping(value = "/chart")
    @AnonymousAccess
    public ResponseEntity<StudentChartVo> chart(){
        StudentChartVo  studentChartVo = studentService.chart();
        return new ResponseEntity<>(studentChartVo,HttpStatus.OK);
    }

    @Log("柱状图报表数据")
    @GetMapping(value = "/charttable")
    @AnonymousAccess
    public ResponseEntity<List<StudentChartTableVo>> charttable(){
        List<StudentChartTableVo> list = studentService.charttable();
        return new ResponseEntity<List<StudentChartTableVo>>(list,HttpStatus.OK);
    }

}
