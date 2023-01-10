<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div ui-view="" class="ng-scope">

        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">用户管理</h1>
        </div>
        <div style="padding:20px 20px 0px 20px">
            <div class="panel panel-default">
                <div class="panel-heading font-bold">检索条件</div>
                <div class="panel-body">
                    <form class="form-inline" id="searchForm">
                        <div class="form-group">
                            <label>用户名：</label>
                            <input type="text" class="form-control inch" id="username" name="username">
                        </div>
                        <div class="form-group">
                            <label>姓名：</label>
                            <input type="text" class="form-control inch" id="name" name="name">
                        </div>
                        <span class="btn btn-sm btn-info" style="margin-left:20px"
                              onclick="javascript:toSearch('${ctx}/sysUser/dataList.do')">查询</span>
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
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增/修改</h4>
            </div>

            <div class="modal-body">
                <form id="editForm" data-parsley-validate="true">
                    <input class="hidden" type="text" name="id" value="0">


                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <label>用户名 </label>
                            <input type="text" class="inch" id="username"
                                   name="username" required/>
                        </div>
                        <div class="inch-group col-sm-6">
                            <label>姓名 </label>
                            <input type="text" class="inch" id="name"
                                   name="name" required/>
                        </div>
                    </div>

                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <label>控制区域</label>
                            <input id="classid" name="classid" class="hidden"/>
                            <input name=className id="className" type="text" class="form-control inch select" value=""
                                   onclick="showMenu($('#className'),$('#menuContent'));"/>
                        </div>

                        <div class="inch-group col-sm-6">
                            <label>角色</label>
                            <input id="selRoles" name="selRoles" class="hidden"/>
                            <input name="roleName" id="roleName" type="text" class="form-control inch select" value=""
                                   onclick="showMenu($('#roleName'),$('#menuContent3'));"/>
                        </div>
                    </div>

                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <label>星级</label>
                            <select name="star" class="inch">
                                <option value="5">5</option>
                                <option value="4">4</option>
                                <option value="3">3</option>
                                <option value="2">2</option>
                                <option value="1">1</option>
                            </select>
                        </div>
                        <div class="inch-group col-sm-6">
                            <label>工号</label>
                            <input type="text" class="inch" id="no"
                                   name="no" required/>
                        </div>
                    </div>

                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <label>是否党员</label>
                            <select name="member" class="inch">
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>
                        <div class="inch-group col-sm-6">
                            <label>是否有效 </label>
                            <select name="state" class="inch">
                                <option value="0">有效</option>
                                <option value="1">无效</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <label>证件号码</label>
                            <input type="text" class="inch" id="idcard"
                                   name="idcard" required/>
                        </div>
                    </div>
                    <div class="inch-group form-group">
                        <label>个人格言</label>
                        <%--<textarea class="form-control inch"  rows="1" id="course" name="course" required></textarea>--%>
                        <input type="text" class="inch" id="course" name="course"/>
                    </div>
                    <div class="inch-group form-group">
                        <label>头像&nbsp;&nbsp;</label>
                        <span>
							   <img id="logopic" alt="" src="" height="200" width="200">
							   <input name="pic" id="pic" type="text" style="display:none"/>
					   </span>

                        <span class="btn btn-success fileinput-button">
							       <i class="glyphicon glyphicon-plus"></i>
							        <span>上传</span>
							        <input id="fileupload" type="file" name="fileupload">
					   </span>
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

<div id="menuContent" class="menuContent" style="display:none; position: absolute;z-index:9999;width:260px;">
    <ul id="classTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>

<div id="menuContent2" class="menuContent2" style="display:none; position: absolute;z-index:9999;width:260px;">
    <ul id="headerTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>

<div id="menuContent3" class="menuContent3" style="display:none; position: absolute;z-index:9999;width:260px;">
    <ul id="roleTree" class="ztree" style="margin-top:0; width:100%;"></ul>
</div>

<script>
    var table;
    $(document).ready(function () {

        table = $('#inch_dataTable').DataTable({
            "processing": false,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "dom": '<"toolbar">frtip',
            "ajax": {
                "url": "${ctx}/sysUser/dataList.do",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "data": "username", "title": "用户名", "width": "160px"
            }, {
                "data": "name", "title": "姓名", "width": "200px"
            }, {
                "data": "schoolname", "title": "单位"
            }, {
                "data": "cteacher", "title": "控制区域"
            }, {
                "data": "addtime", "title": "添加时间", "width": "160px", "class": "text-center"
            }, {
                "data": "", "title": "操作", "width": "60px", "class": "text-center"
            }],
            "columnDefs": [{
                "targets": 3,
                render: function (a, b, c, d) {

                    var html = "";

                    if (c.cList != null && c.cList.length > 0) {

                        for (var item = 0; item < c.cList.length; item++) {
                            var itemData = c.cList[item];
                            html += itemData + "、";
                            if (item > 5) {
                                break;
                            }
                        }
                    }

                    return html;
                }
            }, {
                targets: 5,
                render: function (a, b, c, d) {

                    var html = "<a href=javascript:edit('" + c.id + "');><i class='glyphicon glyphicon-list icon'></i></a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    html += "<a href=\"javascript:delbytype('${ctx}/sysUser/delete.do','" + c.id + "',0);\"><i class='glyphicon glyphicon-minus icon'></i></a>&nbsp;&nbsp;";
                    html += "<a href=\"javascript:resetpwd('" + c.guid + "','" + c.username + "');\"><i class='icon-wrench'></i></a>";
                    return html;
                }
            }
            ]

        });

        $("#save").click(save);

        $('#editForm').bootstrapValidator();

        initRole('${ctx}/sysRole/loadRoleList.do');
        initSchool2('${ctx}/school/schooltree.do?rootid=school');

        initUploadWithPress($("#fileupload"), '${ctx}', true, false, '', '', '', '', '', function (data) {
            $("#pic").val(data.result.url);
            $("#logopic").attr("src", data.result.url);
        })
    });


    function edit(id) {

        clearZTree();
        $("#editForm").resetForm();
        $("#editForm input[id=username]").attr("readonly", "readonly");

        editformbyname($("#editForm"), $("#myModal"), '${ctx}/sysUser/getId.do?id=' + id, function (result) {

            initSelectState(result.data.headteacher, "headerTree", $("#headName"));
            initCheckState(result.data.roleIds, "roleTree", $("#roleName"), $("#selRoles"));
            initCheckUserState(result.data.school, "classTree", $("#className"), $("#classid"));

            $("#logopic").attr("src", result.data.pic);
        });
    }

    function save() {

        saveform($("#editForm"), '${ctx}/sysUser/save.do', function (data) {

            if (data.success) {
                $("#myModal").modal("hide");
                //table.ajax.reload();
                table.page(table.page()).draw(false);
            }
        })
    }

    function resetpwd(id, username) {

        layer.confirm('确认要重置密码吗?', {
            icon: 3,
            title: '提示'
        }, function (index) {

            $.post("${ctx}/sysUser/resetpwd.do", {"guid": id, "username": username}, function (data) {
                if (data.success) {
                    layer.alert("保存成功!", {icon: 1});
                } else {
                    layer.alert(data.msg, {icon: 5});
                }
            });

            layer.close(index);
        });


    }


    function add() {
        clearZTree();

        $("#logopic").attr("src", "");
        $("#editForm input[id=username]").removeAttr("readonly");

        $("#editForm").resetForm();
        $("#myModal").modal("show");
    }

</script>

