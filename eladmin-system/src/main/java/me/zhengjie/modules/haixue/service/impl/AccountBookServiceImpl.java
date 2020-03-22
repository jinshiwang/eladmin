package me.zhengjie.modules.haixue.service.impl;

import me.zhengjie.modules.haixue.domain.AccountBook;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.haixue.repository.AccountBookRepository;
import me.zhengjie.modules.haixue.service.AccountBookService;
import me.zhengjie.modules.haixue.service.dto.AccountBookDto;
import me.zhengjie.modules.haixue.service.dto.AccountBookQueryCriteria;
import me.zhengjie.modules.haixue.service.mapper.AccountBookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
@Service
//@CacheConfig(cacheNames = "accountBook")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookRepository accountBookRepository;

    private final AccountBookMapper accountBookMapper;

    public AccountBookServiceImpl(AccountBookRepository accountBookRepository, AccountBookMapper accountBookMapper) {
        this.accountBookRepository = accountBookRepository;
        this.accountBookMapper = accountBookMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(AccountBookQueryCriteria criteria, Pageable pageable){
        Page<AccountBook> page = accountBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(accountBookMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<AccountBookDto> queryAll(AccountBookQueryCriteria criteria){
        return accountBookMapper.toDto(accountBookRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public AccountBookDto findById(Long id) {
        AccountBook accountBook = accountBookRepository.findById(id).orElseGet(AccountBook::new);
        ValidationUtil.isNull(accountBook.getId(),"AccountBook","id",id);
        return accountBookMapper.toDto(accountBook);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public AccountBookDto create(AccountBook resources) {
        return accountBookMapper.toDto(accountBookRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(AccountBook resources) {
        AccountBook accountBook = accountBookRepository.findById(resources.getId()).orElseGet(AccountBook::new);
        ValidationUtil.isNull( accountBook.getId(),"AccountBook","id",resources.getId());
        accountBook.copy(resources);
        accountBookRepository.save(accountBook);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            accountBookRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AccountBookDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AccountBookDto accountBook : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" schoolId",  accountBook.getSchoolId());
            map.put("学校", accountBook.getSchoolName());
            map.put(" categoryId",  accountBook.getCategoryId());
            map.put("项目", accountBook.getCategoryName());
            map.put("缴费总额", accountBook.getAmount());
            map.put("录入员id", accountBook.getUserId());
            map.put(" remark",  accountBook.getRemark());
            map.put(" modifiedTime",  accountBook.getModifiedTime());
            map.put(" gmtTime",  accountBook.getGmtTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}