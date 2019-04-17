<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>编辑用户</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>
<style type="text/css">
    .input-width {
        width: 400px;
    }
</style>
<body>
<form class="layui-form">
    <input type="hidden" id="srId" name="srId" value="${sys_role.srId}"/>

    <div class="layui-form-item input-width" style="margin-top: 15px;">
        <label class="layui-form-label">角色名</label>
        <div class="layui-input-block">
            <input type="text" name="srName" lay-verify="required"
                   value="${sys_role.srName}"
                   placeholder="请输入角色名" autocomplete="off" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item layui-form-text input-width">
        <label class="layui-form-label">角色描述</label>
        <div class="layui-input-block">
            <textarea name="srDescription" placeholder="请输入内容" class="layui-textarea">${sys_role.srDescription}</textarea>
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
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function (data) {
            var index = parent.layer.getFrameIndex(window.name);
            $.ajax({
                url: '${basePath}/sysRole/edit',
                type: 'POST',
                data: data.field,
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        parent.layer.close(index);
                        parent.layer.msg("系统提示 : 数据编辑成功");
                    } else {
                        parent.layer.close(index);
                        parent.layer.msg("系统提示 : 数据编辑失败");
                    }
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
