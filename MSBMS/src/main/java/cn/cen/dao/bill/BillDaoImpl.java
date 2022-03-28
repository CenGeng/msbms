package cn.cen.dao.bill;

import cn.cen.dao.BaseDao;
import cn.cen.pojo.Bill;
import cn.cen.pojo.User;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BillDaoImpl implements BillDao{
    @Override
    public int addBill() {
        return 0;
    }

    @Override
    public ArrayList<Bill> billList(Connection connection, String productName, String proName, String isPayment, String currentPage) throws SQLException {
        if (connection == null) {
            return null;
        }
        String current = String.valueOf(Integer.parseInt(currentPage) * 5 - 5 );

        String sql = "SELECT bill.billCode billcode,provider.proName proname,bill.productName productname,bill.productUnit productunit,bill.productCount productcount,bill.totalPrice totalprice,bill.isPayment ispayment,bill.creationDate creationdata\n" +
                "FROM smbms_bill bill JOIN smbms_provider provider ON bill.providerId=provider.id\n" +
                "WHERE bill.productName LIKE (?) AND provider.proName LIKE (?) AND bill.isPayment LIKE (?)\n" +
                "LIMIT "+ current + ",5";

        ResultSet rs = null;
        ArrayList<Bill> bills = new ArrayList<>();
        Object[] params = {productName, proName,isPayment};
        PreparedStatement preparedStatement = null;

        rs = BaseDao.execute(connection, preparedStatement, params, sql, rs, true);

        while (rs.next()){
            Bill bill = new Bill();

//            bill.setId(rs.getLong("id"));
            bill.setBillCode(rs.getString("billcode"));
            bill.setProviderName(rs.getString("proname"));
            bill.setProductName(rs.getString("productname"));
            bill.setProductUnit(rs.getString("productunit"));
            bill.setProductCount(rs.getBigDecimal("productcount"));
            bill.setTotalPrice(rs.getBigDecimal("totalprice"));
            bill.setIsPayment(rs.getInt("ispayment"));
            bill.setCreationDate(rs.getTimestamp("creationdata"));

            bills.add(bill);

        }
        BaseDao.closeResource(connection,preparedStatement,rs);
        return bills;
    }

    @Override
    public int billCount(Connection connection, String productName, String proName, String isPayment) throws SQLException {
        if (connection == null) {
            BaseDao.closeResource(connection, null, null);
            return 0;
        }
        String sql = "SELECT COUNT(1)\n" +
                "FROM smbms_bill bill JOIN smbms_provider provider ON bill.providerId=provider.id\n" +
                "WHERE bill.productName LIKE (?) AND provider.proName LIKE (?) AND bill.isPayment LIKE (?)\n";

        PreparedStatement preparedStatement = null;
        Object[] params = {productName, proName,isPayment};
        ResultSet rs = null;
        rs = BaseDao.execute(connection, preparedStatement, params, sql, rs, true);
        int count = -1;

        if (rs.next()) {
            count = rs.getInt(1);
        }
        BaseDao.closeResource(connection, preparedStatement, rs);
        return count;

    }

    @Override
    public int delBill(Connection connection, String code) throws SQLException {
        String sql = "DELETE FROM smbms_bill WHERE billCode=?";
        PreparedStatement preparedStatement = null;
        Object[] params = {code};

        int rows = BaseDao.execute(BaseDao.getConnection(), preparedStatement, params, sql);

        return rows;
    }

    @Override
    public int addBill(Connection connection, String billCode, String productName, String productUnit, String productCount, String totalPrice, String isPayment,String providerId){
        String sql = "INSERt INTO msbms.smbms_bill(billCode, productName,  productUnit, productCount, totalPrice, isPayment,providerId,creationDate) VALUES (?,?,?,?,?,?,?,?)";
        int rows = 0;
        PreparedStatement preparedStatement = null;

        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount));
        bill.setTotalPrice(new BigDecimal(totalPrice));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setProviderId(Integer.parseInt(providerId));

        Date now = new Date(System.currentTimeMillis());
        Object[] params = {bill.getBillCode(),bill.getProductName(),bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),bill.getProviderId(),now};
        try {
            preparedStatement = connection.prepareStatement(sql);
            rows = BaseDao.execute(connection, preparedStatement, params, sql);
            return rows;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,preparedStatement,null);
        }
        return rows;
    }

}
