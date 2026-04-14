package com.system.controller;

import com.system.common.Result;
import com.system.dto.LoginDTO;
import com.system.service.SysUserService;
import com.system.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 登录Controller
 */
@Tag(name = "登录接口")
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = sysUserService.login(loginDTO);
        return Result.success(loginVO);
    }

    /**
     * 用户登出
     */
    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("登出成功");
    }

}
