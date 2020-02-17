package me.zhengjie.modules.haixue.rest;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.service.StudentService;
import me.zhengjie.modules.haixue.service.dto.StudentQueryCriteriaDto;
import me.zhengjie.modules.mnt.domain.App;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public StudentController(StudentService service){
        this.studentService = service;
    }
    @Log("查询应用")
    @ApiOperation(value = "查询应用")
    @GetMapping
    public ResponseEntity<Object> getStudents(StudentQueryCriteriaDto criteria, Pageable pageable){
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

}
