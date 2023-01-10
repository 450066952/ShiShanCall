<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div class="ng-scope">
        <div class="bg-light lter b-b wrapper-md">
            <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
                <i class="fa fa-plus"></i>添加
            </button>
            <h1 class="m-n font-thin h3">一级取号菜单</h1>
        </div>
        <div class="wrapper-md">
            <div class="panel">
                <div class="panel-heading" style="height: 36px">一级取号菜单</div>
                <div class="table-responsive" style="padding: 0 5px">
                    <table id="inch_dataTable" class="table table-striped m-b-none" style="width: 800px !important;">

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
                        <label>所属单位</label>
                        <select name="orgid" id="orgid" class="inch">
                            <option value='0'>请选择</option>
                            <c:forEach var='item' items='${orglist}'>
                                <option value='${item.id}'>${item.text}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="inch-group form-group">
                        <label>业务名称</label>
                        <input type="text" class="inch" id="name" name="name" required/>
                    </div>
                    <div class="inch-group form-group">
                        <label>业务代码</label>
                        <input type="text" class="inch" id="rootid" name="rootid" required/>
                    </div>
                    <div class="inch-group form-group">
                        <label>排序</label>
                        <input type="text" class="inch" id="paixu" name="paixu" required/>
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
            "paging": true,
            "dom": '<"toolbar">frtip',
            "ajax": {
                "url": "${ctx}/sysDic/getDicCode.do",
                "error": function (response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "title": "所属单位", "data": "orgname"
            }, {
                "title": "业务名称", "data": "name"
            }, {
                "title": "业务代码", "data": "rootid"
            }, {
                "title": "添加时间", "data": "addtime"
            }, {
                "data": "", "title": "操作", "width": "60px", "class": "text-center"
            }],
            "columnDefs": [{
                targets: 4,
                render: function (a, b, c, d) {
                    var html = "<a href=\"javascript:edit('" + c.guid + "');\" ><i class='glyphicon glyphicon-list icon'></i></a>&nbsp;&nbsp;";
                    html += "&nbsp;&nbsp;<a href=\"javascript:delbytype('${ctx}/sysDic/deleteCode.do','" + c.guid + "',0);\" ><i class='glyphicon glyphicon-minus icon'></i></a>";
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

        editformbyname($("#editForm"), $("#myModal"), '${ctx}/sysDic/getById.do?guid=' + id);
    }

    function save() {
        debugger;
        saveform($("#editForm"), '${ctx}/sysDic/saveDicCode.do', function () {
            $("#myModal").modal("hide");
            table.ajax.reload(null, false);
        })
    }

    function add() {
        $("#editForm").resetForm();
        $("#myModal").modal("show");
    }

    //联动
    <%--$("#orgid").change(function(){--%>
    <%--var id = $("#orgid").val();--%>
    <%--$("#stype option").remove();--%>
    <%--$.ajax({--%>
    <%--type:"POST",--%>
    <%--url: "${ctx}/sysDic/getDic.do",--%>
    <%--data:{'orgid':id},--%>
    <%--success: function(result){--%>
    <%--var html="<option value='0'>请选择</option>";--%>
    <%--if(result.data != null && result.data.length > 0){--%>
    <%--for(var i=0; i<result.data.length; i++){--%>
    <%--html+="<option value='"+result.data[i].guid+"'>"+result.data[i].name+"</option>";--%>
    <%--}--%>
    <%--}--%>
    <%--$("#stype").html(html);--%>
    <%--}--%>
    <%--})--%>
    <%--});--%>


</script>

