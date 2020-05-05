package me.zhengjie.utils;

import me.zhengjie.modules.security.security.vo.JwtUser;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author jinshi.wang
 * @date 2020-05-04 15:27
 */
public class UserHolderUtils {
    /**
     * 获得登录用户id
     * @return
     */
    public static Long getUserId(){
        UserService userService =  SpringContextHolder.getBean(me.zhengjie.modules.system.service.UserService.class);
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        if(user == null){
            throw new RuntimeException("没有找到该用户");
        }
        return user.getId();
    }
}
