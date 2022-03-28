package cn.cen.service.user;

import cn.cen.pojo.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserService {
    // 用户登录
    public User login(String userCode,String password);
    // 根据userId修改密码
    public boolean updatePwd(int id, String password) throws SQLException;
    // 获取用户总数
    public int getUserCount(String userName,String roleName) throws SQLException;

    // 获取所有用户信息
    public Object[] getAllUser(String userName,String roleName, String currentPage) throws SQLException;
    // 添加用户
    public int addUser(
            String userCode,
            String userName,
            String userPwd,
            int gender,
            Date birthday,
            String phone,
            String address,
            int userRole
    ) throws SQLException;

    public int delUser(int id) throws SQLException;
}
