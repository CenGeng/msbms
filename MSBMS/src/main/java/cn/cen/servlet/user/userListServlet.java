package cn.cen.servlet.user;

import cn.cen.service.role.RoleServiceImpl;
import cn.cen.service.user.UserServiceImpl;
import cn.cen.util.PageSupport;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class userListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageSupport pageSupport = new PageSupport();
        String condition = req.getParameter("n");
        if ("q".equals(condition)){
            this.inquiry(req, resp,"","","1",pageSupport);
        }else if ("frontpage".equals(condition)){
            this.frontPage(req, resp,pageSupport);
        }else if ("nextpage".equals(condition)){
            this.nextPage(req, resp,pageSupport);
        }else if ("previouspage".equals(condition)){
            this.previousPage(req, resp,pageSupport);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    // 根据条件查询用户
    public void inquiry(HttpServletRequest req, HttpServletResponse resp,String userName,String roleName,String pageNum,PageSupport pageSupport){
        // 引入业务层
        UserServiceImpl userService = new UserServiceImpl();
        RoleServiceImpl roleService = new RoleServiceImpl();

        // 获取到前端输入的查询条件
        userName = req.getParameter("username");
        roleName = req.getParameter("rolename");

        // 如果没有传入要查看第几页 默认打开第一页
        if (pageNum==null) {
            pageNum = "1";
        }
        try {
            // 调用业务层获取到所有职位信息
            Object[] roles = roleService.getRoles();
            // 调用业务层 根据条件获取到符合条件的用户信息
            Object[] users = userService.getAllUser(userName,roleName,pageNum);

            // 分页支持
            pageSupport.setTotalCount(userService.getUserCount(userName,roleName));
            int totalPageCount = pageSupport.getTotalCount()/pageSupport.getPageSize();
            if (pageSupport.getTotalCount()%5 == 0){
                pageSupport.setTotalPageCount(totalPageCount);
            }else pageSupport.setTotalPageCount(totalPageCount + 1);

            // 把查询出来的符合条件的数据发送到前端展示
            req.setAttribute("roleNames",roles);
            req.setAttribute("allUser",users);
            // 分页操作支持发送到前端
            req.setAttribute("pageInfo",pageSupport);
            // 把查询条件保留到前端 供分页操作使用
            req.setAttribute("username",userName);
            req.setAttribute("rolename",roleName);

            // 携带数据转发到前端页面
            req.getRequestDispatcher("/jsp/userlist.jsp").forward(req,resp);

        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    // 首页
    public void frontPage(HttpServletRequest req, HttpServletResponse resp,PageSupport pageSupport){
        // 从前端获取到查询条件
        String userName = req.getParameter("username");
        String roleName = req.getParameter("rolename");
        // 直接跳转到第一页
        this.inquiry(req,resp,userName,roleName,"1",pageSupport);
    }

    // 下一页
    public void nextPage(HttpServletRequest req, HttpServletResponse resp,PageSupport pageSupport){
        // 从前端获取当前是第几页 然后+1再调用查询方法跳转到指定页面
        String next = String.valueOf(Integer.parseInt( req.getParameter("current")) + 1);
        // 从前端获取到查询条件
        String userName = req.getParameter("username");
        String roleName = req.getParameter("rolename");

        // 更新当前页码
        pageSupport.setCurrentPageNo(Integer.parseInt(next));

        this.inquiry(req,resp,userName,roleName,next,pageSupport);
    }
    // 上一页
    public void previousPage(HttpServletRequest req, HttpServletResponse resp,PageSupport pageSupport){
        // 从前端获取当前是第几页 然后-1再调用查询方法跳转到指定页面
        int current = Integer.parseInt(req.getParameter("current"));
        // 从前端获取到查询条件
        String userName = req.getParameter("username");
        String roleName = req.getParameter("rolename");
        String previous = String.valueOf(current-1);
        if (current != 1){
            // 更新当前页码
            pageSupport.setCurrentPageNo(Integer.parseInt(previous));
            this.inquiry(req,resp,userName,roleName,previous,pageSupport);
        }else {
            this.frontPage(req,resp,pageSupport);
        }
    }
}
