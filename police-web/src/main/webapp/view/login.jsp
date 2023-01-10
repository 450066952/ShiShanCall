<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<html lang="en" data-ng-app="app" class="ng-scope">
<head>

    <meta charset="utf-8">
    <title>狮山横塘街道智慧服务系统</title>
    <meta name="description"
          content="app, web app, responsive, responsive layout, admin, admin panel, admin dashboard, flat, flat ui, ui kit, AngularJS, ui route, charts, widgets, components">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%=path%>/inchplugin/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/inchplugin/css/animate.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/inchplugin/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/inchplugin/css/simple-line-icons.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/inchplugin/css/font.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/inchplugin/css/app.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/inchplugin/css/inch-style.css?v=201702" type="text/css"/>

    <!-- jQuery -->
    <script src="<%=path%>/inchplugin/vendor/jquery/jquery.min.js"></script>
    <script src="<%=path%>/inchplugin/layer-v3.0.3/layer.js"></script>
    <script src="<%=path%>/inchplugin/vendor/jquery/bootstrap.js"></script>
    <script src="<%=path%>/inchplugin/vendor/jquery/validator/bootstrapValidator.min.js"></script>
    <script src="<%=path%>/inchplugin/commons/jquery.form.js"></script>
</head>
<body style="" class="ng-scope smart">
<!-- uiView:  -->
<div class="app ng-scope app-header-fixed" id="app">
    <!-- uiView:  -->
    <div class="fade-in-right-big smooth ng-scope">
        <div class="container w-xxl w-auto-xs ng-scope">
            <a href="" class="navbar-brand block m-t ng-binding"><font size="5">「 智慧服务系统 」</font></a>
            <div class="m-b-lg">
                <div class="wrapper text-center">
                    Sign in - 登录
                </div>
                <form id="loginForm">
                    <div style="margin:23px 0 30px 0;">
                        <div>
                            <input name="username" type="text" placeholder="用户名" required class="inch">
                        </div>
                        <div style="height: 20px"></div>
                        <div>
                            <input name="password" type="password" placeholder="密码" required class="inch">
                        </div>

                    </div>
                    <span id="btnLogin" class="btn btn-lg btn-primary btn-block">登&nbsp;&nbsp;录</span>

                </form>
            </div>
            <p class="ng-scope" align="center">
                <small class="text-muted">Copyright © 2018 INCH Inc. All Rights Reserved</small>
            </p>
        </div>
    </div>
</div>


<script type="text/javascript">

    function toLogin() {

        $("#loginForm").ajaxSubmit({
            type: "post",
            dataType: "json",
            url: "toLogin.do",
            success: function (data) {
                debugger;
                if (data.success) {
                    window.location = "main.shtml";
                } else {
                    layer.alert(data.msg, {icon: 5});
                }
            },
            error: function (response, textStatus, errorThrown) {
                layer.alert("用户名或密码不正确。", {icon: 5});
            }
        });
    }

    $(function () {
        $("#btnLogin").click(toLogin);

        $(document).keyup(function (event) {
            if (event.keyCode == 13) {
                $("#btnLogin").trigger("click");
            }
        });
    })

</script>


</body>
</html>
