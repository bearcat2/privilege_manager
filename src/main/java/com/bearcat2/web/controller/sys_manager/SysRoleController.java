package com.bearcat2.web.controller.sys_manager;

import com.alibaba.fastjson.JSON;
import com.bearcat2.entity.system.SysRole;
import com.bearcat2.entity.system.SysUserRole;
import com.bearcat2.service.system.SysRoleService;
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
 * <p>Description: 系统角色的控制器 </p>
 * <p>Title: SysRoleController </p>
 * <p>Create Time: 2018/8/17 19:06 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
@RequestMapping("/sysRole")
@Controller
public class SysRoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/list_ui")
    public String listUi() {
        return "sys_manager/sys_role/list";
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public PmResult edit(SysRole sysRole) {
        try {
            sysRole.setSrUpdateTime(new Date());
            return PmResult.build(true, "请求成功", this.sysRoleService.updateByPrimaryKeySelective(sysRole));
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【edit】修改id = %d ,接收原始数据 = %s ,的角色发生错误"
                            , sysRole.getSrId()
                            , JSON.toJSONString(sysRole)
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @RequestMapping(value = "/edit_ui", method = RequestMethod.GET)
    public String editUi(Integer srId, Model model) {
        try {
            model.addAttribute("sys_role", this.sysRoleService.selectByPrimaryKey(srId));
        } catch (Exception e) {
            LOGGER.error(String.format("【editUi】修改页面展示 id = %d 的角色信息发生错误", srId), e);
        }
        return "sys_manager/sys_role/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public LayuiPageResult list(SysRole sysRole) {
        return this.sysRoleService.list(sysRole);
    }

    @RequestMapping(value = "/add_ui")
    public String addUi() {
        return "sys_manager/sys_role/add";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PmResult add(SysRole sysRole) {
        try {
            sysRole.setSrCreateTime(new Date());
            sysRole.setSrUpdateTime(new Date());
            this.sysRoleService.insertSelective(sysRole);
            return PmResult.build(true, "添加成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【add】添加id = %d ,接收原始数据 = %s ,的角色发生错误"
                            , sysRole.getSrId()
                            , JSON.toJSONString(sysRole)
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public PmResult delete(Integer srId) {
        try {
            this.sysRoleService.deleteByPrimaryKey(srId);
            return PmResult.build(true, "删除成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【delete】删除id = %d ,的角色发生错误"
                            , srId
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @RequestMapping(value = "/allotSysRole_ui", method = RequestMethod.GET)
    public String allotSysRoleUi(Integer userId, Model model) {
        model.addAttribute("all_sys_role", this.sysRoleService.findAll());
        List<Integer> integers = new ArrayList<>();
        for (SysUserRole sysUserRole : this.sysRoleService.findByUserId(userId)) {
            integers.add(sysUserRole.getSurRoleId());
        }
        model.addAttribute("userId", userId);
        model.addAttribute("have_sys_role", StringUtils.join(integers, ','));
        return "sys_manager/sys_role/allotSysRole";
    }

    @ResponseBody
    @RequestMapping(value = "/allotSysRole", method = RequestMethod.POST)
    public PmResult allotSysRole(Integer userId, String roleIds) {
        try {
            this.sysRoleService.allotSysRole(userId, roleIds);
            return PmResult.build(true, "角色分配成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【allotSysPrivilege】分配角色失败 用户id = %d ,的角色发生错误"
                            , userId
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }
}
