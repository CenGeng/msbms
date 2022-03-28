package cn.cen.servlet.user;

import cn.cen.pojo.User;
import cn.cen.service.role.RoleServiceImpl;
import cn.cen.service.user.UserServiceImpl;
import cn.cen.servlet.role.RoleServlet;
import cn.cen.util.Constants;
import cn.cen.util.PageSupport;
import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

// 实现Servlet复用
public class userServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("savepwd".equals(method) && !method.isEmpty()){
            this.updatePwd(req, resp);
        }else if ("oldPwdModifyValidate".equals(method) && !method.isEmpty()){
            this.oldPwdModify(req, resp);
        }else if ("newPwdModifyValidate".equals(method) && !method.isEmpty()){
            this.newPwdModify(req, resp);
        }else if ("query".equals(method) && !method.isEmpty()){
            this.userList(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取在登陆时用户存进去的Session
        Object attribute = req.getSession().getAttribute(Constants.USER_SESSION);
        // 从前端获取输入进来的新密码
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;
        if (attribute != null && !StringUtils.isNullOrEmpty(newpassword)){
            UserServiceImpl userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) attribute).getId(), newpassword);
            if (flag){
                req.setAttribute("msg","修改密码成功，请退出使用新密码登录");
                // 如果修改成功 移除当前Session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                req.setAttribute("msg","密码修改失败");
            }
        }else {
            req.setAttribute("msg","新密码有问题");
        }


    }

    // 验证旧密码
    public void oldPwdModify(HttpServletRequest req, HttpServletResponse resp){
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        String oldPwd = user.getUserPassword();
        // 使用万能的Map : 结果集
        HashMap<String, String> resultMap = new HashMap<>();

        if (user == null){      //Session过期了
            resultMap.put("result","sessionerror");
        }else if(oldPwd.isEmpty()){
            resultMap.put("result","error");
        }else if (req.getParameter("oldpassword").equals(oldPwd)){
            resultMap.put("result","true");
        }else {
            resultMap.put("result","false");
        }


        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            // JSONArray 阿里巴巴的JSON工具类,转换格式的
            /*
            把其他格式转换成JSON的格式
             */
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 验证两次输入的新密码
    public void newPwdModify(HttpServletRequest req, HttpServletResponse resp){
        String newPwd = req.getParameter("newPwd");
        String reNewPwd = req.getParameter("renewPwd");
        HashMap<String, String> resultMap = new HashMap<>();
        if (newPwd.equals(reNewPwd)){
            resultMap.put("result","true");
        }else {
            resultMap.put("result","false");
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            // JSONArray 阿里巴巴的JSON工具类,转换格式的
            /*
            把其他格式转换成JSON的格式
             */
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 查询用户列表
    public void userList(HttpServletRequest req, HttpServletResponse resp) {
        UserServiceImpl userService = new UserServiceImpl();
        RoleServiceImpl roleService = new RoleServiceImpl();
        try {
           PageSupport pageSupport = new PageSupport();
           pageSupport.setTotalCount(userService.getUserCount("",""));
            int totalPageCount = pageSupport.getTotalCount()/pageSupport.getPageSize();
            if (pageSupport.getTotalCount()%5 == 0){
                pageSupport.setTotalPageCount(totalPageCount);
            }else pageSupport.setTotalPageCount(totalPageCount + 1);

            Object[] roles = roleService.getRoles();
            Object[] users = userService.getAllUser("","","1");

            req.setAttribute("roleNames",roles);
            req.setAttribute("allUser",users);
            req.setAttribute("pageInfo",pageSupport);



            req.getRequestDispatcher("/jsp/userlist.jsp").forward(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

