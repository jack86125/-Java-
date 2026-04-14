package com.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.dto.LoginDTO;
import com.system.entity.SysUser;
import com.system.vo.LoginVO;

/**
 * 系统用户Service接口
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 根据用户名查询用户
     */
    SysUser getUserByUsername(String username);

}
