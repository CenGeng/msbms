package cn.cen.dao.role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    // 获取所有角色
    public List roles(Connection connection) throws SQLException;
}
