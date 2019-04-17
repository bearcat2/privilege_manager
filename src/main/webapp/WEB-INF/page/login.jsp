<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登陆</title>
    <%@include file="/WEB-INF/page/common/common.jsp" %>

    <style type="text/css">
        body {
            background-color: #f1faff;
        }

        .logo {
            margin-top: 50px;
            margin-left: 50px;
        }
    </style>
</head>
<body>
<div class="logo">
    <img src="${basePath}/statics/img/logo.png">
</div>
<div class="layui-container">
    <div class="layui-row" style="margin-top: 60px;">
        <div class="layui-col-md6 layui-col-md-offset4">
            <form class="layui-form layui-form-pane" method="POST" action="${basePath}/login">
                <div class="layui-form-item">
                    <h3>权限管理系统登录</h3>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">用户名：</label>
                    <div class="layui-input-inline">
                        <input type="text" name="username" class="layui-input" lay-verify="required"
                               placeholder="请输入用户名" autocomplete="on" maxlength="20"/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">密码：</label>

                    <div class="layui-input-inline">
                        <input type="password" name="password" class="layui-input" lay-verify="required"
                               placeholder="请输入密码" maxlength="20"/>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">验证码：</label>
                        <div class="layui-input-inline" style="width: 100px;">
                            <input type="text" name="clientValidateCode" class="layui-input" lay-verify="required"
                                   placeholder="请输入验证码" maxlength="4"/>
                        </div>
                        <div class="layui-input-inline">
                            <img src="${basePath}/validatecode.jsp" title="单击刷新验证码" id="img_rand_code">
                        </div>
                    </div>
                </div>

                <div class="layui-form-item">
                    <button type="reset" class="layui-btn layui-btn-danger btn-reset">重置</button>
                    <button type="button" class="layui-btn btn-submit" lay-submit="" lay-filter="login">立即登录</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    layui.use(['form', 'layer', 'jquery'], function () {
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer;

        // 提交监听
        form.on('submit(login)', function (data) {
            $.ajax({
                url: '${basePath}/sysUser/login',
                type: 'POST',
                data: data.field,
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        // 登录成功跳转到首页
                        window.location.href = "${basePath}/index";
                    } else {
                        layer.msg("系统提示 : " + data.msg);
                    }
                }
            });
            return false;
        });

        //刷新验证码
        $("#img_rand_code").click(function () {
            $(this).attr("src", '${basePath}/validatecode.jsp?' + Math.random());
        });
    });
</script>
</body>

</html>
