package me.zhengjie.modules.haixue.domain.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-03-22 18:19
 */
@Getter
@Setter
public class StudentChartTableVo {

    private Integer year;

    private String schoolName;
    /**
     * 非学员,报名人数
     */
    private Integer initNums;
    /**
     * 意向学员报名人数
     */
    private Integer intentionNums;
    /**
     * 预报名学生数量
     */
    private Integer presignupNums;
    /**
     * 交定金学员人数
     */
    private Integer subscriptionNums;
    /**
     * 全款学员数量
     */
    private Integer fullpaymentNums;
    /**
     * 总收入
     */
    private double totalAmount;
}
