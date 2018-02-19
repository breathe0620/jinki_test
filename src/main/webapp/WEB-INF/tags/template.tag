<%@tag description="base Page template" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="title" %>
<%@attribute name="openNavClass" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="javascriptSrc" fragment="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>블렌딩 운영 서비스</title>

    <!-- Bootstrap Core CSS -->
    <link href="/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- sb-admin CSS -->
    <link href="/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="/vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- font-awesome Fonts -->
    <link href="/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- select2 CSS -->
    <link href="/vendor/select2-master/select2.css" rel="stylesheet">

    <!-- Parsley CSS -->
    <link href="/vendor/Parsley.js-master/src/parsley.css" rel="stylesheet">

    <!-- datepicker CSS -->
    <link href="/vendor/bootstrap-datepicker-master/bootstrap-datepicker.min.css" rel="stylesheet">

    <!-- lightbox CSS -->
    <link href="/vendor/lightbox-master/ekko-lightbox.min.css" rel="stylesheet">

    <!-- Common CSSS -->
    <link href="/css/common.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/css/custom.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col" id="leftSide">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="/" class="site_title"><span>Vlending CMS</span></a>
                </div>

                <div class="clearfix"></div>
                <br/>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <h3>Mubeat</h3>
                        <ul class="nav side-menu">
                            <li>
                                <a href="/"><i class="fa fa-dashboard fa-fw"></i> 대시보드 </a>
                            </li>
                            <li><a><i class="fa fa-bar-chart-o fa-fw"></i> 메타 관리 <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li>
                                        <a href="/meta/clip">클립 메타 관리</a>
                                    </li>
                                    <li>
                                        <a href="/meta/artist">아티스트 메타 관리</a>
                                    </li>
                                    <li>
                                        <a href="/meta/program">프로그램 메타 관리</a>
                                    </li>
                                    <li>
                                        <a href="/meta/album">앨범 메타 관리</a>
                                    </li>
                                    <li>
                                        <a href="/meta/song">곡 메타 관리</a>
                                    </li>
                                    <li>
                                        <a href="/meta/acquisition">방송 정보 입수</a>
                                    </li>
                                    <li>
                                        <a href="/meta/acquisition/state">방송 정보 입수 상태</a>
                                    </li>
                                    <li>
                                        <a href="/meta/archive">아카이브 영상 입수</a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="#"><i class="fa fa-home"></i> 메인 관리<span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li>
                                        <a href="/recommend/home">추천 관리</a>
                                    </li>
                                    <li>
                                        <a href="/recommend/theme">테마 관리</a>
                                    </li>
                                    <li>
                                        <a href="/recommend/weekprogram">프로그램 관리</a>
                                    </li>
                                </ul>
                            </li>
                            <li><a><i class="fa fa-volume-up"></i>  서비스 관리 <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <li>
                                        <a href="/service/notices">공지사항 관리</a>
                                    </li>
                                    <li>
                                        <a href="/service/faqs">FAQ 관리</a>
                                    </li>
                                </ul>
                            </li>
                            <li><a><i class="fa fa-user fa-fw"></i> 회원 관리 <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <c:if test="${cookie.TYPE.value == 'S'}">
                                        <li>
                                            <a href="/manager/user">회원 관리</a>
                                        </li>
                                        <%--<li>--%>
                                            <%--<a href="#" readonly="">탈퇴 관리</a>--%>
                                        <%--</li>--%>
                                        <li>
                                            <a href="/manager/admin">관리자 관리</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </li>
                            <li><a><i class="fa fa-archive fa-fw"></i> 아이템 관리 <span class="fa fa-chevron-down"></span></a>
                                <ul class="nav child_menu">
                                    <c:if test="${cookie.TYPE.value == 'S'}">
                                        <%--<li>--%>
                                            <%--<a href="/manager/versions">버전 관리</a>--%>
                                        <%--</li>--%>
                                        <li>
                                            <a href="/manager/admin/excels">Excel 관리</a>
                                        </li>
                                        <%--<li>--%>
                                            <%--<a href="/manager/admin/es">Elastic Search</a>--%>
                                        <%--</li>--%>
                                    </c:if>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- /sidebar menu -->
            </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li>
                                    <a href="/manager/admin/profile"><i class="fa fa-user fa-fw"></i> 프로필</a>
                                </li>
                                <%--<li>--%>
                                    <%--<a href="#"><i class="fa fa-gear fa-fw"></i> 환경설정</a>--%>
                                <%--</li>--%>
                                <li class="divider"></li>
                                <li>
                                    <a href="/signout"><i class="fa fa-sign-out fa-fw"></i> 로그아웃</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
            <div class="container-content">
                <jsp:doBody/>
            </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div class="pull-right">
                Mubeat CMS by <a href="http://www.vlending.co.kr/" target="_blank">Vlending</a>
            </div>
            <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
    </div>
</div>

<script src="/vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="/vendor/metisMenu/metisMenu.min.js"></script>

<!-- Custom JS -->
<script href="/js/sb-admin-2.js" rel="stylesheet"></script>

<!-- Morris Charts JavaScript -->
<script src="/vendor/raphael/raphael.min.js"></script>
<script src="/vendor/morrisjs/morris.min.js"></script>
<%--<script src="/data/morris-data.js"></script>--%>

<%-- moment javaScript--%>
<script src="/vendor/moment/min/moment.min.js"></script>
<script src="/vendor/moment/min/moment-with-locales.min.js"></script>

<%-- datepicker javaScript--%>
<script src="/vendor/bootstrap-datepicker-master/bootstrap-datepicker.min.js"></script>
<script src="/vendor/bootstrap-datepicker-master/bootstrap-datepicker.ko.min.js"></script

<%-- lightbox javaScript--%>
<script src="/vendor/lightbox-master/ekko-lightbox.min.js"></script>

<%-- select2 javaScript--%>
<script src="/vendor/select2-master/select2.min.js"></script>

<%-- Parsley javaScript--%>
<script src="/vendor/Parsley.js-master/parsley.min.js"></script>
<script src="/vendor/Parsley.js-master/ko.js"></script>

<%-- Blcok ui javaScript--%>
<script src="/vendor/blockui-master/jquery.blockUI.js"></script>

<%--<!-- Custom Theme JavaScript -->--%>
<%--<script src="/js/sb-admin-2.js"></script>--%>

<!-- Custom Theme JavaScript -->
<script src="/js/common.js"></script>

<!-- Custom JavaScript -->
<script src="/js/custom.js"></script>

<jsp:invoke fragment="javascriptSrc"/>
    <script type="text/javascript">
        $(document).ready(function(){
        });
    </script>
</body>
</html>
