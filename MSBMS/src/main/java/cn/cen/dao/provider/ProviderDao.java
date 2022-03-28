package cn.cen.dao.provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {

    public List proList(Connection connection) throws SQLException;
}
