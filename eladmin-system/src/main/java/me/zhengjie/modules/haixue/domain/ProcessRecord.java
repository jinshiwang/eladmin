package me.zhengjie.modules.haixue.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-04 14:35
 */
@Entity
@Getter
@Setter
@Table(name="process_record")
public class ProcessRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = ProcessRecord.Update.class)
    private Long id;
    @Column(name = "process_id")
    private String processId;
    @Column(name = "current_task_id")
    private String currentTaskId;
    @Column(name = "student_id")
    private Long  studentId;
    @Column(name = "user_id")
    private Long userId;
    /**
     * 1:使用中；2：结束
     */
    @Column(name = "status")
    private Integer status;
    @Column(name = "content")
    private String content;
    @Column(name = "modified_time")
    private Date modifiedTime;
    @Column(name = "gmt_time")
    @CreationTimestamp
    private Timestamp gmtTime;
    public @interface Update {}
}
