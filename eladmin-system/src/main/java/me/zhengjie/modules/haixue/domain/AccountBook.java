package me.zhengjie.modules.haixue.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
@Entity
@Data
@Table(name="account_book")
public class AccountBook implements Serializable {

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "school_id")
    private String schoolId;

    /** 学校 */
    @Column(name = "school_name",nullable = false)
    @NotBlank
    private String schoolName;

    @Column(name = "category_id")
    private String categoryId;

    /** 项目 */
    @Column(name = "category_name",nullable = false)
    @NotBlank
    private String categoryName;

    /** 缴费总额 */
    @Column(name = "amount",nullable = false)
    @NotNull
    private BigDecimal amount;
    /** 缴费总额 */
    @Column(name = "year",nullable = false)
    private Integer year;

    /** 录入员id */
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "modified_time")
    private Date modifiedTime;

    @Column(name = "gmt_time")
    @CreationTimestamp
    private Timestamp gmtTime;

    public void copy(AccountBook source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}