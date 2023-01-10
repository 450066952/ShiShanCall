<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="${ctx}/inchplugin/kindeditor-4.1.10/themes/default/default.css"/>
<script charset="utf-8" src="${ctx}/inchplugin/kindeditor-4.1.10/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}/inchplugin/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div class="ng-scope">
        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">校园动态</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">校园动态信息</div>
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
                        <label>标题</label>
                        <input type="text" class="inch" id="title" name="title" autocomplete='off'/>
                    </div>
                    <div class="inch-group form-group">
                        <label>url</label>
                        <input type="text" class="inch" id="url" name="url" autocomplete='off'/>
                    </div>

                    <div class="inch-group form-group">
                        <label>内容</label>
                        <textarea class="form-control inch" rows="5" id="nr" name="nr"
                                  style="width:100%;height:500px;"></textarea>
                    </div>
                    <div class="form-group clearfix">
                        <div class="inch-group">
                            <label>参会人员分组类别 </label>
                            <input id="selPart" name="departid" autocomplete="off" class="hidden" value=""/>
                            <input name="depart" id="partName" type="text" autocomplete="off"
                                   class="form-control inch select" value=""
                                   onclick="showMenu($('#partName'),$('#menuContent1'));"/>
                        </div>
                    </div>

                    <div class="form-group clearfix">
                        <div class="inch-group">
                            <label>参会人员 </label>
                            <input id="selusersValue" name="selusers" autocomplete="off" class="hidden" value=""/>
                            <input name="selusersText" id="selusers" type="text" autocomplete="off"
                                   class="form-control inch select" value=""
                                   onclick="showMenu($('#selusers'),$('#menuContent2'));"/>
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
<div id="menuContent1" class="menuContent1" style="display:none; position: absolute;z-index:9999;width:260px;">
    <ul id="partTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>
<div id="menuContent2" class="menuContent2" style="display:none; position: absolute;z-index:9999;">
    <ul id="selusersTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>

<script>

    var table;
    var editor1;
    $(document).ready(function () {

        table = $('#inch_dataTable').DataTable({
            "processing": false,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "paging": true,
            "dom": '<"toolbar">frtip',
            "ajax": {
                "url": "${ctx}/schoolnews/dataList.do",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "title": "标题", "data": "title", "width": "25%"
            }, {
                "title": "学校", "data": "schoolname"
            }, {
                "title": "添加人", "data": "addusername"
            }, {
                "title": "发布时间", "data": "addtime"
            }, {
                "data": "", "title": "操作", "width": "80px", "class": "text-center"
            }],
            "columnDefs": [{
                targets: 4,
                render: function (a, b, c, d) {
                    var html = "<a href=\"javascript:edit('" + c.guid + "');\" ><i class='glyphicon glyphicon-list icon' title='编辑'></i></a>&nbsp;&nbsp;";
                    html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delbytype('${ctx}/schoolnews/delete.do','" + c.guid + "',0);\" ><i class='glyphicon glyphicon-minus icon' title='删除'></i></a>";

                    return html;
                }
            }
            ]
        });

        $("#save").click(save);

        editor1 = KindEditor.create('#nr', {
            uploadJson: '${ctx}/UploadImageServlet?ispress=1&maxW=1080&notkeeppic=1',
            allowFileManager: false
        });

        $('#myModal').off('shown.bs.modal').on('shown.bs.modal', function (e) {
            $(document).off('focusin.modal');
        });

        <%--if("${u_info.isadmin}"!=1){--%>
        <%--column = table.column(1);--%>
        <%--column.visible(false);--%>
        <%--}--%>

        $('#editForm').bootstrapValidator();
    });


    function edit(id) {
        editformbyname($("#editForm"), $("#myModal"), '${ctx}/schoolnews/getId.do?id=' + id, function (result) {
            console.log(result, "res")
            editor1.html(result.data.nr);
            var _newDepartid = result.data.departid == null ? [] : result.data.departid.split(",")
            initPart3("/school/getMyOrgLists.do", _newDepartid, result.users);
        });

    }

    function save() {
        editor1.sync();
        saveform($("#editForm"), '${ctx}/schoolnews/save.do', function (data) {
            $("#myModal").modal("hide");
            table.ajax.reload(null, false);
        })
    }

    function add() {
        $("#editForm").resetForm();
        editor1.html("");
        $("#myModal").modal("show");
    }


    function initPart3(url, obj, users) {
        //
        $.getJSON(url, {}, function (data) {
            $.fn.zTree.init($("#partTree"), partsetting, data.data);
            initSelectPart3(obj, "partTree", $("#partName"), $("#selPart"))
            initselusers3("/sysUser/getUserByDepart.do?departid=" + obj, users)
        });
    }

    var partsetting = {
        check: {
            enable: true
        },

        callback: {
            onCheck: onCheckPart
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid"
            },
            key: {  //节点数据
                name: "text",//zTree 节点数据保存节点名称的属性名称。

            }
        }
    };

    function initSelectPart3(data, treename, nameObj, idObj) {
        var zTree2 = $.fn.zTree.getZTreeObj(treename);
        var node;
        var cname = "";
        var cid = "";
        $.each(data, function (i, id) {

            node = zTree2.getNodeByParam("id", id);
            var nodes = zTree2.getNodes();
            if (node) {

                if (!node.isParent) {
                    zTree2.checkNode(node, true, true);

                    if (cname.length > 0) {
                        cname = cname + "," + node.text;
                        cid = cid + "," + node.id;
                    } else {
                        cname = node.text;
                        cid = node.id;
                    }
                }


                var parent = node.getParentNode();
                if (parent && !parent.open) {
                    zTree2.expandNode(parent, true, true);
                }
            }
        })


        nameObj.val(cname).change();
        idObj.val(cid);
    }

    function onCheckPart(e, treeId, treeNode) {


        var treeObj = $.fn.zTree.getZTreeObj("partTree");
        nodes = treeObj.getCheckedNodes(true);
        var ids = "", names = "";
        for (var i = 0; i < nodes.length; i++) {
            ids += nodes[i].id + ",";
            names += nodes[i].text + ",";
        }

        if (ids.length > 0) {
            ids = ids.substring(0, ids.length - 1);
            names = names.substring(0, names.length - 1);
        }
        $("#partName").val(names).change();
        $("#selPart").val(ids);
        console.log("ids", ids)
        initselusers3("/sysUser/getUserByDepart.do?departid=" + ids)
    }

    function initselusers3(url, param) {

        $.getJSON(url, {}, function (data) {

            $.fn.zTree.init($("#selusersTree"), seluserssetting, data);
            var _a = []
            if (param == undefined) {
                data.map(function (val) {
                    _a.push(val["username"])
                })
            } else {
                _a = param
            }
            initSelectselusers(_a, "selusersTree", $("#selusers"), $("#selusersValue"));
        });
    }

    var seluserssetting = {
        check: {
            enable: true
        },
        callback: {
            onCheck: onCheckselusers
        },
        data: {
            simpleData: {
                enable: false
            }
        }
    }

    function initSelectselusers(data, treename, nameObj, idObj) {


        var zTree2 = $.fn.zTree.getZTreeObj("selusersTree");
        var node;
        var cname = "";
        var cid = "";
        $.each(data, function (i, id) {
            var nodes = zTree2.getNodes();
            node = zTree2.getNodeByParam("username", id);
            if (node) {
                if (!node.isParent) {
                    zTree2.checkNode(node, true, true);

                    if (cname.length > 0) {
                        cname = cname + "," + node.name;
                        cid = cid + "," + node.username;
                    } else {
                        cname = node.name;
                        cid = node.username;
                    }
                }

                var parent = node.getParentNode();
                if (parent && !parent.open) {
                    zTree2.expandNode(parent, true, true);
                }
            }
        })


        nameObj.val(cname).change();
        idObj.val(cid);
    }

    function onCheckselusers(e, treeId, treeNode) {


        var treeObj = $.fn.zTree.getZTreeObj("selusersTree");
        nodes = treeObj.getCheckedNodes(true);
        var ids = "", names = "";
        for (var i = 0; i < nodes.length; i++) {
            ids += nodes[i].username + ",";
            names += nodes[i].name + ",";
        }

        if (ids.length > 0) {
            ids = ids.substring(0, ids.length - 1);
            names = names.substring(0, names.length - 1);
        }
        $("#selusers").val(names).change();
        $("#selusersValue").val(ids);
    }
</script>

