<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>权限列表</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>
<body>
<div class="search">
    权限名：
    <div class="layui-inline">
        <input class="layui-input" name="spName" id="spName" autocomplete="off">
    </div>
    &nbsp;&nbsp;
    资源uri：
    <div class="layui-inline">
        <input class="layui-input" name="spUri" id="spUri" autocomplete="off">
    </div>
    &nbsp;&nbsp;
    <button class="layui-btn" data-type="reload">搜索</button>
    &nbsp;
    <button class="layui-btn" data-type="btnAdd" data-title="新增权限" data-area="455px,338px"
            data-content="${basePath}/sysPrivilege/add_ui">新增权限
    </button>
</div>

<table class="layui-table" lay-data="{cellMinWidth: 80, method: 'POST' , even: true, page: true, limit:10,
       limits: [10,15, 20, 25, 30, 35, 40, 45, 50],id:'tableReload',url:'${basePath}/sysPrivilege/list'}"
       lay-filter="tableReload">
    <thead>
    <tr>
        <th lay-data="{field:'spId',title:'权限id'}"></th>
        <th lay-data="{field:'spName',title:'权限名'}"></th>
        <th lay-data="{field:'spUri',title:'资源uri'}"></th>
        <th lay-data="{field:'spUri',title:'资源类型',templet:function(d){
                    if(d.spType == 1){
                        return '模块';
                    }else if(d.spType == 2){
                        return '菜单';
                    }else if(d.spType == 3){
                        return '权限';
                    }
                 }}"></th>
        <th lay-data="{field:'spParentId',title:'父权限id'}"></th>
        <th lay-data="{field:'spOrderd',title:'排序'}"></th>
        <th lay-data="{field:'spCreateTime',title:'创建时间',sort:true,templet:'#spCreateTime'}"></th>
        <th lay-data="{field:'spUpdateTime',title:'更新时间',sort:true,templet:'#spUpdateTime'}"></th>
        <th lay-data="{fixed: 'right',title:'操作', width:200, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
</table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit" data-id="2">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="spCreateTime">
    {{formatDate(d.spCreateTime,'yyyy-MM-dd hh:mm:ss')}}
</script>

<script type="text/html" id="spUpdateTime">
    {{formatDate(d.spUpdateTime,'yyyy-MM-dd hh:mm:ss')}}
</script>

<script>
    layui.use(['element', 'table', 'jquery', 'layer'], function () {
        var table = layui.table,
            $ = layui.jquery,
            layer = layui.layer;

        table.on('tool(tableReload)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'del') {
                top.layer.confirm('您真的要执行删除操作吗？', {title: '系统提示', area: ['400px', '200px']}, function (index) {
                    top.layer.close(index);
                    var params = {"spId": data.spId};
                    $.ajax({
                        url: '${basePath}/sysPrivilege/delete',
                        type: 'POST',
                        data: params,
                        dataType: 'json',
                        success: function (data) {
                            if (data.success) {
                                // 执行表格重载
                                table.reload('tableReload');
                                layer.msg("系统提示 : 数据删除成功");
                            } else {
                                layer.msg("系统提示 : 数据删除失败");
                            }
                        }
                    });
                });
            } else if (layEvent === 'edit') {
                layer.open({
                    title: '编辑用户',
                    type: 2,
                    area: ['458px', '341px'],
                    maxmin: true,
                    content: '${basePath}/sysPrivilege/edit_ui?spId=' + data.spId,
                    end: function () {
                        table.reload('tableReload');
                    }
                });
            }
        });
    });
</script>

</body>
</html>
