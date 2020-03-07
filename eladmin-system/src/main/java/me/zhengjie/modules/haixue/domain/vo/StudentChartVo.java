package me.zhengjie.modules.haixue.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-03-06 18:57
 */
@Getter
@Setter
public class StudentChartVo {
    //年月
    private int[] xAxis;
    private List<Integer> total;
    private  List<BigDecimal> amount;

}
