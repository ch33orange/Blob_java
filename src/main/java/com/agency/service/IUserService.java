package com.agency.service;

import com.agency.common.*;
import com.agency.pojo.*;

/**
 * @email chivseg-hao@qq.com
 * @author:ch33orange
 * @date: 2020/9/15
 * @time: 16:59
 */
public interface IUserService {

    //todo 做验证用的
    ServerResponse checkAdminRole(User user);
}
