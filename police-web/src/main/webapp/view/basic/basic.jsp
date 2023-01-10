<%@ page language="java"  pageEncoding="UTF-8"%>
<div class="app-content-body fade-in-up ng-scope" ui-view="">
	<!-- uiView:  -->

	<div class="bg-light lter b-b wrapper-md ng-scope">
		<h1 class="m-n font-thin h3">基本信息</h1>
	</div>
	<div class="wrapper-md ng-scope">
		<div  class="ng-scope">
			<div class="row">
				<div class="col-sm-12">
					<form name="editForm" id="editForm" >
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="form-group">
									<div class="inch-group">
										<label>单位（中文）：</label>
										<input type="hidden" type="text" name="guid" id="guid" value="${info.guid}">
										<input type="text" class="inch" name="name_cn"   value="${info.name_cn}" >
									</div>
								</div>
							</div>

							<div class="panel-body">
								<div class="form-group">
									<div class="inch-group">
										<label>单位（英文）：</label>
										<input type="text" class="inch"  name="name_en" value="${info.name_en}">
									</div>
								</div>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<div class="inch-group">
										<label>联系电话：</label>
										<textarea class="form-control inch"  rows="2" id="tel" name="tel">${info.tel}</textarea>
									</div>
								</div>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<div class="inch-group">
										<label>欢迎语：</label>
										<textarea class="form-control inch"  rows="2" id="welcome" name="welcome">${info.welcome}</textarea>
									</div>
								</div>
							</div>

							<div class="panel-body">
								<div class="form-group">
									<div class="inch-group">
										<label>二维码：</label>
										<span style="display: inline-block;margin-left:10px;">
											<img id="logopic" alt="" src="${info.qrcode}"  height="200" width="300">
											<input name="qrcode"  id="qrcode"  type="text" style="display:none" value="${info.qrcode}" />
										</span>
										<span class="btn btn-success fileinput-button">
									       <i class="glyphicon glyphicon-plus"></i>
									        <span>上传</span>
									        <input id="fileupload" type="file" name="fileupload" >
									    </span>
									</div>
								</div>
							</div>

							<footer class="panel-footer text-right bg-light lter">
								<button type="button" class="btn btn-primary" id="save">保存</button>
							</footer>
						</div>
					</form>
				</div>

			</div>

		</div>
	</div>

	<!-- uiView:  -->
</div>

<script type="text/javascript">
    $(document).ready(function() {

        initUploadWithPress($("#fileupload"),'${ctx}',true,false,1,'',321,406,'1',function(data){
            $("#qrcode").val(data.result.url);
            $("#logopic").attr("src",data.result.url);
        })

        $("#save").click(save);
        $('#editForm').bootstrapValidator();
    });

    function save() {
        saveform($("#editForm"), '${ctx}/basic/save.do', function(data) {

            if(data.success){
                $("#guid").val(data.guid);
            }

        });
    }

</script>
