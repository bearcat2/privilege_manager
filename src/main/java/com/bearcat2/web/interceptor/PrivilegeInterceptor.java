package com.bearcat2.web.interceptor;

import com.bearcat2.entity.LoginUser;
import com.bearcat2.entity.system.SysPrivilege;
import com.bearcat2.utils.PropertiesUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * <p>Description: 权限拦截器</p>
 * <p>Title: PrivilegeInterceptor </p>
 * <p>Create Time: 2018/8/16 20:07 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public class PrivilegeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestURI = httpServletRequest.getServletPath();
        List<String> anonymousUrls = PropertiesUtils.getInstance().getKeyList();
        for (String anonymousUrl : anonymousUrls) {
            if (requestURI.contains(anonymousUrl)) {
                // 匿名访问路径直接放行
                return true;
            }
        }

        List<String> commonUrls = PropertiesUtils.getInstance("properties/commonUrls").getKeyList();
        for (String commonUrl : commonUrls) {
            if (requestURI.equals(commonUrl)) {
                // 公共访问路径直接放行
                return true;
            }
        }

        // 从当前登录的用户中取出权限集合,判断当前访问的uri是否允许访问
        // 如果不允许则跳到无权访问页面,如果允许则直接放行
        LoginUser loginUser = (LoginUser) httpServletRequest.getSession().getAttribute("loginUser");
        Set<SysPrivilege> privileges = loginUser.getLuPrivileges();
        for (SysPrivilege privilege : privileges) {
            //add_ui   add
            if (requestURI.contains("_") && "ui".equalsIgnoreCase(requestURI.split("_")[1])) {
                requestURI = requestURI.split("_")[0];
            }

            if (requestURI.equals(privilege.getSpUri())) {
                // 有权限访问,直接放行 shiro  spring security
                return true;
            }
        }
        // 走到这说明用户访问的路径无权访问跳转到无权访问页面
        httpServletRequest.getRequestDispatcher("/refuse.jsp").forward(httpServletRequest, httpServletResponse);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
