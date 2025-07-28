package cn.yifan.geekgen.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.yifan.geekgen.pojo.dto.UserLoginDTO;
import cn.yifan.geekgen.pojo.dto.UserSignUpDTO;
import cn.yifan.geekgen.pojo.vo.LoginVO;
import cn.yifan.geekgen.service.business.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName UserController
 * @Description
 * @Author yifan
 * @date 2025-01-28 16:06
 **/

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginVO login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }

    @PostMapping("/signup")
    public LoginVO signup(@RequestBody @Validated UserSignUpDTO userSignUpDTO) {
        return userService.signup(userSignUpDTO);
    }

    @GetMapping("/check_login")
    @SaCheckLogin
    public void checkLogin() {
        userService.checkLogin();
    }

}
