<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<style>
	.bedoWin{
		overflow:hidden;
	}
	.bedoWinItem{
		width:12.5%;
		height:100px;
		margin-bottom:20px;
		float:left;
		padding:10px;
	}
	.bedoWinItem p{
		text-align:center;
		border-bottom:2px solid gray;
		line-height:100px;
		background:gray;
		border-top-left-radius:2px;
		border-top-right-radius:2px;
		box-shadow:0px 10px 20px 5px rgba(7,17,27,.05);
		color:#fff;
	}
	.bedoWinItem.yellow p{
		border-bottom:2px solid yellow;
		background:yellow;
	}
	.bedoWinItem.red  p{
		background:#f40f4e;
		border-bottom:2px solid #f40f4e
	}
	#bedoData ul{margin:0px;padding:0px;overflow:hidden;width:100%;display:flex;}
	#bedoData ul li{list-style:none;flex:1 auto;}
	#bedoData ul li h4{font-size:14px;}
	#bedoData ul li h4 small{font-size:18px;}
</style>
<div class="app-content-body fade-in-up ng-scope">
	<!-- uiView:  -->
	<div ui-view="" class="ng-scope">

		<div class="bg-light lter b-b wrapper-md">
			<h1 class="m-n font-thin h3">业务统计</h1>
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
						<div class="form-group">
							<label>评价情况：</label>
							<select name="score" id="score" class="inch" style="display: inline;width:150px;">
								<option value='0'>请选择</option>
								<option value="1">非常不满意</option>
								<option value="2">不满意</option>
								<option value="3">基本满意</option>
								<option value="4">满意</option>
								<option value="5">非常满意</option>
							</select>
						</div>
						<span class="btn btn-sm btn-info queryData" style="margin-left:20px" >查询</span>
					</form>
				</div>
			</div>
		</div>
		
	</div>
	<!-- uiView:  -->
	<div class="hbox hbox-auto-xs hbox-auto-sm ng-scope col-md-12">

		<div class="col">

			<!-- header -->
			<div class="wrapper bg-light lter " style="padding-bottom:0px;">

				<div class="ht-page pull-right"></div>


				<ul class="nav nav-tabs bedoTabs">
					<li class="ng-isolate-scope bedo_tab active " id="holiday">
						<a href="javascript:void(0)" class="ng-binding">
							办件统计
						</a>
					</li>
					<li class="ng-isolate-scope bedo_tab" id="thing">
						<a href="javascript:void(0)" class="ng-binding">
							窗口状态
						</a>
					</li>
					<li class="ng-isolate-scope bedo_tab " id="later">
						<a href="javascript:void(0)" class="ng-binding">
							员工绩效
						</a>
					</li>
					<li class="ng-isolate-scope bedo_tab " id="ye">
						<a href="javascript:void(0)" class="ng-binding">
							办件列表
						</a>
					</li>
				</ul>

			</div>

			<!-- / header -->

			<!-- list -->
			<ul class="list-group list-group-lg no-radius m-b-none m-t-n-xxs bedoContent">
				<!-- ngRepeat: mail in mails | filter:fold -->

				<li class="list-group-item  clearfix b-l-3x ng-scope b-l-info" style=" border:0 none;border-bottom: 1px solid #ddd;">
					<div class="itemlist bedoitemlist">
						<div style="padding:0px 20px 0px 20px" id='bedoData'>
							<div class="panel">
								<div class="table-responsive" style="padding: 0 5px">
									<ul>
										<li>
											<h4>非常满意：<small id='happy2'>0</small></h4>
										</li>
						 				<li>
											<h4>满意：<small id='happy1'>0</small></h4>
										</li>
										<li>
										     <h4>基本满意：<small id='happy'>0</small></h4>
										</li>
										<li>
										     <h4>不满意：<small id="basic">0</small></h4>
										</li>
										<li>
										     <h4>非常不满意：<small id='sad'>0</small></h4>
										</li>
										<li>
										     <h4>未评价：<small id='no'>0</small></h4>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div id="placeholder"  style="width:80%;margin:30px auto;height:600px;">
						
						</div>
					</div>
					<div class="bedoWin itemlist">
						<div class="bedoWinItem">
							
						</div>
					</div>
					<table id="inch_dataTable" class="table table-striped m-b-none itemlist">
					</table>
					<table id="inch_ban" class="table table-striped m-b-none itemlist">
					</table>
				</li>


			</ul>
			<!-- <ul class="list-group ">
				<li class="list-group-item clearfix b-l-3x ng-scope b-l-info" style=" border:0 none;border-bottom: 1px solid #ddd;">
					暂无数据
				</li>
			</ul> -->
			<!-- / list -->

		</div>
	</div>
</div>

<div class="modal fade" id="signModal" tabindex="-1" role="dialog" aria-labelledby="signModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:60%;height:650px;">
		<div class="modal-content" style="height:600px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="titleinfo"></h4>
			</div>
			<div class="modal-body">
				<table id="stuTable" class="table table-striped m-b-none">
				</table>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="videoModal" tabindex="-1" role="dialog" aria-labelledby="signModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:60%;height:650px;">
		<div class="modal-content" style="height:600px;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="titleinfo2"></h4>
			</div>
			<div class="modal-body">
				<embed type="application/x-vlc-plugin" name="VLC"  autoplay="no"  id="VLC"
					   loop="no" volume="100" width="640px" height="450px"   windowlessVideo=1   wmode='transparent'
					   target="">
				</embed>
			</div>
		</div>
	</div>
</div>
	<!-- <script src="https://cdn.bootcss.com/echarts/4.2.1-rc1/echarts.simple.min.js"></script> -->
		<script type="text/javascript" src="/police-web/inchplugin/vendor/jquery/echart.js"></script>
<script>
	
	var table, stuTable,table1;
	$(document).ready(function() {
			queryYeTotal($("#starttime").val(),$("#endtime").val(),$("#orgid").val())
			 $.datetimepicker.setLocale('ch');
			$('.inchtime').datetimepicker({
			    format : "Y-m-d",
			    timepicker : false
			});
		queryYeTotal('','',0)
	});
	
	function queryYeTotal(starttime,endtime,orgid){
		$.ajax({
			url:"${ctx}/statistics/queryByDayTotal.do",
			data:{starttime:starttime,orgid:orgid,endtime:endtime},
			type:"get",
			success:function(res){
				console.log(res)
				 $("#happy2").text(parseInt(res[0]["satisfy2"]))
				 $("#happy1").text(parseInt(res[0]["satisfy1"]))
				 $("#happy").text(parseInt(res[0]["satisfy"]))
				 $("#sad").text(parseInt(res[0]["notsatisfy"]))
				  $("#no").text(parseInt(res[0]["no"]))
				  $("#basic").text(parseInt(res[0]["basic"]))
			}
		})
		$.ajax({
			url:"${ctx}/statistics/queryByDayType.do",
			data:{starttime:starttime,orgid:orgid,endtime:endtime},
			type:"get",
			success:function(res){
				console.log(res)
				if(res.length==0){
				    $("#placeholder").hide()
					return false;
                }else{
                    $("#placeholder").show()
                }
				var _arr =[],
					_value = [];
				for(var i =0;i<res.length;i++){
					_arr.push(res[i]["typename"])
					_value.push(res[i]["cnt"])
				}
				yewu(_arr,_value)
			}
		})
	}
	function getDataDetial(cguid, name) {

		stuTable = $('#stuTable').DataTable({
			"processing": false,
			"serverSide": true,
			"searching": false,
			"ordering": false,
			"paging": true,
			"destroy": true,
			"dom": '<"toolbar">frtip',
			"ajax": {
				url: "${ctx}/typeStatics/queryByTypeDetial.do?id=" + cguid + "&starttime=" + $("#starttime").val() + "&endtime=" +
					$("#endtime").val()
			},
			"columns": [{
				"title": "姓名",
				"data": "name",
                "defaultContent":"",
            }, {
				"title": "号码",
				"data": "allnum",
                "defaultContent":"",
			}, {
				"title": "时间",
                "defaultContent":"",
				"data": "addtime"
			}]
		});

		$("#titleinfo").html(name);
		$("#signModal").modal("show");
	}
	var currentIndex = 0
	var param = {
		starttime: $("#starttime").val(),
		endtime: $("#endtime").val(),
		id: $("#orgid").val(),

	}
	
		
	
	$(".bedoTabs li").click(function() {
		var _index = currentIndex = $(this).index()
		$(".itemlist").eq(_index).show().siblings('.itemlist').hide()
		$(this).addClass("active").siblings("li").removeClass("active")
		var param = {
			starttime: $("#starttime").val(),
			endtime: $("#endtime").val(),
			id: $("#orgid").val(),

		}
		console.log(currentIndex, param)
		switch (currentIndex) {
			case 0:
				queryYeTotal($("#starttime").val(),$("#endtime").val(),$("#orgid").val())
				$("#inch_dataTable_wrapper").hide()
				$("#inch_ban_wrapper").hide()
				break;
			case 1:
				// bedoWin($("#starttime").val(),$("#endtime").val(),$("#orgid").val())
				bedoWin("2019-08-01","2019-08-22",1)
				$("#inch_dataTable_wrapper").hide()
				$("#inch_ban_wrapper").hide()
				$(".bedoitemlist").hide()
				break;
			case 2:
                $(".bedoitemlist").hide()
				$("#inch_dataTable_wrapper").show()
				$("#inch_ban_wrapper").hide()
				$(".bedoWin").hide()
				$("#placeholder").hide()
                pro()
				break;
			case 3:
				banjian()
                $(".bedoitemlist").hide()
				$("#inch_dataTable_wrapper").hide()
				$("#inch_ban_wrapper").show()
				$(".bedoWin").hide()
				$("#placeholder").hide()
				break;
		}
	})
	function getSysDate() {
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      return currentdate;
    }
	function showGraph(param, url, title) {
		var mydata_y = [];
		var mydata_x = [];
		var _echartX = [];
		var _echartY = [];
		$.ajax({
			url: url,
			type: "get",
			data: param,
			success: function(res) {
				var data = res.data

				console.log(data, "echart")
				if (data == '') {
					$(".noData").show()
					$("#placeholder").hide()
					return false
				} else {
					$(".noData").hide()
					$("#placeholder").show()
				}
				for (var i = 0; i < data.length; i++) {
					var ddata_y = [];
					var ddata_x = [];
					ddata_y.push(i + 1, data[i].cnt);
					ddata_x.push(i + 1, data[i].item);
					_echartX.push(data[i].item)
					_echartY.push(data[i].cnt)
					mydata_y.push(ddata_y);
					mydata_x.push(ddata_x);
				}


				// 基于准备好的dom，初始化echarts图表
				var myChart = echarts.init(document.getElementById('placeholder'), 'macarons');
				var option = {
					backgroundColor: 'white',
					title: {
						text: title,
						y: '15'
					},
					tooltip: {
						trigger: 'axis',
						formatter: function(data) {

							return data[0]["name"] + ":" + data[0].data;
						}
					},
					noDataLoadingOption: {
						text: '暂无数据',
						effect: 'bubble',
						effectOption: {
							effect: {
								n: 0
							}
						}
					},
					legend: {
						selectedMode: false,
						data: ['统计详情'],
						y: '20'
					},
					grid: {
						y: '100'
					},
					toolbox: {
						show: false,
						y: '20',
						feature: {
							mark: {
								show: false
							},
							dataView: {
								show: true,
								readOnly: false
							},
							magicType: {
								show: true,
								type: ['line', 'bar']
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},

					calculable: true,
					xAxis: [{
						type: 'category',
						axisTick: {
                            interval: 0,

						},
						axisLabel: {
							interval: 0
						},
						data: _echartX
					}],
					yAxis: [{
						name: '业务名称',
						type: 'value',
                        axisTick: {
                            interval: 0,
                            alignWithLabel: true
                        },
                        axisLabel: {
								interval: 0
							},
					}],
					series: [{
						name: '统计详情',
						type: 'bar',
						//stack: '总量',
						barMaxWidth: 25,

						itemStyle: {
							normal: {
								borderWidth: 3,
								label: {
									show: true, //开启显示
									position: 'top', //在上方显示
									textStyle: { //数值样式
										color: 'black',
										fontSize: 16
									}
								}
							}
						},
						data: _echartY
					}]
				};
				myChart.setOption(option);

			}

		})

	}
	$(".queryData").click(function() {
		
		
		var _idx
		var param = {
			starttime: $("#starttime").val(),
			endtime: $("#endtime").val(),
			id: $("#orgid").val(),

		}
		console.log(currentIndex, param)
		switch (currentIndex) {
			case 0:
				queryYeTotal($("#starttime").val(),$("#endtime").val(),$("#orgid").val())
				$("#inch_dataTable_wrapper").hide()
				$("#inch_ban_wrapper").hide()
				break;
			case 1:
				bedoWin($("#starttime").val(),$("#endtime").val(),$("#orgid").val())

				$("#inch_dataTable_wrapper").hide()
				$("#inch_ban_wrapper").hide()
				break;
			case 2:
				pro()
				$("#inch_dataTable_wrapper").show()
				$("#inch_ban_wrapper").hide()
				$(".bedoWin").hide()
				$("#placeholder").hide()
				break;
			case 3:
				banjian()
				$("#inch_dataTable_wrapper").hide()
				$("#inch_ban_wrapper").show()
				$(".bedoWin").hide()
				$("#placeholder").hide()
				break;
		}
	})
	function yewu(key,val) {
		console.log(key,val)
		var myChart = echarts.init(document.getElementById('placeholder'), 'macarons');
	
		var option = {
			tooltip: {
				trigger: 'axis',
				axisPointer: { // 坐标轴指示器，坐标轴触发有效
					type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend: {
				data: ['业务量']
			},
			grid: {
				left: '3%',
				right: '4%',
				bottom: '3%',
				containLabel: true
			},
			xAxis: {
				type: 'value',

			},
			yAxis: {
				type: 'category',
				data: key,
                axisLabel: {
                    interval: 0,
					rotate:15,
                },
			},
			series: [{
					name: '已评价',
					type: 'bar',
					stack: '总量',
					 barMaxWidth: '40',
					label: {
						normal: {
							show: true,
							position: 'insideLeft',
						}
					},
					data: val
				}
			]
		};
		myChart.setOption(option);
	}
	
	
	function bedoWin(starttime,endtime,orgid){
		$(".bedoWin").empty()
		$.ajax({
			url:"${ctx}/statistics/queryWorkState.do",
			data:{starttime:starttime,orgid:orgid,endtime:endtime},
			type:"get",
			success:function(res){
				console.log(res)
					for(var i=0;i<res.length;i++){
						if(res[i]["model"]){
							if(res[i]["name"]==1){
								$(".bedoWin").append("<div class='bedoWinItem yellow'><p>"+res[i]["winname"]+"</p></div>")
							}else{
								$(".bedoWin").append("<div class='bedoWinItem red'><p>"+res[i]["winname"]+"</p></div>")
							}
						}else{
							$(".bedoWin").append("<div class='bedoWinItem'><p>"+res[i]["winname"]+"</p></div>")
						}
					}
			}
		})
		
		
	}
	function pro(){
		table = $('#inch_dataTable').DataTable({
			 "processing": false,
			"serverSide": true,
			"searching": false,
			"ordering": false,
            "destroy": true,
			"paging": true,
			"destroy": true,
			"dom": '<"toolbar">frtip',
			"ajax": {
				"url": "${ctx}/statistics/queryByPerson.do?starttime=" + $("#starttime").val() + "&endtime=" + $("#endtime").val()+"&orgid=" + $("#orgid").val(),
				"error": function(response) {
					checkpermision(response);
				}
			},
			"columns": [{
				"data": "cname",
                "defaultContent":"",
				"title": "姓名"
			},{
				"data": "satisfy2",
                "defaultContent":"",
				"title": "非常满意"
			},{
                "data": "satisfy1",
                "defaultContent":"",
                "title": "满意"
            },{
                "data": "satisfy",
                "defaultContent":"",
                "title": "基本满意"
            }, {
				"data": "basic",
                "defaultContent":"",
				"title": "不满意"
			}, {
				"data": "notsatisfy",
                "defaultContent":"",
				"title": "非常不满意",
			},{
				"data": "no",
                "defaultContent":"",
				"title": "未评价"
			}, {
				"data": "cnt",
                "defaultContent":"",
				"title": "满意率"
			}],
			"columnDefs": [{
                targets: 7,
                render: function(a, b, c, d) {
					console.log(a,b,c,d,"total = "+c.satisfy + c.basic + c.notsatisfy + c.no);
                    var html = ((parseInt(c.satisfy2)+parseInt(c.satisfy1)+parseInt(c.satisfy)) / (parseInt(c.satisfy2)+parseInt(c.satisfy1)+ parseInt(c.satisfy)+ parseInt(c.basic) + parseInt(c.notsatisfy) + parseInt(c.no)) * 100).toFixed(2);

                    return html + "%";
                }
            }]
		});
		
	}
	function banjian(){
		table1 = $('#inch_ban').DataTable({
			"processing": false,
			"serverSide": true,
			"searching": false,
			"ordering": false,
            "destroy": true,
			"dom": '<"toolbar">frtip',
			"ajax": {
				"url": "${ctx}/statistics/getEvaluate.do?starttime=" + $("#starttime").val() + "&endtime=" + $("#endtime").val()+"&orgid=" + $("#orgid").val()+"&score="+$("#score").val(),
				"error": function(response) {
					checkpermision(response);
				}
			},
			"columns": [{
				"data": "winname",
                "defaultContent":"",
				"title": "窗口号"
			},{
				"data": "num",
                "defaultContent":"",
				"title": "业务号"
			}, {
				"data": "name",
                "defaultContent":"",
				"title": "客户姓名"
			}, {
				"data": "cardno",
                "defaultContent":"",
				"title": "手机号码",
			},{
				"data": "star",
                "defaultContent":"",
				"title": "评价"
			}, {
				"data": "addtime",
                "defaultContent":"",
				"title": "时间"
			}],
			"columnDefs": [{
				targets: 4,
				render: function(a, b, c, d) {
				    var html ="";
					if (c.star == "不满意"){
//					    html = "<a href='#' onclick='getVideo("+c.guid+")'>不满意</a>";
                        html = "<a href='#' onclick=\"getVideo('"+c.guid+"','"+c.addtime2+"','"+c.id+"')\" style='color:red'>"+c.star+"</a>";
					}else{
                        html = c.star;
					}
					return html;
				}
			}]
		});
		
	}


	function getVideo(guid,addtime,id) {
		console.log("guid = " + guid)
		console.log("addtime = " + addtime)

        ajaxJson("${ctx}/hikvision/downloadVideo.do","guid="+guid +"&time="+addtime+"&id="+id,function(data){

            if(data.success){
//                layer.alert("初始化成功!", {icon: 1});
                console.log("filename = "+data.data)
                    console.log(data.time);
				if (data.data != "" && data.time!=1){
                    layer.msg('下载中...', {
                        time: 15000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        //do something
                        $("#videoModal").modal("show");
                        $("#VLC").attr('target',data.data)
                    });

				}else if (data.data != "" && data.time==1) {
                    $("#videoModal").modal("show");
                    $("#VLC").attr('target',data.data)

				}else{
                    layer.alert("无法获取视频！", {icon: 5});
				}
            }else{
				layer.alert(data.msg, {icon: 5});
            }

        })

    }

</script>
