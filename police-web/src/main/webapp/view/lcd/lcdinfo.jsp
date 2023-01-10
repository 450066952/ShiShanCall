<%@ page language="java" pageEncoding="UTF-8" %>

<div class="app-content-body fade-in-up ng-scope">
    <div class="ng-scope">
        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">设备管理</h1>
        </div>

        <div style="padding:5px 20px 0px 20px">
            <div class="panel ">
                <div class="panel-heading font-bold" style="height: 36px">设备信息</div>
                <div class="table-responsive" style="padding: 0 5px">
                    <table id="inch_dataTable" class="table table-striped m-b-none">

                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:60%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增/修改</h4>
            </div>

            <div class="modal-body">

                <form id="editForm">

                    <input class="hidden" type="text" name="guid">

                    <div class="inch-group form-group">
                        <label>位置</label>
                        <input id="classid" name="classid" class="hidden"/>
                        <input name="headName" id="headName" type="text" class="form-control inch select" value=""
                               onclick="showMenu($('#headName'),$('#menuContent2'));"/>

                    </div>
                    <div class="inch-group form-group">
                        <label>窗口 </label>
                        <input id="winguid" name="winguid" class="hidden"/>
                        <input name="winName" id="winName" type="text" class="form-control inch select" value=""
                               onclick="showMenu($('#winName'),$('#menuContent'));"/>
                    </div>

                    <div class="inch-group form-group">
                        <label>模式</label>
                        <select name="model" id="model" class="inch">
                            <option value="0">窗口模式</option>
                            <option value="1">大型叫号模式</option>
                            <option value="3">小型叫号模式</option>
                            <option value="4">评价器</option>
                            <option value="5">取号机</option>
                            <option value="2">固定模式</option>
                        </select>
                    </div>
                    <div class="inch-group form-group">
                        <label>播放声音</label>
                        <select name="isvoice" id="isvoice" class="inch">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div id="menuContent2" class="menuContent2" style="display:none; position: absolute;z-index:9999;width:260px;">
    <ul id="headerTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>

<div id="menuContent" class="menuContent" style="display:none; position: absolute;z-index:9999;">
    <ul id="windowTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>

<script>


    var table;
    $(document).ready(function () {

        table = $('#inch_dataTable').DataTable({
            "processing": false,
            "serverSide": false,
            "searching": false,
            "ordering": true,
            "paging": true,
            "pageLength": 100,
            "dom": '<"toolbar">frtip',
            "ajax": {
                "url": "${ctx}/lcd/dataList.do",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "data": "state", "title": "状态", "width": "60px", "class": "text-center"
            }, {
                "data": "guid", "title": "SN序列号", "bSortable": false, "width": "100px"
            }, {
                "data": "schoolname", "title": "单位"
            }, {
                "data": "gradename", "title": "区域", "width": "120px"
            }, {
                "data": "classname", "title": "柜台"
            }, {
                "data": "version", "title": "版本"
            }, {
                "data": "model", "title": "模式"
            }, {
                "data": "schoolname", "title": "窗口", "width": "180px"
            }, {
                "data": "addtime", "title": "添加时间", "width": "100px"
            }, {
                "data": "", "title": "操作", "width": "80px", "class": "text-center", "bSortable": false
            }],
            "columnDefs": [{
                targets: 0,
                render: function (a, b, c, d) {
                    if (c.state == "1") {
                        return "<i class='icon-screen-desktop text-success'><span style='display:none'>1</span></i>";
                    } else {
                        return "<i class='fa fa-warning icon text-danger'><span style='display:none'>0</span></i>";
                    }
                }
            }, {
                targets: 6,
                render: function (a, b, c, d) {

                    if (c.model == 0) {
                        return "窗口模式";
                    } else if (c.model == 1) {
                        return "大型叫号模式";
                    } else if (c.model == 2) {
                        return "固定模式";
                    } else if (c.model == 3) {
                        return "小型叫号模式";
                    } else if (c.model == 4) {
                        return "评价器";
                    } else if (c.model == 5) {
                        return "取号机";
                    }
                }
            }, {
                targets: 7,
                render: function (a, b, c, d) {
                    var html = "";
                    if (c.winList != null && c.winList.length > 0) {

                        for (var item = 0; item < c.winList.length; item++) {
                            var itemData = c.winList[item];
                            if (item == 0) {
                                html += itemData;
                            } else {
                                html += itemData + ",";
                            }
                        }
                    }
                    return html;
                }
            }, {
                targets: 9,
                render: function (a, b, c, d) {

                    var html = "<a href=\"javascript:edit('" + c.guid + "');\" ><i class='glyphicon glyphicon-list icon'></i></a>";
                    html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:initdata('" + c.guid + "');\" ><i class='icon-screen-desktop'></i></a>";
                    html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delbytype('${ctx}/lcd/delete.do','" + c.guid + "'," + c.type + ");\" ><i class='glyphicon glyphicon-minus icon'></i></a>";

                    return html;
                }
            }
            ]

        });

        $("#save").click(save);

        initSingerSchool2('${ctx}/school/schooltree.do?rootid=school');

        initWin('${ctx}/window/opList.do')
        if ("${u_info.isadmin}" != 1) {
            column = table.column(2);
            column.visible(false);
        }

        $('#editForm').bootstrapValidator();
    });


    function edit(id) {


        clearZTree2();

        editformbyname($("#editForm"), $("#myModal"), '${ctx}/lcd/getId.do?id=' + id, function (result) {

            $.getJSON('/window/opList.do', {'orgid': result.data.schoolid}, function (data) {

                $.fn.zTree.init($("#windowTree"), getSetting("guid", "pguid", true, selwindow), data);
                initCheckStateNew(result.data.winguids, "windowTree", $("#winName"), $("#winguid"));

            });
            initSelectState(result.data.classid, "headerTree", $("#headName"));


        });
    }


    function save() {
        saveform($("#editForm"), '${ctx}/lcd/save.do', function (data) {
            $("#myModal").modal("hide");
            table.ajax.reload(null, false);
        })
    }

    function add() {

        $("#winguid").val("");
        $("#winName").val("");

        $("#editForm").resetForm();
        clearZTree2();
        $("#myModal").modal("show");
    }

    function initdata(guid) {

        ajaxJson("${ctx}/lcd/lcdInitData.do", "id=" + guid, function (data) {

            if (data.success) {
                layer.alert("初始化成功!", {icon: 1});
            } else {
                layer.alert(data.msg, {icon: 5});
            }

        })

    }

</script>

