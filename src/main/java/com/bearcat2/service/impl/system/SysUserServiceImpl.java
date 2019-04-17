package com.bearcat2.service.impl.system;

import com.bearcat2.entity.LoginUser;
import com.bearcat2.entity.system.SysPrivilege;
import com.bearcat2.entity.system.SysUser;
import com.bearcat2.entity.system.SysUserExample;
import com.bearcat2.service.base.BaseServiceImpl;
import com.bearcat2.service.system.SysPrivilegeService;
import com.bearcat2.service.system.SysUserService;
import com.bearcat2.utils.LayuiPageResult;
import com.bearcat2.utils.MD5Util;
import com.bearcat2.utils.PmResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 用户管理的service接口实现类</p>
 * <p>Title: SysUserServiceImpl </p>
 * <p>Create Time: 2018/8/15 23:03 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserExample, Integer> implements SysUserService {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;


    @Override
    public List<SysUser> findByCondition(SysUser sysUser) {
        return null;
    }

    @Override
    public PmResult login(String loginName, String password) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andSuLoginNameEqualTo(loginName);
        List<SysUser> sysUsers = super.selectByExample(example);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return PmResult.build(false, "用户名或密码错误");
        }
        SysUser sysUser = sysUsers.get(0);
        if (!sysUser.getSuPassword().equalsIgnoreCase(MD5Util.getStringMD5(password))) {
            return PmResult.build(false, "用户名或密码错误");
        }

        // 用户登录成功将用户信息(用户id、登录名、真实姓名、菜单、权限)放入LoginUser中
        LoginUser loginUser = new LoginUser();
        loginUser.setLuId(sysUser.getSuId());
        loginUser.setLuLoginName(sysUser.getSuLoginName());
        loginUser.setLuRealName(sysUser.getSuRealName());

        List<SysPrivilege> sysPrivileges = this.sysPrivilegeService.findMenuByUserId(loginUser.getLuId());
        loginUser.getLuMenus().addAll(this.handlerModule(sysPrivileges));
        loginUser.getLuPrivileges().addAll(this.sysPrivilegeService.findPrivilegeByUserId(loginUser.getLuId()));
        return PmResult.build(true, "登录成功", loginUser);
    }

    @Override
    public LayuiPageResult list(SysUser sysUser) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysUser.getSuLoginName())) {
            criteria.andSuLoginNameLike("%" + sysUser.getSuLoginName() + "%");
        }
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(super.selectByExample(example));
        return LayuiPageResult.build(sysUserPageInfo.getTotal(), sysUserPageInfo.getList());
    }

    /**
     * 处理模块
     *
     * @param sysPrivileges 用户拥有的权限集合
     * @return
     */
    private List<SysPrivilege> handlerModule(List<SysPrivilege> sysPrivileges) {
        List<SysPrivilege> currMenus = new ArrayList<>();
        for (SysPrivilege sysPrivilege : sysPrivileges) {
            if (sysPrivilege.getSpType() == 1) {
                sysPrivilege.getChildrenSysPrivilege().addAll(this.handlerMenu(sysPrivileges, sysPrivilege));
                currMenus.add(sysPrivilege);
            }
        }
        return currMenus;
    }

    /**
     * 处理菜单
     *
     * @param sysPrivileges 用户拥有的权限集合
     * @param sysPrivilege  父权限id
     * @return
     */
    private List<SysPrivilege> handlerMenu(List<SysPrivilege> sysPrivileges, SysPrivilege sysPrivilege) {
        List<SysPrivilege> childrens = new ArrayList<>();
        for (SysPrivilege privilege : sysPrivileges) {
            if (privilege.getSpParentId().equals(sysPrivilege.getSpId())) {
                childrens.add(privilege);
            }
        }
        return childrens;
    }
}
