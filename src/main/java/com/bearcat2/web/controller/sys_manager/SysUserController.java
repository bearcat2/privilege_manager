package com.bearcat2.web.controller.sys_manager;

import com.alibaba.fastjson.JSON;
import com.bearcat2.entity.system.SysUser;
import com.bearcat2.service.system.SysUserService;
import com.bearcat2.utils.LayuiPageResult;
import com.bearcat2.utils.MD5Util;
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

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>Description: 系统用户控制器</p>
 * <p>Title: SysUserController </p>
 * <p>Create Time: 2018/8/16 15:12 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
@RequestMapping("/sysUser")
@Controller
public class SysUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/doLogin")
    public String doLogin() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public PmResult login(String username, String password, String clientValidateCode, HttpSession session) {
        String serverValidateCode = (String) session.getAttribute("validateCode");
        if (StringUtils.isBlank(serverValidateCode) || !serverValidateCode.equalsIgnoreCase(clientValidateCode)) {
            return PmResult.build(false, "验证码错误");
        }
        PmResult pmResult = this.sysUserService.login(username, password);
        if (pmResult.isSuccess()) {
            // 往session存入用户登录标记
            session.setAttribute("loginUser", pmResult.getObj());
            // 设置30分钟session失效
            session.setMaxInactiveInterval(1800);
        }
        return pmResult;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/sysUser/doLogin";
    }

    @RequestMapping(value = "/list_ui")
    public String listUi() {
        return "sys_manager/sys_user/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public LayuiPageResult list(SysUser sysUser) {
        return this.sysUserService.list(sysUser);
    }

    @RequestMapping(value = {"/edit_ui", "/settingData"}, method = RequestMethod.GET)
    public String editUi(Integer suId, Model model) {
        try {
            model.addAttribute("sysUser", this.sysUserService.selectByPrimaryKey(suId));
        } catch (Exception e) {
            LOGGER.error(String.format("【editUi】修改页面展示 id = %d 的用户信息发生错误", suId), e);
        }
        return "sys_manager/sys_user/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public PmResult edit(SysUser sysUser) {
        try {
            sysUser.setSuUpdateTime(new Date());
            return PmResult.build(true, "请求成功", this.sysUserService.updateByPrimaryKeySelective(sysUser));
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【edit】修改id = %d ,接收原始数据 = %s ,的用户发生错误"
                            , sysUser.getSuId()
                            , JSON.toJSONString(sysUser)
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @RequestMapping(value = "/add_ui")
    public String addUi() {
        return "sys_manager/sys_user/add";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PmResult add(SysUser sysUser) {
        try {
            sysUser.setSuCreateTime(new Date());
            sysUser.setSuUpdateTime(new Date());
            sysUser.setSuPassword(MD5Util.getStringMD5(sysUser.getSuPassword()));
            this.sysUserService.insertSelective(sysUser);
            return PmResult.build(true, "添加成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【add】添加id = %d ,接收原始数据 = %s ,的用户发生错误"
                            , sysUser.getSuId()
                            , JSON.toJSONString(sysUser)
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public PmResult delete(Integer suId) {
        try {
            this.sysUserService.deleteByPrimaryKey(suId);
            return PmResult.build(true, "删除成功");
        } catch (Exception e) {
            LOGGER.error(
                    String.format(
                            "【delete】删除id = %d ,的用户发生错误"
                            , suId
                    )
                    , e
            );
            return PmResult.build(false, "服务器内部错误");
        }
    }
}
