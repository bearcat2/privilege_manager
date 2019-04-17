<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>权限管理系统</title>
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">权限管理系统</div>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src='${basePath}/statics/img/sys_user/user.png' class="layui-nav-img">
                    ${loginUser.luLoginName}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="#" class="userInfo">基本资料</a></dd>
                    <dd><a href="#" class="updatePwd">修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${basePath}/sysUser/logout">退出系统</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <c:forEach var="module" items="${loginUser.luMenus}">
                    <li class="layui-nav-item layui-nav-itemed">
                        <a href="javascript:;">${module.spName}</a>
                        <dl class="layui-nav-child">
                            <c:forEach var="menu" items="${module.childrenSysPrivilege}">
                                <dd>
                                    <a data-url="${basePath}${menu.spUri}" data-id="${menu.spId}"
                                       data-title="${menu.spName}" href="#" class="site-customTab-active"
                                       data-type="tabAdd" id="defaultTab">${menu.spName}</a></dd>
                            </c:forEach>
                        </dl>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <div class="layui-tab layui-tab-brief" lay-filter="customTab" lay-allowclose="true" style="margin-left: 20px;">
            <ul class="layui-tab-title">
            </ul>
            <div class="layui-tab-content">
            </div>
            <ul class="rightmenu zzp-rightmenu-ul" style="display: none;">
                <li data-type="closethis" class="zzp-rightmenu-li">
                    关闭当前
                </li>
                <hr class="layui-bg-cyan"/>
                <li data-type="closeother" class="zzp-rightmenu-li">
                    关闭其他
                </li>
                <hr class="layui-bg-cyan"/>
                <li data-type="closeleftall" class="zzp-rightmenu-li">
                    关闭左侧所有
                </li>
                <hr class="layui-bg-cyan"/>
                <li data-type="closerightall" class="zzp-rightmenu-li">
                    关闭右侧所有
                </li>
                <hr class="layui-bg-cyan"/>
                <li data-type="closeall" class="zzp-rightmenu-li">
                    关闭所有
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-footer">
        <div class="layui-main">
            <p class="layui-bg-gray">
                这里是底部区域xxx
            </p>
        </div>
    </div>
</div>

<script type="text/javascript">
    layui.use(['layer', 'jquery'], function () {
        var $ = layui.jquery,
            layer = layui.layer;

        // 基本资料
        $('.layui-nav-child .userInfo').on('click', function () {
            layer.open({
                title: '设置我的基本资料',
                type: 2,
                area: ['529px', '483px'],
                maxmin: true,
                content: '${basePath}/sysUser/settingData?suId=' + ${loginUser.luId},
                end: function () {
                    table.reload('tableReload');
                }
            });
        });

        // 修改密码
        $('.layui-nav-child .updatePwd').on('click', function () {
            layer.open({
                title: '修改密码',
                type: 2,
                area: ['590px', '304px'],
                maxmin: true,
                content: '${basePath}/pkdSysUser/updatePwd_ui?suId=' + ${loginUser.luId}
            });
        });
    });
</script>
</body>
</html>
