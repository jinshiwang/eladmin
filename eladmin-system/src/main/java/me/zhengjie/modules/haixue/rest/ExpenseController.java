package me.zhengjie.modules.haixue.rest;

import me.zhengjie.annotation.AnonymousAccess;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

import org.flowable.task.api.Task;
/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-03 1:51
 */
@Controller
@RequestMapping(value = "expense")
public class ExpenseController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    /**
     * 添加报销
      */
    @RequestMapping(value = "add")
    @ResponseBody
    @AnonymousAccess
    public String addExpense(String studentId) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", studentId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return "提交成功.流程Id为：" + processInstance.getId();

    }

    /**
     * 获取审批管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @AnonymousAccess
    public Object list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        return tasks.toArray().toString();
    }

    /**
     * 获取审批管理列表
     */
    @RequestMapping(value = "/managerList")
    @ResponseBody
    @AnonymousAccess
    public Object managerList(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        return tasks.toArray().toString();
    }

    /**
     * 获取审批管理列表
     */
    @RequestMapping(value = "/bossList")
    @ResponseBody
    @AnonymousAccess
    public Object bossList(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("boss").orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        return tasks.toArray().toString();
    }

    /**
     * 批准
     *
     * @param taskId 任务ID
     */
    @RequestMapping(value = "apply")
    @ResponseBody
    @AnonymousAccess
    public String apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return "processed ok!";
    }

    /**
     * 拒绝
     */
    @ResponseBody
    @RequestMapping(value = "reject")
    @AnonymousAccess
    public String reject(String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return "reject";
    }
}
