<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

  <link rel="stylesheet" type="text/css" href="${ctx}/inchplugin/vendor/jquery/fullcalendar/fullcalendar.css">
  <link rel="stylesheet" type="text/css" href="${ctx}/inchplugin/vendor/jquery/fullcalendar/theme.css">
  <link rel="stylesheet" type="text/css" href="${ctx}/inchplugin/vendor/jquery/fancybox/jquery.fancybox.css">

  <script src='${ctx}/inchplugin/vendor/jquery/fullcalendar/moment.min.js'></script>
  <script src="${ctx}/inchplugin/vendor/jquery/fullcalendar/fullcalendar.min.js?v=111"></script>
  <script src="${ctx}/inchplugin/vendor/jquery/fancybox/jquery.fancybox.pack.js"></script>


<style type="text/css">

.fancy{
  width:450px;
  height:auto;
}
.fancy h3{
  height:30px;
  line-height:30px;
  border-bottom:1px solid #f0f0f0;
  font-size:16px;
  margin-top: -5px;
  margin-bottom: 15px;
}
.fancy form{
  padding:5px;
}

.fancy .btndiv{
  width: 100%;
  height: 50px;
  margin-top: 12px;
  padding-top: 15px;
  border-top:1px solid #f0f0f0;
}

</style>


<script type="text/javascript">

$(function() {
	$('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		events: '${ctx}/fullcalendar/dataList.do',
		dayClick: function(date, allDay, jsEvent, view) {
			 var selDate =moment(date).format('YYYY-MM-DD');
			$.fancybox({
				'scrolling' : 'no',
				'type':'ajax',
				'href':'${ctx}/fullcalendar/event.shtml?action=add&date='+selDate,
				'afterShow':function(){
					$('#eventid').val(0);
					$('#startdate').val(selDate);
					$("#del_event").hide();
				}
			});
    	},
		eventClick: function(calEvent, jsEvent, view) {
			$.fancybox({
				'scrolling' : 'no',
				'type':'ajax',
				'href':'${ctx}/fullcalendar/event.shtml?action=edit&id='+calEvent.id,
				'afterShow':function(){
					$('#eventid').val(calEvent.id);
					$('#event').val(calEvent.title);
					$('#startdate').val(moment(calEvent.start).format('YYYY-MM-DD'));
			        $('#enddate').val(moment(calEvent.end).format('YYYY-MM-DD'));

					if (calEvent.end!=null) {
						$("#isend").attr("checked", true);
						$("#enddate").show();
						//$.fancybox.resize();//调整高度自适应
					};
					if (calEvent.color=="#ee3939") {
						$("#closepc").attr("checked","checked");
					};
					if (calEvent.color=="#27c24c") {
						$("#openpc").attr("checked","checked");
					};
					$("#del_event").show();
				}
			});
    	}
	});

	$("#saveOnOff").click(saveOnOff);

    $('.inchtime').datetimepicker({
        format:'H:i',      //格式化日期
        datepicker:false,
        step:5
    });

});

function saveOnOff() {

	saveform($("#OnOffForm"), '${ctx}/lcd/saveOnOff.do', function(result) {
		$("#guid").val(result.data.guid);
	})
}
   </script>

<div class="app-content-body fade-in-up ng-scope" >
	<div class="ng-scope">
		<div class="wrapper-md bg-light b-b">
			<h1 class="m-n font-thin h3">开关机管理</h1>
		</div>

		<div style="padding:20px 20px 0px 20px">
			<div class="panel panel-default">
				<div class="panel-heading font-bold">每日开关机时间</div>
				<div class="panel-body">
					<form class="form-inline" id="OnOffForm">
						<div class="form-group">
							<label>开机时间：</label>
							<input type="text" class="hidden" id="guid" name="guid" value="${sinfo.guid}">
							<input type="text" class="form-control inchtime" id="startup" name="startup" value="${sinfo.startup}" >
						</div>
						<div class="form-group" style="margin-left:16px">
							<label>关机时间：</label>
							<input type="text" class="form-control inchtime" id="shutdown" name="shutdown" value="${sinfo.shutdown}" >
						</div>
						<button type="button" class="btn btn-sm btn-info" id="saveOnOff" style="margin-left:16px">保存</button>
					</form>
				</div>
			</div>
		</div>

		<div class="hbox hbox-auto-xs hbox-auto-sm" >
			<div class="col wrapper-md" style="padding:0px 20px 0px 20px">
				<div id='calendar'></div>
			</div>
		</div>
	</div>
</div>












