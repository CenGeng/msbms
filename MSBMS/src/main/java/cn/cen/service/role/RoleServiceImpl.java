package cn.cen.service.role;

import cn.cen.dao.BaseDao;
import cn.cen.dao.role.RoleDao;
import cn.cen.dao.role.RoleDaoImpl;

import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    @Override
    public Object[] getRoles() throws SQLException {
        List roles = roleDao.roles(BaseDao.getConnection());
        return roles.toArray();
    }
}
