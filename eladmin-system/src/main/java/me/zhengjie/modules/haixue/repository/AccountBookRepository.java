package me.zhengjie.modules.haixue.repository;

import me.zhengjie.modules.haixue.domain.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
public interface AccountBookRepository extends JpaRepository<AccountBook, Long>, JpaSpecificationExecutor<AccountBook> {
}