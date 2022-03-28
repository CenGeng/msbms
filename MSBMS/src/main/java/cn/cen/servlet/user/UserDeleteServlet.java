package cn.cen.servlet.user;

import cn.cen.service.user.UserService;
import cn.cen.service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class UserDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        PrintWriter out = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            if(userService.delUser(id) > 0){
                out.print("<script language='JavaScript'>alert('Success!');window.location.href = 'userlist?username=&rolename='</script>");
            }else {
                out.print("<script language='JavaScript'>alert('Fail!');window.location.href = 'userlist?username=&rolename='</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
