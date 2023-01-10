<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="fancy">
	<h3>开关机日程</h3>
	<form id="add_form" action="save.do" method="post" style="margin:0;">
		<input type="hidden" id="eventid" name="eventid" value="${param.id}">
		<label>事件内容：</label> <input type="text" class="inch" name="event"
			id="event" placeholder="节假日等说明...">
		<div class="form-group pull-in clearfix" style="margin-top: 20px">
			<div class="col-sm-6">
				<label>开始日期：</label> <input type="text" class="inch inchtime"
					id="startdate" name="startdate" >
			</div>
			<div class="col-sm-6">
				<label class="checkbox i-checks" style="margin: 0 0 5px 0">
					<input type="checkbox" value="1" id="isend" name="isend"><i></i>
					结束日期
				</label> <input type="text" class="inch inchtime" name="enddate" id="enddate"
					 style="display: none;">
			</div>
		</div>
		<div class="radio">
			<label class="i-checks" style="margin-right: 20px"> <input
				type="radio" name="radio" id="closepc"  value="1"
				class="ng-untouched ng-valid ng-dirty ng-valid-parse"> <i></i>关机
			</label> <label class="i-checks"> <input type="radio" name="radio"
				id="openpc"  value="2"
				class="ng-untouched ng-valid ng-dirty ng-valid-parse"> <i></i>开机
			</label>
		</div>
		<div class="btndiv">
			<div style="float: left;">
				<%--<button id="del_event" class="btn btn-danger">删除</button>--%>
				<input type="button" class="btn btn-danger" id="del_event" value="删除">
			</div>
			<div style="float: right;">
					<input type="button" class="btn btn-success" id="addtoserver" value="确定">
    				<input type="button" class="btn btn-info" value="取消" onClick="$.fancybox.close()"></div>
			</div>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {

		$("#isend").click(function() {

			if($("#enddate").css("display")=="none"){
				$("#enddate").show();
			}else{
				$("#enddate").hide();
			}
		});

		//提交表单
		$("#addtoserver").click(function() {
			var isvalidated = showRequest();
			var isUseEnd;
			if ($("#isend").is(":checked")) {
				isUseEnd = "1";
			} else {
				isUseEnd = "0";
			}

			var ison;
			ison = $('input:radio[name=radio]:checked').val();

			if (isvalidated != false) {
				$.ajax({
					type : "POST",
					url : "${ctx}/fullcalendar/save.do",
					data : {
						id : $("#eventid").val(),
						title : $("#event").val(),
						ison : ison,
						isEnd : isUseEnd,
						start : $("#startdate").val(),
						end : $("#enddate").val()
					},
					success : function(result) {
						if (result.msg) {
							$.fancybox.close();
							$('#calendar').fullCalendar('refetchEvents'); //重新获取所有事件数据
						} else {
							layer.alert(result.msg, {icon: 5});
						}
					}
				});
			}
		});

		//删除事件
		$("#del_event").click(

				function() {
					layer.confirm('确认要操作吗?', {
						icon : 3,
						title : '提示'
					}, function(index) {

						var eventid = $("#eventid").val();
						$.post("${ctx}/fullcalendar/delete.do?id=" + eventid,
								{}, function(result) {
									if (result.msg) {//删除成功
										$.fancybox.close();
										$('#calendar').fullCalendar(
												'refetchEvents'); //重新获取所有事件数据
									} else {
										layer.alert(result.msg, {icon: 5});
									}
								});

						layer.close(index);
					});



					/*if (confirm("您确定要删除吗？")) {
						var eventid = $("#eventid").val();
						$.post("${ctx}/fullcalendar/delete.do?id=" + eventid,
								{}, function(result) {
									if (result.msg) {//删除成功
										$.fancybox.close();
										$('#calendar').fullCalendar(
												'refetchEvents'); //重新获取所有事件数据
									} else {
										layer.alert(result.msg, {icon: 5});
									}
								});
					}*/
				});

	});

	function showRequest() {
		var isSelected = $('input:radio[name=radio]:checked').val();

		if (isSelected == "1" || isSelected == "2") {
			return true;
		} else {

			layer.alert("请选择开关机选项!", {icon: 0});
			return false;
		}
	}

    $.datetimepicker.setLocale('ch');
    $('.inchtime').datetimepicker({
        format : "Y-m-d",
        timepicker : false
    });
</script>
