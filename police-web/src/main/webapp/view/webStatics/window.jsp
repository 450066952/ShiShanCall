<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<div class="app-content-body fade-in-up ng-scope">
    <!-- uiView:  -->
    <div ui-view="" class="ng-scope">

        <div class="bg-light lter b-b wrapper-md">
            <h1 class="m-n font-thin h3">窗口统计查询</h1>
        </div>
        <div style="padding:20px 20px 0px 20px">
            <div class="panel panel-default">
                <div class="panel-heading font-bold">检索条件</div>
                <div class="panel-body">
                    <form class="form-inline" id="searchForm">
                        <div class="form-group">
                            <label>开始日期：</label>
                            <input type="text" class="form-control inch inchtime" id="starttime" name="starttime" autocomplete="off" value="${starttime}">
                        </div>
                        <div class="form-group">
                            <label>结束日期：</label>
                            <input type="text" class="form-control inch inchtime" id="endtime" name="endtime" autocomplete="off" value="${endtime}">
                        </div>
                        <div class="form-group">
                            <label>组织单位：</label>
                            <select name="orgid" id="orgid" class="inch" style="display: inline;width:150px;">
                                <option value='0'>请选择</option>
                                <c:forEach var='item' items='${orglist}'>
                                    <option value='${item.id}'>${item.text}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <span class="btn btn-sm btn-info" style="margin-left:20px" onclick="javascript:toSearch('${ctx}/windowStatics/dataList.do')">查询</span>
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

<div class="modal fade" id="signModal" tabindex="-1" role="dialog" aria-labelledby="signModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:60%;height:650px;">
        <div class="modal-content" style="height:600px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="titleinfo" ></h4>
            </div>
            <div class="modal-body" >
                <table id="stuTable" class="table table-striped m-b-none">
                </table>
            </div>
        </div>
    </div>
</div>


<script>
    var table,stuTable;
    $(document).ready(function() {

        table = $('#inch_dataTable').DataTable({
            "processing": false,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "dom": '<"toolbar">frtip',
            "ajax": {
                "url": "${ctx}/windowStatics/dataList.do?starttime="+$("#starttime").val()+"&endtime="+$("#endtime").val(),
                "error": function(response) {
                    checkpermision(response);
                }
            },
            "columns": [{
                "data": "winname","title":"窗口名称"
            }, {
                "data": "cnt","title":"办件数量"
            },{
                "data": "","title":"明细","width": "60px","class": "text-center"
            }],
            "columnDefs": [ {
                targets: 2,
                render: function(a, b, c, d) {

                    var html= "<a href=\"javascript:getDataDetial('"+c.guid +"','"+c.winname+"')\" ><i class='icon-camera' title='详情'></i></a>";
                    return html;
                }
            }
            ]
        });
        $.datetimepicker.setLocale('ch');
        $('.inchtime').datetimepicker({
            format : "Y-m-d",
            timepicker : false
        });

    });


    function getDataDetial(cguid,name){

        stuTable=$('#stuTable').DataTable({
            "processing": false,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "paging": true,
            "destroy": true,
            "dom": '<"toolbar">frtip',
            "ajax": {
                url: "${ctx}/windowStatics/queryByWindowDetial.do?id="+cguid+"&starttime="+$("#starttime").val()+"&endtime="+$("#endtime").val()
            },
            "columns": [{
                "title":"业务","data": "typename"
            },{
                "title":"姓名","data": "name"
            },{
                "title":"号码","data": "allnum"
            },{
                "title":"时间","data": "addtime"
            }]
        });

        $("#titleinfo").html(name);
        $("#signModal").modal("show");
    }


</script>

