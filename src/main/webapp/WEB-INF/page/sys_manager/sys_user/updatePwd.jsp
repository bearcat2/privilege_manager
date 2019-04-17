<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>修改密码</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>

<style type="text/css">
    .input-width {
        width: 350px;
    }
</style>
<body>
<form class="layui-form" action="${basePath}/pkdSysUser/updatePwd" method="post">
    <input type="hidden" id="suId" name="suId" value="${suId}"/>

    <div class="layui-form-item input-width" style="margin-top: 15px;">
        <label class="layui-form-label">原始密码</label>
        <div class="layui-input-block">
            <input type="password" name="oldPwd" lay-verify="required" lay-verType="tips" placeholder="请输入原始密码"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item input-width">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-block">
            <input type="password" name="newPwd" lay-verify="required" lay-verType="tips" placeholder="请输入新密码"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item input-width">
        <label class="layui-form-label">确认新密码</label>
        <div class="layui-input-block">
            <input type="password" name="affirmNewPwd" id="affirmNewPwd" lay-verType="tips" lay-verify="required"
                   placeholder="请再次输入新密码"
                   autocomplete="off"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="reset" class="layui-btn layui-btn-danger">重置</button>
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
        </div>
    </div>
</form>

<script type="text/javascript">
    //Demo
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            var flag = true;
            if (data.field.newPwd != data.field.affirmNewPwd) {
                layer.tips('两次输入的密码不一致', '#affirmNewPwd');
                $("#affirmNewPwd").val('');
                flag = false;
            }
            if (flag) {
                var index = parent.layer.getFrameIndex(window.name);
                $.ajax({
                    url: '${basePath}/pkdSysUser/updatePwd',
                    type: 'POST',
                    data: data.field,
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == 200) {
                            parent.layer.close(index);
                            window.top.location.href = '${basePath}/index';
                        } else {
                            parent.layer.close(index);
                            parent.layer.msg("系统提示 : " + data.msg);
                        }
                    }
                });
            }
            return false;
        });
    });
</script>
</body>
</html>
