package cn.cen.service.role;

import java.sql.SQLException;

public interface RoleService {
    // 获取所有roleName
    public Object[] getRoles() throws SQLException;
}
