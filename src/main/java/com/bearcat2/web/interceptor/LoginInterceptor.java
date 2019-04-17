package com.bearcat2.web.interceptor;

import com.bearcat2.entity.LoginUser;
import com.bearcat2.service.system.SysPrivilegeService;
import com.bearcat2.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>Description: 登录的拦截器</p>
 * <p>Title: LoginInterceptor </p>
 * <p>Create Time: 2018/8/16 15:36 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestURI = httpServletRequest.getRequestURI();
        List<String> anonymousUrls = PropertiesUtils.getInstance().getKeyList();
        for (String anonymousUrl : anonymousUrls) {
            if (requestURI.contains(anonymousUrl)) {
                // 请求的路径是允许匿名访问的,直接放行
                return true;
            }
        }
        LoginUser loginUser = (LoginUser) httpServletRequest.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            return true;
        }
        // 没有登录,返回到登录页去登录(返回脚本形式有个好处可以跳到顶层窗口,否则会内嵌在窗口内)
        String loginUrl = httpServletRequest.getContextPath() + "/sysUser/doLogin";
        String res = "<script>window.top.location.href='" + loginUrl + "';</script>";
        httpServletResponse.getWriter().write(res);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
