
function checkselect() {
	
	 var isChecked = $(this).prop("checked");
	 $("input[name='checkList']").prop("checked", isChecked);
}

function toSearch(url) {
    var param = serializeStr($("#searchForm"));
    table.ajax.url(url+"?"+ param).load();
}


/**
 * 删除数据
 * 
 * @param name
 */
function delbytype(url,guid,type) {
	layer.confirm('确认要操作吗?', {
		icon : 3,
		title : '提示'
	}, function(index) {
		
		$.ajax({
            url: url,
            data: {
                "id": guid,
                "stype":type
            },
            success: function(data) {
            	table.ajax.reload(null, false);
            }
        });
		
		layer.close(index);
	});
	

}

/**
 * 删除数据
 * 
 * @param name
 */
function delbycallback(url,guid,callback) {
	
	layer.confirm('确认要操作吗?', {
		icon : 3,
		title : '提示'
	}, function(index) {
		
		$.ajax({
            url: url,
            data: {
                "id": guid
            },
            success: function(data) {
                if (jQuery.isFunction(callback)) {
                    callback(data);
                }
            }
        });
		
		layer.close(index);
	});
}

function del(url) {
    var str = '';
    $("input[name='checkList']:checked").each(function(i, o) {
        str += $(this).val();
        str += ",";
    });

    if (str.length > 0) {
        str = str.substr(0, str.length - 1);
    } else {
    	layer.alert("至少选择一条记录操作！", {icon: 5});
        return;
    }

    layer.confirm('确认要操作吗?', {
		icon : 3,
		title : '提示'
	}, function(index) {
		
		$.ajax({
            url: url,
            data: {
                "id": str
            },
            success: function(data) {
            	table.ajax.reload(null, false);
            }
        });
		
		layer.close(index);
	});
}

function editformbyname(form, win, url, callback) {
	
    var data = {};

    ajaxJson(url, data,
    function(result) {
    	
    	if(!result.success){
    		layer.alert(result.msg, {icon: 0});
    		return;
    	}

    	form.setForm(result.data);
    	
        win.modal("show");

        if (jQuery.isFunction(callback)) {
            callback(result);
        }
    });
}

function refreshdata() {
    table.ajax.reload();
}

function saveform(form, url, callback) {
	
		try{
			 form.data('bootstrapValidator').validate();  
		     if(!form.data('bootstrapValidator').isValid()){  
		         return ;  
		     }  
		}catch(e){
			
		}

        form.ajaxSubmit({
            type: "post",
            dataType: "json",
            url: url,
            success: function(data) {
            	
            	if(data.success){
            		layer.alert("保存成功!", {icon: 1});
            	}else{
            		layer.alert(data.msg, {icon: 5});
            	}
                if (jQuery.isFunction(callback)) {
                    callback(data);
                }
            },
            error: function(response, textStatus, errorThrown) {
            	
            	checkpermision(response);
            }
        });
}

function ajaxJson(url, option, callback) {
    $.ajax(url, {
        type: 'post',
        dataType: 'json',
        data: option,
        success: function(data) {
            if ($.isFunction(callback)) {
                callback(data);
            }
        },
        error: function(response, textStatus, errorThrown) {
        	checkpermision(response); 
        },
        complete: function() {

}
    });
}

function checkpermision(response) {
 	var data = $.parseJSON(response.responseText);
 	
 	try{
 	 	if(response.status=="504"){
 			if(data.logoutFlag){
 					layer.confirm('登录超时,点击确定重新登录.', {
 					  btn: ['确定'] //按钮
 					}, function(){
 						window.top.location= "/login.shtml";
 					});
 					
 					return false;
    		}
 	 	}
 	 	else if(response.status=="403"){
 	 		layer.alert("系统异常，没有权限访问，请联系管理员.", {icon: 5});
 	 		return false;
 	 	}else{
 	 		layer.alert("系统异常，请联系管理员.", {icon: 5});
 	 	}
 	 	
 	}catch(e){
 		layer.alert("请求异常！", {icon: 5});
 	}
}

function getClassInfo(list){
	
	var html="";
	
	if(list!=null&&list.length>0){
		
		for (var item = 0; item < list.length; item++) {
			var itemData = list[item];
			html+=itemData.name+"、";
			
			if(item>5){
				break;
			}
		}
     }
     
	return html;
}
