<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>角色列表</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>
<body>
<div class="search">
    角色名：
    <div class="layui-inline">
        <input class="layui-input" name="srName" id="srName" autocomplete="off">
    </div>
    &nbsp;&nbsp;
    <button class="layui-btn" data-type="reload">搜索</button>
    &nbsp;
    <button class="layui-btn" data-type="btnAdd" data-title="新增角色" data-area="455px,290px"
            data-content="${basePath}/sysRole/add_ui">新增角色
    </button>
</div>

<table class="layui-table" lay-data="{cellMinWidth: 80, method: 'POST' , even: true, page: true, limit:10,
       limits: [10,15, 20, 25, 30, 35, 40, 45, 50],id:'tableReload',url:'${basePath}/sysRole/list'}"
       lay-filter="tableReload">
    <thead>
    <tr>
        <th lay-data="{field:'srName',title:'角色名'}"></th>
        <th lay-data="{field:'srDescription',title:'描述'}"></th>
        <th lay-data="{field:'srCreateTime',title:'创建时间',sort:true,templet:'#srCreateTime'}"></th>
        <th lay-data="{field:'srUpdateTime',title:'更新时间',sort:true,templet:'#srUpdateTime'}"></th>
        <th lay-data="{fixed: 'right',title:'操作', width:200, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
</table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="allotSysPrivilege">分配权限</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="srCreateTime">
    {{formatDate(d.srCreateTime,'yyyy-MM-dd hh:mm:ss')}}
</script>
<script type="text/html" id="srUpdateTime">
    {{formatDate(d.srUpdateTime,'yyyy-MM-dd hh:mm:ss')}}
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
                    var params = {"srId": data.srId};
                    $.ajax({
                        url: '${basePath}/sysRole/delete',
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
                    title: '编辑角色',
                    type: 2,
                    area: ['460px', '300px'],
                    maxmin: true,
                    content: '${basePath}/sysRole/edit_ui?srId=' + data.srId,
                    end: function () {
                        table.reload('tableReload');
                    }
                });
            } else if (layEvent === 'allotSysPrivilege') {
                layer.open({
                    title: '分配权限',
                    type: 2,
                    area: ['860px', '420px'],
                    maxmin: true,
                    content: '${basePath}/sysPrivilege/allotSysPrivilege_ui?roleId=' + data.srId,
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
