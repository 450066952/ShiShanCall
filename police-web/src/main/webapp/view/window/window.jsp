<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    ul.ztree {
        height: 200px;
    }
</style>
<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div ui-view="" class="ng-scope">

        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">窗口管理</h1>
        </div>
        <div style="padding:20px 20px 0px 20px">
            <div class="panel panel-default">
                <div class="panel-heading font-bold">检索条件</div>
                <div class="panel-body">
                    <form class="form-inline" id="searchForm">
                        <div class="form-group">
                            <label>所属单位：</label>
                            <input type="text" class="form-control inch" name="orgname">
                        </div>
                        <div class="form-group">
                            <label>窗口名称：</label>
                            <input type="text" class="form-control inch" name="name">
                        </div>
                        <div class="form-group">
                            <label>业务类型：</label>
                            <input type="text" class="form-control inch" name="typename">
                        </div>
                        <span class="btn btn-sm btn-info" style="margin-left:20px"
                              onclick="javascript:toSearch('${ctx}/window/dataList.do')">查询</span>
                    </form>
                </div>
            </div>
        </div>
        <div style="padding:0px 20px 0px 20px">
            <div class="panel">
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
                        <label>所属单位</label>
                        <select name="orgid" id="orgid" class="inch">
                            <option value='0'>请选择</option>
                            <c:forEach var='item' items='${orglist}'>
                                <option value='${item.id}'>${item.text}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="inch-group form-group">
                        <label>窗口名称</label>
                        <input type="text" class="inch" id="name" name="name" required/>
                    </div>
                    <!-- 			   <div class="inch-group form-group">
					   <label>二级菜单</label>
                      <select name="type" id="stype" class="inch">
                           <c:forEach var='item' items='${itemlist}'>
                               <option value='${item.guid}'>${item.name}</option>
                           </c:forEach>
                       </select>
				   </div>

                   <div class="inch-group form-group">
                       <label>三级菜单</label>
                      <select name="type" id="stype" class="inch">
                           <c:forEach var='item' items='${itemlist}'>
                               <option value='${item.guid}'>${item.name}</option>
                           </c:forEach>
                       </select>
                   </div> -->
                    <div class="form-group clearfix">
                        <div class="inch-group">
                            <label>二级菜单 </label>
                            <input id="secondMenu" name="type" autocomplete="off" class="hidden" value="0"/>
                            <input name="secondMenuTree" id="secondMenuTree" type="text" autocomplete="off"
                                   class="form-control inch select" value=""
                                   onclick="showMenu($('#secondMenuTree'),$('#menuContent2'));"/>
                        </div>
                    </div>
                    <div class="form-group clearfix">
                        <div class="inch-group">
                            <label>三级菜单 </label>
                            <input id="thirdMenu" name="firsttype" autocomplete="off" class="hidden" value="0"/>
                            <input name="thirdMenuTree" id="thirdMenuTree" type="text" autocomplete="off"
                                   class="form-control inch select" value=""
                                   onclick="showMenu($('#thirdMenuTree'),$('#menuContent3'));"/>
                        </div>
                    </div>
                    <div class="inch-group form-group">
                        <label>排序</label>
                        <input type="text" class="inch" id="sort" name="sort" required/>
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
<div id="menuContent2" class="menuContent2" style="display:none; position: absolute;z-index:9999;">
    <ul id="secondTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>
<div id="menuContent3" class="menuContent2" style="display:none; position: absolute;z-index:9999;">
    <ul id="thirdTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>
<script>

    var table;
    $(document).ready(function () {

        table = $('#inch_dataTable').DataTable({
            "processing": false,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "paging": true,
            "dom": '<"toolbar">frtip',
            "ajax": {
                "url": "${ctx}/window/dataList.do",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "title": "所属单位", "data": "orgname"
            }, {
                "title": "窗口名称", "data": "name"
            }, {
                "title": "业务类型", "data": "typename", "defaultContent": ""
            }, {
                "title": "添加人", "data": "addusername", "width": "60px"
            }, {
                "data": "", "title": "操作", "width": "60px", "class": "text-center"
            }],
            "columnDefs": [{
                targets: 4,
                render: function (a, b, c, d) {

                    var html = "<a href=\"javascript:edit('" + c.guid + "');\" ><i class='glyphicon glyphicon-list icon'></i></a>&nbsp;&nbsp;";
                    html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delbytype('${ctx}/window/delete.do','" + c.guid + "',0);\" ><i class='glyphicon glyphicon-minus icon'></i></a>";
                    return html;
                }
            }
            ]

        });

        $("#save").click(save);
        $('#editForm').bootstrapValidator();
    });

    function edit(id) {
        clearZTree();

        editformbyname($("#editForm"), $("#myModal"), '${ctx}/window/getId.do?id=' + id, function (result) {
            console.log(result, "resi")
            var _secondType = result.data.type ? result.data.type.split(",") : '',
                _thirdType = result.data.firsttype ? result.data.firsttype.split(",") : '';
            initSecondTree("${ctx}/sysDic/getTwoDic.shtml?orgid=" + result.data.orgid, _secondType)
            initSecondTree2("${ctx}/sysDic/getThreeDic.shtml?orgid=" + result.data.orgid, _thirdType)
        });

    }

    $("#orgid").change(function () {

        initSecondTree("${ctx}/sysDic/getTwoDic.shtml?orgid=" + $(this).val())
        initSecondTree2("${ctx}/sysDic/getThreeDic.shtml?orgid=" + $(this).val())
    })

    function save() {

        saveform($("#editForm"), '${ctx}/window/save.do', function () {
            $("#myModal").modal("hide");
            table.ajax.reload(null, false);
        })
    }

    function add() {
        $(".ztree").each(function () {
            var obj = $.fn.zTree.getZTreeObj($(this).attr("id"));

            if (obj != null) {
                obj.cancelSelectedNode();
                obj.checkAllNodes(false);
            }
        })
        $("#editForm").resetForm();
        $("#myModal").modal("show");
    }

    //联动
    $("#orgid").change(function () {
        var id = $("#orgid").val();
        $("#stype option").remove();
        $.ajax({
            type: "POST",
            url: "${ctx}/sysDic/getDic.do",
            data: {'orgid': id},
            success: function (result) {
                var html = "<option value='0'>请选择</option>";
                if (result.data != null && result.data.length > 0) {
                    for (var i = 0; i < result.data.length; i++) {
                        html += "<option value='" + result.data[i].guid + "'>" + result.data[i].name + "</option>";
                    }
                }
                $("#stype").html(html);
            }
        })
    });
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "guid",
                pIdKey: "pid"
            }
        },
        callback: {
            onCheck: secondMenuClick
        }
    };

    function initSecondTree(url, param) {

        $.getJSON(url, {}, function (data) {

            $.fn.zTree.init($("#secondTree"), setting, data.data);
            initSelectSecond(param, "secondTree", $("#secondMenuTree"), $("#secondMenu"))
        });
    }

    function secondMenuClick(event, treeId, treeNode) {

        var zTree = $.fn.zTree.getZTreeObj("secondTree"),
            nodes = zTree.getCheckedNodes(true),
            v = "",
            name = "";
        for (var i = 0, l = nodes.length; i < l; i++) {

            if (!nodes[i].isParent) {
                v += nodes[i].guid + ",";
                name += nodes[i].name + ",";
            }
        }
        if (v.length > 0) {
            v = v.substring(0, v.length - 1);
            name = name.substring(0, name.length - 1);
        }

        var catObj = $("#secondMenuTree"), catId = $("#secondMenu");
        catObj.attr("value", name).change();
        catId.attr("value", v);
    }

    // ------------------------------------------------------------>
    var setting2 = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "guid",
                pIdKey: "pid"
            }
        },
        callback: {
            onCheck: secondMenuClick2
        }
    };

    function initSecondTree2(url, param) {

        $.getJSON(url, {}, function (data) {
            console.log("thirdTree", data)
            $.fn.zTree.init($("#thirdTree"), setting2, data.data);
            initSelectSecond(param, "thirdTree", $("#thirdMenuTree"), $("#thirdMenu"))
        });
    }

    function secondMenuClick2(event, treeId, treeNode) {

        var zTree = $.fn.zTree.getZTreeObj("thirdTree"),
            nodes = zTree.getCheckedNodes(true),
            v = "",
            name = "";
        for (var i = 0, l = nodes.length; i < l; i++) {

            if (!nodes[i].isParent) {
                v += nodes[i].guid + ",";
                name += nodes[i].name + ",";
            }
        }
        if (v.length > 0) {
            v = v.substring(0, v.length - 1);
            name = name.substring(0, name.length - 1);
        }

        var catObj = $("#thirdMenuTree"), catId = $("#thirdMenu");
        catObj.attr("value", name).change();
        catId.attr("value", v);
    }


    function initSelectSecond(data, treename, nameObj, idObj) {


        var zTree2 = $.fn.zTree.getZTreeObj(treename);


        var node;
        var cname = "";
        var cid = "";
        $.each(data, function (i, id) {

            node = zTree2.getNodeByParam("guid", id);

            if (node) {

                if (!node.isParent) {
                    zTree2.checkNode(node, true, true);

                    if (cname.length > 0) {
                        cname = cname + "," + node.name;
                        cid = cid + "," + node.id;
                    } else {
                        cname = node.name;
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

    function showMenu(nameobj, menuObj) {

        var cityObj = nameobj; //$("#className");
        var cityOffset = nameobj.offset();  // $("#className").offset();
        menuObj.css({
            left: cityOffset.left + "px",
            top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");

        mObj = menuObj;
        $("body").bind("mousedown", onBodyDown);
    }
</script>

