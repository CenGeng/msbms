package cn.cen.servlet.bill;

import cn.cen.service.bill.BillService;
import cn.cen.service.bill.BillServiceImpl;
import cn.cen.service.provider.ProviderService;
import cn.cen.service.provider.ProviderServiceImpl;
import cn.cen.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class BillServlet extends HttpServlet {
    PageSupport pageSupport = new PageSupport();
    BillService billService = new BillServiceImpl();
    ProviderService providerService = new ProviderServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        String code = req.getParameter("code");
        if (method.equals("init")) {
            try {
                Object[] bills = billService.getAllBill("", "", "", "1");

                // 分页支持
                pageSupport.setCurrentPageNo(1);
                pageSupport.setTotalCount(billService.getBillCount("", "", ""));
                int totalPageCount = pageSupport.getTotalCount() / pageSupport.getPageSize();
                if (pageSupport.getTotalCount() % 5 == 0) {
                    pageSupport.setTotalPageCount(totalPageCount);
                } else pageSupport.setTotalPageCount(totalPageCount + 1);

                Object[] providers = providerService.getProviders();

                req.setAttribute("proNames",providers);
                req.setAttribute("pageInfo", pageSupport);
                req.setAttribute("bills", bills);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (method.equals("query")){
            this.query(req, resp);
        }else if (method.equals("frontpage")){
            System.out.println("点击了首页");
            this.frontPage(req,resp);

        }else if (method.equals("nextpage")){
            this.nextPage(req,resp);
        }else if (method.equals("previouspage")){
            this.previousPage(req,resp);
        }else if (method.equals("del")){
            this.billDel(req,resp,code);
        }else if (method.equals("save")){
            this.billAdd(req,resp);
        }


        req.getRequestDispatcher("/jsp/billlist.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void query(HttpServletRequest req, HttpServletResponse resp){
        try{


            String productName = req.getParameter("productname");
            String proName = req.getParameter("proname");
            String isPayment = req.getParameter("ispayment");

            String currentPageNo = String.valueOf(pageSupport.getCurrentPageNo());


            Object[] bills = billService.getAllBill(productName, proName, isPayment, currentPageNo);

            // 分页支持
            pageSupport.setTotalCount(billService.getBillCount(productName, proName, isPayment));
            int totalPageCount = pageSupport.getTotalCount() / pageSupport.getPageSize();
            if (pageSupport.getTotalCount() % 5 == 0) {
                pageSupport.setTotalPageCount(totalPageCount);
            } else pageSupport.setTotalPageCount(totalPageCount + 1);

            Object[] providers = providerService.getProviders();

            req.setAttribute("proNames",providers);
            req.setAttribute("pageInfo", pageSupport);
            req.setAttribute("bills", bills);

            req.setAttribute("productname",productName);
            req.setAttribute("proname",proName);
            req.setAttribute("ispayment",isPayment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 首页
    public void frontPage(HttpServletRequest req, HttpServletResponse resp){
        this.query(req,resp);
    }

    // 下一页
    public void nextPage(HttpServletRequest req, HttpServletResponse resp){

        int current = Integer.parseInt(req.getParameter("current"));

        int next = current + 1;
        pageSupport.setCurrentPageNo(next);
        this.query(req,resp);
    }
    // 上一页
    public void previousPage(HttpServletRequest req, HttpServletResponse resp){
        int current = Integer.parseInt(req.getParameter("current"));

        int previous = current-1;
        if (current != 1){
            pageSupport.setCurrentPageNo(previous);
            this.query(req,resp);
        }else {
            this.frontPage(req,resp);
        }
    }

    // 删除
    public void billDel(HttpServletRequest req, HttpServletResponse resp,String code) throws IOException {
        PrintWriter out = resp.getWriter();

            try {
                if(billService.delBill(code) != 0){
                    out.print("<script language=\"JavaScript\">alert(\"success\");window.location.href='bill.do?method=init';</script>");
                }else {
                    out.print("<script language=\"JavaScript\">alert(\"fail\");window.location.href='bill.do?method=init';</script>");
                }
                pageSupport.setCurrentPageNo(1);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {

                out.close();
            }
    }

    // 添加用户
    public void billAdd(HttpServletRequest req, HttpServletResponse resp) {
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            int rows = billService.addBill(billCode, productName, productUnit, productCount, totalPrice, providerId, isPayment);
            pageSupport.setCurrentPageNo(1);
            if (rows == 0){
                out.print("<script language=\"JavaScript\">alert(\"fail\");</script>");
                req.getRequestDispatcher("/jsp/billadd.jsp").forward(req, resp);


            }else {
                out.print("<script language=\"JavaScript\">alert(\"success\");</script>");
                req.getRequestDispatcher("/jsp/billadd.jsp").forward(req, resp);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }
    }
}
