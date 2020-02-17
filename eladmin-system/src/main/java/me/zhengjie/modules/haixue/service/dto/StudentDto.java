package me.zhengjie.modules.haixue.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 17:43
 */
@Getter
@Setter
public class StudentDto {
    private Long id;
    private String name;
    private String studentNum;
    private String schoolId;
    private String schoolName;
    private String departmentId;
    private String departmentName;
    private String product;
    private BigDecimal amount;
    private Integer postgraduateYear;
    private Integer gender;
    private Integer status;
    private String phone;
    private String qq;
    private String identityCard;
    private String apartmentInfo;
    private String room;
    private Long userId;
    private String remark;
    private Date modifiedTime;
    private Timestamp gmtTime;
}
