<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<style>
    .checkbox {
        position: relative;
        display: block;
        margin: 18px 30px;
    }

</style>
<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div class="ng-scope">
        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">系统通知</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">系统通知</div>
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

                <form id="editForm">

                    <input class="hidden" type="text" name="guid">
                    <input class="hidden" type="text" name="oldclassid">
                    <input class="hidden" type="text" name="isscreen" value="1">


                    <div class="inch-group form-group">
                        <label>位置</label>
                        <input id="classid" name="classid" class="hidden"/>
                        <input name=className id="className" type="text" class="form-control inch select" value=""
                               onclick="showMenu($('#className'),$('#menuContent'));"/>
                    </div>
                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <label>开始时间 </label>
                            <input type="text" class="inch inchtime" id="begintime" name="begintime"/>
                        </div>
                        <div class="inch-group col-sm-6">
                            <label>结束时间 </label>
                            <input type="text" class="inch inchtime" id="endtime" name="endtime"/>
                        </div>
                    </div>
                    <div class="inch-group form-group">
                        <label>通知</label>
                        <textarea class="form-control inch" rows="3" id="notice" name="notice" required></textarea>
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

<div id="menuContent" class="menuContent" style="display:none; position: absolute;z-index:9999;">
    <ul id="classTree" class="ztree" style="margin-top:0; width:100%;"></ul>
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
                "url": "${ctx}/notice/dataList.do",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "title": "系统通知", "data": "notice"
            }, {
                "title": "开始时间", "data": "begintime", "width": "120px"
            }, {
                "title": "结束时间", "data": "endtime", "width": "120px"
            }, {
                "title": "位置", "data": "location"
            }, {
                "title": "添加人", "data": "addusername", "width": "60px"
            }, {
                "data": "", "title": "操作", "width": "60px", "class": "text-center"
            }],
            "columnDefs": [{
                targets: 0,
                render: function (a, b, c, d) {
                    return decodeURIComponent(c.notice);
                }
            }, {
                targets: 3,
                render: function (a, b, c, d) {

                    return getClassInfo(c.classList);
                }
            }, {
                targets: 5,
                render: function (a, b, c, d) {
                    var html = "";

                    if ("${u_info.id}" == c.adduser || "${u_info.isadmin}" == 1) {
                        html = "<a href=\"javascript:edit('" + c.guid + "');\" ><i class='glyphicon glyphicon-list icon'></i></a>&nbsp;&nbsp;";
                        html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delbytype('${ctx}/notice/delete.do','" + c.guid + "'," + c.type + ");\" ><i class='glyphicon glyphicon-minus icon'></i></a>";
                    }

                    return html;
                }
            }
            ]

        });

        $("#save").click(save);

        $.datetimepicker.setLocale('ch');
        $('.inchtime').datetimepicker({
            format: "Y-m-d H:i",
            timepicker: true,
            step: 5
        });


        initSchool('${ctx}/school/schooltree.do?rootid=school');

        $('#editForm').bootstrapValidator();
    });

    function edit(id) {
        clearZTree();
        editformbyname($("#editForm"), $("#myModal"), '${ctx}/notice/getId.do?id=' + id, function (result) {
            initCheckState(result.data.classids, "classTree", $("#className"), $("#classid"));
            $("#notice").val(decodeURIComponent(result.data.notice));
        });
    }


    function save() {
        saveform($("#editForm"), '${ctx}/notice/save.do', function (data) {
            $("#myModal").modal("hide");
            table.ajax.reload(null, false);
        })
    }


    function add() {
        clearZTree();
        $("#editForm").resetForm();
        $("#myModal").modal("show");
    }

</script>

