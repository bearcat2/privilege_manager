package com.bearcat2.service.impl.system;

import com.bearcat2.dao.system.SysUserRoleMapper;
import com.bearcat2.entity.system.SysRole;
import com.bearcat2.entity.system.SysRoleExample;
import com.bearcat2.entity.system.SysUserRole;
import com.bearcat2.entity.system.SysUserRoleExample;
import com.bearcat2.service.base.BaseServiceImpl;
import com.bearcat2.service.system.SysRoleService;
import com.bearcat2.utils.LayuiPageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 系统角色service接口实现类 </p>
 * <p>Title: SysRoleServiceImpl </p>
 * <p>Create Time: 2018/8/17 18:57 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, SysRoleExample, Integer> implements SysRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> findByCondition(SysRole sysRoleQuery) {
        return null;
    }

    @Override
    public LayuiPageResult list(SysRole sysRole) {
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysRole.getSrName())) {
            criteria.andSrNameLike("%" + sysRole.getSrName() + "%");
        }
        PageHelper.startPage(sysRole.getPage(), sysRole.getLimit());
        PageInfo<SysRole> SysRolePageInfo = new PageInfo<>(super.selectByExample(example));
        return LayuiPageResult.build(SysRolePageInfo.getTotal(), SysRolePageInfo.getList());
    }

    @Override
    public List<SysRole> findAll() {
        SysRoleExample example = new SysRoleExample();
        return super.selectByExample(example);
    }

    @Override
    public List<SysUserRole> findByUserId(Integer userId) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = sysUserRoleExample.createCriteria();
        criteria.andSurUserIdEqualTo(userId);
        return this.sysUserRoleMapper.selectByExample(sysUserRoleExample);
    }

    @Override
    public void allotSysRole(Integer userId, String roleIds) {
        SysUserRoleExample sysUserRoleExample = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = sysUserRoleExample.createCriteria();
        criteria.andSurUserIdEqualTo(userId);
        this.sysUserRoleMapper.deleteByExample(sysUserRoleExample);
        if (StringUtils.isBlank(roleIds)) {
            return;
        }
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (String roleId : roleIds.split(",")) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setSurRoleId(Integer.parseInt(roleId));
            sysUserRole.setSurUserId(userId);
            sysUserRoles.add(sysUserRole);
        }

        if (!CollectionUtils.isEmpty(sysUserRoles)) {
            this.sysUserRoleMapper.insertBatch(sysUserRoles);
        }
    }
}
