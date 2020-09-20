package com.agency.service.impl;

import com.agency.common.*;
import com.agency.common.Const.*;
import com.agency.pojo.*;
import com.agency.service.*;
import org.springframework.stereotype.*;

/**
 * @email chivseg-hao@qq.com
 * @author:ch33orange
 * @date: 2020/9/15
 * @time: 16:59
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    //backend

    @Override
    /**    todo 做验证用的
     * @Param User
     * @Return
     * 校验是否是管理员
     */
    public ServerResponse checkAdminRole(User user){
        if (user != null && user.getRole().intValue() == Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    //end
}
