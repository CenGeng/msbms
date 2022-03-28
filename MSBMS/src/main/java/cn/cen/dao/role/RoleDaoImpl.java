package cn.cen.dao.role;

import cn.cen.dao.BaseDao;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleDaoImpl implements RoleDao{

    @Override
    public ArrayList<String> roles(Connection connection) throws SQLException {
        String sql = "SELECT roleName FROM msbms.smbms_role";
        ArrayList<String> roles = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            roles.add(resultSet.getString(1));
        }
        BaseDao.closeResource(connection,preparedStatement,resultSet);
        return roles;
    }

}
