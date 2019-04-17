package com.bearcat2.service.system;

import com.bearcat2.entity.system.SysUser;
import com.bearcat2.entity.system.SysUserExample;
import com.bearcat2.service.base.BaseService;
import com.bearcat2.utils.LayuiPageResult;
import com.bearcat2.utils.PmResult;

import java.util.List;

/**
 * <p>Description: 用户管理的service接口</p>
 * <p>Title: SysUserServiceImpl </p>
 * <p>Create Time: 2018/8/15 23:05 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public interface SysUserService extends BaseService<SysUser, SysUserExample, Integer> {

    /**
     * 用户登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @return
     */
    PmResult login(String loginName, String password);

    /**
     * 分页查询
     *
     * @param sysUser
     * @return
     */
    LayuiPageResult list(SysUser sysUser);

    public List<SysUser> findByCondition(SysUser sysUse);
}
