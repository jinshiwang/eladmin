package me.zhengjie.modules.haixue.domain;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-17 18:01
 */

public enum  DataScopeEnum {
    SELF(1,"本级"),SCHOOL_ADMIN(2,"本校"),ADMIN(3,"ALL")
    ;

    DataScopeEnum(Integer scope, String desc) {
        this.scope = scope;
        this.desc = desc;
    }

    private Integer scope;
    private String desc;

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
