package me.zhengjie.modules.haixue.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
@Data
public class AccountBookDto implements Serializable {

    /** id */
    private Long id;

    private String schoolId;

    /** 学校 */
    private String schoolName;

    private String categoryId;

    /** 项目 */
    private String categoryName;

    /** 缴费总额 */
    private BigDecimal amount;

    /** 录入员id */
    private Long userId;

    private String remark;

    private Date modifiedTime;

    private Timestamp gmtTime;
}