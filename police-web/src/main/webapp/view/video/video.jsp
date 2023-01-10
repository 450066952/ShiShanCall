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
            <h1 class="m-n font-thin h3">视频信息</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">视频信息</div>
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
                    <input class="hidden" type="text" name="oldclassid">

                    <div class="inch-group form-group">
                        <label>柜台</label>
                        <input id="classid" name="classid" class="hidden"/>
                        <input name=className id="className" type="text" class="form-control inch select" value=""
                               onclick="showMenu($('#className'),$('#menuContent'));"/>
                    </div>

                    <div class="inch-group form-group">
                        <label>标题</label>
                        <input type="text" class="inch" id="title" name="title"/>
                    </div>
                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <label>开始时间 </label> <input type="text" class="inch inchtime"
                                                        id="begintime" name="begintime"/>
                        </div>
                        <div class="inch-group col-sm-6">
                            <label>结束时间 </label> <input type="text" class="inch inchtime"
                                                        id="endtime" name="endtime"/>
                        </div>
                    </div>

                    <div class="inch-group form-group">
                        <label>排序</label>
                        <input type="text" class="inch" name="sort"/>
                    </div>

                    <div class="form-group pull-in clearfix">
                        <div class="inch-group col-sm-6">
                            <video id="myvideo" width="350" height="196" controls="controls">
                                <source id="tonyv" type="video/mp4">
                                视频
                                </source>
                                <!-- <source id="tonym" type="video/ogg" >视频</source> -->
                                您的浏览器不支持video标签
                            </video>
                            <input name="videourl" id="videourl" type="text" style="display:none"
                                   value="C:\DownLoad\192.168.1.14335Time20200610133000Time20200610134000.mp4"/>

                            <span class="btn btn-success fileinput-button" style="margin-top:6px;">
						       <i class="glyphicon glyphicon-plus"></i>
						        <span>上传视频</span>
						        <input id="fileupload" type="file" name="fileupload">
						    </span>
                        </div>
                        <div class="inch-group col-sm-6">
                            <label>显示方式</label>
                            <label class='checkbox i-checks'><input type="checkbox" id="unshared" name="unshared"
                                                                    value="1"/><i></i>独占方式显示</label>
                            <label>说明：独占方式显示（遮挡所有画面）</label>
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
                "url": "${ctx}/video/dataList.do",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "data": "title", "title": "标题"
            }, {
                "data": "begintime", "title": "开始时间", "width": "120px"
            }, {
                "data": "endtime", "title": "结束时间", "width": "120px"
            },
                {
                    "data": "sort", "title": "排序", "width": "120px"
                }, {
                    "data": "", "title": "柜台"
                }, {
                    "data": "addusername", "title": "添加人", "width": "60px"
                }, {
                    "data": "addtime", "title": "添加时间", "width": "120px"
                }, {
                    "data": "", "title": "操作", "width": "60px", "class": "text-center"
                }],
            "columnDefs": [{
                targets: 4,
                render: function (a, b, c, d) {

                    return getClassInfo(c.classList);
                }
            }, {
                targets: 7,
                render: function (a, b, c, d) {
                    var html = "";

                    if ("${u_info.id}" == c.adduser || "${u_info.isadmin}" == 1) {
                        html = "<a href=\"javascript:edit('" + c.guid + "');\" ><i class='glyphicon glyphicon-list icon'></i></a>&nbsp;&nbsp;";
                        html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:delbytype('${ctx}/video/delete.do','" + c.guid + "'," + c.type + ");\" ><i class='glyphicon glyphicon-minus icon'></i></a>";
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


        initUploadCallBack($("#fileupload"), '${ctx}', function (data) {

            $("#videourl").val(data.result.url);
            $("#tonyv").attr("src", data.result.url);
            $("#myvideo").load();
        })

        initSchool('${ctx}/school/schooltree.do?rootid=school');

        $('#editForm').bootstrapValidator();
    });


    /**
     *编辑方法
     **/

    function edit(id) {
        clearZTree();

        editformbyname($("#editForm"), $("#myModal"), '${ctx}/video/getId.do?id=' + id, function (result) {

            initCheckState(result.data.classids, "classTree", $("#className"), $("#classid"));
            $("#tonyv").attr("src", result.data.videourl);
            $("#myvideo").load();
        });
    }


    /**
     *保存
     **/

    function save() {

        saveform($("#editForm"), '${ctx}/video/save.do', function (data) {
            $("#myModal").modal("hide");
            table.ajax.reload(null, false);
        })
    }


    function add() {

        clearZTree();
        $("#tonyv").removeAttr("src");
        $("#myvideo").load();
        $("#editForm").resetForm();
        $("#myModal").modal("show");
    }

</script>

