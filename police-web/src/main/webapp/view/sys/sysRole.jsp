<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div ui-view="" class="ng-scope">

        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">角色管理</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">角色信息</div>
                <div class="table-responsive" style="padding: 0 5px">
                    <table id="inch_dataTable" class="table table-striped m-b-none">
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- uiView:  -->
</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:700px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增/修改</h4>
            </div>

            <div class="modal-body">


                <form id="editForm" data-parsley-validate="true">

                    <input type="hidden" name="id"/>
                    <input type="hidden" name="menubtn" id="menubtn">
                    <div class="inch-group form-group">
                        <label>角色名称 </label> <input type="text" class="inch" id="name"
                                                    name="name" data-parsley-required="true"/>
                    </div>

                    <div class="inch-group form-group">
                        <label>状态</label>

                        <select name="state" class="inch">
                            <option value="0" selected="selected">可用</option>
                            <option value="1">禁用</option>
                        </select>
                    </div>

                    <div class="inch-group form-group">
                        <label>权限 </label>
                        <div class="inch" style="overflow: auto; ">
                            <ul id="menu-tree" class="ztree"></ul>
                        </div>
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


<script>
    var table;
    $(document).ready(function () {

        table = $('#inch_dataTable').DataTable({
            "processing": false,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "ajax": {
                url: "${ctx}/sysRole/dataList.do"
            },
            "dom": '<"toolbar">frtip',
            "columns": [{
                "data": "name", "title": "角色名称"
            }, {
                "data": "state", "title": "角色名称"
            }, {
                "data": "createTime", "title": "添加时间"
            }, {
                "data": "updateTime", "title": "修改时间"
            }, {
                "data": "", "title": "操作", "width": "45px", "class": "text-center"
            }],
            "columnDefs": [{
                targets: 1,
                render: function (a, b, c, d) {

                    if (c.state == 0) {
                        return "可用";
                    } else {
                        return "<span style='color:red'>禁用</span>"
                    }
                    return html;
                }
            }, {
                targets: 4,
                render: function (a, b, c, d) {

                    var html = "<a href=javascript:edit('" + c.id + "');><i class='glyphicon glyphicon-list icon'></i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    html += "<a href=javascript:delbyID('${ctx}/sysRole/delete.do','" + c.id + "');><i class='glyphicon glyphicon-minus icon'></i></a>";
                    return html;
                }
            }
            ]
        });


        $("#save").click(save);

        initTree();

        $('#editForm').bootstrapValidator();
    });


    function initTree() {

        var setting = {
            check: {
                enable: true
            },
            callback: {
                onCheck: onCheck
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                }
            }
        };

        $.ajax({
            type: 'POST',
            dataType: "json",
            async: false,
            url: "${ctx}/sysMenu/getMenuTree.do",
            data: {"flag": true},
            error: function () {
                layer.alert("请求失败!", {icon: 5});
            },
            success: function (data) {
                $.fn.zTree.init($("#menu-tree"), setting, data);
            }

        })
    }


    function onCheck(e, treeId, treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("menu-tree");
        nodes = treeObj.getCheckedNodes(true);
        var ids = "";
        for (var i = 0; i < nodes.length; i++) {
            ids += nodes[i].id + ",";
        }

        $("#menubtn").val(ids);
    }

    function edit(id) {

        clearTreeData();

        editformbyname($("#editForm"), $("#myModal"),
            '${ctx}/sysRole/getId.do?id=' + id, function (result) {

                var zTree = $.fn.zTree.getZTreeObj("menu-tree");
                $.each(result.data.menuIds, function (i, id) {

                    var node = zTree.getNodeByParam("id", id);
                    zTree.checkNode(node, true, false);
                })

                $.each(result.data.btnIds, function (i, id) {

                    var node = zTree.getNodeByParam("id", id + "_btn");
                    zTree.checkNode(node, true, false);
                })
            });

    }

    function add() {
        clearTreeData();
        $("#editForm").resetForm();
        $("#myModal").modal("show");
    }

    function save() {
        saveform($("#editForm"), '${ctx}/sysRole/save.do', function (data) {
            $("#myModal").modal("hide");
            table.ajax.reload();
        })
    }

    function clearTreeData() {
        var zTree = $.fn.zTree.getZTreeObj("menu-tree");
        zTree.checkAllNodes(false);
    }
</script>

