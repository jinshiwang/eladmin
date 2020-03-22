package me.zhengjie.modules.haixue.service;

import me.zhengjie.modules.haixue.domain.AccountBook;
import me.zhengjie.modules.haixue.service.dto.AccountBookDto;
import me.zhengjie.modules.haixue.service.dto.AccountBookQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
public interface AccountBookService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(AccountBookQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<AccountBookDto>
    */
    List<AccountBookDto> queryAll(AccountBookQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return AccountBookDto
     */
    AccountBookDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return AccountBookDto
    */
    AccountBookDto create(AccountBook resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(AccountBook resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<AccountBookDto> all, HttpServletResponse response) throws IOException;
}