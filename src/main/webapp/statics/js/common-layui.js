layui.use(['element', 'table', 'jquery', 'layer'], function () {
    //使用layui定好的模块
    var element = layui.element,
        table = layui.table,
        $ = layui.jquery,
        layer = layui.layer;

    //触发事件
    var active = {
        tabAdd: function (url, id, name) {
            element.tabAdd('customTab', {
                title: name,
                content: '<iframe data-frameid="' + id + '" scrolling="auto" frameborder="0" src=" ' + url + ' " width="100%"  height="100%"></iframe>',
                id: id
            });
            CustomRightClick();
            FrameWH();
        },
        tabChange: function (id) {
            //切换到指定Tab项
            element.tabChange('customTab', id);
        },
        tabDelete: function (id) {
            element.tabDelete("customTab", id);
        },
        tabDeleteAll: function (ids) {
            $.each(ids, function (i, item) {
                element.tabDelete("customTab", item);
            });
        }
    };

    //当点击有site-customTab-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
    $('.site-customTab-active').on('click', function () {
        var dataid = $(this);
        //这时会判断右侧.layui-tab-title属性下的有lay-id属性的li的数目，即已经打开的tab项数目
        if ($(".layui-tab-title li[lay-id]").length <= 0) {
            //如果比零小，则直接打开新的tab项
            active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
        } else {
            //否则判断该tab项是否以及存在,isData为false说明未打开该tab项 为true则说明已有
            var isData = false;
            $.each($(".layui-tab-title li[lay-id]"), function () {
                if ($(this).attr("lay-id") == dataid.attr("data-id")) {
                    isData = true;
                }
            });
            if (!isData) {
                active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
            }
        }
        //最后不管是否新增tab，最后都转到要打开的选项页面上
        active.tabChange(dataid.attr("data-id"));
    });

    // 切换tab选项卡刷新页面内容及切换到左侧指定的导航栏
    element.on('tab(customTab)', function () {
        // var $iframe = $(".layui-tab-item.layui-show").find("iframe");
        // $iframe.attr("src", $iframe.attr("src"));
        var layId = this.getAttribute("lay-id");
        $.each($(".site-customTab-active"), function () {
            if ($(this).attr("data-id") === layId) {
                $(".site-customTab-active").parent("dd").removeClass("layui-this");
                $(this).parent("dd").addClass("layui-this");
            }
        });
    });

    // 页面加载打开默认的tab选项卡
    // active.tabAdd($("#defaultTab").attr("data-url"), $("#defaultTab").attr("data-id"), $("#defaultTab").attr("data-title"));
    // active.tabChange($("#defaultTab").attr("data-id"));

    function CustomRightClick() {
        //取消右键 rightmenu属性开始是隐藏的 ，当右击的时候显示，左击的时候隐藏
        $('.layui-tab-title li').on('contextmenu', function () {
            return false;
        });

        $('.layui-tab-title,.layui-tab-title li').click(function () {
            $('.rightmenu').hide();
        });

        //桌面点击右击
        $('.layui-tab-title li').on('contextmenu', function (e) {
            var popupmenu = $(".rightmenu");
            //判断右侧菜单的位置
            var l = ($(document).width() - e.clientX) < popupmenu.width() ? (e.clientX - popupmenu.width()) : e.clientX;
            var t = ($(document).height() - e.clientY) < popupmenu.height() ? (e.clientY - popupmenu.height()) : e.clientY;
            //进行绝对定位
            popupmenu.show().css({'left': (l - 130) + 'px', 'top': (t - 45) + 'px'});
            return false;
        });
    }

    $(".rightmenu li").hover(
        function () {
            $(this).addClass("layui-bg-green");
            $(this).css("cursor", "pointer");
        },
        function () {
            $(this).removeClass("layui-bg-green");
        }
    );

    //右键菜单中的选项被点击之后，判断type的类型，决定关闭当前、关闭其他还是关闭所有
    $(".rightmenu li").click(function () {
        // 获取tab选中项
        var selectedItem = $(".layui-tab-title").find(".layui-this");
        switch ($(this).attr("data-type")) {
            case "closethis":
                active.tabDelete(selectedItem.attr("lay-id"));
                break;

            case "closeother":
                active.tabDeleteAll(getCloseTabId(selectedItem.siblings()));
                break;

            case "closeleftall":
                active.tabDeleteAll(getCloseTabId(selectedItem.prevAll()));
                break;

            case "closerightall":
                active.tabDeleteAll(getCloseTabId(selectedItem.nextAll()));
                break;

            case "closeall":
                active.tabDeleteAll(getCloseTabId($(".layui-tab-title li")));
                break;

            default:
                console.log("操作不支持");
                break;
        }
        $('.rightmenu').hide();
    });

    // 获得要关闭的tab选项卡id集合
    function getCloseTabId(e) {
        var ids = new Array();
        $.each(e, function (i) {
            ids[i] = $(this).attr("lay-id");
        });
        return ids;
    }

    // 计算iframe层大小
    function FrameWH() {
        var h = $(window).height() - 41 - 10 - 60 - 10 - 44 - 10 - 10 - 10;
        $("iframe").css("height", h + "px");
    }

    $(window).resize(function () {
        FrameWH();
    });

    //表格重载
    var tableActive = {
        reload: function (params) {
            // 执行重载
            table.reload('tableReload', {
                page: {
                    curr: 1
                },
                where: params
            });
        }
    };

    $('.search .layui-btn').on('click', function () {
        var dataType = $(this).attr("data-type");
        if (dataType === 'reload') {
            var params = {};
            $('.search .layui-input').each(function () {
                params[$(this).prop("name")] = $(this).val();
            });
            tableActive.reload(params);
        } else if (dataType === 'btnAdd') {
            var title = $(this).attr("data-title");
            var area = $(this).attr("data-area").split(",");
            var content = $(this).attr("data-content");
            layer.open({
                title: title,
                type: 2,
                area: area,
                maxmin: true,
                content: content,
                end: function () {
                    // 页面关闭后,执行表格重载
                    table.reload('tableReload');
                }
            });
        }
    });

    window.formatDate = function (datetime, fmt) {
        if (parseInt(datetime) == datetime) {
            if (datetime.length == 10) {
                datetime = parseInt(datetime) * 1000;
            } else if (datetime.length == 13) {
                datetime = parseInt(datetime);
            }
        }
        var date = new Date(datetime);
        var o = {
            "M+": date.getMonth() + 1,                 //月份
            "d+": date.getDate(),                    //日
            "h+": date.getHours(),                   //小时
            "m+": date.getMinutes(),                 //分
            "s+": date.getSeconds(),                 //秒
            "q+": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
});
