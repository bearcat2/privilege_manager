<%@ page language="java" contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/page/common/taglibs.jsp" %>
<!doctype html>
<html lang="en">
<head>
    <title>用户列表</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0,
                                   maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <%@include file="/WEB-INF/page/common/common.jsp" %>
</head>
<body>
<div class="search">
    登录名：
    <div class="layui-inline">
        <input class="layui-input" name="suLoginName" id="suLoginName" autocomplete="off">
    </div>
    &nbsp;&nbsp;
    <button class="layui-btn" data-type="reload">搜索</button>
    &nbsp;
    <button class="layui-btn" data-type="btnAdd" data-title="新增用户" data-area="465px,332px"
            data-content="${basePath}/sysUser/add_ui">新增用户
    </button>
</div>

<table class="layui-table" lay-data="{cellMinWidth: 80, method: 'POST' , even: true, page: true, limit:10,
       limits: [10,15, 20, 25, 30, 35, 40, 45, 50],id:'tableReload',url:'${basePath}/sysUser/list'}"
       lay-filter="tableReload">
    <thead>
    <tr>
        <th lay-data="{field:'suLoginName',title:'登录名'}"></th>
        <th lay-data="{field:'suRealName',title:'真实姓名'}"></th>
        <th lay-data="{field:'suCreateTime',title:'创建时间',sort:true,templet:'#suCreateTime'}"></th>
        <th lay-data="{field:'suUpdateTime',title:'更新时间',sort:true,templet:'#suUpdateTime'}"></th>
        <th lay-data="{fixed: 'right',title:'操作', width:200, align:'center', toolbar: '#barDemo'}"></th>
    </tr>
    </thead>
</table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="allotSysRole">分配角色</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit" data-id="2">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="suCreateTime">
    {{formatDate(d.suCreateTime,'yyyy-MM-dd hh:mm:ss')}}
</script>
<script type="text/html" id="suUpdateTime">
    {{formatDate(d.suUpdateTime,'yyyy-MM-dd hh:mm:ss')}}
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
                    var params = {"suId": data.suId};
                    $.ajax({
                        url: '${basePath}/sysUser/delete',
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
                    area: ['465px', '242px'],
                    maxmin: true,
                    content: '${basePath}/sysUser/edit_ui?suId=' + data.suId,
                    end: function () {
                        table.reload('tableReload');
                    }
                });
            } else if (layEvent === 'allotSysRole') {
                layer.open({
                    title: '分配角色',
                    type: 2,
                    area: ['860px', '420px'],
                    maxmin: true,
                    content: '${basePath}/sysRole/allotSysRole_ui?userId=' + data.suId,
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
