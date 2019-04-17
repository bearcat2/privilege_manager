package com.bearcat2.service.impl.system;

import com.bearcat2.dao.system.SysPrivilegeExpandMapper;
import com.bearcat2.dao.system.SysRolePrivilegeMapper;
import com.bearcat2.entity.system.SysPrivilege;
import com.bearcat2.entity.system.SysPrivilegeExample;
import com.bearcat2.entity.system.SysRolePrivilege;
import com.bearcat2.entity.system.SysRolePrivilegeExample;
import com.bearcat2.service.base.BaseServiceImpl;
import com.bearcat2.service.system.SysPrivilegeService;
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
 * <p>Description: 权限管理的service接口实现类 </p>
 * <p>Title: SysPrivilegeServiceImpl </p>
 * <p>Create Time: 2018/8/16 16:20 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
@Service
public class SysPrivilegeServiceImpl extends BaseServiceImpl<SysPrivilege, SysPrivilegeExample, Integer> implements SysPrivilegeService {

    @Autowired
    private SysPrivilegeExpandMapper sysPrivilegeExpandMapper;

    @Autowired
    private SysRolePrivilegeMapper sysRolePrivilegeMapper;


    @Override
    public List<SysPrivilege> findByCondition(SysPrivilege sysPrivilege) {
        SysPrivilegeExample example = new SysPrivilegeExample();
        SysPrivilegeExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysPrivilege.getSpName())) {
            criteria.andSpNameLike("%" + sysPrivilege.getSpName() + "%");
        }
        if (StringUtils.isNotBlank(sysPrivilege.getSpUri())) {
            criteria.andSpUriLike("%" + sysPrivilege.getSpUri() + "%");
        }
        if (sysPrivilege.getSpType() != null) {
            criteria.andSpTypeEqualTo(sysPrivilege.getSpType());
        }
        if (sysPrivilege.getSpParentId() != null) {
            criteria.andSpParentIdEqualTo(sysPrivilege.getSpParentId());
        }
        return this.selectByExample(example);
    }

    @Override
    public List<SysPrivilege> findMenuByUserId(Integer userId) {
        return this.sysPrivilegeExpandMapper.findMenuByUserId(userId);
    }

    @Override
    public List<SysPrivilege> findPrivilegeByUserId(Integer userId) {
        return this.sysPrivilegeExpandMapper.findPrivilegeByUserId(userId);
    }

    @Override
    public List<SysPrivilege> findByModuleId(Integer moduleId) {
        SysPrivilegeExample example = new SysPrivilegeExample();
        SysPrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andSpParentIdEqualTo(moduleId);
        return selectByExample(example);
    }

    @Override
    public LayuiPageResult list(SysPrivilege sysPrivilege) {
        SysPrivilegeExample example = new SysPrivilegeExample();
        SysPrivilegeExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(sysPrivilege.getSpName())) {
            criteria.andSpNameLike("%" + sysPrivilege.getSpName() + "%");
        }
        if (StringUtils.isNotBlank(sysPrivilege.getSpUri())) {
            criteria.andSpUriLike("%" + sysPrivilege.getSpUri() + "%");
        }
        PageHelper.startPage(sysPrivilege.getPage(), sysPrivilege.getLimit());
        PageInfo<SysPrivilege> SysPrivilegePageInfo = new PageInfo<>(this.selectByExample(example));
        return LayuiPageResult.build(SysPrivilegePageInfo.getTotal(), SysPrivilegePageInfo.getList());
    }

    @Override
    public List<SysPrivilege> findByType(Integer type) {
        SysPrivilegeExample example = new SysPrivilegeExample();
        SysPrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andSpTypeEqualTo(type);
        return selectByExample(example);
    }

    @Override
    public List<SysPrivilege> findAll() {
        SysPrivilegeExample example = new SysPrivilegeExample();
        return selectByExample(example);
    }

    @Override
    public List<SysRolePrivilege> findByRoleId(Integer roleId) {
        SysRolePrivilegeExample example = new SysRolePrivilegeExample();
        SysRolePrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andSrpRoleIdEqualTo(roleId);
        return this.sysRolePrivilegeMapper.selectByExample(example);
    }

    @Override
    public void allotSysPrivilege(Integer roleId, String srpPrivilegeIds) {
        SysRolePrivilegeExample example = new SysRolePrivilegeExample();
        SysRolePrivilegeExample.Criteria criteria = example.createCriteria();
        criteria.andSrpRoleIdEqualTo(roleId);
        this.sysRolePrivilegeMapper.deleteByExample(example);

        if (StringUtils.isBlank(srpPrivilegeIds)) {
            return;
        }
        List<SysRolePrivilege> sysRolePrivileges = new ArrayList<>();
        for (String srpPrivilegeId : srpPrivilegeIds.split(",")) {
            SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
            sysRolePrivilege.setSrpRoleId(roleId);
            sysRolePrivilege.setSrpPrivilegeId(Integer.parseInt(srpPrivilegeId));
            sysRolePrivileges.add(sysRolePrivilege);
        }

        if (!CollectionUtils.isEmpty(sysRolePrivileges)) {
            this.sysRolePrivilegeMapper.insertBatch(sysRolePrivileges);
        }
    }
}
