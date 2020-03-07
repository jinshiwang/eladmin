package me.zhengjie.modules.haixue.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-03-07 0:52
 */
public interface StudentGroupby {

     Integer getTotal();

     Integer getPyear();

     BigDecimal getAmount();
}
