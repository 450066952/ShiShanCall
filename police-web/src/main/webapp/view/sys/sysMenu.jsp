<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style>
    .back {
        display: none;
    }
</style>

<div class="app-content-body fade-in-up ng-scope" ui-view="">
    <!-- uiView:  -->
    <div class="ng-scope">

        <div class="bg-light lter b-b wrapper-md">

            <button type="button" class="btn btn-sm btn-success btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>

            <button id='btnback' class="btn m-b-xs btn-sm btn-info btn-addon pull-right back" style="margin-right: 5px"
                    onclick='javascript:toList();'>
                <i class="fa fa-trash-o"></i>返回
            </button>

            <h1 class="m-n font-thin h3">菜单管理</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">菜单管理</div>
                <div class="table-responsive" style="padding: 0 5px">
                    <input class="hidden" id='search_parentId' name="parentId">
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

                    <input type="text" style="display:none" name="id" id="id">
                    <input type="text" style="display:none" name="deleted">
                    <input type="text" style="display:none" name="parentId" id='edit_parentId'>

                    <div class="inch-group form-group">
                        <label>名称 </label> <input type="text" class="inch" id="name"
                                                  name="name" data-parsley-required="true"/>
                    </div>

                    <div class="inch-group form-group">
                        <label>URL</label> <input type="text" class="inch" id="url"
                                                  name="url" data-parsley-required="true"/>
                    </div>

                    <div class="inch-group form-group">
                        <label>排序</label> <input type="text" class="inch" id="rank"
                                                 name="rank" data-parsley-required="true"/>
                    </div>

                    <div class="inch-group form-group">
                        <label>Actions</label>
                        <input type="text" class="form-control" id="actions" name="actions"/>
                    </div>

                    <div class="inch-group form-group">
                        <table style="width:98%">
                            <tr>
                                <td align="center">
                                    <button type='button' id='addLine_btn' class='btn btn-sm btn-primary m-r-5'>新增
                                    </button>
                                    <button type='button' id='addDefLine_btn' class='btn btn-sm btn-primary m-r-5'>默认
                                    </button>
                                    <button type='button' id='delAllLine_btn' class='btn btn-sm btn-danger m-r-5'>删除所有
                                    </button>
                                </td>
                            </tr>
                        </table>
                        <table id="btn-tb" style="width:98%">
                            <thead>
                            <tr>
                                <th width="5%"></th>
                                <th width="25%">按钮名称</th>
                                <th width="25%">按钮类型</th>
                                <th width="35%">注册Action(用"|"分格)</th>
                                <th width="10%">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
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
            "serverSide": false,
            "searching": false,
            "ordering": true,
            "dom": '<"toolbar">frtip',
            "ajax": {
                url: "${ctx}/sysMenu/dataList.do"
            },
            "columns": [{
                "data": "name", "title": "菜单名称"
            },
                {
                    "data": "pname", "title": "上层菜单"
                }, {
                    "data": "rank", "title": "排序"
                }, {
                    "data": "url", "title": "url"
                }, {
                    "data": "childMenus", "title": "子菜单"
                }, {
                    "data": "", "title": "操作", "width": "45px", "class": "text-center"
                }],
            "columnDefs": [{
                targets: 4,
                render: function (a, b, c, d) {
                    var html = "<a href='#' onclick='toList(" + c.id + ")'>子菜单管理(" + c.subCount + ")</a>";
                    return html;
                }
            }, {
                targets: 5,
                render: function (a, b, c, d) {

                    var html = "<a href=javascript:edit('" + c.id + "');><i class='glyphicon glyphicon-list icon'></i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    html += "<a href=javascript:delbyID('${ctx}/sysMenu/delete.do','" + c.id + "');><i class='glyphicon glyphicon-minus icon'></i></a>";
                    return html;
                }
            }
            ]

        });


        $("#save").click(save);

        $('#addLine_btn').click(addLine);
        $('#addDefLine_btn').click(addDefBtns);
        $('#delAllLine_btn').click(function () {
            $.messager.confirm('Confirm', '确定要删除所有数据吗?', function (r) {
                delAllLine(false);
            });
        });

        $('#editForm').bootstrapValidator();
    });


    function toList(parentId) {

        // Get the column API object
        var column = table.column(4);

        if (parentId) {
            $('#search_parentId').val(parentId);

            //$("#btnback").removeAttr("display");
            $("#btnback").removeClass("back");
            // Toggle the visibility
            column.visible(false);
            //$('#data-list').datagrid('hideColumn','childMenus');

            table.ajax.url('${ctx}/sysMenu/dataList.do?parentId=' + parentId).load();
        } else {
            $("#btnback").addClass("back");

            column.visible(true);

            table.ajax.url('${ctx}/sysMenu/dataList.do').load();
        }


        //toSearch();

    }

    function add() {

        delAllLine(true);

        $("#editForm").resetForm();

        var parentId = $('#search_parentId').val();
        if (parentId) {
            $("#edit_parentId").val(parentId);
        }


        $("#myModal").modal("show");
    }

    /**
     *编辑方法
     **/

    function edit(id) {

        delAllLine(true);

        editformbyname($("#editForm"), $("#myModal"), '${ctx}/sysMenu/getId.do?id=' + id, function (result) {
            $.each(result.data.btns, function (i, btn) {
                addLine(btn);
            });
        });
    }

    /**
     *保存
     **/

    function save() {
        saveform($("#editForm"), '${ctx}/sysMenu/save.do', function (data) {
            $("#myModal").modal("hide");
            table.ajax.reload();
        })
    }

    //设置默认按钮数据
    function addDefBtns() {
        var defaultBtns = [
            {"btnName": "添加", "menuid": 2, "actionUrls": "save.do", "btnType": "add"},
            {"btnName": "修改", "menuid": 2, "actionUrls": "getId.do|save.do", "btnType": "edit"},
            {"btnName": "删除", "menuid": 2, "actionUrls": "delete.do", "btnType": "remove"}
        ];
        var tbline = $(".tb-line:visible");
        var btnType = $("input[name='btnType']", tbline);
        $.each(defaultBtns, function (i, btn) {
            var isExists = false;
            //判断按钮类型是否存在
            if (btnType.length > 0) {
                for (var i = 0; i < btnType.length; i++) {
                    if (btnType.eq(i).val() == btn.btnType) {
                        isExists = true;
                        break;
                    }
                }
            }
            if (!isExists) {
                addLine(btn);
            }
        });
    }

    function addLine(data) {
        var table = $("#btn-tb");
        var html = "<tr class='tb-line'>";
        html += "	<td align='center'><span  class='newFlag red'>*</span></td>";
        html += "	<td><input name=\"btnName\"  style=\"width:100%\" data-options=\"required:true\"></td>";
        html += "	<td><input name=\"btnType\"  style=\"width:100%\" data-options=\"required:true\"></td>";
        html += "	<td><input name=\"actionUrls\"  style=\"width:100%\"  ></td>";
        html += "	<td align='center'><a class=\"remove-btn\"  ><i class='glyphicon glyphicon-minus icon'></i></a>";
        html += "	<input class=\"hidden\" name=\"btnId\">";
        html += "	<input class=\"hidden\" name=\"deleteFlag\">";
        html += "	</td>";
        html += "</tr>";
        var line = $(html);
        //版定删除按钮事件
        $(".remove-btn", line).click(function () {

            layer.confirm('确认要删除本条记录吗?', {
                icon: 3,
                title: '提示'
            }, function (index) {
                delLine(line);
                layer.close(index);
            });

        });
        if (data) {
            if (data.id) {
                $(".newFlag", line).html(''); //清空新增标记
            }
            $("input[name='btnId']", line).val(data.id);
            $("input[name='btnName']", line).val(data.btnName);
            $("input[name='btnType']", line).val(data.btnType);
            $("input[name='actionUrls']", line).val(data.actionUrls);
        }
        table.append(line);

    }

    //删除全部
    function delAllLine(b) {
        if (b) {
            $(".tb-line").remove();
        } else {
            $(".tb-line").each(function (i, line) {
                delLine($(line));
            });
        }
    }

    //删除单行
    function delLine(line) {
        if (line) {
            var btnId = $("input[name='btnId']", line).val();
            if (btnId) {
                $("input[name='deleteFlag']", line).val(1); //设置删除状态
                line.fadeOut();
            } else {
                line.fadeOut("fast", function () {
                    $(this).remove();
                });
            }
        }
    }
</script>

