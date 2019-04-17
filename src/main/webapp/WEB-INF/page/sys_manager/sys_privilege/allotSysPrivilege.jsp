<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>分配权限</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>

<body>
<form class="layui-form" style="margin-top: 30px;">
    <input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
    <div class="layui-form-item">
        <label class="layui-form-label">分配权限</label>
        <br/><br/>
        <div class="layui-input-block">
            <c:forEach var="sys_privilege" items="${all_sys_privilege}">
                <input type="checkbox" name="srpPrivilegeIds" title="${sys_privilege.spName}"
                       value="${sys_privilege.spId}"   ${fn:contains(have_sys_privilege,sys_privilege.spId )  ? 'checked' : ''}>
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
            var checkedElem = $("input[name='srpPrivilegeIds']:checked");
            var checkeds = new Array();
            $.each(checkedElem, function (i) {
                checkeds.push($(this).val())
            });

            var params = {};
            params['roleId'] = data.field.roleId;
            params['srpPrivilegeIds'] = checkeds.join();
            console.log(params);
            var index = parent.layer.getFrameIndex(window.name);
            $.ajax({
                url: '${basePath}/sysPrivilege/allotSysPrivilege',
                type: 'POST',
                data: params,
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        parent.layer.close(index);
                        parent.layer.msg("系统提示 : 权限分配成功");
                    } else {
                        parent.layer.close(index);
                        parent.layer.msg("系统提示 : 权限分配失败");
                    }
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
