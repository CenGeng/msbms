package cn.cen.service.user;

import cn.cen.dao.BaseDao;
import cn.cen.dao.role.RoleDao;
import cn.cen.dao.role.RoleDaoImpl;
import cn.cen.dao.user.UserDao;
import cn.cen.dao.user.UserDaoImpl;
import cn.cen.pojo.User;
import com.mysql.cj.util.StringUtils;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService{

    // 业务层会调用DAO曾，所以要引入DAO层
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();

    }

    @Override
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        try {
            // 通过业务层调用对应的具体的数据库操作
            user = userDao.getLoginUser(connection,userCode);
            if (user == null || !user.getUserPassword().equals(password)){
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    @Override
    public boolean updatePwd(int id, String password){
        Connection connection = null;
        connection = BaseDao.getConnection();
        boolean flag = false;
        // 修改密码
        try {
            if( userDao.updatePwd(connection,id,password) > 0){
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String userName, String roleName) throws SQLException {
//        if (StringUtils.isNullOrEmpty(userName) | StringUtils.isNullOrEmpty(roleName) | (StringUtils.isNullOrEmpty(userName) && StringUtils.isNullOrEmpty(roleName))){
//            userName = "";
//            roleName = "";
//        }
        if (userName == null | roleName == null | (roleName==null&&userName==null)){
            userName = "";
            roleName = "";
        }
        userName = userName.replaceAll(" ","");
        roleName = roleName.replaceAll(" ","");

        int count = userDao.userCount(BaseDao.getConnection(), userName, roleName);
        return count;
    }



    @Override
    public Object[] getAllUser(String userName,String roleName,String currentPage) throws SQLException {
        ArrayList<User> users = userDao.userList(BaseDao.getConnection(),userName,roleName,currentPage);
        return users.toArray();
    }

    @Override
    public int addUser(String userCode, String userName, String userPwd, int gender, Date birthday, String phone, String address, int userRole) throws SQLException {
        int rows = userDao.addUser(BaseDao.getConnection(), userCode, userName, userPwd, gender, birthday, phone, address, userRole);
        return rows;
    }

    @Override
    public int delUser(int id) throws SQLException {
        int rows = userDao.delUser(BaseDao.getConnection(),id);
        return rows;
    }

    @Test
    public void test() throws SQLException {
        Object[] allUser = getAllUser("", "", "1");
        for (Object o : allUser) {
            System.out.println(o);
        }
    }

}
