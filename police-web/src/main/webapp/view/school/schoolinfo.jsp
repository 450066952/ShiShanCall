<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<script src="${ctx}/inchplugin/TreeTables/jquery.treetable.js?v=1" type="text/javascript"></script>
<link href="${ctx}/inchplugin/TreeTables/jquery.treetable.css" rel="stylesheet"/>
<link href="${ctx}/inchplugin/TreeTables/jquery.treetable.theme.default.css" rel="stylesheet"/>

<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div class="ng-scope">
        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add(0);'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">窗口柜台</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">窗口柜台信息</div>
                <div class="table-responsive" style="padding: 0 5px">
                    <table id="inch_dataTable" class="table table-striped m-b-none">
                        <thead>
                        <tr>
                            <th style="width:30%">柜台名称</th>
                            <th style="width:20%">排序</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="treebody">
                        </tbody>
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

                    <input class="hidden" type="text" name="id">

                    <div class="inch-group form-group">
                        <label>上层节点 </label>
                        <input id="pid" name="pid" class="hidden"/>
                        <input name="headName" id="headName" type="text" class="form-control inch select" value=""
                               onclick="showMenu($('#headName'),$('#menuContent2'));"/>
                    </div>


                    <div class="inch-group form-group">
                        <label>名称</label>
                        <input type="text" class="inch" id="name" name="name"/>
                    </div>
                    <div class="inch-group form-group">
                        <label>排序</label>
                        <input type="text" class="inch" id="sortby" name="sortby"/>
                    </div>

                    <div id="pictr">
                        <div class="form-group pull-in clearfix">
                            <div class="inch-group col-sm-6">
                                <label>LOGO&nbsp;&nbsp;</label>
                                <span>
									<img id="logopic" alt="" src="" height="200" width="300">
									<input name="pic" id="pic" type="text" style="display:none"/>
								</span>

                                <span class="btn btn-success fileinput-button">
							       <i class="glyphicon glyphicon-plus"></i>
							        <span>上传</span>
							        <input id="fileupload" type="file" name="fileupload">
							    </span>
                            </div>
                        </div>
                        <div class="form-group pull-in clearfix">
                            <div class="inch-group col-sm-6">
                                <label>背景图片</label>
                                <span>
									<img id="bglogopic" alt="" src="" height="200" width="300">
									<input name="bgpic" id="bgpic" type="text" style="display:none"/>
								</span>

                                <span class="btn btn-success fileinput-button">
							       <i class="glyphicon glyphicon-plus"></i>
							        <span>上传</span>
							        <input id="fileupload2" type="file" name="fileupload2">
							    </span>
                            </div>
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


<div id="menuContent2" class="menuContent2" style="display:none; position: absolute;z-index:9999;width:260px;">
    <ul id="headerTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>

<script>

    var table;
    var htmstr = "";
    $(document).ready(function () {

        table = $('#inch_dataTable').DataTable({
            "processing": false,
            "searching": false,
            "ordering": false,
            "dom": '<"toolbar">frtip',
            "paging": false,
            "info": false
        });

        initSingerSchoolTree('${ctx}/school/schooltree.do?rootid=school');

        $("#save").click(save);

        initUploadWithPress($("#fileupload"), '${ctx}', true, false, '', '', 578, 100, '', function (data) {
            $("#pic").val(data.result.url);
            $("#logopic").attr("src", data.result.url);
        })

        initUploadWithPress($("#fileupload2"), '${ctx}', true, false, '', '', '', '', '', function (data) {
            $("#bgpic").val(data.result.url);
            $("#bglogopic").attr("src", data.result.url);
        })

        getTreeData();

        $('#editForm').bootstrapValidator();
    });

    function edit(id) {
        clearZTree();

        editformbyname($("#editForm"), $("#myModal"), '${ctx}/school/getId.do?id=' + id, function (result) {

            if (result.data.pid == 0) {
                $("#logopic").attr("src", result.data.pic);
                $("#bglogopic").attr("src", result.data.bgpic);
                $('#pictr').css('display', '');
            } else {
                initSelectState(result.data.pid, "headerTree", $("#headName"));
                $('#pictr').css('display', 'none');
            }
        });
    }


    function save() {

        saveform($("#editForm"), '${ctx}/school/save.do', function (data) {

            $("#myModal").modal("hide");
            getAjaxHtm('${ctx}/school/list.shtml');
        })
    }


    function add(id) {

        clearZTree();
        if (id == 0) {
            $('#pictr').css('display', '');
        } else {
            $('#pictr').css('display', 'none');
        }

        $("#editForm").resetForm();

        initSelectState(id, "headerTree", $("#headName"));
        $("#pid").val(id);

        $("#myModal").modal("show");
    }

    function delById(id) {
        layer.confirm('确认要操作吗?', {
            icon: 3,
            title: '提示'
        }, function (index) {

            $.ajax({
                url: "${ctx}/school/delete.do",
                data: {
                    "id": id
                },
                success: function (data) {
                    getAjaxHtm('${ctx}/school/list.shtml');
                }
            });

            layer.close(index);
        });
    }


    function getTreeData() {
        $.ajax({
            type: "GET",
            url: "${ctx}/school/schooltreeList.do",
            dataType: "json",
            success: function (result) {

                buildTreeData(result);

                $("#treebody").html(htmstr);

                $("#inch_dataTable").treetable({
                    stringCollapse: '关闭',
                    stringExpand: '展开',
                    expandable: true
                    //initialState :"expanded"//默认打开所有节点
                });
            }
        });
    }


    function buildTreeData(lists) {

        var itemData;
        for (var item = 0; item < lists.length; item++) {
            itemData = lists[item];
            htmstr += "<tr data-tt-id='" + itemData.id + "'";
            if (itemData.pid > 0) {
                htmstr += "data-tt-parent-id='" + itemData.pid + "'";
            }

            htmstr += ">";

            htmstr += "<td>" + itemData.name + "</td>";
            htmstr += "<td>" + itemData.sortby + "</td>";
            //htmstr+="<a href=\"javascript:add("+itemData.id+");\" class='btn btn-sm btn-primary m-r-5'>新增</a>&nbsp;&nbsp;";

            htmstr += "<td><a href=\"javascript:add('" + itemData.id + "');\" ><i class='glyphicon glyphicon-plus icon'></i></a>";
            htmstr += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:edit('" + itemData.id + "');\" ><i class='glyphicon glyphicon-list icon'></i></a>";

            if (itemData.pid == 0) {
                if ("${u_info.isadmin}" == 1) {
                    htmstr += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delById(" + itemData.id + ");\" ><i class='glyphicon glyphicon-minus icon'></i></a>";
                }

                htmstr += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:initNum(" + itemData.id + ");\" >初始化叫号</a>";
            } else {
                htmstr += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delById(" + itemData.id + ");\" ><i class='glyphicon glyphicon-minus icon'></i></a>";
            }

            htmstr += "</td></tr>"

            if (itemData.children != null && itemData.children.length > 0) {
                buildTreeData(itemData.children);
            }
        }
    }


    function initNum(orgid) {
        $.ajax({
            type: "post",
            url: "${ctx}/school/initNum.do?orgid=" + orgid,
            dataType: "json",
            success: function (data) {

                if (data.success) {
                    layer.alert("保存成功!", {icon: 1});
                } else {
                    layer.alert(data.msg, {icon: 5});
                }

            }
        });
    }

</script>

