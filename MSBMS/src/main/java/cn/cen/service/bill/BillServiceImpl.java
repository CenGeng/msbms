package cn.cen.service.bill;

import cn.cen.dao.BaseDao;
import cn.cen.dao.bill.BillDao;
import cn.cen.dao.bill.BillDaoImpl;
import cn.cen.pojo.Bill;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class BillServiceImpl implements BillService{
    BillDao billDao;
    public BillServiceImpl() {
        billDao = new BillDaoImpl();
    }

    @Override
    public Object[] getAllBill(String productName, String proName, String isPayment, String currentPage) throws SQLException {
        ArrayList<Bill> bills = billDao.billList(BaseDao.getConnection(),productName,proName,isPayment,currentPage);
        return bills.toArray();
    }

    @Override
    public int getBillCount(String productName, String proName, String isPayment) throws SQLException {
        int count = billDao.billCount(BaseDao.getConnection(), productName, proName, isPayment);
        return count;
    }


    @Override
    public int delBill(String code) throws SQLException {
        int rows = billDao.delBill(BaseDao.getConnection(), code);
        return rows;
    }

    @Override
    public int addBill(String billCode, String productName, String productUnit, String productCount, String totalPrice, String providerId, String isPayment) throws SQLException {
        int rows = billDao.addBill(BaseDao.getConnection(), billCode, productName, productUnit, productCount, totalPrice, providerId, isPayment);
        return rows;
    }


}
