package cn.cen.service.bill;

import java.sql.Connection;
import java.sql.SQLException;

public interface BillService {
    public Object[] getAllBill(String productName, String proName, String isPayment, String currentPage) throws SQLException;

    public int getBillCount(String productName, String proName, String isPayment) throws SQLException;

    public int delBill(String code) throws SQLException;

    public int addBill(String billCode,
                       String productName,
                       String productUnit,
                       String productCount,
                       String totalPrice,
                       String providerId,
                       String isPayment
    ) throws SQLException;
}
