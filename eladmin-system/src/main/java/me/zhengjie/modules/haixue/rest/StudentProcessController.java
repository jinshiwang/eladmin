package me.zhengjie.modules.haixue.rest;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.modules.haixue.domain.ProcessRecord;
import me.zhengjie.modules.haixue.domain.ProcessStatusEnum;
import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.repository.ProcessRecordRepository;
import me.zhengjie.modules.haixue.repository.StudentRepository;
import me.zhengjie.modules.haixue.service.dto.StudentTaskDto;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.StringUtils;
import me.zhengjie.utils.UserHolderUtils;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-03 22:02
 */
@Controller
@RequestMapping(value = "/api/studentexpense")
public class StudentProcessController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessRecordRepository processRecordRepository;
    @Autowired
    private StudentRepository studentRepository;
    /**
     * 添加报销
     * @param studentId    学生id
     */
    @RequestMapping(value = "add")
    @ResponseBody
    public ResponseEntity addExpense(Long studentId) {
        //启动流程
        ProcessRecord processRecord =  processRecordRepository.selectByStudentId(studentId);
        if(processRecord!=null){
            return new ResponseEntity("该学生当前存在财务审核流程：" + processRecord.getId(),HttpStatus.FORBIDDEN);
        }
        HashMap<String, Object> map = new HashMap<>();
        Long userId =  UserHolderUtils.getUserId();
        map.put("taskUser", userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("studentapp", map);
        processRecord = new ProcessRecord();
        processRecord.setProcessId(processInstance.getId());
        processRecord.setCurrentTaskId("0");
        processRecord.setUserId(userId);
        processRecord.setStudentId(studentId);
        processRecord.setStatus(ProcessStatusEnum.INIT.getStatus());
        Student student =  studentRepository.findById(studentId).get();
        processRecord.setContent(JSONObject.toJSONString(student));
        processRecord.setAmount(student.getAmount());
        processRecordRepository.save(processRecord);
        return new ResponseEntity("该学生当前存在财务审核流程：" + processRecord.getId(),HttpStatus.OK);
    }

    /**
     * 获取财务审批管理列表
     */
    @RequestMapping(value = "/financeTaskList")
    @ResponseBody
    @AnonymousAccess
    public ResponseEntity<Object> financeList(Pageable pageable) {
        List<StudentTaskDto> studentTasks = Lists.newArrayList();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("financeTaskList").orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            ProcessRecord processRecord =  processRecordRepository.selectByProcessIdStatus(task.getProcessInstanceId(),ProcessStatusEnum.INIT.getStatus());
            if(processRecord ==null){
                continue;
            }
            StudentTaskDto studentTaskDto = new StudentTaskDto();
            long studentId =  processRecord.getStudentId();
            String content =  processRecord.getContent();
            if(StringUtils.isNotEmpty(content)){
                Student student = JSONObject.parseObject(content,Student.class);
                BeanUtils.copyProperties(student,studentTaskDto);
                studentTaskDto.setTaskId(task.getId());
                studentTaskDto.setProcessId(task.getProcessInstanceId());
            }else {
                Student student =  studentRepository.findById(studentId).get();
                BeanUtils.copyProperties(student,studentTaskDto);
                studentTaskDto.setTaskId(task.getId());
                studentTaskDto.setProcessId(task.getProcessInstanceId());
            }
            studentTasks.add(studentTaskDto);
        }
        Page page = new PageImpl(studentTasks,pageable,studentTasks.size());
        return new ResponseEntity<>(PageUtil.toPage(page), HttpStatus.OK);
    }

    /**
     * 获取教务管理列表
     */
    @RequestMapping(value = "/managerTaskList")
    @ResponseBody
    @AnonymousAccess
    public ResponseEntity<Object> managerList(Pageable pageable) {
        List<StudentTaskDto> studentTasks = Lists.newArrayList();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managerTaskList").orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            ProcessRecord processRecord =  processRecordRepository.selectByProcessIdStatus(task.getProcessInstanceId(),ProcessStatusEnum.FINANCE_PASS.getStatus());
            if(processRecord ==null){
                continue;
            }
            StudentTaskDto studentTaskDto = new StudentTaskDto();
            long studentId =  processRecord.getStudentId();
            String content =  processRecord.getContent();
            if(StringUtils.isNotEmpty(content)){
                Student student = JSONObject.parseObject(content,Student.class);
                BeanUtils.copyProperties(student,studentTaskDto);
            }else {
                Student student =  studentRepository.findById(studentId).get();
                studentTaskDto.setTaskId(task.getId());
                studentTaskDto.setProcessId(task.getProcessInstanceId());
                BeanUtils.copyProperties(student,studentTaskDto);
            }

            studentTasks.add(studentTaskDto);
        }
        Page page = new PageImpl(studentTasks,pageable,studentTasks.size());
        return new ResponseEntity<>(PageUtil.toPage(page), HttpStatus.OK);
    }

    /**
     * 批准
     * @param taskId 任务ID
     */
    @RequestMapping(value = "apply")
    @ResponseBody
    @AnonymousAccess
    public String apply(String model,String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        ProcessRecord processRecord =  processRecordRepository.selectByProcessId(task.getProcessInstanceId());
        if("finance".equalsIgnoreCase(model)){
            processRecord.setStatus(ProcessStatusEnum.FINANCE_PASS.getStatus());
        }else {
            processRecord.setStatus(ProcessStatusEnum.FIN.getStatus());
        }
        processRecord.setCurrentTaskId(taskId);
        Long userId =  UserHolderUtils.getUserId();
        processRecord.setFinanceId(userId);
        processRecordRepository.save(processRecord);
        return "processed ok!";
    }

    /**
     * 拒绝
     */
    @ResponseBody
    @RequestMapping(value = "reject")
    @AnonymousAccess
    public String reject(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //返回到某个节点
//        List<Execution> executions = runtimeService.createExecutionQuery().parentId(task.getProcessInstanceId()).list();
//        List<String> curTaskKeys = executions.stream().map(e->e.getActivityId()).collect(Collectors.toList());
//        runtimeService.createChangeActivityStateBuilder()
//                .processInstanceId(task.getProcessInstanceId())
//                .moveActivityIdsToSingleActivityId(curTaskKeys, "studentTask")
//                .changeState();
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        ProcessRecord processRecord =  processRecordRepository.selectByProcessId(task.getProcessInstanceId());
        Long userId =  UserHolderUtils.getUserId();
        processRecord.setFinanceId(userId);
        processRecord.setStatus(ProcessStatusEnum.FINANCE_REJECT.getStatus());
        processRecord.setCurrentTaskId(taskId);
        processRecordRepository.save(processRecord);
        return "reject";
    }

    @ResponseBody
    @RequestMapping(value = "history")
    @AnonymousAccess
    public Object history(String processId){
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        //流程走完的不显示图
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        return pi;
    }
}
