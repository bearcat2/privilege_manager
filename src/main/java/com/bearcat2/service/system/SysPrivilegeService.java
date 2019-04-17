package com.bearcat2.service.system;

import com.bearcat2.entity.system.SysPrivilege;
import com.bearcat2.entity.system.SysPrivilegeExample;
import com.bearcat2.entity.system.SysRolePrivilege;
import com.bearcat2.service.base.BaseService;
import com.bearcat2.utils.LayuiPageResult;

import java.util.List;

/**
 * <p>Description: 权限管理的service接口</p>
 * <p>Title: SysPrivilegeService </p>
 * <p>Create Time: 2018/8/16 16:18 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
public interface SysPrivilegeService extends BaseService<SysPrivilege, SysPrivilegeExample, Integer> {

    /**
     * 查找用户下拥有的所有菜单
     *
     * @param userId 用户id
     * @return
     */
    List<SysPrivilege> findMenuByUserId(Integer userId);

    /**
     * 查找用户下拥有的所有权限
     *
     * @param userId 用户id
     * @return
     */
    List<SysPrivilege> findPrivilegeByUserId(Integer userId);

    /**
     * 通过模块id查询该模块下的所有菜单
     *
     * @param moduleId 模块id
     * @return
     */
    List<SysPrivilege> findByModuleId(Integer moduleId);

    /**
     * 分页查询
     *
     * @param sysPrivilege
     * @return
     */
    LayuiPageResult list(SysPrivilege sysPrivilege);

    /**
     * 通过类型查询
     *
     * @param type
     * @return
     */
    List<SysPrivilege> findByType(Integer type);

    /**
     * 查找所有权限
     *
     * @return
     */
    List<SysPrivilege> findAll();

    /**
     * 查找该用户下所有权限id
     *
     * @param roleId
     * @return
     */
    List<SysRolePrivilege> findByRoleId(Integer roleId);

    /**
     * 分配权限
     *
     * @param roleId          角色id
     * @param srpPrivilegeIds 权限id集合
     */
    void allotSysPrivilege(Integer roleId, String srpPrivilegeIds);

    /**
     * 根据条件查询
     *
     * @param sysPrivilege 权限对象
     * @return
     */
    public List<SysPrivilege> findByCondition(SysPrivilege sysPrivilege);
}
