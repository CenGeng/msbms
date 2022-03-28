package cn.cen.filter;

import cn.cen.pojo.User;
import cn.cen.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // 每次执行过滤器都从Session中取出Constants.USER_SESSION的值
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        if (user == null){  //如果没有登录或已注销
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
