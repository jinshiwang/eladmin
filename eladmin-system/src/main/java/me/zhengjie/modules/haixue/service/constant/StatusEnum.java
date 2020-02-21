package me.zhengjie.modules.haixue.service.constant;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-21 5:18
 */
public enum  StatusEnum {
    INIT(0,"非学员"),
    INTENTION(1,"意向"),
    PRESIGNUP(2,"预报名"),
    SUBSCRIPTION(3,"订金"),
    FULLPAYMENT(4,"全款")
    ;
    StatusEnum(Integer status,String statusDesc){
        this.status = status;
        this.statusDesc = statusDesc;
    }
    private int status;
    private String statusDesc;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public static StatusEnum getByCode(Integer code){
        for(StatusEnum statusEnum:StatusEnum.values()){
            if(statusEnum.getStatus()==code){
                return statusEnum;
            }
        }
        return null;
    }
}
