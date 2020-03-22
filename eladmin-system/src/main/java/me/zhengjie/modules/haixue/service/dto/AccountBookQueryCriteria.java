package me.zhengjie.modules.haixue.service.dto;

import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
@Data
public class AccountBookQueryCriteria{

    /** 精确 */
    @Query(type = Query.Type.INNER_LIKE)
    private String schoolName;

    /** 精确 */
    @Query(type = Query.Type.INNER_LIKE)
    private String categoryName;

    /** 精确 */
    @Query(type = Query.Type.INNER_LIKE)
    private Long userId;
}