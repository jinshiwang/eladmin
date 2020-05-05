package me.zhengjie.modules.haixue.repository;

import me.zhengjie.modules.haixue.domain.ProcessRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-04 14:49
 */
public interface ProcessRecordRepository extends JpaRepository<ProcessRecord, Long>, JpaSpecificationExecutor<ProcessRecord> {

    @Query(value="select id,process_id,current_task_id,student_id,user_id,status,content,modified_time,gmt_time from process_record  where student_id=?1 and status=1 order by id desc limit 1",nativeQuery=true)
    ProcessRecord selectByStudentId(Long studentId);

    @Query(value="select id,process_id,current_task_id,student_id,user_id,status,content,modified_time,gmt_time from process_record  where process_id=?1 and status=?2 order by id desc limit 1",nativeQuery=true)
    ProcessRecord selectByProcessIdStatus(String processId,Integer status);

    @Query(value="select id,process_id,current_task_id,student_id,user_id,status,content,modified_time,gmt_time from process_record  where process_id=?1 order by id desc limit 1",nativeQuery=true)
    ProcessRecord selectByProcessId(String processId);
}
