package me.zhengjie.modules.haixue.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-05 3:12
 */

public enum  ProcessStatusEnum {
    INIT(1),FINANCE_PASS(2),FINANCE_REJECT(3),FIN(4)
    ;

    private Integer status;

    ProcessStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
