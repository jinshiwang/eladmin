package me.zhengjie.modules.haixue.service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-05 2:31
 */
@Getter
@Setter
public class StudentTaskDto extends StudentDto {
    private String processId;
    private String taskId;
}
