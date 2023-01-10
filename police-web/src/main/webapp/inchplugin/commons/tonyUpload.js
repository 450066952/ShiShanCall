//var basepicUrl="http://58.211.71.39:9208";
//var suffex="/display-web/UploadImageServlet";

var basepicUrl="";
//var suffex="/police-web/UploadImageServlet";
var suffex="/UploadImageServlet";


function initUploadWithPress(upObject,url,auto,multi,ispress,isthum,maxW,maxH,notkeeppic,callback){
	
	upObject.fileupload({  
        url: basepicUrl+suffex+"?ispress="+ispress+"&thum="+isthum+"&maxW="+maxW+"&maxH="+maxH+"&notkeeppic="+notkeeppic,
        dataType: 'json',
        done: function (e, data) {
        	
	        if (jQuery.isFunction(callback)) {
	            callback(data);
	        }
        },  
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 5, 10);  
            $('#progress .bar').css(  
                'width',  
                progress + '%'  
            );  
        }  
    }); 
}

function initUploadCallBack(upObject,url,callback){
	upObject.fileupload({  
        url: basepicUrl+suffex,
        dataType: 'json',
        done: function (e, data) {
	        if (jQuery.isFunction(callback)) {
	            callback(data);
	        }
        },  
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 5, 10);  
            $('#progress .bar').css(  
                'width',  
                progress + '%'  
            );  
        }  
    }); 
}










function initUploadCallBack2(upObject,url,callback){
	
	upObject.uploadify({
		　'swf': url+'/js/Uploadify-3.2.1/uploadify.swf', 
		  'uploader':'/screen-webn/UploadImageServlet?maxW=1080', 
		　//'queueID' : 'fileQueue', //和存放队列的DIV的id一致  不一致就不显示进度条了 这里故意写的不一致
		　'auto'  : true, //是否自动开始  
		　'multi': false, //是否支持多文件上传  
		  'buttonText': '浏览', //按钮上的文字  
		　'simUploadLimit' : 1, //一次同步上传的文件数目  
		　'fileSizeLimit' : '102400KB',
		 'overrideEvents' : ['onDialogClose','onSelectError' ],  //重写事件  是为了错误的时候弹出的英文设置为中文
		  'queueSizeLimit' : 4, //队列中同时存在的文件个数限制  
		　'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.mp4;*.xls;*.xlsx',//允许的格式
		  'fileTypeDesc': '支持格式:jpg/gif/jpeg/png/bmp/mp4/xls/xlsx.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
		　'onUploadSuccess': function ( fileObj, data, response) { 
				
				var obj = jQuery.parseJSON(data);
				
		        // 回调函数
		        if (jQuery.isFunction(callback)) {
		            callback(obj);
		        }
				
		},
		 
		'onUploadError': function(file, errorCode, errorMsg, errorString) {  
			//alert(file.name);　		
			alert("文件:" + fileObj.name + "上传失败");  
		　},
		
		'onSelectError' : function(file, errorCode, errorMsg) {  
					        var msgText = "上传失败\n";  
					        switch (errorCode) {  
					            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:  
					                //this.queueData.errorMsg = "每次最多上传 " + this.settings.queueSizeLimit + "个文件";  
					                msgText += "每次最多上传 " + this.settings.queueSizeLimit + "个文件";  
					                break;  
					            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:  
					                msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";  
					                break;  
					            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:  
					                msgText += "文件大小为0";  
					                break;  
					            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:  
					                msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;  
					                break;  
					            default:  
					                msgText += "错误代码：" + errorCode + "\n" + errorMsg;  
					        }  
					        alert(msgText);  
					    }
	　});
}

/*
function initUploadCallBack(upObject,url,callback){
	
	upObject.uploadify({
		　'swf': url+'/js/Uploadify-3.2.1/uploadify.swf', 
		  'uploader':basepicUrl+suffex, 
		　//'queueID' : 'fileQueue', //和存放队列的DIV的id一致  不一致就不显示进度条了 这里故意写的不一致
		　'auto'  : true, //是否自动开始  
		　'multi': false, //是否支持多文件上传  
		  'buttonText': '浏览', //按钮上的文字  
		　'simUploadLimit' : 1, //一次同步上传的文件数目  
		　'fileSizeLimit' : '102400KB',
		 'overrideEvents' : ['onDialogClose','onSelectError' ],  //重写事件  是为了错误的时候弹出的英文设置为中文
		  'queueSizeLimit' : 4, //队列中同时存在的文件个数限制  
		　'fileTypeExts': '*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.mp4;*.xls;*.xlsx',//允许的格式
		  'fileTypeDesc': '支持格式:jpg/gif/jpeg/png/bmp/mp4/xls/xlsx.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
		　'onUploadSuccess': function ( fileObj, data, response) { 
				
				var obj = jQuery.parseJSON(data);
				
		        // 回调函数
		        if (jQuery.isFunction(callback)) {
		            callback(obj);
		        }
				
		},
		 
		'onUploadError': function(file, errorCode, errorMsg, errorString) {  
			//alert(file.name);　		
			alert("文件:" + fileObj.name + "上传失败");  
		　},
		
		'onSelectError' : function(file, errorCode, errorMsg) {  
					        var msgText = "上传失败\n";  
					        switch (errorCode) {  
					            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:  
					                //this.queueData.errorMsg = "每次最多上传 " + this.settings.queueSizeLimit + "个文件";  
					                msgText += "每次最多上传 " + this.settings.queueSizeLimit + "个文件";  
					                break;  
					            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:  
					                msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";  
					                break;  
					            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:  
					                msgText += "文件大小为0";  
					                break;  
					            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:  
					                msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;  
					                break;  
					            default:  
					                msgText += "错误代码：" + errorCode + "\n" + errorMsg;  
					        }  
					        alert(msgText);  
					    }
	　});
}
*/
