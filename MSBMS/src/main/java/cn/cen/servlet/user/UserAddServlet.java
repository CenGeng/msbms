package cn.cen.servlet.user;

import cn.cen.service.role.RoleService;
import cn.cen.service.role.RoleServiceImpl;
import cn.cen.service.user.UserService;
import cn.cen.service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class UserAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isSave = req.getParameter("isSave");
        if (!"true".equals(isSave)) {
            req.getRequestDispatcher("/jsp/useradd.jsp").forward(req, resp);
        }else {
            UserService userService = new UserServiceImpl();
            String userCode = req.getParameter("usercode");
            String userName = req.getParameter("username");
            String userPwd = req.getParameter("userpassword");
            String reUserPwd = req.getParameter("ruserPassword");
            int gender = Integer.parseInt(req.getParameter("gender"));
            Date birthday = Date.valueOf(req.getParameter("birthday"));
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            int userRole = Integer.parseInt(req.getParameter("userrole"));
            if (userPwd.equals(reUserPwd)){
                try {
                    userService.addUser(userCode,userName,userPwd,gender,birthday,phone,address,userRole);
                    System.out.println("添加成功");
                    req.getRequestDispatcher("/jsp/useradd.jsp").forward(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("两次输入的密码不同");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
