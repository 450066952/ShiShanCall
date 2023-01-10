<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>狮山横塘街道智慧服务系统</title>
    <meta name="description"
          content="app, web app, responsive, responsive layout, admin, admin panel, admin dashboard, flat, flat ui, ui kit, AngularJS, ui route, charts, widgets, components"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1"/>

    <%@include file="/view/resource.jsp" %>

    <script type="text/javascript">
        var url = "";
    </script>

</head>
<body>

<div class="app app-header-fixed" id="app">
    <!-- navbar -->
    <div class="app-header navbar">
        <!-- navbar header -->
        <div id="navbar-header-my" class="navbar-header bg-dark">
            <button class="pull-right visible-xs dk" data-toggle="class:show"
                    data-target=".navbar-collapse">
                <i class="glyphicon glyphicon-cog"></i>
            </button>
            <button class="pull-right visible-xs" data-toggle="class:off-screen"
                    data-target=".app-aside" ui-scroll="app">
                <i class="glyphicon glyphicon-align-justify"></i>
            </button>
            <!-- brand -->
            <a href="#" class="navbar-brand text-lt"> <i
                    class="icon-graduation"></i> <img src="inchplugin/img/logo.png" alt="."
                                                      class="hide"> <span class="hidden-folded m-l-xs">SERVICE</span>
            </a>
            <!-- / brand -->
        </div>
        <!-- / navbar header -->

        <!-- navbar collapse -->
        <div id="collapse-my"
             class="collapse pos-rlt navbar-collapse box-shadow bg-white-only">
            <!-- buttons -->
            <div class="nav navbar-nav hidden-xs">
                <a href="#" class="btn no-shadow navbar-btn"
                   data-toggle="class:app-aside-folded" data-target=".app"> <i
                        class="fa fa-dedent fa-fw text"></i> <i
                        class="fa fa-indent fa-fw text-active"></i>
                </a> <a href class="btn no-shadow navbar-btn" data-toggle="class:show"
                        data-target="#aside-user"> <i class="icon-user fa-fw"></i>
            </a>
            </div>
            <!-- / buttons -->


            <!-- nabar right -->
            <ul class="nav navbar-nav navbar-right">

                <li class="hidden-xs"><a ui-fullscreen="" class=""
                                         onclick="javascript:fulls();"> <i
                        class="fa fa-expand fa-fw text"></i> <i
                        class="fa fa-compress fa-fw text-active"></i>
                </a></li>

                <li class="hidden-xs"><a class="" data-toggle="class:active"
                                         data-target=".settings"> <i class="fa  fa-gear text"></i> <i
                        class="fa  fa-gear text-active"></i>
                </a></li>

                <li class="dropdown"><a href="#" data-toggle="dropdown"
                                        class="dropdown-toggle clear"> <span
                        class="thumb-sm avatar pull-right m-t-n-sm m-b-n-sm m-l-sm">
								<img src="inchplugin/img/a0.jpg" alt="..."> <i
                        class="on md b-white bottom"></i>
						</span> <span class="hidden-sm hidden-md">${user.name}</span> <b class="caret"></b>
                </a> <!-- dropdown -->
                    <ul class="dropdown-menu animated fadeInRight w">
                        <li><a href="javascript:modifypwd();">修改密码</a></li>
                        <li><a ui-sref="app.docs"> <span
                                class="label bg-info pull-right">new</span> 帮助
                        </a></li>
                        <li class="divider"></li>
                        <li><a href="${ctx}/logout.shtml">退出登录</a></li>
                    </ul> <!-- / dropdown --></li>
            </ul>
            <!-- / navbar right -->

        </div>
        <!-- / navbar collapse -->
    </div>
    <!-- / navbar -->

    <!-- menu -->
    <div id="app-aside-my" class="app-aside hidden-xs bg-dark">
        <div class="aside-wrap">
            <div class="navi-wrap">
                <!-- user -->
                <div class="clearfix hidden-xs text-center hide" id="aside-user">
                    <div class="dropdown wrapper">
                        <a ui-sref="app.page.profile"> <span
                                class="thumb-lg w-auto-folded avatar m-t-sm"> <img
                                src="inchplugin/img/a0.jpg" class="img-full" alt="...">
							</span>
                        </a> <a href="#" data-toggle="dropdown"
                                class="dropdown-toggle hidden-folded"> <span class="clear">
									<span class="block m-t-sm"> <strong
                                            class="font-bold text-lt">${user.name}</strong> <b class="caret"></b>
								</span> <!-- <span class="text-muted text-xs block">超级管理员</span> -->
							</span>
                    </a>
                        <!-- dropdown -->
                        <ul class="dropdown-menu animated fadeInRight w hidden-folded">
                            <li class="wrapper b-b m-b-sm bg-info m-t-n-xs"><span
                                    class="arrow top hidden-folded arrow-info"></span>
                                <div>
                                    <p>设置</p>
                                </div>
                                <progressbar value="60" type="white"
                                             class="progress-xs m-b-none dker"></progressbar>
                            </li>
                            <!-- <li><a href>设置</a></li> -->
                            <li><a href="javascript:modifypwd();">修改密码</a></li>
                            <!-- <li><a href> <span class="badge bg-danger pull-right">2</span>
                                    未读通知
                            </a></li> -->
                            <li class="divider"></li>
                            <li><a href="${ctx}/logout.shtml">退出登录</a></li>
                        </ul>
                        <!-- / dropdown -->
                    </div>
                    <div class="line dk hidden-folded"></div>
                </div>
                <!-- / user -->

                <!-- 左侧树形菜单 -->
                <nav ui-nav class="navi">
                    <ul class="nav">


                        <c:forEach var="item" items="${menuList}" varStatus="s1">

                            <li class="hidden-folded padder m-t m-b-sm text-muted text-xs">
                                <span translate="aside.nav.HEADER">${item.name}</span>
                            </li>

                            <c:forEach var="item2" items="${item.childList}" varStatus="s2">
                                <c:choose>
                                    <c:when test="${s1.count==1&&s2.count==1}">
                                        <li class="active">
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                    </c:otherwise>
                                </c:choose>

                                <a href class="auto" data="${item2.url}">
										<span class="pull-right text-muted"> 
											<i class="fa fa-fw fa-angle-right text"></i> 
											<i class="fa fa-fw fa-angle-down text-active"></i>
										</span>

                                    <c:choose>
                                        <c:when test="${s1.count==1&&s2.count==1}">
                                            <i class="${item2.icon} text-success"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="${item2.icon}"></i>
                                        </c:otherwise>
                                    </c:choose>
                                    <span translate="aside.nav.DASHBOARD">${item2.name}</span>
                                </a>
                                <ul class="nav nav-sub dk">
                                    <c:forEach var="node" items="${item2.childList}" varStatus="s3">
                                        <c:choose>
                                            <c:when test="${s1.count==1&&s2.count==1&&s3.count==1}">
                                                <li class="active">
                                                    <a href="javascript:getAjaxHtm('${ctx}${node.url}')"
                                                       data=""><span>${node.name}</span></a>
                                                    <!--<a href="${ctx}${node.url}" target="_blank" data=""><span>${node.name}</span></a>-->
                                                </li>
                                                <script type="text/javascript">url = '${ctx}${node.url}';</script>
                                            </c:when>
                                            <c:otherwise>
                                                <li>
                                                    <!--<a href="${ctx}${node.url}" target="_blank" data=""><span>${node.name}</span></a>-->

                                                    <a href="javascript:getAjaxHtm('${ctx}${node.url}')"
                                                       data=""><span>${node.name}</span></a>

                                                </li>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </ul>
                                </li>

                            </c:forEach>

                            <li class="line dk"></li>

                        </c:forEach>


                    </ul>
                </nav>
                <!-- nav -->

                <!-- aside footer -->
                <div class="wrapper m-t">
                    <div class="text-center-folded">
                        <span class="pull-right pull-none-folded">100%</span> <span
                            class="hidden-folded" translate="aside.MILESTONE">设备运行</span>
                    </div>
                    <div class="progress progress-xxs m-t-sm dk">
                        <div class="progress-bar progress-bar-info" style="width: 100%;">
                        </div>
                    </div>
                    <div class="text-center-folded">
                        <span class="pull-right pull-none-folded">15%</span> <span
                            class="hidden-folded" translate="aside.RELEASE">空间大小</span>
                    </div>
                    <div class="progress progress-xxs m-t-sm dk">
                        <div class="progress-bar progress-bar-primary"
                             style="width: 15%;"></div>
                    </div>
                </div>
                <!-- / aside footer -->
            </div>
        </div>
    </div>
    <!-- / menu -->

    <!-- content -->
    <div class="app-content" id="maincontent">
    </div>
    <!-- /content -->


    <!-- footer -->
    <div class="app-footer wrapper b-t bg-light">
			<span class="pull-right">1.0.0 <a href="#app"
                                              class="m-l-sm text-muted"><i class="fa fa-long-arrow-up"></i></a></span>
        &copy; 2018 Copyright. inch inc. <a href="http://www.incich.com/"
                                            title="紫橙科技" target="_blank">紫橙科技</a>
    </div>
    <!-- / footer -->


    <!-- settings -->
    <div data-ng-include=" 'tpl/blocks/settings.html' "
         class="settings panel panel-default ng-scope">
        <!-- settings -->
        <div class="panel-heading ng-scope">设置</div>
        <div class="panel-body ng-scope">


            <div class="m-b-sm">
                <label class="i-switch bg-info pull-right"> <input
                        type="checkbox" id="asideFixed" checked
                        class="ng-valid ng-dirty ng-valid-parse ng-touched"> <i></i>
                </label> 菜单固定
            </div>


            <div class="m-b-sm">
                <label class="i-switch bg-info pull-right"> <input
                        type="checkbox" id="asidefolded"
                        class="ng-pristine ng-untouched ng-valid"> <i></i>
                </label> 菜单折叠
            </div>

            <div class="m-b-sm">
                <label class="i-switch bg-info pull-right"> <input
                        type="checkbox" id="asidedock"
                        class="ng-pristine ng-untouched ng-valid"> <i></i>
                </label> 菜单样式
            </div>

        </div>

        <script>

            var headbgcur = "bg-dark";
            var collapsecur = "bg-white-only";
            var asidecur = "bg-dark";


            function changetest(headbg, collapsebg, asidebg) {

                if ($('#navbar-header-my').hasClass(headbgcur)) {
                    $("#navbar-header-my").removeClass(headbgcur);
                    headbgcur = headbg;
                }

                if ($('#collapse-my').hasClass(collapsecur)) {
                    $("#collapse-my").removeClass(collapsecur);
                    collapsecur = collapsebg;
                }

                if ($('#app-aside-my').hasClass(asidecur)) {
                    $("#app-aside-my").removeClass(asidecur);
                    asidecur = asidebg;
                }

                $("#navbar-header-my").addClass(headbg);
                $("#collapse-my").addClass(collapsebg);
                $("#app-aside-my").addClass(asidebg);

            }

        </script>

        <div class="wrapper b-t b-light bg-light lter r-b ng-scope">
            <div class="row row-sm">
                <div class="col-xs-6">

                    <label class="i-checks block m-b"> <input type="radio"
                                                              name="a"
                                                              onclick="javascript:changetest('bg-black','bg-white-only','bg-black');"
                                                              value="1" class="ng-pristine ng-untouched ng-valid"> <span
                            class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-black header"></b> <b class="bg-white header"></b>
								<b class="bg-black"></b>
						</span>
                    </label> <label class="i-checks block m-b"> <input type="radio"
                                                                       name="a"
                                                                       onclick="javascript:changetest('bg-dark','bg-white-only','bg-dark');"
                                                                       value="13"
                                                                       class="ng-pristine ng-untouched ng-valid"> <span
                        class="block bg-light clearfix pos-rlt"> <span
                        class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-dark header"></b> <b class="bg-white header"></b> <b
                        class="bg-dark"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-white-only','bg-white-only','bg-black');"
                                                                   value="2" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-white header"></b> <b class="bg-white header"></b>
								<b class="bg-black"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-primary','bg-white-only','bg-dark');"
                                                                   value="3" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-primary header"></b> <b class="bg-white header"></b>
								<b class="bg-dark"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-info','bg-white-only','bg-black');"
                                                                   value="4" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-info header"></b> <b class="bg-white header"></b> <b
                            class="bg-black"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-success','bg-white-only','bg-dark');"
                                                                   value="5" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-success header"></b> <b class="bg-white header"></b>
								<b class="bg-dark"></b>
						</span>
                </label> <label class="i-checks block"> <input type="radio"
                                                               onclick="javascript:changetest('bg-danger','bg-white-only','bg-dark');"
                                                               value="6" class="ng-pristine ng-untouched ng-valid"
                                                               name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-danger header"></b> <b class="bg-white header"></b>
								<b class="bg-dark"></b>
						</span>
                </label>
                </div>
                <div class="col-xs-6">
                    <label class="i-checks block m-b"> <input type="radio"
                                                              onclick="javascript:changetest('bg-black','bg-black','bg-white b-r');"
                                                              value="7" class="ng-pristine ng-untouched ng-valid"
                                                              name="a">
                        <span class="block bg-light clearfix pos-rlt"> <span
                                class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-black header"></b> <b class="bg-black header"></b>
								<b class="bg-white"></b>
						</span>
                    </label> <label class="i-checks block m-b"> <input type="radio"
                                                                       name="a"
                                                                       onclick="javascript:changetest('bg-dark','bg-dark','bg-light');"
                                                                       value="14"
                                                                       class="ng-pristine ng-untouched ng-valid"> <span
                        class="block bg-light clearfix pos-rlt"> <span
                        class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-dark header"></b> <b class="bg-dark header"></b> <b
                        class="bg-light"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-info dker','bg-info dker','bg-light dker b-r');"
                                                                   value="8" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-info dker header"></b> <b
                            class="bg-info dker header"></b> <b class="bg-light dker"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-primary','bg-primary','bg-dark');"
                                                                   value="9" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-primary header"></b> <b class="bg-primary header"></b>
								<b class="bg-dark"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-info dker','bg-info dk','bg-black');"
                                                                   value="10" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-info dker header"></b> <b class="bg-info dk header"></b>
								<b class="bg-black"></b>
						</span>
                </label> <label class="i-checks block m-b"> <input type="radio"
                                                                   onclick="javascript:changetest('bg-success','bg-success','bg-dark');"
                                                                   value="11" class="ng-pristine ng-untouched ng-valid"
                                                                   name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-success header"></b> <b class="bg-success header"></b>
								<b class="bg-dark"></b>
						</span>
                </label> <label class="i-checks block"> <input type="radio"
                                                               onclick="javascript:changetest('bg-danger dker bg-gd','bg-danger dker bg-gd','bg-dark');"
                                                               value="12" class="ng-pristine ng-untouched ng-valid"
                                                               name="a">
                    <span class="block bg-light clearfix pos-rlt"> <span
                            class="active pos-abt w-full h-full bg-black-opacity text-center">
									<i class="glyphicon glyphicon-ok text-white m-t-xs"></i>
							</span> <b class="bg-danger dker header"></b> <b
                            class="bg-danger dker header"></b> <b class="bg-dark"></b>
						</span>
                </label>
                </div>
            </div>
        </div>
        <!-- /settings -->
    </div>

</div>


<!-- 修改密码 -->
<div class="modal fade" id="pwdModal" tabindex="-1" role="dialog" aria-labelledby="pwdModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
            </div>

            <div class="modal-body">

                <form id="pwdForm">

                    <div class="inch-group form-group">
                        <label>原始密码</label>
                        <input type="password" class="inch" id="oldpwd" name="oldpwd" required/>
                    </div>

                    <div class="inch-group form-group">
                        <label>新密码</label>
                        <input type="password" class="inch" id="newpwd" name="newpwd" required/>
                    </div>

                    <div class="inch-group form-group">
                        <label>密码确认</label>
                        <input type="password" class="inch" id="conformpwd" name="conformpwd" required/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save" onclick="javascript:savepwd();">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    +function ($) {
        $(function () {


            // class
            $(document).on('click', '[data-toggle^="class"]', function (e) {
                e && e.preventDefault();
                console.log('abc');
                var $this = $(e.target), $class, $target, $tmp, $classes, $targets;
                !$this.data('toggle') && ($this = $this.closest('[data-toggle^="class"]'));
                $class = $this.data()['toggle'];
                $target = $this.data('target') || $this.attr('href');
                $class && ($tmp = $class.split(':')[1]) && ($classes = $tmp.split(','));
                $target && ($targets = $target.split(','));
                $classes && $classes.length && $.each($targets, function (index, value) {
                    if ($classes[index].indexOf('*') !== -1) {
                        var patt = new RegExp('\\s' +
                            $classes[index].replace(/\*/g, '[A-Za-z0-9-_]+').split(' ').join('\\s|\\s') +
                            '\\s', 'g');
                        $($this).each(function (i, it) {
                            var cn = ' ' + it.className + ' ';
                            while (patt.test(cn)) {
                                cn = cn.replace(patt, ' ');
                            }
                            it.className = $.trim(cn);
                        });
                    }
                    ($targets[index] != '#') && $($targets[index]).toggleClass($classes[index]) || $this.toggleClass($classes[index]);
                });
                $this.toggleClass('active');
            });

            // collapse nav
            $(document).on('click', 'nav a', function (e) {

                var $this = $(e.target), $active;
                $this.is('a') || ($this = $this.closest('a'));

                var durl = $this.attr('data');

                if (durl != "undefined" && durl != "") {
                    getAjaxHtm('${ctx}' + durl);
                }

                $active = $this.parent().siblings(".active");
                $active && $active.toggleClass('active').find('> ul:visible').slideUp(200);
                $active.find('> a>i').toggleClass('text-success'); //add by tony 2017
                ($this.parent().hasClass('active') && $this.next().slideUp(200)) || $this.next().slideDown(200);
                $this.parent().toggleClass('active');
                $this.parent().find('> a>i').toggleClass('text-success'); // add by tony 2017
                $this.next().is('ul') && e.preventDefault();

                setTimeout(function () {
                    $(document).trigger('updateNav');
                }, 300);

            });

            $('#asidefolded').click(function () {
                if ($(this).prop("checked")) {
                    $("#app").addClass("app-aside-folded");
                } else {
                    $("#app").removeClass("app-aside-folded");
                }
            });

            $('#asidedock').click(function () {
                if ($(this).prop("checked")) {
                    $("#app").addClass("app-aside-dock");
                } else {
                    $("#app").removeClass("app-aside-dock");
                }
            });

            $('#asideFixed').click(function () {
                if ($(this).prop("checked")) {
                    $("#app").addClass("app-aside-fixed");
                } else {
                    $("#app").removeClass("app-aside-fixed");
                }
            });

            if (url != "") {
                getAjaxHtm(url);
            }

            $('#pwdForm').bootstrapValidator();

        });
    }(jQuery);


    function fulls() {
        if (screenfull.enabled) {
            screenfull.toggle();
        }
        if (screenfull.isFullscreen) {
            $(".fa-expand").addClass('text-active');
            $(".fa-compress").removeClass('text-active');
        } else {
            $(".fa-expand").removeClass('text-active');
            $(".fa-compress").addClass('text-active');
        }
    }

    function getAjaxHtm(url) {

        if (url == "/display-web") {
            return;
        }

        $.ajax({
            url: url,
            type: 'post',
            dataType: 'html',
            error: function (response) {
                checkpermision(response);
            },
            success: function (data) {
                $("#maincontent").html(data);
            }
        });

        $('body').removeClass("modal-open");
    }

    function modifypwd() {

        $("#pwdForm").resetForm();
        $("#pwdModal").modal("show");
    }

    function savepwd() {
        saveform($("#pwdForm"), 'modifypwd.do', function (data) {
            if (data.success) {
                $("#pwdModal").modal("hide");
            }

        })
    }

</script>
</body>
</html>
