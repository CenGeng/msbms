package cn.cen.dao.bill;

import cn.cen.pojo.Bill;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BillDao {
    public int addBill();

    // 获取订单列表
    public ArrayList<Bill> billList(Connection connection, String productName, String proName,String isPayment, String currentPage) throws SQLException;

    // 获取订单数量
    public int billCount(Connection connection, String productName, String proName,String isPayment) throws SQLException;

    public int delBill(Connection connection,String code) throws SQLException;

    public int addBill(Connection connection,
                       String billCode,
                       String productName,
                       String productUnit,
                       String productCount,
                       String totalPrice,
                       String providerId,
                       String isPayment
                       ) ;


}
