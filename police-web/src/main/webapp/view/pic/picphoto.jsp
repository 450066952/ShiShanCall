<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="app-content-body fade-in-up ng-scope">


    <div class="bg-light lter b-b wrapper-md ng-scope">
        <button type="button" class="btn btn-sm btn-primary btn-addon pull-right" onclick='javascript:add();'>
            <i class="fa fa-plus"></i>添加图片
        </button>

        <h1 class="m-n font-thin h3">宣传照集合</h1>
    </div>

    <div class="wrapper-md ng-scope">
        <div class="ng-scope">
            <div class="row">
                <div class="col-sm-12">
                    <div class="form-group pull-in clearfix">
                        <c:forEach var="c" items="${piclist}">
                            <div id="${c.guid}" class="col-sm-3 inch-group" style="margin-top:5px;">
                                <img alt="" src="${c.pic}" width="100%" style="margin-bottom:8px;">
                                <span>
										<a href="javascript:delpic('${c.guid}');">删除</a>
									</span>
                            </div>
                        </c:forEach>
                    </div>
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
                <h4 class="modal-title" id="myModalLabel">照片管理</h4>
            </div>

            <div class="modal-body">
                <form id="editForm">
                    <input class="hidden" type="text" name="guid">
                    <input class="hidden" type="text" name="pguid" value='${param.pguid}'>
                    <input class="hidden" type="text" name="begintime" value='${param.begintime}' )>
                    <input class="hidden" type="text" name="endtime" value='${param.endtime}'>

                    <div class="inch-group form-group">
                        <div id="imgview"></div>
                        <span style="display: inline-block;margin-left:10px;">
								<input name="pic" id="pic" type="text" style="display:none"/>
					 			<input name="picsmall" id="picsmall" type="text" style="display:none"/>
					 			<input name="picpress" id="picpress" type="text" style="display:none"/>
							</span>
                        <span class="btn btn-success fileinput-button"> <i
                                class="glyphicon glyphicon-plus"></i> <span>上传</span> <input
                                id="fileupload" type="file" name="fileupload" multiple>
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

<script type="text/javascript">
    $(document).ready(function () {

        $("#save").click(save);

        initUploadWithPress($("#fileupload"), '${ctx}', true, true, '', '', 0, 0, '', function (data) {
            var strs = data.result.url.split("@");
            var ditem;
            for (var item = 0; item < strs.length; item++) {
                ditem = strs[item];

                $("#pic").val($("#pic").val() + "," + ditem);
                $("#imgview").append("<img  alt='' src='" + ditem + "'  height='200' width='300'>");
            }

        })
    });


    /**
     *保存
     **/

    function save() {
        saveform($("#editForm"), '${ctx}/picPhoto/save.do', function (data) {
            $("#myModal").modal("hide");
            getAjaxHtm('${ctx}/picPhoto/list.shtml?pguid=${param.pguid}&begintime=${param.begintime}&endtime=${param.endtime}');
        })
    }


    function add() {
        $("#editForm").resetForm();
        $("#myModal").modal("show");
    }

    function delpic(guid) {

        delbycallback('${ctx}/picPhoto/delete.do', guid, function (data) {

            if (data.success) {
                $("#" + guid).remove();
            }
        });
    }
</script>

