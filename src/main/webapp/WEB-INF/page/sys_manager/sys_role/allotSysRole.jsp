<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>分配角色</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>

<body>
<form class="layui-form" style="margin-top: 30px;">
    <input type="hidden" id="userId" name="userId" value="${userId}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">分配角色</label>
        <br/><br/>
        <div class="layui-input-block">
            <c:forEach var="sys_role" items="${all_sys_role}">
                <input type="checkbox" name="roleIds" title="${sys_role.srName}"
                       value="${sys_role.srId}"   ${fn:contains(have_sys_role,sys_role.srId)  ? 'checked' : ''}>
            </c:forEach>
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
    layui.use(['form', 'jquery'], function () {
        var form = layui.form,
            $ = layui.jquery;

        //监听提交
        form.on('submit(formDemo)', function (data) {
            var checkedElem = $("input[name='roleIds']:checked");
            var checkeds = new Array();
            $.each(checkedElem, function (i) {
                checkeds.push($(this).val())
            });

            var params = {};
            params['userId'] = data.field.userId;
            params['roleIds'] = checkeds.join();
            var index = parent.layer.getFrameIndex(window.name);
            $.ajax({
                url: '${basePath}/sysRole/allotSysRole',
                type: 'POST',
                data: params,
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        parent.layer.close(index);
                        parent.layer.msg("系统提示 : 角色分配成功");
                    } else {
                        parent.layer.close(index);
                        parent.layer.msg("系统提示 : 角色分配失败");
                    }
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
