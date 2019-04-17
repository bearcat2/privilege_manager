package com.bearcat2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>Description: 系统首页控制器</p>
 * <p>Title: IndexController </p>
 * <p>Create Time: 2018/8/16 15:31 </p>
 *
 * @author: zhongzhipeng
 * @version: 1.0
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

}
