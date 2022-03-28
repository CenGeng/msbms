package cn.cen.dao.provider;

import cn.cen.dao.BaseDao;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImpl implements ProviderDao{
    @Override
    public List proList(Connection connection) throws SQLException {
        String sql = "SELECT proName FROM msbms.smbms_provider";
        ArrayList<String> providers = new ArrayList<String>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            providers.add(resultSet.getString(1));
        }
        BaseDao.closeResource(connection,preparedStatement,resultSet);
        return providers;
    }



}
