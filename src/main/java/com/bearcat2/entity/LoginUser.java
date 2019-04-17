package com.bearcat2.entity;


import com.bearcat2.entity.system.SysPrivilege;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: 存储登录用户的身份信息及用户的权限信息</p>
 * <p>Title: LoginUser </p>
 * <p>Create Time: 2018/8/16 14:13 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 2653828870317327093L;

    /**
     * 登录用户的id
     */
    private Integer luId;

    /**
     * 登录用户名
     */
    private String luLoginName;

    /**
     * 登录用户的真实姓名
     */
    private String luRealName;

    /**
     * 用户所拥有的菜单集合
     */
    private Set<SysPrivilege> luMenus = new HashSet<>();

    /**
     * 用户所拥有的权限集合
     */
    private Set<SysPrivilege> luPrivileges = new HashSet<>();

    public Integer getLuId() {
        return luId;
    }

    public void setLuId(Integer luId) {
        this.luId = luId;
    }

    public String getLuLoginName() {
        return luLoginName;
    }

    public void setLuLoginName(String luLoginName) {
        this.luLoginName = luLoginName;
    }

    public String getLuRealName() {
        return luRealName;
    }

    public void setLuRealName(String luRealName) {
        this.luRealName = luRealName;
    }

    public Set<SysPrivilege> getLuMenus() {
        return luMenus;
    }

    public void setLuMenus(Set<SysPrivilege> luMenus) {
        this.luMenus = luMenus;
    }

    public Set<SysPrivilege> getLuPrivileges() {
        return luPrivileges;
    }

    public void setLuPrivileges(Set<SysPrivilege> luPrivileges) {
        this.luPrivileges = luPrivileges;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "luId=" + luId +
                ", luLoginName='" + luLoginName + '\'' +
                ", luRealName='" + luRealName + '\'' +
                ", luMenus=" + luMenus +
                ", luPrivileges=" + luPrivileges +
                '}';
    }
}
