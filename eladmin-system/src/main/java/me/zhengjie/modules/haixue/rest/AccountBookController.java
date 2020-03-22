package me.zhengjie.modules.haixue.rest;

import cn.hutool.core.date.DateTime;
import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.haixue.domain.AccountBook;
import me.zhengjie.modules.haixue.service.AccountBookService;
import me.zhengjie.modules.haixue.service.dto.AccountBookQueryCriteria;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
@Api(tags = "AccountBookController管理")
@RestController
@RequestMapping("/api/accountBook")
public class AccountBookController {

    @Autowired
    private UserService userService;

    private final AccountBookService accountBookService;

    public AccountBookController(AccountBookService accountBookService) {
        this.accountBookService = accountBookService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('accountBook:list')")
    public void download(HttpServletResponse response, AccountBookQueryCriteria criteria) throws IOException {
        accountBookService.download(accountBookService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询账本")
    @ApiOperation("查询账本")
    @PreAuthorize("@el.check('accountBook:list')")
    public ResponseEntity<Object> getAccountBooks(AccountBookQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(accountBookService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增账本")
    @ApiOperation("新增账本")
    @PreAuthorize("@el.check('accountBook:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody AccountBook resources){
        DateTime dateTime = DateTime.now();
        resources.setYear(Integer.valueOf( dateTime.toDateStr().substring(0,4)));
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        resources.setUserId(user.getId());
        return new ResponseEntity<>(accountBookService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改账本")
    @ApiOperation("修改账本")
    @PreAuthorize("@el.check('accountBook:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody AccountBook resources){
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        resources.setUserId(user.getId());
        resources.setModifiedTime(new Date());
        accountBookService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除账本")
    @ApiOperation("删除账本")
    @PreAuthorize("@el.check('accountBook:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        accountBookService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}