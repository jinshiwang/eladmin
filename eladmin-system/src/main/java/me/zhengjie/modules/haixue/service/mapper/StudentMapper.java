package me.zhengjie.modules.haixue.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.haixue.domain.Student;
import me.zhengjie.modules.haixue.service.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 17:37
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper extends BaseMapper<StudentDto,Student> {
}
