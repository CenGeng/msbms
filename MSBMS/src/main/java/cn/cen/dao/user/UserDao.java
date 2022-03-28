package cn.cen.dao.user;

import cn.cen.pojo.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserDao {

    // 得到要登陆的用户
    public User getLoginUser(Connection connection,String userCode) throws SQLException;

    // 修改当前用户密码
    public int updatePwd(Connection connection,int id,String password) throws SQLException;

    // 获取用户总数量
    public int userCount(Connection connection,String userName,String roleName) throws SQLException;

    // 获取用户列表（包含roleName）
    public ArrayList<User> userList(Connection connection, String userName, String roleName, String currentPage) throws SQLException;

    // 添加用户
    public int addUser(
            Connection connection,
            String userCode,
            String userName,
            String userPwd,
            int gender,
            Date birthday,
            String phone,
            String address,
            int userRole
    ) throws SQLException;

    // 删除用户
    public int delUser(Connection connection,int id) throws SQLException;
}
