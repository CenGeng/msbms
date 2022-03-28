package cn.cen.servlet.user;

import cn.cen.pojo.User;
import cn.cen.service.user.UserServiceImpl;
import cn.cen.util.Constants;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    // Servlet：控制层调用用户层

//    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet---start....");

        // 获取用户名和密码
        String userCode = req.getParameter("userCode");
        String password = req.getParameter("userPassword");


        // 和数据库中的用户名密码进行对比，调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, password);  // 已经把登陆的人给查询出来了

        if (user != null){// 查有此人，可以登录
            // 将用户的信息放在Session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            // 跳转到内部主页
            resp.sendRedirect("jsp/frame.jsp");
        }else { // 查无此人,无法登录
            // 转发回登录页面。提示用户名或密码错误
            req.setAttribute("error","用户名或密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
