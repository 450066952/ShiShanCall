<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<div class="app-content-body fade-in-up">
    <!-- uiView:  -->
    <div class="ng-scope">
        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">宣传相册</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">宣传相册信息</div>
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
                    <div class="inch-group form-group">
                        <label>位置</label>
                        <input id="classid" name="classid" class="hidden"/>
                        <input name=className id="className" type="text" class="form-control inch select" value=""
                               onclick="showMenu($('#className'),$('#menuContent'));"/>
                    </div>
                    <div class="inch-group form-group">
                        <label>宣传相册名称 </label>
                        <input type="text" class="inch" id="title" name="title">
                    </div>
                    <input type="hidden" class="inch inchtime" id="begintime" name="begintime"/>
                    <input type="hidden" class="inch inchtime" id="endtime" name="endtime"/>
                    <%--<div class="inch-group form-group">--%>
                    <%--<label>开始时间 </label>--%>
                    <%--<input type="hidden" class="inch inchtime"  id="begintime" name="begintime"  />--%>
                    <%--</div>               		 				--%>
                    <%--<div class="inch-group form-group">--%>
                    <%--<label>结束时间 </label> --%>
                    <%--<input type="hidden" class="inch inchtime"  id="endtime" name="endtime"   />--%>
                    <%--</div>--%>
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
                "url": "${ctx}/pic/dataList.do?classid=${param.classid}",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "title": "相册名称", "data": "title"
            },
//            {
//			"title":"开始时间","data": "begintime","width":"120px"
//		}, {
//			"title":"结束时间","data": "endtime","width":"120px"
//		},
                {
                    "title": "位置", "data": ""
                }, {
                    "title": "添加人", "data": "addusername", "width": "150px"
                }, {
                    "data": "", "title": "操作", "width": "150px", "class": "text-center"
                }],

            "columnDefs": [{
                targets: 1,
                render: function (a, b, c, d) {

                    return getClassInfo(c.classList);
                }
            }, {
                targets: 3,
                render: function (a, b, c, d) {
                    var html = "";

                    if ("${u_info.id}" == c.adduser || "${u_info.isadmin}" == 1) {

                        html = "<a href=\"javascript:edit('" + c.guid + "');\" ><i class='glyphicon glyphicon-list icon'></i></a>";
                        html += "&nbsp;&nbsp;<a href=\"javascript:delbytype('${ctx}/pic/delete.do','" + c.guid + "'," + c.type + ");\" ><i class='glyphicon glyphicon-minus icon'></i></a>";
                        html += "&nbsp;&nbsp;<a href=\"javascript:getAjaxHtm('${ctx}/picPhoto/list.shtml?pguid=" + c.guid + "&begintime=" + c.begintime + "&endtime=" + c.endtime + "');\" ><i class='icon-camera'></i></a>";
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


    /**
     *编辑方法
     **/

    function edit(id) {
        clearZTree();
        editformbyname($("#editForm"), $("#myModal"), '${ctx}/pic/getId.do?id=' + id, function (result) {

            initCheckState(result.data.classids, "classTree", $("#className"), $("#classid"));

        });
    }


    /**
     *保存
     **/

    function save() {

        saveform($("#editForm"), '${ctx}/pic/save.do', function (data) {
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

