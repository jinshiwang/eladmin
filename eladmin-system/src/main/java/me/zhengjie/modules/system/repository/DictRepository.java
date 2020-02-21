package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
* @author Zheng Jie
* @date 2019-04-10
*/
public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {
    @Query("SELECT d FROM Dict d where d.name=:name")
    public Dict findByName(@Param("name") String name);

    @Query("SELECT d FROM Dict d where d.remark=:remark")
    public Dict findByRemark(@Param("remark") String remark);
}