package cn.yifan.geekgen.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.yifan.geekgen.mapper.UserMapper;
import cn.yifan.geekgen.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName StpInterfaceImpl
 * @Description
 * @Author yifan
 * @date 2025-03-01 19:45
 **/

@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = Long.valueOf((String) loginId);
        User user = userMapper.getById(userId);
        List<String> roleList = new ArrayList<>();
        roleList.add(user.getRole());
        return roleList;
    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return List.of();
    }
}
