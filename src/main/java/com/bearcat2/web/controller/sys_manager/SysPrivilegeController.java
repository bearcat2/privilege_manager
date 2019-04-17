package com.bearcat2.web.controller.sys_manager;

import com.alibaba.fastjson.JSON;
import com.bearcat2.entity.system.SysPrivilege;
import com.bearcat2.entity.system.SysRolePrivilege;
import com.bearcat2.service.system.SysPrivilegeService;
import com.bearcat2.utils.LayuiPageResult;
import com.bearcat2.utils.PmResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: 系统权限的控制器 </p>
 * <p>Title: SysPrivilegeController </p>
 * <p>Create Time: 2018/8/17 19:05 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
@RequestMapping("/sysPrivilege")
@Controller
public class SysPrivilegeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysPrivilegeController.class);

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @RequestMapping(value = "/list_ui")
    public String listUi() {
        return "sys_manager/sys_privilege/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public LayuiPageResult list(SysPrivilege sysPrivilege) {
        return this.sysPrivilegeService.list(sysPrivilege);
    }

    @RequestMapping(value = "/edit_ui", method = RequestMethod.GET)
    public String editUi(Integer spId, Model model) {
        try {
            model.addAttribute("sys_privilege", this.sysPrivilegeService.selectByPrimaryKey(spId));
        } catch (Exception e) {
            LOGGER.error(String.format("【editUi】修改页面展示 id = %d 的权限信息发生错误", spId), e);
        }
        return "sys_manager/sys_privilege/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public PmResult edit(SysPrivilege sysPrivilege) {
        try {
            sysPrivilege.setSpUpdateTime(new Date());
            return PmResult.build(true, "请求成功", this.sysPrivilegeService.updateByPrimaryKeySelective(sysPrivilege));
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【edit】修改id = %d ,接收原始数据 = %s ,的权限发生错误"
                            , sysPrivilege.getSpId()
                            , JSON.toJSONString(sysPrivilege)
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @RequestMapping(value = "/add_ui")
    public String addUi() {
        return "sys_manager/sys_privilege/add";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PmResult add(SysPrivilege sysPrivilege) {
        try {
            sysPrivilege.setSpCreateTime(new Date());
            sysPrivilege.setSpUpdateTime(new Date());
            this.sysPrivilegeService.insertSelective(sysPrivilege);
            return PmResult.build(true, "添加成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【add】添加id = %d ,接收原始数据 = %s ,的权限发生错误"
                            , sysPrivilege.getSpId()
                            , JSON.toJSONString(sysPrivilege)
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public PmResult delete(Integer spId) {
        try {
            this.sysPrivilegeService.deleteByPrimaryKey(spId);
            return PmResult.build(true, "删除成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【delete】删除id = %d ,的权限发生错误"
                            , spId
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @RequestMapping(value = "/allotSysPrivilege_ui", method = RequestMethod.GET)
    public String allotSysPrivilegeUi(Integer roleId, Model model) {
        model.addAttribute("all_sys_privilege", this.sysPrivilegeService.findAll());
        List<Integer> integers = new ArrayList<>();
        for (SysRolePrivilege sysRolePrivilege : this.sysPrivilegeService.findByRoleId(roleId)) {
            integers.add(sysRolePrivilege.getSrpPrivilegeId());
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("have_sys_privilege", StringUtils.join(integers, ','));
        return "sys_manager/sys_privilege/allotSysPrivilege";
    }

    @ResponseBody
    @RequestMapping(value = "/allotSysPrivilege", method = RequestMethod.POST)
    public PmResult allotSysPrivilege(Integer roleId, String srpPrivilegeIds) {
        try {
            this.sysPrivilegeService.allotSysPrivilege(roleId, srpPrivilegeIds);
            return PmResult.build(true, "权限分配成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【allotSysPrivilege】分配权限失败 角色id = %d ,的权限发生错误"
                            , roleId
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }
}
