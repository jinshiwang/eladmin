package me.zhengjie.modules.haixue.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.zhengjie.modules.mnt.domain.App;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-02-16 15:32
 */
@Entity
@Getter
@Setter
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Column(name = "student_num")
    private String studentNum;
    @NotBlank
    @Column(name = "school_id")
    private String schoolId;
    @Column(name = "school_name")
    private String schoolName;
    @Column(name = "department_id")
    private String departmentId;
    @Column(name = "department_name")
    private String departmentName;
    @Column(name = "product")
    private String product;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "postgraduate_year")
    private Integer postgraduateYear;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "status")
    private Integer status;
    @NotBlank
    @Column(name = "phone")
    private String phone;
    @Column(name = "qq")
    private String qq;
    @Column(name = "identity_card")
    private String identityCard;
    @Column(name = "apartment_info")
    private String apartmentInfo;
    @Column(name = "room")
    private String room;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "remark")
    private String remark;
    @Column(name = "modified_time")
    private Date modifiedTime;
    @Column(name = "gmt_time")
    @CreationTimestamp
    private Timestamp gmtTime;

    public void copy(Student source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
