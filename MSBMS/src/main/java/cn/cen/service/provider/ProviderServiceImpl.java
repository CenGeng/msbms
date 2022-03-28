package cn.cen.service.provider;

import cn.cen.dao.BaseDao;
import cn.cen.dao.provider.ProviderDao;
import cn.cen.dao.provider.ProviderDaoImpl;

import java.sql.SQLException;
import java.util.List;

public class ProviderServiceImpl implements ProviderService {
    ProviderDao providerDao;
    public ProviderServiceImpl() {
        providerDao = new ProviderDaoImpl();
    }

    @Override
    public Object[] getProviders() throws SQLException {
        List list = providerDao.proList(BaseDao.getConnection());
        return list.toArray();
    }
}
