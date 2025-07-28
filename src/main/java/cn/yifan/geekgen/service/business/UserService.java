package cn.yifan.geekgen.service.business;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.yifan.geekgen.exception.ApiError;
import cn.yifan.geekgen.exception.ApiException;
import cn.yifan.geekgen.mapper.UserMapper;
import cn.yifan.geekgen.pojo.dto.UserLoginDTO;
import cn.yifan.geekgen.pojo.dto.UserSignUpDTO;
import cn.yifan.geekgen.pojo.entity.User;
import cn.yifan.geekgen.pojo.vo.LoginVO;
import org.springframework.stereotype.Service;

/**
 * @FileName UserService
 * @Description
 * @Author yifan
 * @date 2025-01-28 16:08
 **/

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public LoginVO login(UserLoginDTO userLoginDTO) {
        User user = userMapper.getByUsername(userLoginDTO.getUsername());
        if (user == null) {
            throw new ApiException(ApiError.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(userLoginDTO.getPassword())) {
            throw new ApiException(ApiError.PASSWORD_ERROR);
        }
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return new LoginVO(tokenInfo.tokenValue, user.getRole(), user.getLevel());
    }

    public LoginVO signup(UserSignUpDTO userSignUpDTO) {
        User user = new User(
            userSignUpDTO.getUsername(),
            userSignUpDTO.getPassword(),
            userSignUpDTO.getLevel(),
            userSignUpDTO.getRole(),
            userSignUpDTO.getSchool(),
            userSignUpDTO.getName()
        );
        userMapper.insert(user);
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return new LoginVO(tokenInfo.tokenValue, user.getRole(), user.getLevel());
    }

    public void checkLogin() {
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userMapper.getById(userId);
        if (user == null) {
            throw new ApiException(ApiError.USER_NOT_EXIST);
        }
    }

}
