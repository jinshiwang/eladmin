package me.zhengjie.modules.haixue.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 16:48
 */
@Getter
@Setter
public class StudentQueryCriteriaDto {

    /**
     * 模糊
     */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    @Query(type = Query.Type.EQUAL)
    private Long userId;

    @Query(type = Query.Type.EQUAL)
    private String schoolId;
}
