package me.zhengjie.modules.haixue.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.haixue.domain.AccountBook;
import me.zhengjie.modules.haixue.service.dto.AccountBookDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jinshi.wang
* @date 2020-03-22
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountBookMapper extends BaseMapper<AccountBookDto, AccountBook> {

}