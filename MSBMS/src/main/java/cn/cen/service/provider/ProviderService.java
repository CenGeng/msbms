package cn.cen.service.provider;

import java.sql.SQLException;

public interface ProviderService {
    public Object[] getProviders() throws SQLException;
}
