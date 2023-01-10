<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//从单点登录中拿到警号
String code = request.getRemoteUser();
%>

<html>
  <head>  
  
  <script src="${ctx}/inchplugin/vendor/jquery/jquery.min.js"></script>
  
	<script type="text/javascript">

	//进入后台程序  业务系统自行处理

	$.ajax({
		url: '<%=basePath%>/sso/toLogin.do',
		type: "GET",
		contentType: "application/json;charset=utf-8",
		data: {userName:'<%=code%>'},
		success:function(data){
			console.log("2222")
			if(data.success == true){
				console.log("1111")
				window.location.href = "<%=basePath%>main.shtml";
			}
			
		}
	});


	</script>
  </head>
  
  <body>
  
  
  </body>
</html>
