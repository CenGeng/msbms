package cn.cen.dao.user;

import cn.cen.dao.BaseDao;
import cn.cen.pojo.User;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        User user = null;
        Object[] params = {userCode};
        String sql = "SELECT * FROM smbms_user where userCode=? ";

        if (connection != null) {
            rs = BaseDao.execute(connection, pst, params, sql, rs);

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("Birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("Address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getLong("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getLong("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(connection, pst, rs);
        }
        return user;
    }

    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        String sql = "update smbms_user set userPassword=? where id =?";
        int execute = 0;
        if (connection != null) {
            PreparedStatement pst = null;
            Object[] params = {password, id};

            execute = BaseDao.execute(connection, pst, params, sql);
            BaseDao.closeResource(null, pst, null);
        }
        return execute;
    }

    @Override
    public int userCount(Connection connection, String userName, String roleName) throws SQLException {
        if (connection == null) {
            BaseDao.closeResource(connection, null, null);
            return 0;
        }

        String sql = "SELECT COUNT(1)\n" +
                "FROM smbms_user user JOIN smbms_role role ON user.userRole=role.id\n" +
                "WHERE role.roleName LIKE (?) AND userName LIKE (?)";

        PreparedStatement preparedStatement = null;
        Object[] params = {roleName,userName};
        ResultSet rs = null;
        rs = BaseDao.execute(connection, preparedStatement, params, sql, rs, true);
//        preparedStatement.setObject(1,"%" + roleName + "%");
//        preparedStatement.setObject(2,"%" + userName + "%");


        int count = -1;

        if (rs.next()) {
            count = rs.getInt(1);
        }
        BaseDao.closeResource(connection, preparedStatement, rs);
        return count;
    }


    @Override
    public ArrayList<User> userList(Connection connection, String userName, String roleName, String currentPage) throws SQLException {
        if (connection == null) {
            return null;
        }
        String current = String.valueOf(Integer.parseInt(currentPage) * 5 - 5 );
        String sql = "SELECT user.id ,user.userCode,user.userName,user.gender,user.birthday,user.phone,role.roleName\n" +
                "FROM smbms_user user JOIN smbms_role role ON user.userRole=role.id\n" +
                "WHERE role.roleName LIKE (?) AND userName LIKE (?)" +
                "LIMIT "+ current + ",5";
        User user = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = null;
        ArrayList<User> users = new ArrayList<>();
        Object[] params = {roleName, userName};

        rs = BaseDao.execute(connection, preparedStatement, params, sql, rs, true);

        while (rs.next()) {
            user = new User();

            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("Birthday"));
            user.setPhone(rs.getString("phone"));
            user.setUserRoleName(rs.getString("roleName"));

            users.add(user);

        }

        return users;
    }

    @Override
    public int addUser(Connection connection,
                       String userCode,
                       String userName,
                       String userPwd,
                       int gender,
                       Date birthday,
                       String phone,
                       String address,
                       int userRole) {
        if (connection == null){
            return 0;
        }
        String sql = "INSERT INTO smbms_user(userCode, userName, userPassword, gender, birthday, phone, address, userRole) VALUES(?,?,?,?,?,?,?,?) ";
        Object[] params = {userCode,userName,userPwd,gender,birthday,phone,address,userRole};
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            rows = BaseDao.execute(connection,preparedStatement,params,sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,preparedStatement,null);

        }
        return rows;
    }

    @Override
    public int delUser(Connection connection, int id){
        if (connection == null){
            return -0;
        }
        String sql = "DELETE FROM smbms_user WHERE id=?";
        PreparedStatement preparedStatement = null;
        Object[] params = {id};
        int rows = 0;
        try {
            rows = BaseDao.execute(connection, preparedStatement, params, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,preparedStatement,null);

        }
        return rows;

    }


}
