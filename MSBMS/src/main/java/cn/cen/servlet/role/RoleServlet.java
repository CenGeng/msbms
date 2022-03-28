package cn.cen.servlet.role;

import cn.cen.dao.BaseDao;
import cn.cen.dao.role.RoleDao;
import cn.cen.dao.role.RoleDaoImpl;

import java.sql.SQLException;

public class RoleServlet {
    private int roleCount;
    private Object[] roleNames;

    public RoleServlet() throws SQLException {
        RoleDao roleDao = new RoleDaoImpl();
        roleNames = roleDao.roles(BaseDao.getConnection()).toArray();
    }

    public int getRoleCount() {
        return roleCount;
    }


    public Object[] getRoleNames() {
        return roleNames;
    }

}
